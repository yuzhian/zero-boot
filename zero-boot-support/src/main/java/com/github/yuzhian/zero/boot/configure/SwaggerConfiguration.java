package com.github.yuzhian.zero.boot.configure;

import com.github.yuzhian.zero.boot.properties.ApiProperties;
import com.github.yuzhian.zero.boot.properties.SecurityProperties;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * @author yuzhian
 */
@EnableOpenApi
@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {
    private final ApiProperties apiProperties;
    private final SecurityProperties securityProperties;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiProperties.getTitle())
                .description(apiProperties.getDescription())
                .version(apiProperties.getVersion())
                .contact(new Contact(apiProperties.getAuthor(), apiProperties.getAuthorUrl(), apiProperties.getAuthorEmail()))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(securityProperties.getName(), securityProperties.getKeyName(), securityProperties.getIn().getValue());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(
                        new SecurityReference(
                                securityProperties.getName(),
                                new AuthorizationScope[]{new AuthorizationScope("global", "access everything")}
                        )
                ))
                .build();
    }
}
