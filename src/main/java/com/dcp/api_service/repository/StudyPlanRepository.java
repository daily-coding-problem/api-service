package com.dcp.api_service.repository;

import com.dcp.api_service.entity.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long> {
	StudyPlan findBySlug(String slug);
}
