package com.internhub.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 岗位实体 — 对应 jobs 表
 */
public class Job {
    private Long id;
    private Long companyId;
    private String title;
    private String description;
    private String requirement;
    private String salaryRange;
    private String location;
    private String jobType;    // 暑期实习 / 日常实习 / 校招
    private String status;     // OPEN / CLOSED
    private LocalDateTime postedAt;
    private LocalDate deadline;

    // 关联对象（查询时填充，不是数据库字段）
    private String companyName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRequirement() { return requirement; }
    public void setRequirement(String requirement) { this.requirement = requirement; }
    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getPostedAt() { return postedAt; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
