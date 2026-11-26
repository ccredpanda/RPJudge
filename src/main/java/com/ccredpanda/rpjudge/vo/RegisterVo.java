package com.ccredpanda.rpjudge.vo;

import com.ccredpanda.rpjudge.dto.RegisterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
*  注册视图类
* */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RegisterVo {
    //用户ID
    private Long id;

    // 用户名
    private String username;

    //用户昵称
    private String nickname;
}
