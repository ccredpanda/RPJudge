package com.ccredpanda.rpjudge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordDto {
    // 用户旧密码
    @NotBlank(message = "请输入原密码")
    private String oldPassword;
    // 用户新密码
    @Size(min = 6,max = 32,message = "新密码长度需要 6-32 位")
    private String newPassword;
}
