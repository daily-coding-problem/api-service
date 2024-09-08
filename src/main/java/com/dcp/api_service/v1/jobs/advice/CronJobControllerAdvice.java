package com.dcp.api_service.v1.jobs.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dcp.api_service.v1.jobs.exceptions.JobExecutionException;

@ControllerAdvice
public class CronJobControllerAdvice {
	@ExceptionHandler(JobExecutionException.class)
	public ResponseEntity<String> handleJobExecutionException(JobExecutionException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return ResponseEntity.internalServerError().body("An unexpected error occurred: " + ex.getMessage());
	}
}
