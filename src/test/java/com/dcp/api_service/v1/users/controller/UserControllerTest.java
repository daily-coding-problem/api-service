package com.dcp.api_service.v1.users.controller;

import com.dcp.api_service.utilities.TokenUtil;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
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
	@WithMockUser
	public void testGetAllUsers() throws Exception {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<User> userPage = new PageImpl<>(Collections.singletonList(user), pageRequest, 1);

		// Mock paginated response
		when(userService.getAllUsers(0, 10)).thenReturn(userPage);

		// Perform request with pagination params
		mockMvc.perform(get("/api/v1/users?page=0&size=10")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(1)))  // "content" contains the list of users in a paginated response
			.andExpect(jsonPath("$.content[0].email").value("test@example.com"));
	}

	@Test
	@WithMockUser
	public void testGetUserByEmail() throws Exception {
		when(userService.getUserByEmail("test@example.com")).thenReturn(user);

		mockMvc.perform(get("/api/v1/users/test@example.com")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email").value("test@example.com"));
	}

	@Test
	@WithMockUser
	public void testGetAllPremiumUsers() throws Exception {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<User> userPage = new PageImpl<>(Collections.singletonList(user), pageRequest, 1);

		// Mock paginated response for premium users
		when(userService.getAllPremiumUsers(0, 10)).thenReturn(userPage);

		// Perform request with pagination params
		mockMvc.perform(get("/api/v1/users/premium?page=0&size=10")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(1)))
			.andExpect(jsonPath("$.content[0].email").value("test@example.com"));
	}
}
