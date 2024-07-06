package com.dcp.api_service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

@Getter
@Configuration
@ConfigurationProperties(prefix = "properties")
@Validated
public class Properties {
	private final WebSecurity webSecurity;

	public Properties() {
		this.webSecurity = new WebSecurity();
	}

	@Setter
	public static class WebSecurity {
		private String corsAllowedOrigins;

		public List<String> getCorsAllowedOrigins() {
			return Arrays.asList(corsAllowedOrigins.split(","));
		}
	}
}
