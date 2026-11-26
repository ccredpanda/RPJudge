package com.ccredpanda.rpjudge.common;

import lombok.Data;

/*
*  用户分页请求
* */
@Data
public class UserPageQueryParam {
    // 需要查询的用户ID
    private Long userId;

    // 当前页码
    private Integer pageNum = 1;

    // 每页展示记录数
    private Integer pageSize = 50;

    // 已过题题号
    private Long problemId;

}
