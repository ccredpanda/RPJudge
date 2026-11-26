package com.ccredpanda.rpjudge.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    // jwt生成令牌
    private String token;

    // 用户身份
    private Integer role;
}
