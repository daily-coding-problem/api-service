package com.dcp.api_service.v1.subscriptions.service;

import com.dcp.api_service.v1.leetcode.entities.StudyPlan;
import com.dcp.api_service.v1.leetcode.repository.StudyPlanRepository;
import com.dcp.api_service.v1.users.entities.UserStudyPlan;
import com.dcp.api_service.v1.users.entities.UserSubscription;
import com.dcp.api_service.v1.users.repository.UserStudyPlanRepository;
import com.dcp.api_service.v1.users.repository.UserSubscriptionRepository;
import org.springframework.stereotype.Service;

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

	public void subscribeUserToDefaultPlan(Long userId) {
		StudyPlan defaultPlan = studyPlanRepository.findBySlug("leetcode-75");

		// Create and save the user subscription
		UserSubscription userSubscription = new UserSubscription();
		userSubscription.setUserId(userId);
		userSubscription.setStudyPlanId(defaultPlan.getId());
		userSubscriptionRepository.save(userSubscription);

		// Create and save the user study plan
		UserStudyPlan userStudyPlan = new UserStudyPlan();
		userStudyPlan.setUserId(userId);
		userStudyPlan.setStudyPlanId(defaultPlan.getId());
		userStudyPlanRepository.save(userStudyPlan);
	}

	public void unsubscribeUserFromAllPlans(Long userId) {
		userSubscriptionRepository.deleteUserSubscriptionByUserId(userId);
		userStudyPlanRepository.deleteUserStudyPlanByUserId(userId);
	}
}
