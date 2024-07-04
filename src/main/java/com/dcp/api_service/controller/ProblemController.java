package com.dcp.api_service.controller;

import com.dcp.api_service.entity.Problem;
import com.dcp.api_service.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@GetMapping
	public List<Problem> getAllProblems() {
		return problemService.getAllProblems();
	}

	@GetMapping("/{slug}")
	public Problem getProblemBySlug(@PathVariable String slug) {
		return problemService.getProblemBySlug(slug);
	}

	@PostMapping
	public Problem createProblem(@RequestBody Problem problem) {
		return problemService.saveProblem(problem);
	}
}
