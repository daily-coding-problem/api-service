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

	public Problem getRandomProblem() {
		return problemRepository.findRandomProblem();
	}

	public Problem updateProblem(String slug, Problem problem) {
		Problem existingProblem = problemRepository.findBySlug(slug);

		if (existingProblem == null) {
			return null;
		}

		existingProblem.setTitle(problem.getTitle());
		existingProblem.setContent(problem.getContent());
		existingProblem.setDifficulty(problem.getDifficulty());
		existingProblem.setTopics(problem.getTopics());
		existingProblem.setCompanies(problem.getCompanies());
		existingProblem.setHints(problem.getHints());
		existingProblem.setLink(problem.getLink());
		existingProblem.setSolution(problem.getSolution());

		return problemRepository.save(existingProblem);
	}
}
