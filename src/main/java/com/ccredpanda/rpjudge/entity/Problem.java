package com.ccredpanda.rpjudge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/*
 * 题目实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {

    // 题目ID (主键)
    private Long id;

    // 题目标题
    private String title;

    // 题目描述内容 (text 类型)
    private String content;

    // 难度 (0: 简单, 1: 中等, 2: 困难)
    private Integer difficulty;

    // 标签 (如 "数组,哈希表")
    private String tag;

    // 时间限制 (单位: ms)
    private Integer timeLimit;

    // 内存限制 (单位: MB)
    private Integer memoryLimit;

    // 代码模板 (数据库中为 json 类型，Java 中通常先用 String 接收)
    private String templateCode;

    // 通过人数 (accepted_count)
    private Integer acceptedCount;

    // 提交总次数 (submit_count)
    private Integer submitCount;

    // 题解总数 (solution_count)
    private Integer solutionCount;

    // 状态 (1: 正常, 0: 隐藏/下架)
    private Integer status;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    // 忽略getTag
    @JsonIgnore
    String getTag(){
        return this.tag;
    }

    // 显示getTags
    @JsonProperty("tags")
    public List<String> getTags(){
        if (tag == null || tag.isBlank()) {
            return List.of();
        }
        return Arrays.stream(tag.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}