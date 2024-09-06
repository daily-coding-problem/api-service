package com.dcp.api_service.v1.users.controller;

import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.exceptions.UserNotFoundException;
import com.dcp.api_service.v1.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userService.getAllUsers();

		return ResponseEntity.ok(users);
	}

	@GetMapping("/premium")
	public ResponseEntity<?> getAllPremiumUsers() {
		List<User> users = userService.getAllPremiumUsers();

		return ResponseEntity.ok(users);
	}

	@GetMapping("/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable(name = "email") String email) {
		User user = userService.getUserByEmail(email);

		if (user == null) {
			throw new UserNotFoundException("User with email '" + email + "' not found.");
		}

		return ResponseEntity.ok(user);
	}

	@PostMapping
	public ResponseEntity<?> createUser(User user) {
		User savedUser = userService.saveUser(user);

		return ResponseEntity.ok(savedUser);
	}
}
