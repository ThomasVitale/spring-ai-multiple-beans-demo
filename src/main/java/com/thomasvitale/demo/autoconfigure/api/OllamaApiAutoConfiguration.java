package com.thomasvitale.demo.autoconfigure.api;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.retry.autoconfigure.SpringAiRetryAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfiguration
@ConditionalOnClass(OllamaApi.class)
@EnableConfigurationProperties(OllamaConnectionProperties.class)
@ImportAutoConfiguration(classes = { SpringAiRetryAutoConfiguration.class })
public final class OllamaApiAutoConfiguration {
    
    @Bean
	@ConditionalOnMissingBean(OllamaConnectionDetails.class)
	PropertiesOllamaConnectionDetails ollamaConnectionDetails(OllamaConnectionProperties properties) {
		return new PropertiesOllamaConnectionDetails(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	OllamaApi ollamaApi(OllamaConnectionDetails connectionDetails,
			ObjectProvider<RestClient.Builder> restClientBuilderProvider,
			ObjectProvider<WebClient.Builder> webClientBuilderProvider, ResponseErrorHandler responseErrorHandler) {
		return OllamaApi.builder()
			.baseUrl(connectionDetails.getBaseUrl())
			.restClientBuilder(restClientBuilderProvider.getIfAvailable(RestClient::builder))
			.webClientBuilder(webClientBuilderProvider.getIfAvailable(WebClient::builder))
			.responseErrorHandler(responseErrorHandler)
			.build();
	}

	static class PropertiesOllamaConnectionDetails implements OllamaConnectionDetails {

		private final OllamaConnectionProperties properties;

		PropertiesOllamaConnectionDetails(OllamaConnectionProperties properties) {
			this.properties = properties;
		}

		@Override
		public String getBaseUrl() {
			return this.properties.getBaseUrl();
		}

	}

}
