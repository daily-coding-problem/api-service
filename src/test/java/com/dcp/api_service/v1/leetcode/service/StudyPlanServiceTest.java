package com.dcp.api_service.v1.leetcode.service;

import com.dcp.api_service.v1.leetcode.entities.StudyPlan;
import com.dcp.api_service.v1.leetcode.repository.StudyPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudyPlanServiceTest {

	@Mock
	private StudyPlanRepository studyPlanRepository;

	@InjectMocks
	private StudyPlanService studyPlanService;

	private StudyPlan studyPlan;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		studyPlan = new StudyPlan(1L, "test-plan", "Test Plan", "Test Description");
	}

	@Test
	public void testGetAllStudyPlans() {
		when(studyPlanRepository.findAll()).thenReturn(Collections.singletonList(studyPlan));
		List<StudyPlan> studyPlans = studyPlanService.getAllStudyPlans();
		assertEquals(1, studyPlans.size());
		verify(studyPlanRepository, times(1)).findAll();
	}

	@Test
	public void testGetStudyPlanBySlug() {
		when(studyPlanRepository.findBySlug("test-plan")).thenReturn(studyPlan);
		StudyPlan foundStudyPlan = studyPlanService.getStudyPlanBySlug("test-plan");
		assertEquals("Test Plan", foundStudyPlan.getName());
		verify(studyPlanRepository, times(1)).findBySlug("test-plan");
	}

	@Test
	public void testSaveStudyPlan() {
		when(studyPlanRepository.save(any(StudyPlan.class))).thenReturn(studyPlan);
		StudyPlan savedStudyPlan = studyPlanService.saveStudyPlan(studyPlan);
		assertEquals("Test Plan", savedStudyPlan.getName());
		verify(studyPlanRepository, times(1)).save(studyPlan);
	}
}
