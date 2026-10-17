package com.pulsenotify.notifications.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsenotify.exception.ResourceNotFoundException;
import com.pulsenotify.notifications.Enum.Channel;
import com.pulsenotify.notifications.Enum.Status;
import com.pulsenotify.notifications.dto.CreateNotificationRequestDto;
import com.pulsenotify.notifications.dto.CreateNotificationResponseDto;
import com.pulsenotify.notifications.dto.GetNotificationResponseDto;
import com.pulsenotify.notifications.dto.NotificationPageResponseDto;
import com.pulsenotify.notifications.entity.Notification;
import com.pulsenotify.notifications.repository.NotificationRepository;
import com.pulsenotify.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;




@Service
public class NotificatonService {
	private final NotificationRepository notificationRepository;
	public NotificatonService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	@Operation(
		    summary = "Create a notification",
		    description = """
		        Creates and persists a notification.
		        The application assigns the initial status.
		        EMAIL receivers must match the supported email format;
		        SMS receivers must be 10-digit Indian mobile numbers
		        beginning with 6, 7, 8, or 9.
		        """
		)
	@ApiResponses({
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "201",
	        description = "Notification created successfully",
	        useReturnTypeSchema = true
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "400",
	        description = "Request validation failed, receiver is invalid, or channel is unsupported",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ErrorResponse.class)
	        )
	    )
	})
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
	
	@Operation(
		    summary = "Get a notification by ID",
		    description = "Retrieves a notification using its UUID."
		)
	@ApiResponses({
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "200",
	        description = "Notification retrieved successfully",
	        useReturnTypeSchema = true
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "400",
	        description = "The supplied ID is not a valid UUID",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ErrorResponse.class)
	        )
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "404",
	        description = "Notification not found",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ErrorResponse.class)
	        )
	    )
	})
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
	
	@Operation(
		    summary = "List notifications",
		    description = """
		        Returns a paginated list of notifications.
		        Results are ordered by creation time descending,
		        with UUID descending as a tie-breaker.
		        """
		)
	@ApiResponses({
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "200",
	        description = "Notifications retrieved successfully",
	        useReturnTypeSchema = true
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "400",
	        description = "Page must be zero or greater and size must be between 1 and 100",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ErrorResponse.class)
	        )
	    )
	})
	
	@Transactional(readOnly = true)
	public NotificationPageResponseDto getNotifications(int page, int size) {

	    Pageable pageable = PageRequest.of(
	            page,
	            size,
	            Sort.by(
	                    Sort.Order.desc("createdAt"),
	                    Sort.Order.desc("id")
	            )
	    );

	    Page<GetNotificationResponseDto> notificationPage = notificationRepository.findAll(pageable)
	            .map(notification -> {
	                GetNotificationResponseDto response =
	                        new GetNotificationResponseDto();

	                response.setId(notification.getId());
	                response.setMessage(notification.getMessage());
	                response.setReceiver(notification.getReceiver());
	                response.setChannel(notification.getChannel().name());
	                response.setStatus(notification.getStatus().name());
	                response.setCreatedAt(notification.getCreatedAt());
	                response.setUpdatedAt(notification.getUpdatedAt());

	                return response;
	            });
	    
	    NotificationPageResponseDto response = new NotificationPageResponseDto();
	    response.setContent(notificationPage.getContent());
	    response.setCurrentPage(notificationPage.getNumber());
	    response.setPageSize(notificationPage.getSize());
	    response.setTotalElements(notificationPage.getTotalElements());
	    response.setTotalPages(notificationPage.getTotalPages());
	    
	    return response;
	    
	}
}
