package com.ccredpanda.rpjudge.constant;


import lombok.Getter;

@Getter
public enum Difficulty implements BaseEnum {
    EASY(1, "简单"),
    MEDIUM(2, "中等"),
    HARD(3, "困难");

    private final Integer code;
    private final String desc;

    Difficulty(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}