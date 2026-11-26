package com.ccredpanda.rpjudge.service;


import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.ProblemPageParam;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.dto.ProblemCreateDto;
import com.ccredpanda.rpjudge.dto.ProblemUpdateDto;
import com.ccredpanda.rpjudge.vo.*;

import java.util.List;
import java.util.Map;

public interface ProblemService {
    // 通过用户ID与分页请求 获得用户已解题
    PageResult<SolvedProblemVo> getSolvedProblemById(UserPageQueryParam userPageQueryParam);

    // 条件分页查询题目列表
    PageResult<ProblemVo> getList(ProblemPageParam param);

    // 通过题目ID查询题目信息
    ProblemDataVo getProblemById(Long problemId, Long userId);

    // 获得所有的标签信息
    List<String> getTags();

    // 新增一个题目并返回题目ID
    Map<String, Object> add(ProblemCreateDto problemDto, Long userId);

    // 更新题目信息
    void update(ProblemUpdateDto p, Long userId);

    // 删除题目 (如果无题解,无提交记录 直接删除 否则改为下架)
    DeleteProblemVo delete(Long problemId, Long userId);

    // 管理员查询题目列表
    PageResult<ProblemVo> adminGetList(ProblemPageParam param, Long userId);
}
