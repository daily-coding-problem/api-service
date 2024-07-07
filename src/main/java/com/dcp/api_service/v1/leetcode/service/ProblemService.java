package com.dcp.api_service.v1.leetcode.service;

import com.dcp.api_service.v1.leetcode.entities.Problem;
import com.dcp.api_service.v1.leetcode.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

	@Autowired
	private ProblemRepository problemRepository;

	public List<Problem> getAllProblems() {
		return problemRepository.findAll();
	}

	public Problem getProblemBySlug(String slug) {
		return problemRepository.findBySlug(slug);
	}

	public Problem saveProblem(Problem problem) {
		return problemRepository.save(problem);
	}
}
