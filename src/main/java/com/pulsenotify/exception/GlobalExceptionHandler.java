package com.pulsenotify.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pulsenotify.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex){
		ErrorResponse response = new ErrorResponse(ex.getMessage(),List.of(ex.getMessage()));
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex){
		logger.error("An unexpected Error Occured",ex);
		ErrorResponse response = new ErrorResponse("An unexpected Error Occured",List.of("Please Contact Support"));
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError :: getDefaultMessage).toList();
		ErrorResponse response = new ErrorResponse("Validation Failed" , errors);
		
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleExistingEmailException(EmailAlreadyExistsException ex){
		ErrorResponse errorResponse = new ErrorResponse("Email already exists",List.of(ex.getMessage()));
		
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex){
		ErrorResponse errorResponse = new ErrorResponse("Authentication Failed",List.of(ex.getMessage()));
		
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

	    ErrorResponse response = new ErrorResponse(ex.getMessage(), List.of(ex.getMessage()));
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {

	    ErrorResponse response = new ErrorResponse(ex.getMessage(), List.of(ex.getMessage()));
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
	        DataIntegrityViolationException ex) {
	    ErrorResponse response = new ErrorResponse("Data integrity violation",  List.of("The request conflicts with existing data or database constraintss"));
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
}
