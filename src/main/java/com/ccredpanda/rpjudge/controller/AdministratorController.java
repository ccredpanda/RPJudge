package com.ccredpanda.rpjudge.controller;

import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.ProblemPageParam;
import com.ccredpanda.rpjudge.common.Result;
import com.ccredpanda.rpjudge.dto.ProblemCreateDto;
import com.ccredpanda.rpjudge.dto.ProblemUpdateDto;
import com.ccredpanda.rpjudge.service.ProblemService;
import com.ccredpanda.rpjudge.vo.DeleteProblemVo;
import com.ccredpanda.rpjudge.vo.ProblemVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
*  管理员请求处理
* */
@RestController
public class AdministratorController {

    @Autowired
    private ProblemService problemService;

    /*
    *  管理员新增题目 4.4
    * */
    @PostMapping("/problem")
    public Result add(@RequestBody @Valid ProblemCreateDto p, @RequestAttribute("userId") Long id){

        Map<String, Object> result = problemService.add(p, id);

        return Result.success(result);
    }

    /*
    *  管理员更新题目 4.5
    * */
    @PutMapping("/problem/{id}")
    public Result update(@RequestBody @Valid ProblemUpdateDto p, @PathVariable Long id, @RequestAttribute("userId") Long userId){

        p.setProblemId(id);
        problemService.update(p, userId);

        return Result.success();
    }

    /*
    *  管理员删除(或下架)题目 4.6
    * */
    @DeleteMapping("/problem/{id}")
    public Result delete(@PathVariable Long id, @RequestAttribute("userId") Long userId){

        DeleteProblemVo deleted = problemService.delete(id, userId);

        return Result.success(deleted);
    }

    /*
    *  管理员端题目列表 4.7
    * */
    @GetMapping("/admin/problem/list")
    public Result adminGetList(ProblemPageParam param,@RequestAttribute("userId") Long userId){

        PageResult<ProblemVo> list = problemService.adminGetList(param, userId);

        return Result.success(list);
    }
}
