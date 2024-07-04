package com.dcp.api_service.repository;

import com.dcp.api_service.entity.StudyPlanProblem;
import com.dcp.api_service.entity.StudyPlanProblemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPlanProblemRepository extends JpaRepository<StudyPlanProblem, StudyPlanProblemId> {
}
