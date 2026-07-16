package com.pulsenotify.response;

import java.time.Instant;
import java.util.List;

public class ErrorResponse {
	private boolean success;
	private String message;
	private List<String> errors;
	private Instant timestamp;
	
	public ErrorResponse() {
		this.success = false;
		this.timestamp = Instant.now();
	}
	public ErrorResponse(String message , List<String> errors) {
		this();
		this.message = message;
		this.errors = errors;
	}
	
	public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

}
