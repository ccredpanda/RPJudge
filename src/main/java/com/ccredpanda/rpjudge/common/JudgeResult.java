package com.ccredpanda.rpjudge.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
*  判题的结果
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeResult {
    // 判题状态
    private Integer status;

    // 通过所有案例所花费的时间
    private Integer timeUsed;

    // 通过所有案例所花费的空间代价
    private Integer memoryUsed;

    // 判题时给出的错误信息
    private String errorMessage;

    // 出错的案例
    private Integer failCase;
}
