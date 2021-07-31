package com.alanph.devsuperiorresourceclient.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alanph.devsuperiorresourceclient.services.exceptions.DatabaseException;
import com.alanph.devsuperiorresourceclient.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException resourceNotFound, HttpServletRequest request){
		StandardError standardError = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		standardError.setTimestamp(Instant.now());
		standardError.setStatus(status.value());
		standardError.setError("Resource Not Found");
		standardError.setMessage(resourceNotFound.getMessage());
		standardError.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException error, HttpServletRequest request){
		StandardError standardError = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		standardError.setTimestamp(Instant.now());
		standardError.setStatus(status.value());
		standardError.setError("Resource Not Found");
		standardError.setMessage(error.getMessage());
		standardError.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}
	
}
