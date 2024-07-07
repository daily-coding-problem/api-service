package com.dcp.api_service.v1.users.controller;

import com.dcp.api_service.v1.users.entity.User;
import com.dcp.api_service.v1.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
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
		user = new User(1L, "test@example.com", true, "America/New_York", new Timestamp(new Date().getTime()));
	}

	@Test
	@WithMockUser
	public void testGetAllUsers() throws Exception {
		when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));
		mockMvc.perform(get("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].email").value("test@example.com"));
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
		when(userService.getAllPremiumUsers()).thenReturn(Collections.singletonList(user));
		mockMvc.perform(get("/api/v1/users/premium")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].email").value("test@example.com"));
	}
}
