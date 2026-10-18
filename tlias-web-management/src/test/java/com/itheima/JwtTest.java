package com.itheima;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
//生成jwt令牌的
//    @Test
//    public void  testGenerateJwt(){//前面是签名算法，后面是密钥用的itheima,jwt令牌是可以添加自定义信息的
//        Map<String, Object> dataMap = new HashMap<>();
//        dataMap.put("id",1);
//        dataMap.put("name","admin");
//        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aXRoZWltYQ==")
//                .addClaims(dataMap)//添加自定义集合，且有效期为两个小时
//                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))//读取到毫秒值，设置过期时间为一个小时，
//                .compact();//生成令牌
//        System.out.println(jwt);
//    }
//@Test//解析JWT令牌
//public void testParseJWT(){
//
//    String token ="令牌";
//    Claims claims = Jwts.parser().setSigningKey("aXRoZWltYQ==")//指定密钥
//            .parseClaimsJwt(token)
//            .getBody();
//}
//    以上是旧版本加密的写法0.9.1的写法
//
//    接下来是新版本的写法


        // 生成jwt令牌
        @Test
        public void testGenerateJwt() {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("id", 1);
            dataMap.put("name", "admin");

            // 1. 将字符串密钥转换为 SecretKey 对象（新版要求）
            // 注意：这里的密钥字符串长度必须至少为 32 个字符（256位），否则会报 WeakKeyException 错误
            String secretString = "itheima-very-long-secret-key-for-jwt-123456";
            // 如果上面这行报错提示密钥太短，请改成更长的字符串，例如：
            // String secretString = "itheima-very-long-secret-key-for-jwt-123456";
            SecretKey secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

            // 2. 使用新的 SecretKey 生成令牌
            String jwt = Jwts.builder()
                    .signWith(secretKey, SignatureAlgorithm.HS256) // 传入 SecretKey 对象和算法
                    .addClaims(dataMap)
                    // 3. 设置过期时间为 2 小时（2 * 3600 * 1000 毫秒）
                    .setExpiration(new Date(System.currentTimeMillis() + 2 * 3600 * 1000))
                    .compact();

            System.out.println(jwt);
        }

    }

