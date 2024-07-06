package com.dcp.api_service.v1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_plan_problems")
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
