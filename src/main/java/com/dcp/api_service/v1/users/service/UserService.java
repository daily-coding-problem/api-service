package com.dcp.api_service.v1.users.service;

import com.dcp.api_service.v1.users.entity.User;
import com.dcp.api_service.v1.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public List<User> getAllPremiumUsers() {
		return userRepository.findByIsPremium(true);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
