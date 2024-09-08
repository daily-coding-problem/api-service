package com.dcp.api_service.v1.users.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "user_study_plans")
@Data
public class UserStudyPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Many-to-one relationship with User (owner side)
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "study_plan_id", nullable = false)
	private Long studyPlanId;

	@Column(name = "started_at", nullable = false)
	private Timestamp startedAt;

	// Nullable, only set when the study plan is finished
	@Column(name = "finished_at")
	private Timestamp finishedAt;
}
