package com.ccredpanda.rpjudge.constant;

import lombok.Getter;

/*
 * 用户状态
 */
@Getter
public enum UserStatus implements BaseEnum {
    // 显式指定状态码
    NORMAL(0, "正常"),
    BANNED(1, "已封禁");

    // 状态码
    private final Integer code;
    // 描述
    private final String desc;

    UserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}