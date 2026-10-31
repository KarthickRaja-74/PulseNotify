package com.pulsenotify.templates.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsenotify.notifications.Enum.Channel;
import com.pulsenotify.templates.dto.CreateTemplateRequest;
import com.pulsenotify.templates.dto.TemplateResponse;
import com.pulsenotify.templates.entity.Template;
import com.pulsenotify.templates.repository.TemplateRepository;

@Service
public class TemplateService {
	
    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Transactional
    public TemplateResponse createTemplate(CreateTemplateRequest request) {
        validateTemplateRequest(request);

        Template template = new Template();
        template.setTemplateKey(request.getTemplateKey());
        template.setChannel(request.getChannel());
        template.setSubject(request.getSubject());
        template.setBody(request.getBody());

        Template savedTemplate = templateRepository.save(template);
        return mapToResponse(savedTemplate);
    }
	
	private void validateTemplateRequest(CreateTemplateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Template request cannot be null");
        }

        Channel channel = request.getChannel();
        if (channel == null) {
            throw new IllegalArgumentException("Channel is required");
        }

        if (channel == Channel.EMAIL) {
            if (request.getSubject() == null || request.getSubject().isBlank()) {
                throw new IllegalArgumentException("Subject is required when channel is EMAIL.");
            }
            return;
        }

        if (channel == Channel.SMS) {
            if (request.getSubject() != null) {
                throw new IllegalArgumentException("Subject must be null when channel is SMS.");
            }
        }
    }
	
	private TemplateResponse mapToResponse(Template template) {
	    TemplateResponse response = new TemplateResponse();

	    response.setId(template.getId());
	    response.setTemplateKey(template.getTemplateKey());
	    response.setChannel(template.getChannel());
	    response.setSubject(template.getSubject());
	    response.setBody(template.getBody());
	    response.setCreatedAt(template.getCreatedAt());
	    response.setUpdatedAt(template.getUpdatedAt());

	    return response;
	}
}
