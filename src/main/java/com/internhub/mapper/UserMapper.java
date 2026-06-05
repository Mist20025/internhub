package com.internhub.mapper;

import com.internhub.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据访问层
 * 接口 + XML 映射文件的方式（MyBatis 标准用法）
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查找用户（用于登录验证）
     */
    User findByUsername(@Param("username") String username);

    /**
     * 注册新用户，返回受影响行数
     */
    int insert(User user);

    /**
     * 检查用户名是否已存在
     */
    int countByUsername(@Param("username") String username);
}
