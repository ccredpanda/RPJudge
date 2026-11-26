package com.ccredpanda.rpjudge.vo;

import com.ccredpanda.rpjudge.entity.Solution;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/*
*  题解视图类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionVo {
    // 主键ID
    private Long id;

    // 关联的题目ID
    private Long problemId;

    // 题解标题
    private String title;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public SolutionVo(Solution solution){
        this.id = solution.getId();
        this.title = solution.getTitle();
        this.createTime = solution.getCreateTime();
        this.problemId = solution.getProblemId();
    }
}
