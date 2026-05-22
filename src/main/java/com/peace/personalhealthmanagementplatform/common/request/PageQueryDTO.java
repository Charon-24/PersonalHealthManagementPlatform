package com.peace.personalhealthmanagementplatform.common.request;

import com.peace.personalhealthmanagementplatform.common.constant.PaginationConstants;

public class PageQueryDTO {

    private Integer pageNum = PaginationConstants.DEFAULT_PAGE_NUM;
    private Integer pageSize = PaginationConstants.DEFAULT_PAGE_SIZE;
    private String sortBy;
    private String sortOrder;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int resolvePageNum() {
        if (pageNum == null || pageNum < 1) {
            return PaginationConstants.DEFAULT_PAGE_NUM;
        }
        return pageNum;
    }

    public int resolvePageSize() {
        if (pageSize == null || pageSize < 1) {
            return PaginationConstants.DEFAULT_PAGE_SIZE;
        }
        return Math.min(pageSize, PaginationConstants.MAX_PAGE_SIZE);
    }
}
