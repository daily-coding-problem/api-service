package com.dcp.api_service.v1.subscriptions.service;

import com.dcp.api_service.v1.leetcode.entities.StudyPlan;
import com.dcp.api_service.v1.leetcode.repository.StudyPlanRepository;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.entities.UserStudyPlan;
import com.dcp.api_service.v1.users.entities.UserSubscription;
import com.dcp.api_service.v1.users.repository.UserStudyPlanRepository;
import com.dcp.api_service.v1.users.repository.UserSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SubscriptionService {
	private final StudyPlanRepository studyPlanRepository;

	private final UserStudyPlanRepository userStudyPlanRepository;

	private final UserSubscriptionRepository userSubscriptionRepository;

	public SubscriptionService(StudyPlanRepository studyPlanRepository, UserStudyPlanRepository userStudyPlanRepository, UserSubscriptionRepository userSubscriptionRepository) {
		this.studyPlanRepository = studyPlanRepository;
		this.userStudyPlanRepository = userStudyPlanRepository;
		this.userSubscriptionRepository = userSubscriptionRepository;
	}

	public void subscribeUserToDefaultPlan(User user) {
		StudyPlan defaultPlan = studyPlanRepository.findBySlug("leetcode-75");

		// Create and save the user subscription
		UserSubscription userSubscription = new UserSubscription();
		userSubscription.setUser(user);
		userSubscription.setStudyPlanId(defaultPlan.getId());
		userSubscription.setSubscribedAt(Timestamp.valueOf(java.time.LocalDateTime.now()));
		userSubscriptionRepository.save(userSubscription);

		// Create and save the user study plan
		UserStudyPlan userStudyPlan = new UserStudyPlan();
		userStudyPlan.setUser(user);
		userStudyPlan.setStudyPlanId(defaultPlan.getId());
		userStudyPlan.setStartedAt(Timestamp.valueOf(java.time.LocalDateTime.now()));
		userStudyPlanRepository.save(userStudyPlan);
	}
}
