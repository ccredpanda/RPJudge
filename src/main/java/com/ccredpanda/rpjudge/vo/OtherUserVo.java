package com.ccredpanda.rpjudge.vo;

import com.ccredpanda.rpjudge.entity.Solution;
import com.ccredpanda.rpjudge.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
*  其他用户主页信息
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherUserVo {
    // 用户ID
    private Long id;

    // 用户昵称
    private String  nickname;

    // 用户头像
    private String avatar;

    // 用户签名
    private String signature;

    // 用户过题数
    private Integer solvedCount;

    // 用户提交数
    private Integer submitCount;

    // 已发布题解数
    private Integer solutionCount;

    // 已解答问题编号
    private List<Long> solvedProblems;

    // 最近发布的题解
    private List<SolutionVo> recentSolutions;

    public OtherUserVo(User user){
        this.avatar = user.getAvatar();
        this.id = user.getId();
        this.solutionCount = user.getSolutionCount();
        this.signature = user.getSignature();
        this.nickname = user.getNickname();
        this.solvedCount = user.getSolvedCount();
        this.submitCount = user.getSubmitCount();
    }
}
