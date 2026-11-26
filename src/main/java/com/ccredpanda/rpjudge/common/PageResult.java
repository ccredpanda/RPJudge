package com.ccredpanda.rpjudge.common;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
* 分页响应格式
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T>{
    //总记录数
    private Long total;

    //当前页码
    private Integer pageNum = 1;

    //每页条数
    @Size(max = 100, message = "每页上下数为100")
    private Integer pageSize = 20;

    //总页数
    private Integer pages;

    //当前页数据
    private List<T> list;
}
