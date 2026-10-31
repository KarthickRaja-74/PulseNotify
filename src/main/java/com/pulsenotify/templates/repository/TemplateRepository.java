package com.pulsenotify.templates.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pulsenotify.templates.entity.Template;

public interface TemplateRepository extends JpaRepository<Template, UUID> {
	
}
