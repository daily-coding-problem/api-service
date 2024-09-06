package com.dcp.api_service.v1.leetcode.controller;

import com.dcp.api_service.v1.leetcode.entities.Problem;
import com.dcp.api_service.v1.leetcode.exceptions.ProblemNotFoundException;
import com.dcp.api_service.v1.leetcode.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
public class ProblemController {

	private final ProblemService problemService;

	public ProblemController(ProblemService problemService) {
		this.problemService = problemService;
	}

	@GetMapping
	public ResponseEntity<?> getAllProblems() {
		List<Problem> problems = problemService.getAllProblems();

		return ResponseEntity.ok(problems);
	}

	@GetMapping("/{slug}")
	public ResponseEntity<?> getProblemBySlug(@PathVariable String slug) {
		Problem problem = problemService.getProblemBySlug(slug);

		if (problem == null) {
			throw new ProblemNotFoundException("Problem with slug '" + slug + "' not found.");
		}

		return ResponseEntity.ok(problem);
	}

	@PostMapping
	public ResponseEntity<?> createProblem(@RequestBody @Validated Problem problem) {
		Problem savedProblem = problemService.saveProblem(problem);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedProblem);
	}

	@GetMapping("/random")
	public ResponseEntity<?> getRandomProblem() {
		Problem problem = problemService.getRandomProblem();

		if (problem == null) {
			throw new ProblemNotFoundException("Random problem not found.");
		}

		return ResponseEntity.ok(problem);
	}

	@PutMapping("/{slug}")
	public ResponseEntity<?> updateProblem(@PathVariable String slug, @RequestBody @Validated Problem problem) {
		Problem updatedProblem = problemService.updateProblem(slug, problem);

		if (updatedProblem == null) {
			throw new ProblemNotFoundException("Problem with slug '" + slug + "' not found.");
		}

		return ResponseEntity.ok(updatedProblem);
	}
}
