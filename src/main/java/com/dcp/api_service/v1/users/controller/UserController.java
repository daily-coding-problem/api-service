package com.dcp.api_service.v1.users.controller;

import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.exceptions.UserNotFoundException;
import com.dcp.api_service.v1.users.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/premium")
	public ResponseEntity<Page<User>> getAllPremiumUsers(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		Page<User> premiumUsers = userService.getAllPremiumUsers(page, size);
		return ResponseEntity.ok(premiumUsers);
	}

	@GetMapping
	public ResponseEntity<Page<User>> getAllUsers(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		Page<User> users = userService.getAllUsers(page, size);
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

		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
}
