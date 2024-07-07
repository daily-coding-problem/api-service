package com.dcp.api_service.v1.leetcode.repository;

import com.dcp.api_service.v1.leetcode.entities.Problem;

public interface CustomProblemRepository {
	Problem findRandomProblem();
}

