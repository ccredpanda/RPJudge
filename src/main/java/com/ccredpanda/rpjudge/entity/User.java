package com.ccredpanda.rpjudge.entity;

import com.ccredpanda.rpjudge.constant.Role;
import com.ccredpanda.rpjudge.constant.UserStatus;
import com.ccredpanda.rpjudge.dto.RegisterDto;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/*
* 用户信息实体类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // 主键ID
    private Long id;

    // 用户密码
    private String password;

    // 用户名称
    private String username;

    // 用户昵称
    private String nickname;

    // 用户头像 URL
    private String avatar;

    // 用户个性签名
    private String signature;

    // 0 为普通用户 1 为管理员
    private Integer role;

    // 用户过题数
    private Integer solvedCount;

    // 用户提交数
    private Integer submitCount;

    // 已发布题解数
    private Integer solutionCount;

    // 1 为正常状态 0 为封禁状态
    private Integer status;

    // 用户账号的创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 用户账号更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public User(RegisterDto registerDto){
        this.username = registerDto.getUsername();
        this.password = registerDto.getPassword();
        this.nickname = (registerDto.getNickname() == null || registerDto.getNickname().isBlank())
                ? registerDto.getUsername()
                : registerDto.getNickname();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.solvedCount = 0;
        this.solutionCount = 0;
        this.submitCount = 0;
        this.role = Role.USER.getCode();
        this.status = UserStatus.NORMAL.getCode();
        this.avatar = "";
        this.signature = "";
    }
}
