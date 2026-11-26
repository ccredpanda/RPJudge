package com.ccredpanda.rpjudge.interceptor;

import com.ccredpanda.rpjudge.exception.BusinessException;
import com.ccredpanda.rpjudge.utils.JwtUtils;
import com.ccredpanda.rpjudge.utils.NetworkUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.regex.Pattern;


/*
*  拦截器
* */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    // 通过正则表达式来过滤白名单
    private static final Pattern PUBLIC_GET = Pattern.compile(
            "^/user/\\d+(/solved-problems|/solutions)?$"   // 3.6 / 3.7 / 3.8
                    + "|^/problem/(list|tags)$"                       // 4.1 / 4.3
                    + "|^/problem/\\d+$");                            // 4.2
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        if ("GET".equalsIgnoreCase(request.getMethod())
                && PUBLIC_GET.matcher(uri).matches()) {
            return true;
        }


        // 判断是否有令牌
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if(token == null || token.isEmpty()){
            throw new BusinessException(401, "用户未登录");
        }

        // 解析令牌是否合法并且将令牌中userId记录下来
        Claims claims;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            throw new BusinessException(401, "登录已过期，请重新登录");
        }

        Object sub = claims.get("sub");
        if (sub == null) {
            throw new BusinessException(401, "用户未登录");
        }

        request.setAttribute("userId", Long.valueOf(sub.toString()));

        // 放行
        return true;
    }
}
