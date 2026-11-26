package com.ccredpanda.rpjudge;

import com.ccredpanda.rpjudge.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtTest {

    @Test
    public void jwtGenerateTest(){

        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("zhangsan", 123456);

        String token = JwtUtils.generateJwt(userInfo,3600L * 1000);

        System.out.println(token);
    }

    @Test
    public void jwtParseTest(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ6aGFuZ3NhbiI6MTIzNDU2LCJpYXQiOjE3OTUxNTcwMTksImV4cCI6MTc5NTE2MDYxOX0.E0zGiqvGW9-QkvOsPjuLQGkvY7Etv0W6iEK4ms0DiMI";

        Claims claims = null;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            System.out.println("令牌解析失败");
            return ;
        }

        System.out.println(claims);
    }
}
