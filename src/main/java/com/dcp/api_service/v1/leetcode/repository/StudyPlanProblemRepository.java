package com.dcp.api_service.v1.leetcode.repository;

import com.dcp.api_service.v1.leetcode.entities.StudyPlanProblem;
import com.dcp.api_service.v1.leetcode.entities.StudyPlanProblemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyPlanProblemRepository extends JpaRepository<StudyPlanProblem, StudyPlanProblemId> {
	List<StudyPlanProblem> findByStudyPlanId(Long studyPlanId);
}
