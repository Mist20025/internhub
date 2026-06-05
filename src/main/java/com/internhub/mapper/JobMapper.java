package com.internhub.mapper;

import com.internhub.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper {

    /**
     * 分页查询岗位列表
     * @param keyword  搜索关键词（匹配岗位名称和公司名）
     * @param location 地点过滤（北京/天津，可选）
     * @param jobType  类型过滤（暑期实习/日常实习，可选）
     * @param offset   分页偏移量 = (page - 1) * pageSize
     * @param pageSize 每页数量
     */
    List<Job> findWithPage(@Param("keyword") String keyword,
                           @Param("location") String location,
                           @Param("jobType") String jobType,
                           @Param("offset") int offset,
                           @Param("pageSize") int pageSize);

    /**
     * 统计符合条件的总记录数（分页需要）
     */
    long count(@Param("keyword") String keyword,
               @Param("location") String location,
               @Param("jobType") String jobType);

    /**
     * 根据 ID 查询岗位详情（含公司名）
     */
    Job findById(@Param("id") Long id);

    /**
     * 新增岗位
     */
    int insert(Job job);

    /**
     * 更新岗位
     */
    int update(Job job);

    /**
     * 删除岗位
     */
    int deleteById(@Param("id") Long id);
}
