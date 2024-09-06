package com.dcp.api_service.v1.subscriptions.advice;

import com.dcp.api_service.v1.subscriptions.exceptions.UserNotFoundException;
import com.dcp.api_service.v1.subscriptions.exceptions.SubscriptionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SubscriptionControllerAdvice {

	// Handle UserNotFoundException
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return new ResponseEntity<>("User not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle SubscriptionException
	@ExceptionHandler(SubscriptionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleSubscriptionException(SubscriptionException ex) {
		return new ResponseEntity<>("Subscription error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// Handle any other exceptions
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
