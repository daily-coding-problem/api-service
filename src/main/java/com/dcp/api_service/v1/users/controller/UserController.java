package com.dcp.api_service.v1.users.controller;

import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/premium")
	public List<User> getAllPremiumUsers() {
		return userService.getAllPremiumUsers();
	}

	@GetMapping("/{email}")
	public User getUserByEmail(@PathVariable(name = "email") String email) {
		return userService.getUserByEmail(email);
	}

	@PostMapping
	public User createUser(User user) {
		return userService.saveUser(user);
	}
}
