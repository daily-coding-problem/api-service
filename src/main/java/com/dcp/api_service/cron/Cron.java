package com.dcp.api_service.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Cron {
	@Scheduled(cron = "0 0 0 * * ?")
	public void sendDailyEmails() {
		// Send daily emails to all users
	}
}
