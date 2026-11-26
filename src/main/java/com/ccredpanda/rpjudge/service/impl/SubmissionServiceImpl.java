package com.ccredpanda.rpjudge.service.impl;

import com.ccredpanda.rpjudge.common.JudgeResult;
import com.ccredpanda.rpjudge.constant.Language;
import com.ccredpanda.rpjudge.constant.ProblemStatus;
import com.ccredpanda.rpjudge.constant.SubmissionStatus;
import com.ccredpanda.rpjudge.dto.ProblemUpdateDto;
import com.ccredpanda.rpjudge.dto.SubmissionDto;
import com.ccredpanda.rpjudge.entity.Problem;
import com.ccredpanda.rpjudge.entity.Submission;
import com.ccredpanda.rpjudge.entity.User;
import com.ccredpanda.rpjudge.entity.UserProblemAc;
import com.ccredpanda.rpjudge.exception.BusinessException;
import com.ccredpanda.rpjudge.mapper.*;
import com.ccredpanda.rpjudge.service.JudgeService;
import com.ccredpanda.rpjudge.service.SubmissionService;
import com.ccredpanda.rpjudge.utils.CodeUtils;
import com.ccredpanda.rpjudge.vo.SubmissionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/*
*  服务层提交记录接口实现类
* */
@Slf4j
@Service
public class SubmissionServiceImpl implements SubmissionService {


    private static final List<String> FORBIDDEN = List.of(
            "Runtime.getRuntime", "ProcessBuilder", "System.exit",
            "java.io.File", "java.nio.file", "java.net.", "Class.forName", "Unsafe"
    );

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private UserProblemAcMapper userProblemAcMapper;

    @Autowired
    private ProblemCaseMapper problemCaseMapper;



    /*
    *  提交题目 & 判题
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubmissionVo submitCode(SubmissionDto submissionDto, Long userId) {

        // 通过用户ID获得用户信息
        User user = userMapper.getUserById(userId);
        if(user == null){
            throw new BusinessException(404, "用户不存在");
        }

        // 判断题目是否存在
        Long problemId = submissionDto.getProblemId();
        Problem problem = problemMapper.getProblemById(problemId);
        if(problem == null || ProblemStatus.REMOVED.getCode().equals(problem.getStatus())){
            throw new BusinessException(404, "题目不存在");
        }

        // 检查用户提交代码是否合法
        String code = submissionDto.getCode();
        if(CodeUtils.exceeds100KB(code.trim())){
            throw new BusinessException(400, "代码长度超过100KB");
        }
         for (String s : FORBIDDEN) {
             if(code.contains(s)){
                 throw new BusinessException(400, "提交非法代码");
             }
         }

        // 检查用户语言是否合法
        Integer language = submissionDto.getLanguage();
        Language lang = Arrays.stream(Language.values())
                .filter(l -> l.getCode().equals(language))
                .findFirst()
                .orElseThrow(() -> new BusinessException(400, "语言不合法"));

        // 新增一个提交记录 并封装
        Submission submission = new Submission();
        submission.setCode(code);
        submission.setLanguage(language);
        submission.setStatus(SubmissionStatus.JUDGING.getCode());
        submission.setProblemId(problemId);
        submission.setUserId(userId);

        // 在数据库中新增一个提交记录
        submissionMapper.add(submission);
        userMapper.incSubmitCount(userId);
        problemMapper.incSubmitCount(problemId);

        // 开始判题
        JudgeResult result = null;
        try {
            result = judgeService.judge(submissionDto, problemId);
        } catch (Exception e) {
            log.error("判题失败 submissionId={}", submission.getId(), e);
            result = new JudgeResult();
            result.setStatus(SubmissionStatus.RUNTIME_ERROR.getCode());
            result.setErrorMessage("判题服务异常，请稍后重试");
        }

        // 封装判题结果
        submission.setStatus(result.getStatus());
        submission.setErrorMessage(result.getErrorMessage());
        submission.setFailCase(result.getFailCase());
        submission.setMemoryUsed(result.getMemoryUsed());
        submission.setTimeUsed(result.getTimeUsed());

        // 更新提交记录
        submissionMapper.update(submission);

        // 将前端所需信息封装并返回
        SubmissionVo submissionVo = new SubmissionVo();
        submissionVo.setSubmissionId(submission.getId());
        submissionVo.setTimeUsed(result.getTimeUsed());
        submissionVo.setMemoryUsed(result.getMemoryUsed());
        submissionVo.setStatus(result.getStatus());

        // 插入用户通过题的表 并更新用户通过题数
        if(result.getStatus().equals(SubmissionStatus.ACCEPTED.getCode())){
            int inserted = userProblemAcMapper.insertIgnore(userId, problemId, submission.getId());
            if (inserted > 0) {
                userMapper.incSolvedCount(userId);
                problemMapper.incAcceptedCount(problemId);
            }
        }


        return submissionVo;
    }
}
