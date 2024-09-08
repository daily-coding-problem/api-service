package com.dcp.api_service.v1.mail.service;

import com.dcp.api_service.config.properties.Properties;
import com.dcp.api_service.v1.mail.entities.Email;
import com.dcp.api_service.v1.users.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MailServiceImpl implements MailService {
	private final RestTemplate restTemplate;
	private final Properties properties;

	public MailServiceImpl(RestTemplate restTemplate, Properties properties) {
		this.restTemplate = restTemplate;
		this.properties = properties;
	}

	@Override
	public String getEmailContentForProblem(String slug, User user) {
		String url = properties.getServices().getMail().getUrl() + "/api/v1/mail/problem/" + slug + "?provide_solution=" + user.isPremium();
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

		return restTemplate.postForObject(uriBuilder.toUriString(), user, String.class);
	}

	@Override
	public String getEmailContentForProblem(String slug, boolean provideSolution) {
		String url = properties.getServices().getMail().getUrl() + "/api/v1/mail/problem/" + slug + "?provide_solution=" + provideSolution;
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		return restTemplate.getForObject(uriBuilder.toUriString(), String.class);
	}

	@Override
	public String getEmailContentForRandomProblem(boolean provideSolution) {
		String url = properties.getServices().getMail().getUrl() + "/api/v1/mail/problem/random?provide_solution=" + provideSolution;
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		return restTemplate.getForObject(uriBuilder.toUriString(), String.class);
	}

	@Override
	public void sendEmail(Email email) {
		String url = properties.getServices().getMail().getUrl() + "/api/v1/mail/send/problem/random";
		restTemplate.postForObject(url, email, Void.class);
	}
}
