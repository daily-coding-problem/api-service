package com.dcp.api_service.v1.users.repository;

import com.dcp.api_service.v1.users.entities.UserStudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStudyPlanRepository extends JpaRepository<UserStudyPlan, Long> {
	UserStudyPlan deleteUserStudyPlanByUserId(Long userId);
}
