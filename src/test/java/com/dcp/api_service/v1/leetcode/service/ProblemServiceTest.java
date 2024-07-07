package com.dcp.api_service.v1.leetcode.service;

import com.dcp.api_service.v1.leetcode.entity.Problem;
import com.dcp.api_service.v1.leetcode.repository.ProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProblemServiceTest {

	@Mock
	private ProblemRepository problemRepository;

	@InjectMocks
	private ProblemService problemService;

	private Problem problem;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		problem = new Problem(1L, 123, "Test Problem", "test-problem", "Test Content", "Easy",
			Arrays.asList("Array", "String"), Arrays.asList("Company1", "Company2"), List.of("Hint1"), "https://test.link");
	}

	@Test
	public void testGetAllProblems() {
		when(problemRepository.findAll()).thenReturn(Collections.singletonList(problem));
		List<Problem> problems = problemService.getAllProblems();
		assertEquals(1, problems.size());
		verify(problemRepository, times(1)).findAll();
	}

	@Test
	public void testGetProblemBySlug() {
		when(problemRepository.findBySlug("test-problem")).thenReturn(problem);
		Problem foundProblem = problemService.getProblemBySlug("test-problem");
		assertEquals("Test Problem", foundProblem.getTitle());
		verify(problemRepository, times(1)).findBySlug("test-problem");
	}

	@Test
	public void testSaveProblem() {
		when(problemRepository.save(any(Problem.class))).thenReturn(problem);
		Problem savedProblem = problemService.saveProblem(problem);
		assertEquals("Test Problem", savedProblem.getTitle());
		verify(problemRepository, times(1)).save(problem);
	}
}

