package com.dcp.api_service.v1.jobs.controller;

import com.dcp.api_service.jobs.EmailJobHandler;
import com.dcp.api_service.jobs.TokenHandler;
import com.dcp.api_service.jobs.AnonymizedUserCleanupHandler;
import com.dcp.api_service.jobs.enums.JobName;
import com.dcp.api_service.v1.jobs.exceptions.JobExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/jobs/cron")
public class CronJobController {

	private final Map<JobName, Runnable> jobHandlers;

	public CronJobController(EmailJobHandler emailJobHandler, TokenHandler tokenHandler, AnonymizedUserCleanupHandler anonymizedUserCleanupHandler) {
		jobHandlers = new HashMap<>();
		jobHandlers.put(JobName.SEND_DAILY_CODING_PROBLEMS, emailJobHandler::processDailyEmailJob);
		jobHandlers.put(JobName.GENERATE_UNSUBSCRIBE_TOKEN, tokenHandler::handleExpiredTokens);
		jobHandlers.put(JobName.ANONYMIZE_USER_DATA_CLEANUP, anonymizedUserCleanupHandler::cleanUpAnonymizedUserData);
	}

	@GetMapping("/run/{jobName}")
	public ResponseEntity<String> runJob(@PathVariable String jobName) {
		try {
			JobName job = JobName.valueOf(jobName.toUpperCase().replace(" ", "_"));
			jobHandlers.get(job).run();
			return ResponseEntity.ok("Job " + jobName + " executed successfully.");
		} catch (IllegalArgumentException e) {
			throw new JobExecutionException("Invalid job name: " + jobName);
		}
	}
}
