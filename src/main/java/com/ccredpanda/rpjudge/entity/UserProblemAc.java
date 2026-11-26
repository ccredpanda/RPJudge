package com.ccredpanda.rpjudge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/*
*  用户已通过题存储实体类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProblemAc {
    // 过题编号
    private Long id;

    // 用户ID
    private Long userId;

    // 题号
    private Long problemId;

    // 提交ID
    private Long submissionId;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
