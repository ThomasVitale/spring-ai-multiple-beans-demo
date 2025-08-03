package com.thomasvitale.demo.autoconfigure.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(OllamaConnectionProperties.CONFIG_PREFIX)
public class OllamaConnectionProperties {

	public static final String CONFIG_PREFIX = "spring.ai.ollama";

	private String baseUrl = "http://localhost:11434";

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
