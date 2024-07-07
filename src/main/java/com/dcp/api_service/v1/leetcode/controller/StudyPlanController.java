package com.dcp.api_service.v1.leetcode.controller;

import com.dcp.api_service.v1.leetcode.entities.StudyPlan;
import com.dcp.api_service.v1.leetcode.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/study-plans")
public class StudyPlanController {

	@Autowired
	private StudyPlanService studyPlanService;

	@GetMapping
	public List<StudyPlan> getAllStudyPlans() {
		return studyPlanService.getAllStudyPlans();
	}

	@GetMapping("/{slug}")
	public StudyPlan getStudyPlanBySlug(@PathVariable String slug) {
		return studyPlanService.getStudyPlanBySlug(slug);
	}

	@PostMapping
	public StudyPlan createStudyPlan(@RequestBody StudyPlan studyPlan) {
		return studyPlanService.saveStudyPlan(studyPlan);
	}
}
