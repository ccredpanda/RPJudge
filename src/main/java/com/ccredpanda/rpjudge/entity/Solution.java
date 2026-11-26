package com.ccredpanda.rpjudge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/*
 * 题解/文章实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solution {

    // 主键ID
    private Long id;

    // 发布该题解的用户ID
    private Long userId;

    // 关联的题目ID
    private Long problemId;

    // 关联的提交记录ID（如果题解是基于某次提交生成的）
    private Long submissionId;

    // 题解标题
    private String title;

    // 题解正文内容
    private String content;

    // 状态 (1: 正常/已发布, 0: 隐藏/删除 等)
    private Integer status;

    // 点赞数
    private Integer likeCount;

    // 浏览量
    private Integer viewCount;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}