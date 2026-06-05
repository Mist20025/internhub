package com.internhub.controller;

import com.internhub.common.Result;
import com.internhub.dto.PageResult;
import com.internhub.entity.Application;
import com.internhub.mapper.UserMapper;
import com.internhub.service.impl.ApplicationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 投递记录管理接口
 */
@Tag(name = "投递管理", description = "投递记录相关操作")
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationServiceImpl applicationService;
    private final UserMapper userMapper;

    public ApplicationController(ApplicationServiceImpl applicationService, UserMapper userMapper) {
        this.applicationService = applicationService;
        this.userMapper = userMapper;
    }

    /**
     * 从 JWT 中获取当前登录用户的 ID
     */
    private Long getCurrentUserId(Authentication authentication) {
        String username = authentication.getName();
        return userMapper.findByUsername(username).getId();
    }

    /**
     * POST /api/applications
     * Body: { "jobId": 1, "resumeUrl": "", "notes": "" }
     */
    @Operation(summary = "投递岗位")
    @PostMapping
    public Result<?> apply(@RequestBody Map<String, Object> body, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        Long jobId = Long.valueOf(body.get("jobId").toString());
        String resumeUrl = (String) body.getOrDefault("resumeUrl", "");
        String notes = (String) body.getOrDefault("notes", "");

        applicationService.apply(userId, jobId, resumeUrl, notes);
        return Result.success("投递成功");
    }

    /**
     * GET /api/applications?page=1&pageSize=10
     */
    @Operation(summary = "查看我的投递列表")
    @GetMapping
    public Result<PageResult<Application>> listMy(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        return Result.success(applicationService.listMyApplications(userId, page, pageSize));
    }

    /**
     * PUT /api/applications/1/status
     * Body: { "status": "INTERVIEW" }
     */
    @Operation(summary = "更新投递状态")
    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        applicationService.updateStatus(id, body.get("status"));
        return Result.success("状态更新成功");
    }
}
