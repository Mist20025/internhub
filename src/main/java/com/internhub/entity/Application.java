package com.internhub.entity;

import java.time.LocalDateTime;

/**
 * 投递记录实体 — 对应 applications 表
 *
 * 状态流转：PENDING → SCREENING → INTERVIEW → OFFER
 *                                ↘ REJECTED
 */
public class Application {
    private Long id;
    private Long userId;
    private Long jobId;
    private String resumeUrl;
    private String status;     // PENDING / SCREENING / INTERVIEW / OFFER / REJECTED
    private String notes;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    // 关联对象
    private String jobTitle;
    private String companyName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
