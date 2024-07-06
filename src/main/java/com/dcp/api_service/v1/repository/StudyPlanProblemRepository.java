package com.dcp.api_service.v1.repository;

import com.dcp.api_service.v1.entity.StudyPlanProblem;
import com.dcp.api_service.v1.entity.StudyPlanProblemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPlanProblemRepository extends JpaRepository<StudyPlanProblem, StudyPlanProblemId> {
}
