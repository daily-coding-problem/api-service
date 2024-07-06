package com.dcp.api_service.v1.controller;

import com.dcp.api_service.v1.controller.StudyPlanController;
import com.dcp.api_service.v1.entity.StudyPlan;
import com.dcp.api_service.v1.service.StudyPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudyPlanController.class)
@AutoConfigureMockMvc
public class StudyPlanControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudyPlanService studyPlanService;

	private StudyPlan studyPlan;

	@BeforeEach
	public void setUp() {
		studyPlan = new StudyPlan(1L, "test-plan", "Test Plan", "Test Description");
	}

	@Test
	@WithMockUser
	public void testGetAllStudyPlans() throws Exception {
		when(studyPlanService.getAllStudyPlans()).thenReturn(Collections.singletonList(studyPlan));
		mockMvc.perform(get("/api/study-plans")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].name").value("Test Plan"));
	}

	@Test
	@WithMockUser
	public void testGetStudyPlanBySlug() throws Exception {
		when(studyPlanService.getStudyPlanBySlug("test-plan")).thenReturn(studyPlan);
		mockMvc.perform(get("/api/study-plans/test-plan")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Test Plan"));
	}
}
