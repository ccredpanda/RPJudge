package com.ccredpanda.rpjudge.common;

import lombok.Data;

/*
*  判题配置
* */
@Data
public class JudgeConfig {
    // 判题的时间限制
    private Integer timeLimit;

    // 判题的空间限制
    private Integer memoryLimit;
}
