package com.pulsenotify.templates.dto;

import java.util.List;

public class TemplatePageResponseDto {

    private List<TemplateResponse> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    
    public List<TemplateResponse> getContent() {
        return content;
    }

    public void setContent(List<TemplateResponse> content) {
        this.content = content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}