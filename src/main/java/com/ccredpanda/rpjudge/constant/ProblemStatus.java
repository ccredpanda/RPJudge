package com.ccredpanda.rpjudge.constant;

import lombok.Getter;

@Getter
public enum ProblemStatus implements BaseEnum{
    // 已发布
    PUBLISHED(1, "已发布"),
    // 已下架 (或被封禁)
    REMOVED(0, "已下架");

    private final Integer code;
    private final String desc;

    ProblemStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
