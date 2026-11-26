package com.ccredpanda.rpjudge.service.impl;


import com.ccredpanda.rpjudge.common.PageResult;
import com.ccredpanda.rpjudge.common.UserPageQueryParam;
import com.ccredpanda.rpjudge.entity.User;
import com.ccredpanda.rpjudge.exception.BusinessException;
import com.ccredpanda.rpjudge.mapper.ProblemMapper;
import com.ccredpanda.rpjudge.mapper.SolutionMapper;
import com.ccredpanda.rpjudge.mapper.UserMapper;
import com.ccredpanda.rpjudge.service.SolutionService;
import com.ccredpanda.rpjudge.vo.SolutionDataVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/*
*  服务层题解接口实现类
* */
@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private SolutionMapper solutionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProblemMapper problemMapper;

    /*
    *  返回用户题解并分页
    * */
    @Override
    public PageResult<SolutionDataVo> getSolutions(UserPageQueryParam param) {

        // 查询用户是否存在
        User user = userMapper.getUserById(param.getUserId());
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        // startPage 紧贴要分页的查询
        List<SolutionDataVo> list;
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            list = solutionMapper.list(param);
        } finally {
            PageHelper.clearPage();
        }
        Page<SolutionDataVo> page = (Page<SolutionDataVo>) list;

        // 组装分页结果
        PageResult<SolutionDataVo> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setPages(page.getPages());
        result.setList(page.getResult());
        return result;
    }
}
