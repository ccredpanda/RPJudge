package com.ccredpanda.rpjudge.mapper;

import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.entity.Submission;
import com.ccredpanda.rpjudge.vo.SolvedProblemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface SubmissionMapper {
    // 通过用户ID和题目ID 统计提交次数
    Integer countUserStatusById(Long problemId, Long userId);

    // 通过题目ID 统计提交记录
    Long countByProblemId(Long problemId);

    // 新增提交记录
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Submission submission);

    // 更新提交记录
    void update(Submission submission);
}
