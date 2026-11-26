package com.ccredpanda.rpjudge.mapper;

import com.ccredpanda.rpjudge.entity.ProblemCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemCaseMapper {
    // 插入测试样例
    void add(ProblemCase problemCase);

    // 通过题目ID 删除测试案例
    void deleteByProblemId(Long problemId);

    // 通过题目ID 获得测试案例
    List<ProblemCase> getByProblemId(Long problemId);
}
