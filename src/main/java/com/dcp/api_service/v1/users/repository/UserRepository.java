package com.dcp.api_service.v1.users.repository;

import com.dcp.api_service.v1.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	List<User> findByIsPremium(boolean isPremium);
}
