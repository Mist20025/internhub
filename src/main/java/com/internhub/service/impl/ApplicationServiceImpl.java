package com.internhub.service.impl;

import com.internhub.dto.PageResult;
import com.internhub.entity.Application;
import com.internhub.entity.Job;
import com.internhub.mapper.ApplicationMapper;
import com.internhub.mapper.JobMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 投递服务：投递、查询、状态更新
 */
@Service
public class ApplicationServiceImpl {

    private final ApplicationMapper applicationMapper;
    private final JobMapper jobMapper;

    public ApplicationServiceImpl(ApplicationMapper applicationMapper, JobMapper jobMapper) {
        this.applicationMapper = applicationMapper;
        this.jobMapper = jobMapper;
    }

    /**
     * 投递一个岗位
     */
    public Application apply(Long userId, Long jobId, String resumeUrl, String notes) {
        // 1. 检查岗位是否存在
        Job job = jobMapper.findById(jobId);
        if (job == null) {
            throw new IllegalArgumentException("岗位不存在");
        }

        // 2. 检查是否已经投递过（不允许重复投递）
        if (applicationMapper.countByUserAndJob(userId, jobId) > 0) {
            throw new IllegalArgumentException("您已经投递过这个岗位了");
        }

        // 3. 创建投递记录
        Application app = new Application();
        app.setUserId(userId);
        app.setJobId(jobId);
        app.setResumeUrl(resumeUrl);
        app.setNotes(notes);

        applicationMapper.insert(app);
        return app;
    }

    /**
     * 查看我的投递列表
     */
    public PageResult<Application> listMyApplications(Long userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        List<Application> list = applicationMapper.findByUserId(userId, offset, pageSize);
        long total = applicationMapper.countByUserId(userId);

        return new PageResult<>(list, total, page, pageSize);
    }

    /**
     * 更新投递状态（面试通过、被拒绝等）
     */
    public void updateStatus(Long id, String status) {
        // 校验状态值
        if (!List.of("PENDING", "SCREENING", "INTERVIEW", "OFFER", "REJECTED").contains(status)) {
            throw new IllegalArgumentException("无效的状态值: " + status);
        }
        applicationMapper.updateStatus(id, status);
    }
}
