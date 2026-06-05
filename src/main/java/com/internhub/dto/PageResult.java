package com.internhub.dto;

import java.util.List;

/**
 * 分页结果 — 面试必问：分页怎么实现的？
 *
 * 回答：用 MyBatis 的 LIMIT + OFFSET，封装成 PageResult。
 *       前端传 page（页码）和 pageSize（每页条数），
 *       后端计算 offset = (page - 1) * pageSize。
 *
 * @param <T> 列表中的数据类型（岗位、投递记录等）
 */
public class PageResult<T> {

    private List<T> list;        // 当前页数据
    private long total;          // 总记录数
    private int page;            // 当前页码
    private int pageSize;        // 每页大小
    private int totalPages;      // 总页数

    public PageResult(List<T> list, long total, int page, int pageSize) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }

    public List<T> getList() { return list; }
    public void setList(List<T> list) { this.list = list; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
}
