package com.dcp.api_service.v1.jobs.service;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.dcp.api_service.v1.jobs.entities.CronJob;
import com.dcp.api_service.v1.jobs.repository.CronJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CronJobService {
	private final CronJobRepository cronJobRepository;

	private final Logger logger = LoggerFactory.getLogger(CronJobService.class);

	public CronJobService(CronJobRepository cronJobRepository) {
		this.cronJobRepository = cronJobRepository;
	}

	public CronJob getCronJobByName(String name) {
		return cronJobRepository.findByName(name);
	}

	public void updateLastRunAt(CronJob cronJob) {
		cronJob.setLastRunAt(Timestamp.valueOf(LocalDateTime.now()));
		cronJobRepository.save(cronJob);
	}

	public void updateNextRunAt(CronJob cronJob) {
		try {
			// Define the cron definition based on Spring cron format
			CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING));
			Cron cron = parser.parse(cronJob.getSchedule());
			ExecutionTime executionTime = ExecutionTime.forCron(cron);

			// Get the current time in UTC
			ZonedDateTime now = ZonedDateTime.now();

			// Calculate the next execution time based on the cron schedule
			Optional<ZonedDateTime> nextExecution = executionTime.nextExecution(now);

			if (nextExecution.isPresent()) {
				ZonedDateTime nextRunTime = nextExecution.get();
				cronJob.setNextRunAt(Timestamp.valueOf(nextRunTime.toLocalDateTime()));
				cronJobRepository.save(cronJob);
			} else {
				// If no next execution is found, log a warning
				logger.info("Could not calculate next execution time for cron job: {}", cronJob.getName());
			}
		} catch (Exception e) {
			// Log any parsing errors
			logger.error("Error parsing cron expression for job: {} - {}", cronJob.getName(), e.getMessage());
		}
	}
}
