package com.ccredpanda.rpjudge.constant;

import lombok.Getter;

/*
 * 用户身份
 */
@Getter
public enum Role implements BaseEnum {
    // 0 普通用户
    USER(0, "普通用户"),
    // 1 管理员
    ADMIN(1, "管理员");

    private final Integer code;
    private final String desc;

    Role(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}