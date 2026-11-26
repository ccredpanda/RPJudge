package com.ccredpanda.rpjudge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 题目测试用例实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemCase {

    // 测试用例主键ID
    private Long id;

    // 关联的题目ID
    private Long problemId;

    // 测试用例输入数据 (mediumtext 大字段)
    private String input;

    // 测试用例期望输出数据 (mediumtext 大字段)
    private String output;

    // 该测试用例的分值 (默认为 0)
    private Integer score = 0;

    // 测试用例的执行顺序 (默认为 1)
    private Integer sort = 1;
}