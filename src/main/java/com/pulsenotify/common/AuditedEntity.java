package com.pulsenotify.common;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public abstract class AuditedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private UUID id;
//	@CreatedBy
//	private String createdBy;
//	
//	@LastModifiedBy
//	private String updatedBy;
	
	@CreatedDate
	private Instant createdAt;
	
	@LastModifiedDate
	private Instant updatedAt;
	
	public Instant getCreatedAt() {
	    return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
	    this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
	    return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
	    this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}
	
}
