package com.internhub.service.impl;

import com.internhub.dto.JobRequest;
import com.internhub.dto.PageResult;
import com.internhub.entity.Job;
import com.internhub.mapper.JobMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位服务：增删改查 + 分页搜索
 */
@Service
public class JobServiceImpl {

    private final JobMapper jobMapper;

    public JobServiceImpl(JobMapper jobMapper) {
        this.jobMapper = jobMapper;
    }

    /**
     * 分页查询岗位列表
     *
     * @param keyword  搜索关键词
     * @param location 地点（北京/天津）
     * @param jobType  类型（暑期实习/日常实习）
     * @param page     页码（从 1 开始）
     * @param pageSize 每页条数
     */
    public PageResult<Job> listJobs(String keyword, String location, String jobType, int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        List<Job> list = jobMapper.findWithPage(keyword, location, jobType, offset, pageSize);
        long total = jobMapper.count(keyword, location, jobType);

        return new PageResult<>(list, total, page, pageSize);
    }

    /**
     * 查询岗位详情
     */
    public Job getJobById(Long id) {
        Job job = jobMapper.findById(id);
        if (job == null) {
            throw new IllegalArgumentException("岗位不存在");
        }
        return job;
    }

    /**
     * 新增岗位
     */
    public Job createJob(JobRequest request) {
        Job job = new Job();
        job.setCompanyId(request.getCompanyId());
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirement(request.getRequirement());
        job.setSalaryRange(request.getSalaryRange());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType() != null ? request.getJobType() : "日常实习");
        job.setDeadline(request.getDeadline());

        jobMapper.insert(job);
        return job;
    }

    /**
     * 更新岗位
     */
    public Job updateJob(Long id, JobRequest request) {
        Job job = getJobById(id); // 先查是否存在

        job.setCompanyId(request.getCompanyId());
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirement(request.getRequirement());
        job.setSalaryRange(request.getSalaryRange());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setDeadline(request.getDeadline());

        jobMapper.update(job);
        return job;
    }

    /**
     * 删除岗位
     */
    public void deleteJob(Long id) {
        getJobById(id); // 先检查存在
        jobMapper.deleteById(id);
    }
}
