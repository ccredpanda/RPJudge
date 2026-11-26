package com.ccredpanda.rpjudge.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateMyselfDto {
    // 用户昵称
    @Size(max = 32, message = "昵称不能超过 32 字")
    private String nickname;
    // 用户签名
    @Size(max = 128, message = "个性签名不超过 128 字")
    private String signature;
    // 用户头像 (v1 为url)
    @Size(max = 256, message = "头像 URL 过长")
    private String avatar;
}
