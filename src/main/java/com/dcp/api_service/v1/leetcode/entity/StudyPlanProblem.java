package com.dcp.api_service.v1.leetcode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_plan_problems", schema = "leetcode")
public class StudyPlanProblem {
	@EmbeddedId
	private StudyPlanProblemId id;

	@ManyToOne
	@MapsId("studyPlanId")
	@JoinColumn(name = "study_plan_id")
	private StudyPlan studyPlan;

	@ManyToOne
	@MapsId("problemId")
	@JoinColumn(name = "problem_id")
	private Problem problem;

	private String categoryName;
}
