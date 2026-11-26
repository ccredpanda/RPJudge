package com.ccredpanda.rpjudge.mapper;

import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.entity.Solution;
import com.ccredpanda.rpjudge.vo.SolutionDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/*
*  持久层题解接口
* */
@Mapper
public interface SolutionMapper {

    // 通过用户ID获得题解
    List<Solution> findSolutionsByUserId(Long userId);

    // 通过用户ID获得用户最近状态为已发布的题解并只返回指定前n条
    Collection<Solution> findRecentPublishedByUserId(@Param("userId") Long userId,
                                                     @Param("limit") Integer limit);
    // 分页查询用户发布题解
    List<SolutionDataVo> list(UserPageQueryParam param);

    // 通过题目ID统计题解数量
    Integer countByProblemId(Long problemId);
}
