package com.pulsenotify.notifications.dto;

import java.util.List;

public class NotificationPageResponseDto {
	private List<GetNotificationResponseDto> content;
	private int currentPage;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	
	public List<GetNotificationResponseDto> getContent() {
		return content;
	}

	public void setContent(List<GetNotificationResponseDto> content) {
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
