# 简历项目描述 & 面试话术

## 一、简历上这样写

### 项目名称
**InternHub — 实习信息管理平台**（后端开发）

### 项目描述（2-3 行）
基于 Spring Boot 3 + MyBatis + MySQL 开发的 RESTful API 服务，提供实习岗位管理、用户认证授权和投递记录追踪功能。支持分页搜索、JWT 无状态鉴权、BCrypt 密码加密，配套 Swagger API 文档。

### 技术栈
Spring Boot 3 · MyBatis · MySQL · JWT · Spring Security · Swagger/Knife4j · Maven · Git

### 个人职责
- 独立完成后端架构设计，采用 Controller → Service → Mapper 三层分层架构
- 设计并实现用户认证模块：基于 Spring Security + JWT 的无状态鉴权方案，BCrypt 加密存储密码
- 实现岗位管理 CRUD 接口，支持多条件筛选和分页查询，使用 MyBatis 动态 SQL
- 统一封装 API 响应格式（Result），实现全局异常捕获和参数校验
- 编写 API 文档（Swagger），方便前后端联调

---

## 二、面试常见问题（提前准备）

### Q1: 为什么用 JWT 而不是 Session？
**答**：JWT 是无状态的，用户信息存在 Token 里，服务端不存 Session。好处是：
- 适合分布式系统（多个服务实例共享同一个密钥就能验证 Token）
- 不需要服务端存储，省内存
- 前端存在 localStorage，每次请求带上就行

### Q2: CSRF 攻击是什么？你的项目怎么防的？
**答**：CSRF 是利用浏览器自动携带 Cookie 的特性伪造请求。我的项目是前后端分离的，JWT 存在 localStorage 里，不依赖 Cookie，浏览器不会自动带上 Token，所以天然防 CSRF。同时 SecurityConfig 里也显式禁用了 CSRF 保护。

### Q3: 密码存的是明文吗？
**答**：不是。用 BCrypt 加密存储。BCrypt 的优势是：
- 自带盐值（salt），每条密码的盐不同
- 哈希计算速度慢，暴力破解成本高
- 是不可逆的——只能比对，不能"解密"

### Q4: 分页怎么实现的？
**答**：用 MyBatis 的 LIMIT + OFFSET。前端传 page 和 pageSize，后端计算 offset = (page - 1) * pageSize。查询分为两步：
- COUNT(*) 获取总记录数
- 带 LIMIT 的查询获取当前页数据
- 封装成 PageResult 返回，包含 list、total、page、totalPages

### Q5: 你的项目里 MyBatis XML 里 `WHERE 1=1` 是干嘛的？
**答**：动态 SQL 的技巧。后面所有条件都用 `AND` 开头，如果所有可选参数都为空（没有搜索关键词、没有地点筛选），SQL 就变成 `WHERE 1=1`，不会报语法错误。如果不用这个技巧，就需要判断"这是第一个条件吗？前面要不要加 AND？"。

### Q6: 如果面试官问"你这个项目有什么不足"
**答**：
- 目前用的是 H2 内存数据库，数据重启后会丢失，生产环境需要切 MySQL
- Redis 缓存还没加，热点数据可以缓存提高性能
- 没有单元测试覆盖
- 这些是下一步迭代的方向

---

## 三、如何跑起来给面试官看

```bash
cd internhub
mvn spring-boot:run
# 浏览器打开 http://localhost:8090/doc.html
```

Swagger 页面可以直接发请求测试所有接口。面试官看到能跑的项目比光看代码强十倍。

---

## 四、投递策略建议

根据之前搜的北京/天津岗位，你的投递关键词：
- BOSS直聘搜："Java 实习 北京 大二"
- 实习僧搜："后端实习 暑期 北京"
- 前面搜到的匹配度高又接受低年级的公司：**库巴扎（北京/天津）**、**慧策**、**新长城**

简历上把 InternHub 项目放第一位，前面标注"个人项目 · 2026.06"，技术栈写清楚：Spring Boot + MyBatis + JWT + MySQL。
