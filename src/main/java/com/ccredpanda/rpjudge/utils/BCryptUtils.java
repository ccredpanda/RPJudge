package com.ccredpanda.rpjudge.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/*
*  用户密码处理工具类
* */
public class BCryptUtils {
    /*
    *  生成密文
    * */
    public static String generateKey(String rawPassword){
        // 设置工作因子
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        // 生成密文
        String hash = encoder.encode(rawPassword);

        return hash;
    }
    /*
    *  核对密码
    * */
    public static boolean matchKey(String Password, String hash){
        // 设置工作因子
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        // 匹配密文
        return encoder.matches(Password,hash);
    }

}
