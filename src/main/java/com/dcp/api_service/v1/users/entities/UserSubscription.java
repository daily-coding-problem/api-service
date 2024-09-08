package com.dcp.api_service.v1.users.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "user_subscriptions")
@Data
public class UserSubscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Many-to-one relationship with User (owner side)
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "study_plan_id", nullable = false)
	private Long studyPlanId;

	@Column(name = "subscribed_at", nullable = false)
	private Timestamp subscribedAt;
}
