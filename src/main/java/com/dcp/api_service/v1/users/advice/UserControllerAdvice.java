package com.dcp.api_service.v1.users.advice;

import com.dcp.api_service.v1.users.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {
	private final Logger logger = LoggerFactory.getLogger(UserControllerAdvice.class);

	// Handle UserNotFoundException
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
		logger.error("User not found: {}", ex.getMessage(), ex);
		return new ResponseEntity<>("User not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle any other exceptions
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleGenericException(Exception ex) {
		logger.error("An error occurred: {}", ex.getMessage(), ex);
		return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
