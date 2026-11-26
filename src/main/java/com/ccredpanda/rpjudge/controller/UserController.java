package com.ccredpanda.rpjudge.controller;

import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.Result;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.dto.LoginDto;
import com.ccredpanda.rpjudge.dto.RegisterDto;
import com.ccredpanda.rpjudge.dto.UpdateMyselfDto;
import com.ccredpanda.rpjudge.dto.UserPasswordDto;
import com.ccredpanda.rpjudge.entity.User;
import com.ccredpanda.rpjudge.exception.BusinessException;
import com.ccredpanda.rpjudge.service.ProblemService;
import com.ccredpanda.rpjudge.service.SolutionService;
import com.ccredpanda.rpjudge.service.UserService;
import com.ccredpanda.rpjudge.utils.JwtUtils;
import com.ccredpanda.rpjudge.utils.NetworkUtils;
import com.ccredpanda.rpjudge.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
*  用户请求处理
* */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    // 服务层接口
    @Autowired
    private UserService userService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private SolutionService solutionService;

    /*
    * 处理用户登录请求  3.2
    * */
    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginDto loginDto){

        LoginVo loginVo = userService.login(loginDto);

        return Result.success(loginVo);
    }

    /*
    * 处理用户注册请求  3.1
    * */
    @PostMapping("/register")
    public Result register(@RequestBody @Valid RegisterDto registerDto){

        RegisterVo registerVo = new RegisterVo();

        registerVo = userService.register(registerDto);

        log.info("用户注册信息为{}", registerDto.getUsername());

        return Result.success(registerVo);
    }


    /*
    *  查看当前用户主页信息  3.3
    * */
    @GetMapping("/me")
    public Result getMyInfo(@RequestAttribute("userId") Long id){

        log.info("当前用户ID={}",id);

        UserMyselfVo userMyselfVo = userService.getSelfById(id);

        return Result.success(userMyselfVo);
    }

    /*
    *  更新用户主页信息  3.4
    * */
    @PutMapping("/me")
    public Result updateMyInfo(@RequestBody @Valid UpdateMyselfDto updateMyselfDto,@RequestAttribute("userId") Long id){

        log.info("当前更新操作ID={}",id);

        userService.UpdateUserInfo(updateMyselfDto, id);

        return Result.success();
    }

    /*
    *  用户修改密码   3.5
    * */
    @PutMapping("/password")
    public Result updatePassword(@RequestBody @Valid UserPasswordDto userPasswordDto, @RequestAttribute("userId") Long id){

        log.info("更新密码ID={}",id);

        userService.UpdateUserPassword(userPasswordDto, id);

        return Result.success();
    }

    /*
    *  查看他人主页信息 3.6
    * */
    @GetMapping("/{id}")
    public Result getOthersInfo(@PathVariable Long id){

        log.info("查询ID={}",id);

        OtherUserVo otherUserVo = userService.getUserInfoById(id);

        return Result.success(otherUserVo);
    }

    /*
    *  某用户已解决问题  3.7
    * */
    @GetMapping("/{id}/solved-problems")
    public Result getSolvedProblemById(UserPageQueryParam userPageQueryParam, @PathVariable Long id){

        userPageQueryParam.setUserId(id);
        log.info("分页信息为:{}",userPageQueryParam);
        PageResult<SolvedProblemVo> pageResult = problemService.getSolvedProblemById(userPageQueryParam);

        return Result.success(pageResult);
    }

    /*
    *  某用户的题解列表   3.8
    * */
    @GetMapping("/{id}/solutions")
    public Result getSolutions(UserPageQueryParam param, @PathVariable Long id){

        param.setUserId(id);

        PageResult<SolutionDataVo> solutions = solutionService.getSolutions(param);

        return Result.success(solutions);
    }
}
