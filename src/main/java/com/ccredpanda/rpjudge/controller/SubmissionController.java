package com.ccredpanda.rpjudge.controller;

import com.ccredpanda.rpjudge.common.Result;
import com.ccredpanda.rpjudge.dto.SubmissionDto;
import com.ccredpanda.rpjudge.service.SubmissionService;
import com.ccredpanda.rpjudge.vo.SubmissionVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
*  提交题目&判题
* */
@RestController
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    /*
    *  提交代码
    * */
    @PostMapping
    public Result submitCode(@RequestBody @Valid SubmissionDto submissionDto, @RequestAttribute("userId") Long userId){

        SubmissionVo submissionVo = submissionService.submitCode(submissionDto, userId);

        return Result.success(submissionVo);
    }
}
