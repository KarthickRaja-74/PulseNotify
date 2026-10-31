package com.pulsenotify.templates.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulsenotify.response.ApiResponse;
import com.pulsenotify.templates.dto.CreateTemplateRequest;
import com.pulsenotify.templates.dto.TemplatePageResponseDto;
import com.pulsenotify.templates.dto.TemplateResponse;
import com.pulsenotify.templates.service.TemplateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TemplateResponse>> createTemplate(
            @Valid @RequestBody CreateTemplateRequest request) {

        TemplateResponse savedTemplate = templateService.createTemplate(request);

        ApiResponse<TemplateResponse> response = new ApiResponse<>(
                true,
                "Template created successfully",
                savedTemplate
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TemplateResponse>> getTemplateById(@PathVariable UUID id) {
        TemplateResponse template = templateService.getTemplateById(id);

        ApiResponse<TemplateResponse> response = new ApiResponse<>(
                true,
                "Template retrieved successfully",
                template
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TemplatePageResponseDto>> getTemplates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
    	
		if (page < 0) {
	        throw new IllegalArgumentException("Page must be zero or greater");
	    }
	    if (size <= 0 || size > 100) {
	        throw new IllegalArgumentException("Size must be between 1 and 100");
	    }

        TemplatePageResponseDto templates = templateService.getTemplates(page, size);

        ApiResponse<TemplatePageResponseDto> response = new ApiResponse<>(
                true,
                "Templates retrieved successfully",
                templates
        );

        return ResponseEntity.ok(response);
    }
}
