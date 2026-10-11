package com.pulsenotify.notifications.service;

import org.springframework.stereotype.Service;

import com.pulsenotify.notifications.Enum.Channel;
import com.pulsenotify.notifications.dto.CreateNotificationRequestDto;
import com.pulsenotify.notifications.dto.CreateNotificationResponseDto;
import com.pulsenotify.notifications.entity.Notification;
import com.pulsenotify.notifications.repository.NotificationRepository;

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
		
		Notification notification = new Notification();
		notification.setMessage(request.getMessage());
		notification.setReceiver(request.getReceiver());
		notification.setChannel(Channel.valueOf(request.getChannel()));
		notification.setStatus(com.pulsenotify.notifications.Enum.Status.PENDING);
		
		Notification saved = notificationRepository.save(notification);
		CreateNotificationResponseDto response = new CreateNotificationResponseDto();
		response.setMessage(saved.getMessage());
		response.setReceiver(saved.getReceiver());
		response.setChannel(saved.getChannel().name());
		response.setStatus(saved.getStatus().name());
		response.setMessageId(saved.getId());	
		
		return response;
		
	}
}
