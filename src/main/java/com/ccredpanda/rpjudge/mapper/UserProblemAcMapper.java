package com.ccredpanda.rpjudge.mapper;

import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.entity.UserProblemAc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
*  持久层用户已过题接口
* */
@Mapper
public interface UserProblemAcMapper {

    /*
    *  查询用户所过题并分页
    * */
    List<UserProblemAc> list(UserPageQueryParam userPageQueryParam);

    // 通过题目ID和用户ID获得用户提交状态
    Integer countUserStatusById(Long problemId, Long userId);

    // 新增一个用户通过记录
    void add(UserProblemAc userProblemAc);

    // 通过数据库来判断是不是首次插入
    Integer insertIgnore(Long userId, Long problemId, Long id);
}
