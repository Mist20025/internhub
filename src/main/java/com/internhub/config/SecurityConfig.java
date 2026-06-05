package com.internhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全配置
 *
 * 核心设计：
 * - 注册和登录接口放行，不需要 Token
 * - 其他接口需要携带 Token
 * - 使用无状态 Session（STATELESS），因为 JWT 自身包含用户信息
 * - BCrypt 加密密码，绝对不能存明文
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF（前后端分离项目，不需要 Cookie，也就不会有 CSRF 攻击）
            .csrf(csrf -> csrf.disable())

            // 无状态会话（JWT 自带信息，服务端不存 Session）
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 允许 H2 控制台使用 iframe（H2 控制台需要 frame）
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

            // 接口权限配置
            .authorizeHttpRequests(auth -> auth
                // 放行：注册、登录、API 文档、H2 控制台
                .requestMatchers(
                    "/api/auth/**",
                    "/doc.html",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/h2-console/**"
                ).permitAll()
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
            )

            // 把 JWT 过滤器加到 Spring Security 过滤器链中
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 密码加密器 — BCrypt 是业界标准
     *
     * 面试常见问题：
     * Q: 为什么用 BCrypt 而不是 MD5？
     * A: MD5 太快，容易被彩虹表暴力破解。BCrypt 自带盐值且计算慢，抗暴力破解。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
