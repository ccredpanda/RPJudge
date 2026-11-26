package com.ccredpanda.rpjudge.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/*
*  已解题视图类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolvedProblemVo {
    // 已解决问题题号
    private Long problemId;

    // 题目标题
    private String title;

    // 题目难度
    private Integer difficulty;

    // 通过时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acTime;
}
