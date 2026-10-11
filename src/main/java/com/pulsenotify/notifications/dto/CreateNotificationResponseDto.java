package com.pulsenotify.notifications.dto;

import java.util.UUID;

public class CreateNotificationResponseDto {
	
	private UUID id;
	private String message;
	private String receiver;
	private String channel;
	private String status;
	
	public UUID getId() {
		return id;
	}
	public void setMessageId(UUID id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

