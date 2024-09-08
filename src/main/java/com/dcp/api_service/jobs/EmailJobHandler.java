package com.dcp.api_service.jobs;

import com.dcp.api_service.service.BatchProcessingService;
import com.dcp.api_service.service.ProblemService;
import com.dcp.api_service.v1.users.service.UserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.dcp.api_service.v1.jobs.entities.CronJob;
import com.dcp.api_service.v1.jobs.service.CronJobService;


@Component
public class EmailJobHandler implements JobHandler {

	private final UserService userService;
	private final ProblemService problemService;
	private final CronJobService cronJobService;
	private final BatchProcessingService batchProcessingService;

	private static final Logger logger = LoggerFactory.getLogger(EmailJobHandler.class);

	public EmailJobHandler(UserService userService, ProblemService problemService, CronJobService cronJobService, BatchProcessingService batchProcessingService) {
		this.userService = userService;
		this.problemService = problemService;
		this.cronJobService = cronJobService;
		this.batchProcessingService = batchProcessingService;
	}

	@Scheduled(cron = "#{@cronJobService.getCronJobByName('Send Daily Coding Problems').getSchedule()}")
	public void execute() {
		CronJob cronJob = cronJobService.getCronJobByName("Send Daily Coding Problems");

		if (cronJob != null && Boolean.TRUE.equals(cronJob.getEnabled())) {
			logger.info("Cron job '{}' is enabled. Executing.", cronJob.getName());

			// Execute the actual job logic (email sending)
			processDailyEmailJob();

			// Update cron job run timestamps
			cronJobService.updateLastRunAt(cronJob);
			cronJobService.updateNextRunAt(cronJob);
		} else {
			assert cronJob != null;
			logger.info("Cron job '{}' is disabled. Skipping execution.", cronJob.getName());
		}
	}

	public void processDailyEmailJob() {
		int page = 0;
		int size = 100;  // Batch size

		try {
			while (true) {
				var userPage = userService.getAllUsers(page, size);

				if (userPage.isEmpty()) {
					logger.info("No more users found. Exiting.");
					break;
				}

				logger.info("Processing page {} with {} users.", page, userPage.getNumberOfElements());

				// Process the current batch using the batch processing service with multithreading
				batchProcessingService.processBatch(userPage, user -> {
					logger.info("Processing daily coding problem for user: {}", user.getEmail());
					problemService.processUserProblem(user);  // Send daily coding problem email
				});

				if (!userPage.hasNext()) break;
				page++;
			}
		} catch (Exception e) {
			logger.error("An error occurred while processing the daily email job.", e);
		}

		logger.info("Finished processing daily emails.");
	}
}

