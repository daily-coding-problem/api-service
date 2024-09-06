package com.dcp.api_service.v1.users.repository;


import com.dcp.api_service.v1.users.entities.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
	void deleteUserSubscriptionByUserId(Long id);
}
