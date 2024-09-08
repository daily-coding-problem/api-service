package com.dcp.api_service.v1.users.repository;

import com.dcp.api_service.v1.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	Page<User> findByPremiumIs(boolean isPremium, Pageable pageable);
	User findByUnsubscribeToken(String token);

	@NonNull
	@Query("SELECT user FROM User user WHERE user.isProcessing = false")
	Page<User> findAll(@NonNull Pageable pageable);

	@Query("SELECT user FROM User user WHERE user.unsubscribeTokenExpiresAt < :now AND user.isProcessing = false")
	Page<User> findUsersWithExpiredTokens(LocalDateTime now, Pageable pageable);

	// Fetch users who are anonymized and whose anonymization timestamp is older than the retention period
	@Query("SELECT user FROM User user WHERE user.anonymizedAt < :retentionThreshold AND user.isProcessing = false")
	Page<User> findUsersForCleanup(LocalDateTime retentionThreshold, Pageable pageable);
}
