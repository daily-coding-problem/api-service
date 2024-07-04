package com.dcp.api_service.service;

import com.dcp.api_service.entity.Problem;
import com.dcp.api_service.repository.ProblemRepository;
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
