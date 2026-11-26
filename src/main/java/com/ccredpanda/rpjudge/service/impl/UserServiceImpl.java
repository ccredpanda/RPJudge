package com.ccredpanda.rpjudge.service.impl;

import com.ccredpanda.rpjudge.common.MyDuration;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.constant.SolutionStatus;
import com.ccredpanda.rpjudge.constant.UserStatus;
import com.ccredpanda.rpjudge.dto.LoginDto;
import com.ccredpanda.rpjudge.dto.RegisterDto;
import com.ccredpanda.rpjudge.dto.UpdateMyselfDto;
import com.ccredpanda.rpjudge.dto.UserPasswordDto;
import com.ccredpanda.rpjudge.entity.Solution;
import com.ccredpanda.rpjudge.entity.User;
import com.ccredpanda.rpjudge.entity.UserProblemAc;
import com.ccredpanda.rpjudge.exception.BusinessException;
import com.ccredpanda.rpjudge.mapper.SolutionMapper;
import com.ccredpanda.rpjudge.mapper.UserProblemAcMapper;
import com.ccredpanda.rpjudge.mapper.UserMapper;
import com.ccredpanda.rpjudge.service.UserService;
import com.ccredpanda.rpjudge.utils.BCryptUtils;
import com.ccredpanda.rpjudge.utils.JwtUtils;
import com.ccredpanda.rpjudge.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*  服务层用户接口实现类
* */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserProblemAcMapper userProblemAcMapper;

    @Autowired
    private SolutionMapper solutionMapper;
    /*
    *  用户注册
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterVo register(RegisterDto registerDto) {

        // 判断用户是否存在
        if(userMapper.countByUsername(registerDto.getUsername()) > 0){
            log.info("{}用户已存在",registerDto.getUsername());
            throw new BusinessException(400,"用户已存在");
        }

        // 对用户密文进行加密
        String hash = BCryptUtils.generateKey(registerDto.getPassword());

        // 新建一个用户实体类
        User user = new User(registerDto);
        user.setPassword(hash);

        // 调用持久层业务逻辑 进行新增一个用户
        userMapper.add(user);

        // 创建一个registerVo 实体类返回前端
        RegisterVo registerVo = new RegisterVo(user.getId(),user.getUsername(),user.getNickname());

        return registerVo;
    }

    /*
    *  用户登录
    * */
    @Override
    public LoginVo login(LoginDto loginDto) {
        // 通过用户名查询用户是否存在
        if(userMapper.countByUsername(loginDto.getUsername()) <= 0){
            log.info("{}用户不存在",loginDto.getUsername());
            throw new BusinessException(400, "用户名或密码错误");
        }

        // 通过用户名获取用户信息
        User user = userMapper.getUserByUsername(loginDto.getUsername());

        // 判断用户账号状态是否正常
        if(user.getStatus() == UserStatus.BANNED.getCode()){
            log.info("{}用户账户已被封禁",user.getUsername());
            throw  new BusinessException(403,"账号已封禁");
        }

        // 判断用户密码是否正确
        if(!BCryptUtils.matchKey(loginDto.getPassword(),user.getPassword())){
            log.info("{}用户输入密码错误",user.getUsername());
            throw new BusinessException(400, "用户名或密码错误");
        }

        // 生成jwt令牌
        Map<String ,Object> claim = new HashMap<>();
        claim.put("sub",user.getId().toString());
        claim.put("username",user.getUsername());
        claim.put("role",user.getRole());
        String token = JwtUtils.generateJwt(claim, MyDuration.SEVEN_DAYS);

        // 成功登录
        return new LoginVo(token, user.getRole());
    }

    /*
    *  通过用户名获得用户信息
    * */
    @Override
    public UserMyselfVo getSelfById(Long id) {
        // 通过用户ID获得用户信息
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        // 构造用户视图实体
        UserMyselfVo userMyselfVo = new UserMyselfVo(user);

        // 对用户已过题进行分页
        UserPageQueryParam userPageQueryParam = new UserPageQueryParam();
        userPageQueryParam.setUserId(id);
        List<UserProblemAc> problemAcslist;
        try {
            PageHelper.startPage(userPageQueryParam.getPageNum(), userPageQueryParam.getPageSize());
            problemAcslist = userProblemAcMapper.list(userPageQueryParam);
        } finally {
            PageHelper.clearPage();
        }

        // 将数据封装到Page中
        Page<UserProblemAc> p = (Page<UserProblemAc>)  problemAcslist;

        // 利用流将结果封装成Long类型的集合
        List<Long> problemList = p.getResult().stream().map(UserProblemAc::getProblemId).toList();

        // 将结果封装到用户视图类中
        userMyselfVo.setSolvedProblems(problemList);

        return userMyselfVo;
    }

    /*
    *  更新用户主页信息
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void UpdateUserInfo(UpdateMyselfDto updateMyselfDto, Long id) {
        // 通过用户ID获得用户信息
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        // 更新用户信息
        userMapper.updateProfile(id, updateMyselfDto.getNickname(),
                updateMyselfDto.getSignature(), updateMyselfDto.getAvatar() );
    }

    /*
    *  更新用户密码
    * */

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void UpdateUserPassword(UserPasswordDto userPasswordDto, Long id) {
        // 通过用户ID获得用户信息
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        // 对比新密码与旧密码
        if( userPasswordDto.getOldPassword() == null
            || !BCryptUtils.matchKey(userPasswordDto.getOldPassword(), user.getPassword())){
            throw new BusinessException(400, "原密码错误");
        }
        if(BCryptUtils.matchKey(userPasswordDto.getNewPassword(), user.getPassword())){
            throw new BusinessException(400, "新密码与原密码不能相同");
        }

        // 更新旧密码
        String newPw = BCryptUtils.generateKey(userPasswordDto.getNewPassword());
        userMapper.updatePassword(id, newPw);

    }

    /*
    *  通过ID获得其他用户信息
    * */
    @Override
    public OtherUserVo getUserInfoById(Long id) {
        // 通过用户ID获得用户信息
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        // 对用户已过题进行分页
        UserPageQueryParam param = new UserPageQueryParam();
        param.setUserId(id);
        List<UserProblemAc> problemAcList;
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            problemAcList = userProblemAcMapper.list(param);
        } finally {
            PageHelper.clearPage();
        }
        Page<UserProblemAc> p = (Page<UserProblemAc>) problemAcList;
        List<Long> problemList = p.getResult().stream().map(UserProblemAc::getProblemId).toList();


        // 通过用户ID获得用户题解信息 再对其信息进行封装
        List<SolutionVo> solutionVos = solutionMapper.findRecentPublishedByUserId(id, 5)
                .stream().map(SolutionVo::new).toList();
        OtherUserVo otherUserVo = new OtherUserVo(user);
        otherUserVo.setRecentSolutions(solutionVos);
        otherUserVo.setSolvedProblems(problemList);

        return otherUserVo;
    }

}
