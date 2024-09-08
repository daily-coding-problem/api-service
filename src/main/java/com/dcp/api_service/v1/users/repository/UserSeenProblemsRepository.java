package com.dcp.api_service.v1.users.repository;

import com.dcp.api_service.v1.users.entities.UserSeenProblems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserSeenProblemsRepository extends JpaRepository<UserSeenProblems, Long> {
	@Query("SELECT u FROM UserSeenProblems u WHERE u.user.id = ?1 AND u.problem.id = ?2")
	Optional<UserSeenProblems> findByUserIdAndProblemId(Long userId, Long problemId);
}
