package com.ccredpanda.rpjudge.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
*  judge0 响应体
* */
@Data
public class Judge0Response {

    private String stdout;

    private String time;

    private Integer memory;

    private String stderr;

    private String compile_output;

    // 响应状态
    @Data
    public static class Status {
        // id
        private Integer id;
        // 判题描述 (Accepted……)
        private String description;
    }

    public Status getStatus(){
        return new Status();
    }
}
