package com.dcp.api_service.jobs;

import com.dcp.api_service.utilities.TokenUtil;
import com.dcp.api_service.v1.jobs.entities.CronJob;
import com.dcp.api_service.v1.jobs.service.CronJobService;
import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.repository.UserRepository;
import com.dcp.api_service.service.BatchProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class TokenHandler implements JobHandler {

	private static final int BATCH_SIZE = 100;

	private final UserRepository userRepository;

	private final BatchProcessingService batchProcessingService;
	private final CronJobService cronJobService;

	private final Logger logger = LoggerFactory.getLogger(TokenHandler.class);

	public TokenHandler(UserRepository userRepository, BatchProcessingService batchProcessingService, CronJobService cronJobService) {
		this.userRepository = userRepository;
		this.batchProcessingService = batchProcessingService;
		this.cronJobService = cronJobService;
	}

	@Override
	@Scheduled(cron = "#{@cronJobService.getCronJobByName('Handle Expired Tokens').getSchedule()}")
	public void execute() {
		CronJob cronJob = cronJobService.getCronJobByName("Handle Expired Tokens");

		if (cronJob != null && Boolean.TRUE.equals(cronJob.getEnabled())) {
			logger.info("Cron job '{}' is enabled. Executing.", cronJob.getName());

			handleExpiredTokens();

			cronJobService.updateLastRunAt(cronJob);
			cronJobService.updateNextRunAt(cronJob);
		} else {
			assert cronJob != null;
			logger.info("Cron job '{}' is disabled. Skipping execution.", cronJob.getName());
		}
	}

	public void handleExpiredTokens() {
		logger.info("Starting the task to handle expired tokens.");

		int page = 0;
		Page<User> usersWithExpiredTokens;

		do {
			usersWithExpiredTokens = userRepository.findUsersWithExpiredTokens(
				LocalDateTime.now(), PageRequest.of(page, BATCH_SIZE));

			logger.info("Processing page {} with {} users.", page, usersWithExpiredTokens.getNumberOfElements());

			batchProcessingService.processBatch(usersWithExpiredTokens, user -> {
				String unsubscribeToken = TokenUtil.generateSecureToken();
				user.setUnsubscribeToken(unsubscribeToken);
				user.setUnsubscribeTokenExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusDays(7)));
				logger.info("Regenerated token for user: {}. New token expires at: {}", user.getEmail(), user.getUnsubscribeTokenExpiresAt());
			});

			page++;

		} while (usersWithExpiredTokens.hasNext());

		logger.info("Finished processing expired tokens.");
	}
}
