package com.dcp.api_service.v1.jobs.repository;

import com.dcp.api_service.v1.jobs.entities.CronJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronJobRepository extends JpaRepository<CronJob, Long> {
	CronJob findByName(String name);
}
