package com.thomasvitale.demo.autoconfigure.chat;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.restclient.autoconfigure.RestClientAutoConfiguration;
import org.springframework.boot.webclient.autoconfigure.WebClientAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import com.thomasvitale.demo.autoconfigure.api.OllamaApiAutoConfiguration;
import com.thomasvitale.demo.autoconfigure.chat.OllamaChatAutoConfiguration.OllamaChatBeanRegistrar;

@AutoConfiguration(after = { RestClientAutoConfiguration.class })
@ConditionalOnClass(OllamaChatModel.class)
@EnableConfigurationProperties(OllamaChatProperties.class)
@ImportAutoConfiguration(classes = { OllamaApiAutoConfiguration.class, RestClientAutoConfiguration.class,
		WebClientAutoConfiguration.class })
@Import(OllamaChatBeanRegistrar.class)
public final class OllamaChatAutoConfiguration {
    
    static class OllamaChatBeanRegistrar implements BeanRegistrar {

        @Override
        public void register(BeanRegistry registry, Environment env) {
            var properties = Binder.get(env).bindOrCreate(OllamaChatProperties.CONFIG_PREFIX, OllamaChatProperties.class);
            
            // Register default chat model as primary bean
            registry.registerBean("defaultChatModel", OllamaChatModel.class, spec -> spec
                .primary()
                .supplier(context -> {
                    var ollamaApi = context.bean(OllamaApi.class);
                    return OllamaChatModel.builder()
                        .ollamaApi(ollamaApi)
                        .defaultOptions(properties.getOptions())
                        .build();
                }));
            
            // Register named chat models from configuration
            for (var entry : properties.getModels().entrySet()) {
                String modelName = entry.getKey();
                var config = entry.getValue();
                
                registry.registerBean(modelName, OllamaChatModel.class, spec -> spec
                    .supplier(context -> {
                        var ollamaApi = context.bean(OllamaApi.class);
                        return OllamaChatModel.builder()
                            .ollamaApi(ollamaApi)
                            .defaultOptions(config.getOptions())
                            .build();
                    }));
            }
        }

    }
}
