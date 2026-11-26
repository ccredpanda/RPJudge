package com.ccredpanda.rpjudge.service.impl;

import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.ProblemPageParam;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.constant.ProblemStatus;
import com.ccredpanda.rpjudge.constant.Role;
import com.ccredpanda.rpjudge.constant.SubmissionStatus;
import com.ccredpanda.rpjudge.dto.JudgeCaseDto;
import com.ccredpanda.rpjudge.dto.ProblemCreateDto;
import com.ccredpanda.rpjudge.dto.ProblemUpdateDto;
import com.ccredpanda.rpjudge.entity.Problem;
import com.ccredpanda.rpjudge.entity.ProblemCase;
import com.ccredpanda.rpjudge.entity.User;
import com.ccredpanda.rpjudge.exception.BusinessException;
import com.ccredpanda.rpjudge.mapper.*;
import com.ccredpanda.rpjudge.service.ProblemService;
import com.ccredpanda.rpjudge.vo.DeleteProblemVo;
import com.ccredpanda.rpjudge.vo.ProblemDataVo;
import com.ccredpanda.rpjudge.vo.ProblemVo;
import com.ccredpanda.rpjudge.vo.SolvedProblemVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*  服务层问题接口实现类
* */
@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private SolutionMapper solutionMapper;

    @Autowired
    private UserProblemAcMapper userProblemAcMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProblemCaseMapper problemCaseMapper;

    /*
     *  通过用户ID和分页请求获得用户已过题并分页
     * */
    @Override
    public PageResult<SolvedProblemVo> getSolvedProblemById(UserPageQueryParam userPageQueryParam) {
        // 通过用户ID获得用户已过题记录并分页
        List<SolvedProblemVo> solvedProblemVos;
        try {
            PageHelper.startPage(userPageQueryParam.getPageNum(), userPageQueryParam.getPageSize());
            solvedProblemVos = problemMapper.getSolvedProblemByUserId(userPageQueryParam);
        } finally {
            PageHelper.clearPage();
        }
        Page<SolvedProblemVo> page = (Page<SolvedProblemVo>) solvedProblemVos;

        // 封装分页结果
        PageResult<SolvedProblemVo> pageResult = new PageResult<>();
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setList(page.getResult());

        return pageResult;
    }

    /*
    *  对题目列表分页查询
    * */
    @Override
    public PageResult<ProblemVo> getList(ProblemPageParam param) {

        // 条件分页查询题目列表
        param.setStatus(ProblemStatus.PUBLISHED.getCode());
        List<ProblemVo> problemVos;
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            problemVos = problemMapper.list(param);
        } finally {
            PageHelper.clearPage();
        }

        // 封装结果
        Page<ProblemVo> page = (Page<ProblemVo>) problemVos;
        PageResult<ProblemVo> pageResult = new PageResult<>();
        pageResult.setPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setPageSize(page.getPageSize());
        page.getResult().stream().forEach(
                problemVo -> problemVo.setStatus(null));
        pageResult.setList(page.getResult());
        pageResult.setPageNum(page.getPageNum());

        return pageResult;
    }

    /*
    *   通过题目ID获得题目对当前用户的信息
    * */
    @Override
    public ProblemDataVo getProblemById(Long problemId, Long userId) {

        // 通过题目ID获得题目信息
        Problem problem = problemMapper.getProblemById(problemId);
        if (problem == null) {
            throw new BusinessException(404, "题目不存在");
        }

        // 判断题目是否下架
        if(problem.getStatus().equals(ProblemStatus.REMOVED.getCode()) ){
            throw new BusinessException(404, "访问资源不存在");
        }

        // 通过用户ID和题目ID获得 当前题目对用户的信息
        Integer myStatus;
        if(userId == null){
            myStatus = null;
        }else if(userProblemAcMapper.countUserStatusById(problemId, userId) > 0){
            myStatus = SubmissionStatus.ACCEPTED.getCode();
        }else if(submissionMapper.countUserStatusById(problemId, userId) > 0){
            myStatus = SubmissionStatus.WRONG_ANSWER.getCode();
        }else{
            myStatus = null;
        }

        // 封装结果给前端
        ProblemDataVo problemDataVo = new ProblemDataVo(problem);
        problemDataVo.setMyStatus(myStatus);

        return problemDataVo;
    }

    /*
    *  获得所有的标签信息
    * */
    @Override
    public List<String> getTags() {

        // 获得所有标签并去重
        List<String> list = problemMapper.getTags().stream()
                .flatMap(tags -> Arrays.stream(tags.split(",")))
                .distinct().toList();

        return list;
    }

    /*
    *  新增题目
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> add(ProblemCreateDto problemDto, Long userId) {
        // 判断用户是否有权限
        User user = userMapper.getUserById(userId);
        if(user == null){
            throw new BusinessException(404, "用户不存在");
        }
        if (Role.USER.getCode().equals(user.getRole())) {
            throw new BusinessException(403, "无权限访问");
        }


        // 将数据插入数据库
        problemDto.setStatus(ProblemStatus.PUBLISHED.getCode());
        problemMapper.add(problemDto);

        int sort = 1;
        for (JudgeCaseDto judgeCase : problemDto.getJudgeCases()) {
            ProblemCase problemCase = new ProblemCase();
            problemCase.setInput(judgeCase.getInput());
            problemCase.setProblemId(problemDto.getProblemId());
            problemCase.setOutput(judgeCase.getOutput());
            problemCase.setSort(sort++);
            problemCaseMapper.add(problemCase);
        }

        // 封装数据返回给前端
        Map<String ,Object> result = new HashMap<>();
        result.put("id", problemDto.getProblemId());
        return result;
    }

    /*
    *  更新题目
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ProblemUpdateDto p, Long userId) {
        // 判断题目是否存在
        if(problemMapper.getProblemById(p.getProblemId()) == null){
            throw new BusinessException(404, "题目不存在");
        }

        // 检查用户是否有权限
        User user = userMapper.getUserById(userId);
        if(user == null){
            throw new BusinessException(404, "用户不存在");
        }
        if(user.getRole().equals(Role.USER.getCode())){
            throw new BusinessException(403, "无权限访问");
        }

        // 更新题目信息
        problemMapper.update(p);

        if(p.getJudgeCases() != null) {
            // 删除上次更新的测试案例
            if(problemCaseMapper.getByProblemId(p.getProblemId()) != null) {
                problemCaseMapper.deleteByProblemId(p.getProblemId());
            }
            int sort = 1;

            // 插入这次更新的测试案例
            for (JudgeCaseDto judgeCase : p.getJudgeCases()) {
                ProblemCase problemCase = new ProblemCase();
                problemCase.setOutput(judgeCase.getOutput());
                problemCase.setInput(judgeCase.getInput());
                problemCase.setProblemId(p.getProblemId());
                problemCase.setSort(sort++);
                problemCaseMapper.add(problemCase);
            }
        }
    }

    /*
    *  删除题目 (如果没有提交记录 也没有题解 就直接物理删除 否则 改为下架)
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DeleteProblemVo delete(Long problemId, Long userId) {
        // 判断题目是否存在
        if(problemMapper.getProblemById(problemId) == null){
            throw new BusinessException(404, "题目不存在");
        }

        User user = userMapper.getUserById(userId);
        if(user == null){
            throw new BusinessException(404, "用户不存在");
        }
        // 检查用户是否有权限
        if(user.getRole().equals(Role.USER.getCode())){
            throw new BusinessException(403, "无权限访问");
        }

        // 检查是否有提交记录和是否有无题解
        DeleteProblemVo deleteProblemVo = new DeleteProblemVo();
        if( submissionMapper.countByProblemId(problemId) > 0 || solutionMapper.countByProblemId(problemId) > 0){
            ProblemUpdateDto problemUpdateDto = new ProblemUpdateDto();
            problemUpdateDto.setProblemId(problemId);
            problemUpdateDto.setStatus(ProblemStatus.REMOVED.getCode());
            problemMapper.update(problemUpdateDto);
            deleteProblemVo.setOffline(true);
        }else{
            problemCaseMapper.deleteByProblemId(problemId);
            problemMapper.deleteByProblemId(problemId);
            deleteProblemVo.setDeleted(true);
        }

        return deleteProblemVo;
    }

    /*
    *  管理员条件分页查询题目列表
    * */
    @Override
    public PageResult<ProblemVo> adminGetList(ProblemPageParam param, Long userId) {
        // 检查用户是否有权限
        if(userMapper.getUserById(userId).getRole().equals(Role.USER.getCode())){
            throw new BusinessException(403, "无权限访问");
        }

        // 条件分页查询题目列表
        List<ProblemVo> problemVos;
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            problemVos = problemMapper.list(param);
        } finally {
            PageHelper.clearPage();
        }

        // 封装结果
        Page<ProblemVo> page = (Page<ProblemVo>) problemVos;
        PageResult<ProblemVo> pageResult = new PageResult<>();
        pageResult.setPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setList(page.getResult());
        pageResult.setPageNum(page.getPageNum());

        return pageResult;
    }
}
