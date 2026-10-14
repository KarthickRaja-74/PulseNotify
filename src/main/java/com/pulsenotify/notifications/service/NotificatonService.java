package com.pulsenotify.notifications.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pulsenotify.exception.ResourceNotFoundException;
import com.pulsenotify.notifications.Enum.Channel;
import com.pulsenotify.notifications.Enum.Status;
import com.pulsenotify.notifications.dto.CreateNotificationRequestDto;
import com.pulsenotify.notifications.dto.CreateNotificationResponseDto;
import com.pulsenotify.notifications.entity.Notification;
import com.pulsenotify.notifications.repository.NotificationRepository;
import com.pulsenotify.users.dto.GetNotificationResponseDto;

import jakarta.transaction.Transactional;

@Service
public class NotificatonService {
	private final NotificationRepository notificationRepository;
	public NotificatonService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	@Transactional
	public CreateNotificationResponseDto sendNotification(CreateNotificationRequestDto request) {
		if (Channel.EMAIL == Channel.valueOf(request.getChannel())) {
			if (!request.getReceiver().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) 
		        throw new IllegalArgumentException("Invalid email receiver");
		} else if (Channel.SMS == Channel.valueOf(request.getChannel())) {
		    if (!request.getReceiver().matches("^[6-9][0-9]{9}$")) 
		        throw new IllegalArgumentException("Invalid phone number receiver");    
		}
		
		Notification notification = new Notification
				(request.getMessage(), request.getReceiver(), Channel.valueOf(request.getChannel()));
		
		Notification saved = notificationRepository.save(notification);
		CreateNotificationResponseDto response = new CreateNotificationResponseDto();
		response.setMessage(saved.getMessage());
		response.setReceiver(saved.getReceiver());
		response.setChannel(saved.getChannel().name());
		response.setStatus(saved.getStatus().name());
		response.setMessageId(saved.getId());	
		
		return response;
		
	}
	
	@Transactional
	public void transitionNotificationStatus(UUID notificationId, Status nextStatus) {

	    Notification notification = notificationRepository.findById(notificationId)
	            .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + notificationId));

	    notification.transitionTo(nextStatus);
	    notificationRepository.save(notification);
	}
	
	public GetNotificationResponseDto getNotificationById(UUID notificationId) {
	    Notification notification = notificationRepository.findById(notificationId)
	            .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + notificationId));

	    GetNotificationResponseDto response = new GetNotificationResponseDto();
	    response.setId(notification.getId());
	    response.setMessage(notification.getMessage());
	    response.setReceiver(notification.getReceiver());
	    response.setChannel(notification.getChannel().name());
	    response.setStatus(notification.getStatus().name());
	    response.setCreatedAt(notification.getCreatedAt());
	    response.setUpdatedAt(notification.getUpdatedAt());

	    return response;
	}
}
