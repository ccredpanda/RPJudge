package com.ccredpanda.rpjudge.vo;

import com.ccredpanda.rpjudge.entity.Problem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/*
 * 题目详情 VO
 */
@Data
@NoArgsConstructor
public class ProblemDataVo {
        // 题目ID
        private Long id;

        // 题目标题
        private String title;

        // 题目描述（Markdown）
        private String content;

        // 难度 (0: 简单, 1: 中等, 2: 困难)
        private Integer difficulty;

        // 标签列表
        private List<String> tags;

        // 时间限制(毫秒)
        private Integer timeLimit;

        // 内存限制(MB)
        private Integer memoryLimit;

        // 代码模板 (数据库中为 json 字符串，前端拿到会解析)
        private String templateCode;

        // 通过人数
        private Integer acceptedCount;

        // 提交总次数
        private Integer submitCount;

        // 通过率
        private Double passRate;

        // 题解总数
        private Integer solutionCount;

        // 当前登录用户对该题的最好状态 (2=已AC, null=未提交；未登录时也为 null)
        private Integer myStatus;


        /*
         * 构造函数：从 Problem 实体类转换
         */

        public ProblemDataVo(Problem problem) {
                if (problem == null) return;

                this.id = problem.getId();
                this.title = problem.getTitle();
                this.content = problem.getContent();
                this.difficulty = problem.getDifficulty();
                this.timeLimit = problem.getTimeLimit();
                this.memoryLimit = problem.getMemoryLimit();
                this.templateCode = problem.getTemplateCode();
                this.acceptedCount = problem.getAcceptedCount();
                this.submitCount = problem.getSubmitCount();
                this.solutionCount = problem.getSolutionCount();
                this.tags = problem.getTags();

                // 2. 处理 passRate
                if (problem.getSubmitCount() != null && problem.getSubmitCount() > 0) {
                        double rate = (double) problem.getAcceptedCount() / problem.getSubmitCount() * 100;
                        this.passRate = Math.round(rate * 10.0) / 10.0;
                } else {
                        this.passRate = 0.0;
                }
        }
}