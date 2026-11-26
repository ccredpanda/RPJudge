package com.ccredpanda.rpjudge.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionVo {
    // 提交编号
    private Long submissionId;

    // 提交状态
    private Integer status;

    // 通过所以案例所需时间 (ms)
    private Integer timeUsed;

    // 通过所以案例所消耗的空间 (MB)
    private Integer memoryUsed;
}
