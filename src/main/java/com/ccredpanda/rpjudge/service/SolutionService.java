package com.ccredpanda.rpjudge.service;

import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.vo.SolutionDataVo;

import java.util.List;

public interface SolutionService {

    // 通过分页请求对得到的题解分页
    PageResult<SolutionDataVo> getSolutions(UserPageQueryParam param);
}
