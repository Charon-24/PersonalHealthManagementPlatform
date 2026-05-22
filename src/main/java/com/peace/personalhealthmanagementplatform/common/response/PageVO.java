package com.peace.personalhealthmanagementplatform.common.response;

import java.util.Collections;
import java.util.List;

public class PageVO<T> {

    private int pageNum;
    private int pageSize;
    private long total;
    private long pages;
    private List<T> records;

    public PageVO() {
    }

    public PageVO(int pageNum, int pageSize, long total, long pages, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.records = records;
    }

    public static <T> PageVO<T> of(int pageNum, int pageSize, long total, List<T> records) {
        long totalPages = pageSize <= 0 ? 0 : (total + pageSize - 1) / pageSize;
        List<T> safeRecords = records == null ? Collections.emptyList() : records;
        return new PageVO<>(pageNum, pageSize, total, totalPages, safeRecords);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
