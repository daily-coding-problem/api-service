package com.dcp.api_service.v1.users.service;

import com.dcp.api_service.v1.users.entity.User;
import com.dcp.api_service.v1.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		user = new User(1L, "test@example.com", true, "America/New_York", new Timestamp(new Date().getTime()));
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
		when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
		List<User> users = userService.getAllUsers();
		assertEquals(1, users.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllPremiumUsers() {
		when(userRepository.findByIsPremium(true)).thenReturn(Collections.singletonList(user));
		List<User> users = userService.getAllPremiumUsers();
		assertEquals(1, users.size());
		verify(userRepository, times(1)).findByIsPremium(true);
	}

	@Test
	public void testSaveUser() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		User savedUser = userService.saveUser(user);
		assertEquals("test@example.com", savedUser.getEmail());
		verify(userRepository, times(1)).save(user);
	}
}
