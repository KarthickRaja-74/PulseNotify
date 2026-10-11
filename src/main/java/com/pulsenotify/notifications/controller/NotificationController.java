package com.pulsenotify.notifications.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsenotify.notifications.dto.CreateNotificationRequestDto;
import com.pulsenotify.notifications.dto.CreateNotificationResponseDto;
import com.pulsenotify.notifications.service.NotificatonService;
import com.pulsenotify.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	
	private final NotificatonService notificatonService;
	
	public NotificationController(NotificatonService notificatonService) {
		this.notificatonService = notificatonService;
	}
	@PostMapping("/send")
	public ResponseEntity<ApiResponse<CreateNotificationResponseDto>> createNotification(@RequestBody @Valid CreateNotificationRequestDto request) {
		CreateNotificationResponseDto response = notificatonService.sendNotification(request);
		ApiResponse<CreateNotificationResponseDto> apiResponse = new ApiResponse<>(true, "Notification created successfully", response);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
}
