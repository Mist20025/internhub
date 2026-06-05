# InternHub — 实习信息管理平台

基于 **Spring Boot 3 + MyBatis + MySQL + JWT** 的实习信息管理后端服务。

## 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 3.2.5 | 应用框架 |
| MyBatis | 3.0.3 | ORM / 数据库访问 |
| MySQL / H2 | — | 关系型数据库（H2 用于开发） |
| Spring Security + JWT | 0.12.5 | 认证鉴权 |
| Knife4j (Swagger) | 4.3.0 | API 文档 |
| BCrypt | — | 密码加密 |
| Hutool | 5.8.25 | 通用工具类 |

## 快速启动

### 前置要求
- JDK 17+
- Maven 3.9+

### 运行（开发模式 — H2 内存数据库）

```bash
cd internhub
mvn spring-boot:run
```

启动后访问：
- **API 文档（Swagger）**：http://localhost:8080/doc.html
- **H2 数据库控制台**：http://localhost:8080/h2-console

### 切换到 MySQL

1. 确保 MySQL 已安装并运行
2. 执行 `db/schema.sql` 建表
3. 修改 `src/main/resources/application.yml` 中的数据源配置

## API 接口

### 认证（无需 Token）
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/register` | 用户注册 |
| POST | `/api/auth/login` | 用户登录（返回 JWT） |

### 岗位管理（需要 Token）
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/jobs` | 分页搜索岗位 |
| GET | `/api/jobs/{id}` | 岗位详情 |
| POST | `/api/jobs` | 新增岗位 |
| PUT | `/api/jobs/{id}` | 更新岗位 |
| DELETE | `/api/jobs/{id}` | 删除岗位 |

### 投递管理（需要 Token）
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/applications` | 投递岗位 |
| GET | `/api/applications` | 我的投递列表 |
| PUT | `/api/applications/{id}/status` | 更新投递状态 |

### 请求示例

```bash
# 1. 注册
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"123456","email":"test@example.com"}'

# 2. 登录（复制返回的 token）
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"123456"}'

# 3. 查询岗位（替换 YOUR_TOKEN）
curl http://localhost:8080/api/jobs?keyword=Java&location=北京&page=1&pageSize=10 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## 项目结构

```
internhub/
├── src/main/java/com/internhub/
│   ├── InternHubApplication.java       # 启动入口
│   ├── config/
│   │   ├── SecurityConfig.java         # Spring Security 配置
│   │   ├── JwtAuthenticationFilter.java # JWT 认证过滤器
│   │   └── SwaggerConfig.java          # API 文档配置
│   ├── controller/
│   │   ├── AuthController.java         # 注册登录接口
│   │   ├── JobController.java          # 岗位 CRUD 接口
│   │   └── ApplicationController.java  # 投递管理接口
│   ├── service/impl/
│   │   ├── UserServiceImpl.java        # 用户业务逻辑
│   │   ├── JobServiceImpl.java         # 岗位业务逻辑
│   │   └── ApplicationServiceImpl.java # 投递业务逻辑
│   ├── mapper/
│   │   ├── UserMapper.java             # 用户数据访问
│   │   ├── JobMapper.java              # 岗位数据访问
│   │   └── ApplicationMapper.java      # 投递数据访问
│   ├── entity/                         # 数据实体
│   ├── dto/                            # 请求/响应 DTO
│   ├── common/
│   │   ├── Result.java                 # 统一响应格式
│   │   └── JwtUtil.java                # JWT 工具类
│   └── exception/
│       └── GlobalExceptionHandler.java  # 全局异常处理
├── src/main/resources/
│   ├── application.yml                 # 应用配置
│   ├── db/schema.sql                   # 建表脚本
│   └── mapper/                         # MyBatis XML 映射文件
└── pom.xml                             # Maven 依赖配置
```

## 面试要点

这个项目展示了以下后端开发核心能力：

1. **RESTful API 设计** — 标准 HTTP 方法 + 资源命名 + 统一响应格式
2. **认证鉴权** — JWT + Spring Security 无状态认证方案
3. **数据库设计** — 多表关联、外键约束、索引
4. **分页查询** — LIMIT + OFFSET + 动态 SQL
5. **参数校验** — `@Valid` + 统一异常处理
6. **API 文档** — Swagger/Knife4j 自动生成
7. **密码安全** — BCrypt 加密存储
8. **分层架构** — Controller → Service → Mapper 三层分离
