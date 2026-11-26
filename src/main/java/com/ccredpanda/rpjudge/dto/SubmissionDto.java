package com.ccredpanda.rpjudge.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmissionDto {
    // 题目ID
    @NotNull(message = "题目编号不能为空")
    private Long problemId;

    // 提交代码所用的语言
    @NotNull(message = "语言不合法")
    private Integer language;

    // 提交的代码
    @NotBlank(message = "提交代码不能为空")
    private String code;
}
