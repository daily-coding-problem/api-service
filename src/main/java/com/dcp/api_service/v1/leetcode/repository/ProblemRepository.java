package com.dcp.api_service.v1.leetcode.repository;

import com.dcp.api_service.v1.leetcode.entities.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
	Problem findBySlug(String slug);
}
