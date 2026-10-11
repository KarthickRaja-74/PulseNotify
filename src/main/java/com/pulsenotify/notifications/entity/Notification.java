package com.pulsenotify.notifications.entity;

import java.util.UUID;

import com.pulsenotify.common.AuditedEntity;
import com.pulsenotify.notifications.Enum.Channel;
import com.pulsenotify.notifications.Enum.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification extends AuditedEntity {
	
	public Notification() {
	}
	public Notification(String message, String receiver, Channel channel) {
	    this.message = message;
	    this.receiver = receiver;
	    this.channel = channel;
	    this.status = Status.PENDING;
	}
	private String message;
	private String receiver;
	@Enumerated(EnumType.STRING)
	private Channel channel;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public void setMessage(String message) {
		this.message = message;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public String getMessage() {
		return message;
	}
	public String getReceiver() {
		return receiver;
	}
	public Channel getChannel() {
		return channel;
	}
	public Status getStatus() {
		return status;
	}
	
	public void transitionTo(Status nextStatus) {
		boolean isValidTransition = switch(this.status) {
			case PENDING -> nextStatus == Status.PROCESSING;
			case PROCESSING -> nextStatus == Status.SENT || nextStatus == Status.FAILED;
			case SENT, FAILED -> false;
		};
		if(!isValidTransition) {
			throw new IllegalStateException("Invalid status transition from " + this.status + " to " + nextStatus);
		}
		this.status = nextStatus;
	}
}
