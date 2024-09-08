package com.dcp.api_service.jobs;

import com.dcp.api_service.service.BatchProcessingService;
import com.dcp.api_service.v1.jobs.entities.CronJob;
import com.dcp.api_service.v1.jobs.service.CronJobService;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.repository.UserRepository;
import com.dcp.api_service.v1.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnonymizedUserCleanupHandler implements JobHandler {

	private static final int BATCH_SIZE = 100;
	private static final int RETENTION_DAYS = 30;

	private final UserService userService;
	private final CronJobService cronJobService;
	private final BatchProcessingService batchProcessingService;

	private final UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(AnonymizedUserCleanupHandler.class);

	public AnonymizedUserCleanupHandler(UserService userService, CronJobService cronJobService, BatchProcessingService batchProcessingService, UserRepository userRepository) {
		this.userService = userService;
		this.cronJobService = cronJobService;
		this.batchProcessingService = batchProcessingService;
		this.userRepository = userRepository;
	}

	@Override
	@Scheduled(cron = "#{@cronJobService.getCronJobByName('Anonymize User Data Clean Up').getSchedule()}")
	public void execute() {
		CronJob cronJob = cronJobService.getCronJobByName("Anonymize User Data Clean Up");

		if (cronJob != null && Boolean.TRUE.equals(cronJob.getEnabled())) {
			logger.info("Cron job '{}' is enabled. Executing.", cronJob.getName());

			cleanUpAnonymizedUserData();

			cronJobService.updateLastRunAt(cronJob);
			cronJobService.updateNextRunAt(cronJob);
		} else {
			assert cronJob != null;
			logger.info("Cron job '{}' is disabled. Skipping execution.", cronJob.getName());
		}
	}

	public void cleanUpAnonymizedUserData() {
		logger.info("Starting the cleanup job for anonymized users.");

		int page = 0;
		Page<User> anonymizedUsers;

		do {
			anonymizedUsers = userRepository.findUsersForCleanup(
				LocalDateTime.now().minusDays(RETENTION_DAYS), PageRequest.of(page, BATCH_SIZE));

			logger.info("Processing page {} with {} users.", page, anonymizedUsers.getNumberOfElements());

			batchProcessingService.processBatch(anonymizedUsers, user -> {
				logger.info("Deleting anonymized user: {}", user.getEmail());
				userService.deactivateAndAnonymizeUser(user);
			});

			page++;

		} while (anonymizedUsers.hasNext());

		logger.info("Finished cleaning up anonymized users.");
	}
}
