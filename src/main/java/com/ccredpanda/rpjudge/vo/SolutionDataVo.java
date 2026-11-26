package com.ccredpanda.rpjudge.vo;

import com.ccredpanda.rpjudge.entity.Solution;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/*
 * 题解详情 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDataVo {

    // 题解ID
    private Long id;

    // 题目ID
    private Long problemId;

    // 题目名称 (来自题目表)
    private String problemTitle;

    // 题解标题
    private String title;

    // 题解正文内容 (Markdown格式)
    private String content;

    // 作者昵称 (来自用户表)
    private String author;

    // 作者ID
    private Long authorId;

    // 作者头像 (来自用户表)
    private String avatar;

    // 点赞数
    private Integer likeCount;

    // 浏览量
    private Integer viewCount;

    // 关联的提交记录ID (对应表中的 submission_id)
    private Long relatedSubmissionId;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public SolutionDataVo(Solution solution){
        this.id = solution.getId();
        this.authorId = solution.getUserId();
        this.relatedSubmissionId = solution.getSubmissionId();
        this.content = solution.getContent();
        this.createTime = solution.getCreateTime();
        this.likeCount = solution.getLikeCount();
        this.viewCount = solution.getViewCount();
        this.updateTime = solution.getUpdateTime();
    }
}