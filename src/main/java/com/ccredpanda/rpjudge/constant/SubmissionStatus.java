package com.ccredpanda.rpjudge.constant;

import lombok.Getter;

/*
 *  判题状态 & 提交状态
 * */
@Getter
public enum SubmissionStatus {
    // 0 等待中
    WAITING(0, "等待中"),
    // 1 判题中
    JUDGING(1, "判题中"),
    // 2 答案正确
    ACCEPTED(2, "通过"),
    // 3 答案错误
    WRONG_ANSWER(3, "答案错误"),
    // 4 编译错误
    COMPILE_ERROR(4, "编译错误"),
    // 5 运行时错误
    RUNTIME_ERROR(5, "运行时错误"),
    // 6 用时超出限制
    TIME_LIMIT_EXCEEDED(6, "时间超限"),
    // 7 内存超出限制
    MEMORY_LIMIT_EXCEEDED(7, "内存超限");

    // 状态码
    private final int code;
    // 中文描述
    private final String desc;

    SubmissionStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 提供一个静态方法，方便通过 code 获取枚举
    public static SubmissionStatus fromCode(int code) {
        for (SubmissionStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}