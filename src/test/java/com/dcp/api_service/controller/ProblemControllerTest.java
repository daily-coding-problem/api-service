package com.dcp.api_service.controller;

import com.dcp.api_service.entity.Problem;
import com.dcp.api_service.service.ProblemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProblemController.class)
@AutoConfigureMockMvc
public class ProblemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProblemService problemService;

	private Problem problem;

	@BeforeEach
	public void setUp() {
		problem = new Problem(1L, 123, "Test Problem", "test-problem", "Test Content", "Easy",
			Arrays.asList("Array", "String"), Arrays.asList("Company1", "Company2"), List.of("Hint1"), "https://test.link");
	}

	@Test
	@WithMockUser
	public void testGetAllProblems() throws Exception {
		when(problemService.getAllProblems()).thenReturn(Collections.singletonList(problem));
		mockMvc.perform(get("/api/problems")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].title").value("Test Problem"));
	}

	@Test
	@WithMockUser
	public void testGetProblemBySlug() throws Exception {
		when(problemService.getProblemBySlug("test-problem")).thenReturn(problem);
		mockMvc.perform(get("/api/problems/test-problem")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("Test Problem"));
	}
}
