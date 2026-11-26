package com.ccredpanda.rpjudge.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/*
 * 题目列表展示 VO
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemVo {

    // 题目ID
    private Long id;

    // 题目标题
    private String title;

    // 难度
    private Integer difficulty;

    // 算法标签
    private String tag;

    // 通过人数
    private Integer acceptedCount;

    // 提交总次数
    private Integer submitCount;

    // 通过率 (acceptedCount / submitCount * 100，保留1位小数)
    private Double passRate;

    // 题解总数
    private Integer solutionCount;

    // 题目状态只对管理员展示 (1 为上架 0 为下架)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    // 处理tag
    @JsonIgnore
    public String getTag() {
        return tag;
    }

    /*
     * 对外输出成数组，JSON 字段名就是 tags
     * */
    @JsonProperty("tags")
    public List<String> getTags() {
        if (tag == null || tag.isBlank()) {
            return List.of();
        }
        return Arrays.stream(tag.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}