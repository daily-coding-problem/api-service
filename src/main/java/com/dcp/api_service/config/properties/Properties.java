package com.dcp.api_service.config.properties;

import lombok.Data;
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
	private final Services services = new Services();

	public Properties() {
		this.webSecurity = new WebSecurity();
	}

	@Data
	@Validated
	public static class Services {
		private final Mail mail = new Mail();

		@Data
		@Validated
		public static class Mail {
			private String url;
		}
	}

	@Setter
	public static class WebSecurity {
		private String corsAllowedOrigins = "*";

		public List<String> getCorsAllowedOrigins() {
			if (corsAllowedOrigins == null || corsAllowedOrigins.isBlank()) {
				return List.of("*");
			}

			return Arrays.asList(corsAllowedOrigins.split(","));
		}
	}
}
