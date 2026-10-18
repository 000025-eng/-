package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 令牌工具类
 */
public class JwtUtils {

    // 1. 定义签名密钥（必须 >= 32 个字符）
    // ⚠️ 实际项目中，建议将此密钥提取到 application.yml 配置文件中
    private static final String SECRET_STRING = "itheima-very-long-secret-key-for-jwt-123456";

    // 2. 将字符串转换为 SecretKey 对象（只转换一次，作为常量使用）
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    // 3. 定义令牌过期时间（例如：2 小时，单位毫秒）
    private static final long EXPIRATION_TIME = 2 * 3600 * 1000;

    /**
     * 生成 JWT 令牌
     * @param claims 要存放的数据（比如 id、name 等）
     * @return 生成的 JWT 字符串
     */
    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 设置签名算法和密钥
                .addClaims(claims)                              // 添加自定义数据
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .compact();                                     // 生成令牌
    }

    /**
     * 解析 JWT 令牌
     * @param jwt 客户端传来的 JWT 字符串
     * @return 解析出来的数据（Claims 对象）
     * @throws io.jsonwebtoken.JwtException 如果令牌被篡改或已过期，会抛出异常
     */
    public static Claims parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // 设置解析用的密钥
                .build()
                .parseClaimsJws(jwt)       // 解析令牌
                .getBody();                // 获取载荷（Payload）部分
    }


}