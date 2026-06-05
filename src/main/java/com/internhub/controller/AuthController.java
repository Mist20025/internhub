package com.internhub.controller;

import com.internhub.common.Result;
import com.internhub.dto.LoginRequest;
import com.internhub.dto.RegisterRequest;
import com.internhub.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 认证接口：注册 & 登录
 * 这两个接口在 SecurityConfig 中已放行，不需要 Token
 */
@Tag(name = "认证管理", description = "用户注册和登录")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * POST /api/auth/register
     * Body: { "username": "xxx", "password": "xxx", "email": "xx@xx.com" }
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return Result.success("注册成功");
    }

    /**
     * POST /api/auth/login
     * Body: { "username": "xxx", "password": "xxx" }
     * 返回: { "token": "eyJhbG...", "username": "xxx" }
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginRequest request) {
        return Result.success("登录成功", userService.login(request));
    }
}
