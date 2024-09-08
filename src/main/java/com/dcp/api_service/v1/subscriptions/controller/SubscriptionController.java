package com.dcp.api_service.v1.subscriptions.controller;

import com.dcp.api_service.v1.subscriptions.service.SubscriptionService;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.exceptions.UserNotFoundException;
import com.dcp.api_service.v1.users.service.UserService;
import com.dcp.api_service.utilities.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
	public ResponseEntity<?> subscribeUser(@RequestBody @Validated User user) {
		user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		User savedUser = userService.saveUser(user);

		if (savedUser == null) {
			throw new UserNotFoundException("User could not be saved.");
		}

		// Generate a secure unsubscribe token and store it
		String unsubscribeToken = TokenUtil.generateSecureToken();
		savedUser.setUnsubscribeToken(unsubscribeToken);
		savedUser.setUnsubscribeTokenExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusDays(7))); // Token valid for 7 days
		userService.saveUser(savedUser); // Save token to user record

		// Subscribe user to default plan
		subscriptionService.subscribeUserToDefaultPlan(savedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	@PostMapping("/unsubscribe")
	public ResponseEntity<?> unsubscribeUser(@RequestParam String token) {
		// Find user by token
		User user = userService.findUserByUnsubscribeToken(token);

		if (user == null || user.getUnsubscribeTokenExpiresAt().before(Timestamp.valueOf(LocalDateTime.now()))) {
			return ResponseEntity.badRequest().body("Invalid or expired unsubscribe token.");
		}

		// Deactivate and anonymize user
		// This is done so that the user's data is not completely deleted from the system
		// This is useful for analytics and reporting purposes
		// Also, as part of GDPR compliance, the user's data is anonymized
		User deletedUser = userService.deactivateAndAnonymizeUser(user);

		if (deletedUser == null) {
			throw new UserNotFoundException("User could not be deleted.");
		}

		return ResponseEntity.ok("Successfully unsubscribed.");
	}
}
