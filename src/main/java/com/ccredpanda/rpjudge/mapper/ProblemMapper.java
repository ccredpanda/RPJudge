package com.ccredpanda.rpjudge.mapper;

import com.ccredpanda.rpjudge.common.ProblemPageParam;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.dto.ProblemCreateDto;
import com.ccredpanda.rpjudge.dto.ProblemUpdateDto;
import com.ccredpanda.rpjudge.entity.Problem;
import com.ccredpanda.rpjudge.vo.ProblemVo;
import com.ccredpanda.rpjudge.vo.SolvedProblemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ProblemMapper {
    // 通过用户ID获得用户已过题并分页
    List<SolvedProblemVo> getSolvedProblemByUserId(UserPageQueryParam userPageQueryParam);

    // 通过题目ID获得题目信息
    Problem getProblemById(Long id);

    // 获得题目列表
    List<ProblemVo> list(ProblemPageParam param);

    // 获得题目的所有标签
    List<String> getTags();

    // 新增一个题目
    @Options(useGeneratedKeys = true, keyProperty = "problemId")
    Long add(ProblemCreateDto problemDto);

    // 更新题目信息
    void update(ProblemUpdateDto p);

    // 通过题目ID删除题目信息
    void deleteByProblemId(Long problemId);

    // 题目提交数增长1
    void incSubmitCount(Long problemId);

    // 题目通过数增长1
    void incAcceptedCount(Long problemId);
}
