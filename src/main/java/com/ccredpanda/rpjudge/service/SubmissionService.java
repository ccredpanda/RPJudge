package com.ccredpanda.rpjudge.service;

import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.dto.SubmissionDto;
import com.ccredpanda.rpjudge.vo.SolvedProblemVo;
import com.ccredpanda.rpjudge.vo.SubmissionVo;
import jakarta.validation.Valid;

import java.util.List;

public interface SubmissionService {

    // 提交题目 & 判题
    SubmissionVo submitCode(SubmissionDto submissionDto, Long userId);
}
