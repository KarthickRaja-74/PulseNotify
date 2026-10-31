package com.pulsenotify.templates.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsenotify.response.ApiResponse;
import com.pulsenotify.templates.dto.CreateTemplateRequest;
import com.pulsenotify.templates.dto.TemplateResponse;
import com.pulsenotify.templates.entity.Template;
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
}
