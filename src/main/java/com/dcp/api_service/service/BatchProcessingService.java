package com.dcp.api_service.service;

import com.dcp.api_service.v1.users.entities.User;
import com.dcp.api_service.v1.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BatchProcessingService {

	private final Logger logger = LoggerFactory.getLogger(BatchProcessingService.class);

	private final UserRepository userRepository;

	public BatchProcessingService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void processBatch(Page<User> users, BatchProcessor<User> processor) {
		logger.info("Processing a batch of {} users.", users.getNumberOfElements());

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		List<User> processedUsers = new ArrayList<>();

		try {
			for (User user : users) {
				if (!user.isProcessing()) {
					user.setProcessing(true);  // Mark as processing
					processedUsers.add(user);

					// Submit the task to the executor service
					executor.submit(() -> {
						try {
							processor.process(user);  // Apply the provided processing logic
						} catch (Exception e) {
							logger.error("Error processing user: {}", user.getEmail(), e);
						}
					});
				} else {
					logger.warn("Skipping user {} as they are already being processed.", user.getEmail());
				}
			}

			// Await the completion of all tasks
			executor.shutdown();
			if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
				executor.shutdownNow();
				logger.warn("Executor did not terminate within 1 hour. Forced shutdown.");
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
			logger.error("Executor service was interrupted.", e);
		} finally {
			// Reset the `isProcessing` flag for processed users after all tasks complete
			processedUsers.forEach(user -> {
				user.setProcessing(false);  // Reset processing flag
				userRepository.save(user);  // Unlock the record after processing
				logger.info("Finished processing user: {}. Reset processing flag.", user.getEmail());
			});
		}

		logger.info("Batch processing completed for {} users.", users.getNumberOfElements());
	}
}

