package com.ccredpanda.rpjudge.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/*
 * jwt令牌生成与解析工具类
 * */

public class JwtUtils {

    // 签名秘钥 (长度必须大于等于256位，即32个字节以上)
    private static final String SECRET_STRING = "MySuperSecretKeyForJwtTokenGeneration123456!";

    // API秘钥
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    // 生成jwtToken
    public static String generateJwt(Map<String, Object> claims, Long ttlMillis){
        // 计算过期时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + ttlMillis);

        return Jwts.builder()
                // 1. 设置自定义载荷
                .setClaims(claims)
                // 2. 设置签发时间
                .setIssuedAt(now)
                // 3. 设置过期时间
                .setExpiration(exp)
                // 4. 设置签名密钥 (新版本直接传 Key 即可，内部会自动识别算法)
                .signWith(SECRET_KEY)
                // 5. 生成字符串
                .compact();
    }

    // 解析jwtToken
    public static Claims parseJwt(String token){
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}