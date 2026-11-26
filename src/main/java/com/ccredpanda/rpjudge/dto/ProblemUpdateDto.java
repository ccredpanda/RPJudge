package com.ccredpanda.rpjudge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

/*
 * 创建/更新题目请求 DTO
 * */
@Data
public class ProblemUpdateDto {

    // 题目ID
    private Long problemId;

    // 题目标题
    @Size(min = 1, max = 128, message = "题目标题长度必须在 1-128 字之间")
    private String title;

    // 题目描述
    @Size(max = 65536, message = "题目描述不能超过 64KB")
    private String content;

    // 题目难度
    @Min(value = 0, message = "难度最小值为 0")
    @Max(value = 2, message = "难度最大值为 2")
    private Integer difficulty;

    // 非必填 最多 5 个标签 且每个标签最大 32 字符
    @Size(max = 5, message = "标签最多只能有 5 个")
    private List<@Size(max = 32, message = "单个标签长度不能超过 32 字") String> tags;

    // 非必填 范围 100-10000
    @Min(value = 100, message = "时间限制不能低于 100ms")
    @Max(value = 10000, message = "时间限制不能高于 10000ms")
    private Integer timeLimit;

    // 非必填 范围 16-1024
    @Min(value = 16, message = "内存限制不能低于 16MB")
    @Max(value = 1024, message = "内存限制不能高于 1024MB")
    private Integer memoryLimit;

    // 代码模板
    private String templateCode;

    // 测试用例
    private List<JudgeCaseDto> judgeCases;

    // 题目状态
    private Integer status;


    /* 给 MyBatis 用：把 tags 列表拼成 "数组,哈希表" */
    public String getTag() {
        return (tags == null || tags.isEmpty()) ? "" : String.join(",", tags);
    }
}