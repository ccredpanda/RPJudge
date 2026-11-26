package com.ccredpanda.rpjudge.service.impl;

import cn.hutool.core.codec.Base64;
import com.ccredpanda.rpjudge.common.Judge0Response;
import com.ccredpanda.rpjudge.common.JudgeConfig;
import com.ccredpanda.rpjudge.common.JudgeResult;
import com.ccredpanda.rpjudge.constant.Language;
import com.ccredpanda.rpjudge.constant.SubmissionStatus;
import com.ccredpanda.rpjudge.dto.SubmissionDto;
import com.ccredpanda.rpjudge.entity.ProblemCase;
import com.ccredpanda.rpjudge.mapper.ProblemCaseMapper;
import com.ccredpanda.rpjudge.mapper.ProblemMapper;
import com.ccredpanda.rpjudge.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ProblemCaseMapper problemCaseMapper;

    // 语言映射
    private static final Map<Integer, Integer> LANG_MAP = Map.of(
            Language.JAVA17.getCode(),62,
            Language.C21.getJudgeId(), 54,
            Language.PYTHON3.getCode(), 71
    );

    // Judge0 状态码
    private static final Map<Integer, Integer> JUDGE0_MAP = Map.of(
            3, SubmissionStatus.ACCEPTED.getCode(),
            4,SubmissionStatus.WRONG_ANSWER.getCode(),
            6,SubmissionStatus.COMPILE_ERROR.getCode(),
            5,SubmissionStatus.TIME_LIMIT_EXCEEDED.getCode(),
            9,SubmissionStatus.MEMORY_LIMIT_EXCEEDED.getCode(),
            10,SubmissionStatus.MEMORY_LIMIT_EXCEEDED.getCode(),
            8, SubmissionStatus.RUNTIME_ERROR.getCode(),
            11,SubmissionStatus.RUNTIME_ERROR.getCode(),
            12,SubmissionStatus.RUNTIME_ERROR.getCode(),
            13,SubmissionStatus.RUNTIME_ERROR.getCode()
    );
    /*
    *  执行判题逻辑
    * */
    @Override
    public JudgeResult judge(SubmissionDto submissionDto, Long problemId) {


    }

    JudgeResult judgeOneCase(String code, int language, ProblemCase problemCase, JudgeConfig cfg){

    }
}
