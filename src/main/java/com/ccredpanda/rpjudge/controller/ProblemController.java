package com.ccredpanda.rpjudge.controller;

import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.ProblemPageParam;
import com.ccredpanda.rpjudge.common.Result;
import com.ccredpanda.rpjudge.service.ProblemService;
import com.ccredpanda.rpjudge.vo.ProblemDataVo;
import com.ccredpanda.rpjudge.vo.ProblemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
*  题目列表请求处理
* */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    /*
    *  分页查询题目列表 4.1
    * */
    @GetMapping("/list")
    public Result getList(ProblemPageParam param){

        PageResult<ProblemVo> list = problemService.getList(param);

        return Result.success(list);
    }

    /*
    *  题目详情  4.2
    * */
    @GetMapping("/{id}")
    public Result getProblemById(@PathVariable("id") Long problemId, @RequestAttribute(name = "userId" , required = false) Long userId){

        ProblemDataVo problemDataVo = problemService.getProblemById(problemId, userId);

        return Result.success(problemDataVo);
    }

    /*
    *  标签列表 4.3
    * */
    @GetMapping("/tags")
    public Result getTags(){

        List<String> list = problemService.getTags();

        return Result.success(list);
    }


}
