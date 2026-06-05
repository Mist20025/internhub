package com.internhub.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类：生成 Token、解析 Token、验证 Token
 *
 * JWT 是什么？
 *   一个加密字符串，包含用户信息和过期时间。
 *   用户登录后，后端返回 Token；后续请求携带 Token，后端就能识别"你是谁"。
 *
 * 面试常见问题：
 *   Q: JWT 和 Session 的区别？
 *   A: JWT 存在客户端，服务端不存状态（无状态），适合分布式系统；
 *      Session 存在服务端，需要共享存储，适合单体应用。
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * 根据用户名生成 JWT Token
     */
    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(username)                              // 把用户名存进 Token
                .issuedAt(new Date())                           // 签发时间
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 过期时间
                .signWith(key)                                  // 签名
                .compact();
    }

    /**
     * 从 Token 中提取用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 验证 Token 是否有效（不抛出异常即为有效）
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析 Token 中的 Claims（包含用户名、过期时间等）
     */
    private Claims getClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
