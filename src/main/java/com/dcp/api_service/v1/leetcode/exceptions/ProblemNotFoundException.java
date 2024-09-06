package com.dcp.api_service.v1.leetcode.exceptions;

public class ProblemNotFoundException extends RuntimeException {
	public ProblemNotFoundException(String message) {
		super(message);
	}
}
