package com.internhub.controller;

import com.internhub.common.Result;
import com.internhub.dto.JobRequest;
import com.internhub.dto.PageResult;
import com.internhub.entity.Job;
import com.internhub.service.impl.JobServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 岗位管理接口 — 核心业务
 *
 * 这些接口需要携带 JWT Token（在请求头加 Authorization: Bearer <token>）
 */
@Tag(name = "岗位管理", description = "实习岗位的增删改查")
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobServiceImpl jobService;

    public JobController(JobServiceImpl jobService) {
        this.jobService = jobService;
    }

    /**
     * GET /api/jobs?keyword=Java&location=北京&jobType=暑期实习&page=1&pageSize=10
     *
     * RESTful API 设计规范：查询用 GET，参数拼在 URL 后面
     */
    @Operation(summary = "分页查询岗位列表")
    @GetMapping
    public Result<PageResult<Job>> list(
            @Parameter(description = "搜索关键词（匹配岗位名和公司名）")
            @RequestParam(required = false) String keyword,

            @Parameter(description = "工作地点（北京/天津）")
            @RequestParam(required = false) String location,

            @Parameter(description = "岗位类型（暑期实习/日常实习/校招）")
            @RequestParam(required = false) String jobType,

            @Parameter(description = "页码（从1开始）")
            @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "每页条数")
            @RequestParam(defaultValue = "10") int pageSize) {

        return Result.success(jobService.listJobs(keyword, location, jobType, page, pageSize));
    }

    /**
     * GET /api/jobs/1
     */
    @Operation(summary = "查询岗位详情")
    @GetMapping("/{id}")
    public Result<Job> getById(@PathVariable Long id) {
        return Result.success(jobService.getJobById(id));
    }

    /**
     * POST /api/jobs
     * Body: { "companyId": 1, "title": "Java后端实习生", ... }
     */
    @Operation(summary = "新增岗位")
    @PostMapping
    public Result<Job> create(@Valid @RequestBody JobRequest request) {
        return Result.success("新增成功", jobService.createJob(request));
    }

    /**
     * PUT /api/jobs/1
     * RESTful API 设计规范：更新用 PUT，资源 ID 放在 URL 中
     */
    @Operation(summary = "更新岗位")
    @PutMapping("/{id}")
    public Result<Job> update(@PathVariable Long id, @Valid @RequestBody JobRequest request) {
        return Result.success("更新成功", jobService.updateJob(id, request));
    }

    /**
     * DELETE /api/jobs/1
     */
    @Operation(summary = "删除岗位")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        jobService.deleteJob(id);
        return Result.success("删除成功");
    }
}
