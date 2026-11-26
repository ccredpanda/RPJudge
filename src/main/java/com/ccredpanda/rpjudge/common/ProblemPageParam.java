package com.ccredpanda.rpjudge.common;

import lombok.Data;

@Data
public class ProblemPageParam {
    // 当前页码
    private Integer pageNum = 1;

    // 一页的题数
    private Integer pageSize = 20;

    // 题目难度
    private Integer difficulty;

    // 关键字 (用于模糊匹配)
    private String keyword;

    // 算法标签
    private String tag;

    // 排序关键字
    private String sort;

    // 管理员特: 通过题目状态查询 (1 为上架 0 为下架)
    private Integer status;
}
