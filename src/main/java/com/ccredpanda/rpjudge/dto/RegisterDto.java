package com.ccredpanda.rpjudge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
*  用户注册信息
* */

@Data
public class RegisterDto {
    // 用户名
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 32, message = "用户名格式不正确")
    private String username;
    // 用户密码
    @NotBlank(message = "密码不为能空")
    @Size(min = 6, max = 32,message = "密码格式不正确")
    private String password;
    // 用户昵称
    @Size(max = 32, message = "昵称不能超过 32 字")
    private String nickname;
}
