package com.ccredpanda.rpjudge.service;

import com.ccredpanda.rpjudge.dto.LoginDto;
import com.ccredpanda.rpjudge.dto.RegisterDto;
import com.ccredpanda.rpjudge.dto.UpdateMyselfDto;
import com.ccredpanda.rpjudge.dto.UserPasswordDto;
import com.ccredpanda.rpjudge.vo.LoginVo;
import com.ccredpanda.rpjudge.vo.OtherUserVo;
import com.ccredpanda.rpjudge.vo.RegisterVo;
import com.ccredpanda.rpjudge.vo.UserMyselfVo;
import jakarta.validation.Valid;

public interface UserService {
    // 用户注册请求接口
    RegisterVo register(@Valid RegisterDto registerDto);

    // 用户登录请求接口
    LoginVo login(@Valid LoginDto loginDto);

    // 通过用户名获得主页信息
    UserMyselfVo getSelfById(Long id);

    // 更新用户信息
    void UpdateUserInfo(@Valid UpdateMyselfDto updateMyselfDto, Long id);

    // 更新用户密码
    void UpdateUserPassword(UserPasswordDto userPasswordDto, Long id);

    // 通过ID获得其他用户信息
    OtherUserVo getUserInfoById(Long id);
}
