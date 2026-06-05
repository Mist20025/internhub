package com.internhub.entity;

/**
 * 公司实体 — 对应 companies 表
 */
public class Company {
    private Long id;
    private String name;
    private String industry;
    private String size;
    private String location;
    private String website;
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
