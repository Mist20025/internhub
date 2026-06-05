package com.internhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/Knife4j API 文档配置
 *
 * 项目启动后访问: http://localhost:8080/doc.html
 *
 * API 文档的作用：
 * 1. 开发时方便调试（直接在页面上发请求）
 * 2. 团队协作（前端看着文档写调用代码）
 * 3. 面试时加分（证明你有文档意识）
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("InternHub — 实习信息管理平台 API")
                        .version("1.0.0")
                        .description("基于 Spring Boot 3 + MyBatis + MySQL + JWT 的实习信息管理后端服务")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your-email@example.com")));
    }
}
