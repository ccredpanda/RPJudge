package com.ccredpanda.rpjudge.constant;

import lombok.Getter;

/*
 * 题解状态
 */
@Getter
public enum SolutionStatus implements BaseEnum {

    // 待审核
    PENDING(0, "待审核"),
    // 已发布
    PUBLISHED(1, "已发布"),
    // 已下架 (或被封禁)
    REMOVED(2, "已下架");

    private final Integer code;
    private final String desc;

    SolutionStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}