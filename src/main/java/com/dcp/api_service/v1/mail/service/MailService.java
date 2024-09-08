package com.dcp.api_service.v1.mail.service;

import com.dcp.api_service.v1.mail.entities.Email;
import com.dcp.api_service.v1.users.entities.User;

public interface MailService {
	String getEmailContentForProblem(String slug, User user);

	String getEmailContentForProblem(String slug, boolean provideSolution);

	String getEmailContentForRandomProblem(boolean provideSolution);

	void sendEmail(Email email);
}
