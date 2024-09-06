package com.dcp.api_service.v1.subscriptions.controller;

import com.dcp.api_service.v1.subscriptions.exceptions.SubscriptionException;
import com.dcp.api_service.v1.subscriptions.service.SubscriptionService;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.exceptions.UserNotFoundException;
import com.dcp.api_service.v1.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {
	private final UserService userService;
	private final SubscriptionService subscriptionService;

	public SubscriptionController(UserService userService, SubscriptionService subscriptionService) {
		this.userService = userService;
		this.subscriptionService = subscriptionService;
	}

	@PostMapping("/subscribe")
	public ResponseEntity<?> subscribeUser(User user) {
		User savedUser = userService.saveUser(user);

		if (savedUser == null) {
			throw new UserNotFoundException("User could not be saved.");
		}

		try {
			subscriptionService.subscribeUserToDefaultPlan(savedUser.getId());
		} catch (Exception e) {
			throw new SubscriptionException("Subscription failed for user with ID " + savedUser.getId());
		}

		return ResponseEntity.ok(savedUser);
	}

	@PostMapping("/unsubscribe")
	public ResponseEntity<?> unsubscribeUser(User user) {
		User deletedUser = userService.deleteUser(user);

		if (deletedUser == null) {
			throw new UserNotFoundException("User could not be deleted.");
		}

		try {
			subscriptionService.unsubscribeUserFromAllPlans(deletedUser.getId());
		} catch (Exception e) {
			throw new SubscriptionException("Unsubscribing failed for user with ID " + deletedUser.getId());
		}

		return ResponseEntity.ok(deletedUser);
	}
}
