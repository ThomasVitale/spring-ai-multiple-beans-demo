package com.thomasvitale.demo.autoconfigure.chat;

import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(OllamaChatProperties.CONFIG_PREFIX)
public class OllamaChatProperties {

    public static final String CONFIG_PREFIX = "spring.ai.ollama.chat";

    @NestedConfigurationProperty
    private OllamaOptions options = OllamaOptions.builder().model(OllamaModel.MISTRAL.id()).build();

    private Map<String, OllamaChatConfig> models = new HashMap<>();

    public OllamaOptions getOptions() {
        return this.options;
    }

	public Map<String, OllamaChatConfig> getModels() {
		return this.models;
	}

    public OllamaOptions getOptionsForConfig(String configName) {
        if (configName == null || configName.trim().isEmpty()) {
            return this.options;
        }
        
        OllamaChatConfig config = this.models.get(configName);
        if (config != null) {
            return config.getOptions();
        }
        
        return this.options;
    }

    public static class OllamaChatConfig {
        @NestedConfigurationProperty
        private OllamaOptions options = OllamaOptions.builder().model(OllamaModel.MISTRAL.id()).build();

        public OllamaOptions getOptions() {
            return this.options;
        }
    }
}
