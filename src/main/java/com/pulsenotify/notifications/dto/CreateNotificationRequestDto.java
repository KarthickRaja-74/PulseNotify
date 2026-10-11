package com.pulsenotify.notifications.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateNotificationRequestDto {
	@NotNull(message = "Message cannot be null")
	@NotBlank(message = "Message cannot be blank")
	private String message;
	@NotNull(message = "Receiver cannot be null")
	@NotBlank(message = "Receiver cannot be blank")
	private String receiver;
	@NotNull(message = "Channel cannot be null")
	@NotBlank(message = "Channel cannot be blank")
	private String channel;
	
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
}
