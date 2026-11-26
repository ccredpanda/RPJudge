package com.ccredpanda.rpjudge.vo;
import lombok.Data;

/*
*  删除题目 Vo
* */
@Data
public class DeleteProblemVo {
    // 是否物理删除
    private boolean deleted = false;

    // 是否改为下架
    private boolean offline = false;
}
