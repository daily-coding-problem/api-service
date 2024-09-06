package com.dcp.api_service.v1.leetcode.controller;

import com.dcp.api_service.v1.leetcode.entities.StudyPlan;
import com.dcp.api_service.v1.leetcode.exceptions.StudyPlanNotFoundException;
import com.dcp.api_service.v1.leetcode.service.StudyPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/study-plans")
public class StudyPlanController {

	private final StudyPlanService studyPlanService;

	public StudyPlanController(StudyPlanService studyPlanService) {
		this.studyPlanService = studyPlanService;
	}

	@GetMapping
	public ResponseEntity<?> getAllStudyPlans() {
		List<StudyPlan> studyPlans = studyPlanService.getAllStudyPlans();
		return ResponseEntity.ok(studyPlans);
	}

	@GetMapping("/{slug}")
	public ResponseEntity<?> getStudyPlanBySlug(@PathVariable String slug) {
		StudyPlan studyPlan = studyPlanService.getStudyPlanBySlug(slug);

		if (studyPlan == null) {
			throw new StudyPlanNotFoundException("Study plan with slug '" + slug + "' not found.");
		}

		return ResponseEntity.ok(studyPlan);
	}

	@PostMapping
	public ResponseEntity<?> createStudyPlan(@RequestBody @Validated StudyPlan studyPlan) {
		StudyPlan savedStudyPlan = studyPlanService.saveStudyPlan(studyPlan);

		return ResponseEntity.ok(savedStudyPlan);
	}
}
