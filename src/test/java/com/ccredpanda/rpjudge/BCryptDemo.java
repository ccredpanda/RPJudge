package com.ccredpanda.rpjudge;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptDemo {
    @Test
    public void securityTest(){

        //工作因子
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        String rawPassword = "mySecretPassword";
        String hash = encoder.encode(rawPassword); //生成密文

        //验证密码
        boolean match = encoder.matches(rawPassword,hash);
        boolean wrongMatch = encoder.matches("wrongPassword",hash);

        System.out.println("密文:" + hash);
        System.out.println("正确密码验证:" + match);
        System.out.println("错误密码验证:" + wrongMatch);
    }
}
