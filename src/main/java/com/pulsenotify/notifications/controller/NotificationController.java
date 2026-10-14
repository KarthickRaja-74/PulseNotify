package com.pulsenotify.notifications.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsenotify.notifications.dto.CreateNotificationRequestDto;
import com.pulsenotify.notifications.dto.CreateNotificationResponseDto;
import com.pulsenotify.notifications.service.NotificatonService;
import com.pulsenotify.response.ApiResponse;
import com.pulsenotify.users.dto.GetNotificationResponseDto;

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
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<GetNotificationResponseDto>> getNotificationById(@PathVariable UUID id) {

	    GetNotificationResponseDto response = notificatonService.getNotificationById(id);
	    ApiResponse<GetNotificationResponseDto> apiResponse = new ApiResponse<>(true, "Notification retrieved successfully", response);
	    return ResponseEntity.ok(apiResponse);
	}
}
