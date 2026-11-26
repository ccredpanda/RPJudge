package com.ccredpanda.rpjudge.utils;

import java.nio.charset.StandardCharsets;

/*
*  字符串处理工具类
* */
public class CodeUtils {

    /*
    *  判断一个字符串是否超过100KB
    * */
    public static boolean exceeds100KB(String s){
        if(s == null)return false;
        int maxBytes = 100 * 1024;
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        return bytes.length > maxBytes;
    }
}
