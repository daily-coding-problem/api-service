package com.dcp.api_service.v1.leetcode.advice;

import com.dcp.api_service.v1.leetcode.exceptions.ProblemNotFoundException;
import com.dcp.api_service.v1.leetcode.exceptions.StudyPlanNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class APIControllerAdvice {
	private final Logger logger = LoggerFactory.getLogger(APIControllerAdvice.class);

	// Handle ProblemNotFoundException
	@ExceptionHandler(ProblemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleProblemNotFoundException(ProblemNotFoundException ex) {
		logger.error("Problem not found: {}", ex.getMessage(), ex);
		return new ResponseEntity<>("Problem not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle StudyPlanNotFoundException
	@ExceptionHandler(StudyPlanNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleStudyPlanNotFoundException(StudyPlanNotFoundException ex) {
		logger.error("Study plan not found: {}", ex.getMessage(), ex);
		return new ResponseEntity<>("Study plan not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle any other exceptions (e.g., validation errors, server errors)
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleGenericException(Exception ex) {
		logger.error("An error occurred: {}", ex.getMessage(), ex);
		return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
