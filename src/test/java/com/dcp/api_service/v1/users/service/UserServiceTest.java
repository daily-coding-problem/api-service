package com.dcp.api_service.v1.users.service;

import com.dcp.api_service.utilities.TokenUtil;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User user;

	@BeforeEach
	public void setUp() {
		// Setting up user with default values for testing
		user = new User(
			1L,                                   	 				// id
			"test@example.com",                    						// email
			true,                                  						// isPremium
			"America/New_York",                    						// timezone
			new Timestamp(new Date().getTime()),   						// createdAt
			false,                                 						// isProcessing
			TokenUtil.generateSecureToken(),       						// unsubscribeToken
			Timestamp.valueOf(LocalDateTime.now().plusDays(7)),         // unsubscribeTokenExpiresAt
			Collections.emptyList(),               						// subscriptions
			Collections.emptyList(),                					// studyPlans
			false,                                 						// isSubscribed
			null,                                  						// unsubscribedAt
			false,                                 						// isAnonymized
			null                                   						// anonymizedAt
		);
	}

	@Test
	public void testGetUserByEmail() {
		when(userRepository.findByEmail("test@example.com")).thenReturn(user);
		User foundUser = userService.getUserByEmail("test@example.com");
		assertEquals("test@example.com", foundUser.getEmail());
		verify(userRepository, times(1)).findByEmail("test@example.com");
	}

	@Test
	public void testGetAllUsers() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<User> userPage = new PageImpl<>(Collections.singletonList(user), pageable, 1);
		when(userRepository.findAll(pageable)).thenReturn(userPage);

		Page<User> users = userService.getAllUsers(0, 10);
		assertEquals(1, users.getContent().size());
		verify(userRepository, times(1)).findAll(pageable);
	}

	@Test
	public void testGetAllPremiumUsers() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<User> userPage = new PageImpl<>(Collections.singletonList(user), pageable, 1);
		when(userRepository.findByPremiumIs(true, pageable)).thenReturn(userPage);

		Page<User> users = userService.getAllPremiumUsers(0, 10);
		assertEquals(1, users.getContent().size());
		verify(userRepository, times(1)).findByPremiumIs(true, pageable);
	}

	@Test
	public void testSaveUser() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		User savedUser = userService.saveUser(user);
		assertEquals("test@example.com", savedUser.getEmail());
		verify(userRepository, times(1)).save(user);
	}
}
