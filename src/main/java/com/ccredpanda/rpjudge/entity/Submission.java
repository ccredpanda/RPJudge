package com.ccredpanda.rpjudge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 * 提交记录实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    // 提交记录ID
    private Long id;

    // 提交该代码的用户ID
    private Long userId;

    // 对应的题目ID
    private Long problemId;

    // 使用的编程语言 (通常用数字或枚举表示，例如 0: Java, 1: C++, 2: Python 等)
    private Integer language;

    // 提交的源代码 (mediumtext 对应 String)
    private String code;

    // 判题状态 (tinyint。通常 0: 等待判题, 1: 判题中, 2: 通过(Accepted), 3: 答案错误(Wrong Answer) 等)
    private Integer status;

    // 运行耗时 (单位通常是毫秒 ms)
    private Integer timeUsed;

    // 内存消耗 (单位通常是 KB 或 MB)
    private Integer memoryUsed;

    // 错误信息 (比如编译错误、运行错误的具体堆栈信息)
    private String errorMessage;

    // 失败或出错的测试用例编号
    private Integer failCase;

    // 提交时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 更新时间 (在判题完成后可能会更新这个时间)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}