package com.pulsenotify.templates.dto;

import com.pulsenotify.notifications.Enum.Channel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTemplateRequest {
	@NotBlank(message = "Template key is required")
    @Size(max = 255, message = "Template key must be at most 100 characters")
    private String templateKey;

    @NotNull(message = "Channel is required")
    private Channel channel;

    private String subject;

    @NotBlank(message = "Body is required")
    private String body;

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
