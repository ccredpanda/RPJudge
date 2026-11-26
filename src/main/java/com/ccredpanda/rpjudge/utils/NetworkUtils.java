package com.ccredpanda.rpjudge.utils;

import com.ccredpanda.rpjudge.exception.BusinessException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/*
*  用户网络请求处理工具类
* */
@Slf4j
public class NetworkUtils {

    /*
    *  通过网络请求体获得用户名
    * */
    public static String getUsername(HttpServletRequest httpServletRequest){
        // 获得当前请求的token
        String token = httpServletRequest.getHeader("Authorization");
        // 解析token
        Claims claims = null;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            log.info("用户登录过期");
            throw new BusinessException(401, e.getMessage());
        }
        // 获得用户名并返回
        Object username = claims.get("username");
        if (username == null) {
            throw new BusinessException(401, "用户未登录");
        }
        return username.toString();
    }

    /*
    *  通过网络请求体获得用户ID
    * */
    public static Long getUserId(HttpServletRequest httpServletRequest){
        // 获得当前请求的token
        String token = httpServletRequest.getHeader("Authorization");
        // 解析token
        Claims claims = null;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            log.info("用户登录已过期");
            throw new BusinessException(401, e.getMessage());
        }
        // 获得用户ID并返回
        Object sub = claims.get("sub");
        if (sub == null) {
            throw new BusinessException(401, "用户未登录");
        }
        return Long.valueOf(sub.toString());
    }
}
