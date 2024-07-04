package com.dcp.api_service.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Value("${cors.allowed-origins}")
	private String corsAllowedOrigins;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Configure CORS
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

		// Configure headers disabling frame options and XSS protection
		http.headers(headers ->
			headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
				.xssProtection(HeadersConfigurer.XXssConfig::disable));

		// Disable CSRF
		http.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(corsAllowedOrigins.split(",")));
		configuration.setAllowedMethods(List.of("GET", "POST", "DELETE"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
