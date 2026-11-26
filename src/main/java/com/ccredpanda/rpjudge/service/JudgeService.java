package com.ccredpanda.rpjudge.service;

import com.ccredpanda.rpjudge.common.JudgeResult;
import com.ccredpanda.rpjudge.dto.SubmissionDto;

/*
*  服务层判题接口
* */
public interface JudgeService {

    JudgeResult judge(SubmissionDto submissionDto, Long problemId);
}
