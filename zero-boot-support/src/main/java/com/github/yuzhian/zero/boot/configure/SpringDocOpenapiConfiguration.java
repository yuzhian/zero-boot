package com.github.yuzhian.zero.boot.configure;

import com.github.yuzhian.zero.boot.properties.OpenApiProperties;
import com.github.yuzhian.zero.boot.properties.SecurityProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author yuzhian
 */
@Configuration
@RequiredArgsConstructor
public class SpringDocOpenapiConfiguration {
    private final OpenApiProperties openApiProperties;
    private final SecurityProperties securityProperties;

    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI()
                // api info
                .info(new Info()
                        .title(openApiProperties.getTitle())
                        .description(openApiProperties.getDescription())
                        .version(openApiProperties.getVersion())
                        .license(new License()
                                .name(openApiProperties.getLicense().getName())
                                .url(openApiProperties.getLicense().getUrl())
                        )
                        .contact(new Contact()
                                .name(openApiProperties.getContact().getName())
                                .email(openApiProperties.getContact().getEmail())
                                .url(openApiProperties.getContact().getUrl())
                        )
                )
                // security info
                .components(new Components().addSecuritySchemes(
                        securityProperties.getKey(),
                        new SecurityScheme()
                                .type(securityProperties.getType())
                                .in(securityProperties.getIn())
                                .name(securityProperties.getName())
                                .description(securityProperties.getDescription())
                ))
                .security(Collections.singletonList(
                        new SecurityRequirement().addList(securityProperties.getKey())
                ))
                ;
    }
}
