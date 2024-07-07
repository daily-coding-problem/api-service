package com.dcp.api_service.v1.leetcode.service;

import com.dcp.api_service.v1.leetcode.entities.StudyPlan;
import com.dcp.api_service.v1.leetcode.repository.StudyPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyPlanService {

	@Autowired
	private StudyPlanRepository studyPlanRepository;

	public List<StudyPlan> getAllStudyPlans() {
		return studyPlanRepository.findAll();
	}

	public StudyPlan getStudyPlanBySlug(String slug) {
		return studyPlanRepository.findBySlug(slug);
	}

	public StudyPlan saveStudyPlan(StudyPlan studyPlan) {
		return studyPlanRepository.save(studyPlan);
	}
}
