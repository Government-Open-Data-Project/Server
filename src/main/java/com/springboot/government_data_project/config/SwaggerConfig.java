package com.springboot.government_data_project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
        (
                info = @Info(title = "[Emo:D] API 명세서", version = "0.1"),
                security = { @SecurityRequirement(name = "Authorization")}
        )
@SecurityScheme
        (
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                name = "Authorization",
                description = "로그인 후 응답 받은 토큰"
        )
@Configuration
public class SwaggerConfig {

    /** API Group */
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("v0.1-BehaviorAnalysis")
                .pathsToMatch("/**")
                // .pathsToExclude("/bhs/mkd/**")
                .build();
    }

    /** Test Data Make Group */
    @Bean
    public GroupedOpenApi mkdOpenApi() {
        return GroupedOpenApi.builder()
                .group("v0.1-MakeTestData")
                .pathsToMatch("/bhs/mkd/**")
                .build();
    }
}