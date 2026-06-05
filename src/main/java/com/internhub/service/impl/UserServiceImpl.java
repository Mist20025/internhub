package com.internhub.service.impl;

import com.internhub.common.JwtUtil;
import com.internhub.dto.LoginRequest;
import com.internhub.dto.RegisterRequest;
import com.internhub.entity.User;
import com.internhub.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务：注册 & 登录
 *
 * 面试知识点：
 * - BCrypt 加密是不可逆的（所以登录时不"解密"，而是"比对"）
 * - 永远不会在日志/响应里返回用户密码
 */
@Service
public class UserServiceImpl {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 注册
     */
    public void register(RegisterRequest request) {
        // 1. 检查用户名是否重复
        if (userMapper.countByUsername(request.getUsername()) > 0) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 2. 创建用户，密码用 BCrypt 加密存储
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 加密！
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userMapper.insert(user);
    }

    /**
     * 登录
     * @return { token: "xxx", username: "zhangsan" }
     */
    public Map<String, String> login(LoginRequest request) {
        // 1. 根据用户名查找用户
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("用户名或密码错误");
        }

        // 2. 比对密码（明文 vs BCrypt 密文）
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("用户名或密码错误");
        }

        // 3. 生成 JWT Token，返回给前端
        String token = jwtUtil.generateToken(user.getUsername());

        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        result.put("username", user.getUsername());
        return result;
    }
}
