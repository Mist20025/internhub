package com.internhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * InternHub — 实习信息管理平台
 *
 * 技术栈：Spring Boot 3 + MyBatis + MySQL/H2 + JWT + Redis + Swagger
 *
 * 核心功能：
 * 1. 用户注册登录（JWT 鉴权）
 * 2. 实习岗位 CRUD + 分页搜索
 * 3. 投递记录管理
 * 4. API 文档自动生成
 */
@SpringBootApplication
public class InternHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternHubApplication.class, args);
    }
}
