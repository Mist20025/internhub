package com.internhub.entity;

import java.time.LocalDateTime;

/**
 * 用户实体 — 对应 users 表
 *
 * @Data 是 Lombok 注解，自动生成 getter/setter/toString/equals/hashCode
 * 如果没有安装 Lombok 插件，IDE 可能会标红，但不影响编译运行
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDateTime createdAt;

    // ===== Getter/Setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
