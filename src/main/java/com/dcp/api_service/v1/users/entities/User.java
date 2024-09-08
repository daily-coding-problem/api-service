package com.dcp.api_service.v1.users.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	private boolean isPremium;

	private String timezone;

	private Timestamp createdAt;

	private boolean isSubscribed;

	private Timestamp unsubscribedAt;

	private boolean isAnonymized;

	private Timestamp anonymizedAt;

	private boolean isProcessing; // This field is used to prevent multiple requests from the same user

	private String unsubscribeToken;

	private Timestamp unsubscribeTokenExpiresAt;

	// One-to-many relationship with UserSubscription (owner side)
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<UserSubscription> subscriptions;

	// One-to-many relationship with UserStudyPlan (owner side)
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<UserStudyPlan> studyPlans;

	public User(Long id, String email, boolean isPremium, String timezone,
				Timestamp createdAt, boolean isProcessing, String unsubscribeToken,
				Timestamp unsubscribeTokenExpiresAt, List<UserSubscription> subscriptions,
				List<UserStudyPlan> studyPlans, boolean isSubscribed,
				Timestamp unsubscribedAt, boolean isAnonymized,
				Timestamp anonymizedAt) {
		this.id = id;
		this.email = email;
		this.isPremium = isPremium;
		this.timezone = timezone;
		this.createdAt = createdAt;
		this.isProcessing = isProcessing;
		this.unsubscribeToken = unsubscribeToken;
		this.unsubscribeTokenExpiresAt = unsubscribeTokenExpiresAt;
		this.subscriptions = subscriptions;
		this.studyPlans = studyPlans;
		this.isSubscribed = isSubscribed;
		this.unsubscribedAt = unsubscribedAt;
		this.isAnonymized = isAnonymized;
		this.anonymizedAt = anonymizedAt;
	}
}
