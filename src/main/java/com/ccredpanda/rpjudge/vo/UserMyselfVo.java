package com.ccredpanda.rpjudge.vo;

import com.ccredpanda.rpjudge.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/*
*  用户个人信息视图类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMyselfVo {
    //用户ID
    private Long id;

    //用户名称
    private String username;

    //用户昵称
    private String nickname;

    //用户头像 URL
    private String avatar;

    //用户个性签名
    private String signature;

    //0 为普通用户 1 为管理员
    private Integer role;

    //用户过题数
    private Integer solvedCount;

    //用户提交数
    private Integer submitCount;

    //已发布题解数
    private Integer solutionCount;

    //用户默认已过题数(50)
    private List<Long> solvedProblems;

    /*
    *  通过用户实体类构造
    * */
    public UserMyselfVo(User user){
        this.id = user.getId();
        this.avatar = user.getAvatar();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.signature = user.getSignature();
        this.solutionCount = user.getSolutionCount();
        this.solvedCount = user.getSolvedCount();
        this.username = user.getUsername();
        this.submitCount = user.getSubmitCount();
    }
}
