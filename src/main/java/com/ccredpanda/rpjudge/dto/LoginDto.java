package com.ccredpanda.rpjudge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
* 登录所需信息
* */

@Data
public class LoginDto {
    // 用户名
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 32, message = "用户名格式不正确")
    private String username;
    // 用户密码
    @NotBlank(message = "密码不为能空")
    @Size(min = 6, max = 32,message = "密码格式不正确")
    private String password;
}
