package com.internhub.mapper;

import com.internhub.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationMapper {

    /**
     * 查询某用户的投递记录（分页）
     */
    List<Application> findByUserId(@Param("userId") Long userId,
                                   @Param("offset") int offset,
                                   @Param("pageSize") int pageSize);

    long countByUserId(@Param("userId") Long userId);

    /**
     * 新增投递记录
     */
    int insert(Application application);

    /**
     * 更新投递状态（PENDING → INTERVIEW → OFFER）
     */
    int updateStatus(@Param("id") Long id,
                     @Param("status") String status);

    /**
     * 检查用户是否已投递过该岗位
     */
    int countByUserAndJob(@Param("userId") Long userId,
                          @Param("jobId") Long jobId);
}
