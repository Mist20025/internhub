-- ============================================================
-- InternHub 数据库建表脚本
-- 兼容 MySQL 和 H2（开发环境使用 H2 内存数据库）
-- ============================================================

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名',
    password    VARCHAR(255) NOT NULL       COMMENT '加密后的密码',
    email       VARCHAR(100)                COMMENT '邮箱',
    phone       VARCHAR(20)                 COMMENT '手机号',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间'
);

-- 公司表
CREATE TABLE IF NOT EXISTS companies (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE COMMENT '公司名称',
    industry    VARCHAR(50)                COMMENT '行业（互联网/金融/教育...）',
    size        VARCHAR(20)                COMMENT '规模（50-150人/500-2000人...）',
    location    VARCHAR(100)               COMMENT '办公地点',
    website     VARCHAR(200)               COMMENT '官网地址',
    description TEXT                       COMMENT '公司简介',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 岗位表
CREATE TABLE IF NOT EXISTS jobs (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id   BIGINT       NOT NULL             COMMENT '关联公司 ID',
    title        VARCHAR(100) NOT NULL             COMMENT '岗位名称（Java后端实习生）',
    description  TEXT                              COMMENT '岗位描述',
    requirement  TEXT                              COMMENT '任职要求',
    salary_range VARCHAR(50)                       COMMENT '薪资范围（200-300元/天）',
    location     VARCHAR(100)                      COMMENT '工作地点（北京/天津）',
    job_type     VARCHAR(20) DEFAULT '日常实习'     COMMENT '岗位类型（暑期实习/日常实习/校招）',
    status       VARCHAR(20) DEFAULT 'OPEN'        COMMENT 'OPEN=在招 CLOSED=结束',
    posted_at    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    deadline     DATE                              COMMENT '投递截止日期',

    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

-- 投递记录表
CREATE TABLE IF NOT EXISTS applications (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT       NOT NULL             COMMENT '用户 ID',
    job_id       BIGINT       NOT NULL             COMMENT '岗位 ID',
    resume_url   VARCHAR(200)                      COMMENT '简历文件链接（选填）',
    status       VARCHAR(30) DEFAULT 'PENDING'     COMMENT 'PENDING=已投递 SCREENING=初筛中 INTERVIEW=面试 OFFER=录用 REJECTED=已拒绝',
    notes        TEXT                              COMMENT '个人备注',
    applied_at   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '投递时间',
    updated_at   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE
);

-- ========== 测试数据（方便开发时看到效果） ==========
INSERT INTO companies (name, industry, size, location, website, description) VALUES
('字节跳动', '互联网', '10000人以上', '北京海淀区', 'https://www.bytedance.com', '全球领先的科技公司，旗下有抖音、今日头条等产品'),
('美团', '互联网', '5000-10000人', '北京朝阳区', 'https://www.meituan.com', '中国领先的生活服务电子商务平台'),
('BOSS直聘', '互联网/招聘', '1000-5000人', '北京海淀区', 'https://www.zhipin.com', '领先的在线招聘平台'),
('天津凯莱英医药集团', '医药/IT', '500-2000人', '天津滨海新区', 'https://www.asymchem.com', '全球领先的CDMO企业');

INSERT INTO jobs (company_id, title, description, requirement, salary_range, location, job_type, status, deadline) VALUES
(1, 'Java 后端开发实习生', '参与公司核心业务系统的后端开发与维护', '1. 掌握 Java 基础和面向对象编程\n2. 了解 Spring Boot、MyBatis\n3. 熟悉 MySQL、Redis\n4. 本科及以上，可实习 3 个月以上', '300-400元/天', '北京海淀区', '日常实习', 'OPEN', '2026-07-31'),
(1, 'Python 数据开发实习生', '参与数据平台建设与数据分析工具开发', '1. 熟练使用 Python\n2. 了解 Django/Flask/FastAPI\n3. 熟悉 Pandas、NumPy\n4. 有数据处理经验优先', '250-350元/天', '北京海淀区', '日常实习', 'OPEN', '2026-07-31'),
(2, '后端开发实习生（Java）', '参与美团到店事业群的后端服务开发', '1. 扎实的 Java 基础，了解 JVM\n2. 熟悉 Spring Boot、MyBatis\n3. 了解 MySQL、Redis、消息队列\n4. 有项目经验优先', '200-300元/天', '北京朝阳区', '暑期实习', 'OPEN', '2026-07-15'),
(3, 'Java 开发实习生', '参与BOSS直聘后端服务的设计与开发', '1. 掌握 Java，了解多线程\n2. 了解 Spring Boot\n3. 熟悉 SQL\n4. 有 Git 使用经验', '200-250元/天', '北京海淀区', '日常实习', 'OPEN', '2026-08-01'),
(4, 'IT开发实习生', '参与企业内部系统开发与运维', '1. 计算机相关专业本科\n2. 了解 Java 或 Python\n3. 了解数据库基础\n4. 学习能力强', '100-150元/天', '天津滨海新区', '日常实习', 'OPEN', '2026-07-20'),
(1, '前端开发实习生', '参与抖音Web端产品的开发', '1. 掌握 HTML/CSS/JavaScript\n2. 了解 React 或 Vue\n3. 了解 TypeScript\n4. 有项目经验优先', '250-350元/天', '北京海淀区', '暑期实习', 'OPEN', '2026-07-15');

-- 密码都是 "123456" 的 BCrypt 加密（60位标准格式）
INSERT INTO users (username, password, email, phone) VALUES
('zhangsan', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', 'zhangsan@example.com', '13800138001');
