package com.dcp.api_service.v1.users.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_study_plans")
@Data
public class UserStudyPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "study_plan_id")
	private Long studyPlanId;
}
