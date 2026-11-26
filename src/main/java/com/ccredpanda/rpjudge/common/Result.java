package com.ccredpanda.rpjudge.common;


import lombok.Data;

/*
* 后端统一响应结果
* */
@Data
public class Result {
    //响应前端状态码
    private Integer code;

    //响应前段消息
    private String message;

    //响应前段数据
    private Object data;

    /*
    * 默认成功响应 (无需返回数据)
    * */
    public static Result success(){
        Result result = new Result();
        result.code = 200;
        result.message = "success";
        result.data = null;
        return result;
    }

    /*
    * 响应成功 (带返回值)
    * */
    public static Result success(Object data){
        Result result = new Result();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }

    /*
    * 响应失败
    * */
    public static Result error(Integer code, String message){
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = null;
        return result;
    }

}
