package com.internhub.config;

import com.internhub.common.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器 —— 拦截所有请求，验证 Token
 *
 * 工作流程（面试必问）：
 * 1. 从请求头 Authorization 中取出 Token
 * 2. 验证 Token 是否有效
 * 3. 有效 → 把用户信息存入 Spring Security 上下文
 * 4. 无效 → 放行（由 SecurityConfig 中的配置决定哪些接口需要认证）
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 从请求头中获取 Token
        String authHeader = request.getHeader("Authorization");

        // 2. Token 存在且格式正确（Bearer xxx）
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // 去掉 "Bearer " 前缀

            // 3. 验证 Token，提取用户名
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);

                // 4. 将用户认证信息存入 Spring Security 上下文
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 5. 放行，继续后续过滤器
        filterChain.doFilter(request, response);
    }
}
