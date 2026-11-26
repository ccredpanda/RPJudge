package com.ccredpanda.rpjudge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 测试用例 DTO
 * */
@Data
public class JudgeCaseDto {

    // 测试用例的 输入数据
    @NotBlank(message = "测试用例的输入数据不能为空")
    private String input;

    // 测试用例的 输出数据
    @NotBlank(message = "测试用例的期望输出不能为空")
    private String output;

}