package com.dcp.api_service.service;

import com.dcp.api_service.v1.leetcode.entities.Problem;
import com.dcp.api_service.v1.mail.entities.Email;
import com.dcp.api_service.v1.mail.service.MailService;
import com.dcp.api_service.v1.users.entities.User;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final MailService mailService;

	public EmailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void sendProblem(User user, Problem problem) {
		Email problemEmail = new Email();
		problemEmail.setTo(user.getEmail());
		problemEmail.setSubject("Daily Coding Problem - " + problem.getTitle());
		String problemContent = mailService.getEmailContentForProblem(problem.getSlug(), false);
		problemEmail.setHtml(problemContent);

		mailService.sendEmail(problemEmail);
	}

	public void sendSolution(User user, Problem problem) {
		Email solutionEmail = new Email();
		solutionEmail.setTo(user.getEmail());
		solutionEmail.setSubject("Daily Coding Problem Solution - " + problem.getTitle());
		String solutionContent = mailService.getEmailContentForProblem(problem.getSlug(), true);
		solutionEmail.setHtml(solutionContent);

		mailService.sendEmail(solutionEmail);
	}
}
