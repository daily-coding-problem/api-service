package com.dcp.api_service.v1.users.service;

import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Page<User> getAllPremiumUsers(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return userRepository.findByPremiumIs(true, pageRequest);
	}

	public Page<User> getAllUsers(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return userRepository.findAll(pageRequest);
	}

	public User deleteUser(User user) {
		userRepository.delete(user);
		return user;
	}

	public User findUserByUnsubscribeToken(String token) {
		return userRepository.findByUnsubscribeToken(token);
	}

	public User deactivateAndAnonymizeUser(User user) {
		// Deactivate user
		user.setSubscribed(false);
		user.setUnsubscribedAt(Timestamp.valueOf(LocalDateTime.now()));

		// Anonymize user
		user.setEmail("anonymized-" + UUID.randomUUID() + "@example.com");
		user.setTimezone("UTC");
		user.setPremium(false);
		user.setUnsubscribeToken(null);
		user.setUnsubscribeTokenExpiresAt(null);
		user.setStudyPlans(List.of());
		user.setSubscriptions(List.of());

		// Mark the user as anonymized and set the timestamp
		user.setAnonymized(true);
		user.setAnonymizedAt(Timestamp.valueOf(LocalDateTime.now()));

		return userRepository.save(user);
	}
}
