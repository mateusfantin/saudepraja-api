package br.com.saudepraja.api.core.openapi;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public GroupedOpenApi usersGroup(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder().group("global")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Saude Praja API").version(appVersion)))
                .packagesToScan("br.com.saudepraja.api.controller")
                .build();
    }
}
