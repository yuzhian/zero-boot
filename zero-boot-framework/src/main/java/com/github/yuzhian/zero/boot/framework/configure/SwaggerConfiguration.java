package com.github.yuzhian.zero.boot.framework.configure;

import com.github.yuzhian.zero.boot.properties.AccessProperties;
import com.github.yuzhian.zero.boot.properties.ApiProperties;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
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
    private final AccessProperties accessProperties;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .globalRequestParameters(Collections.singletonList(authorizationHeader()))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiProperties.getTitle())
                .description(apiProperties.getDescription())
                .version(apiProperties.getVersion())
                .contact(new Contact(apiProperties.getAuthor(), apiProperties.getAuthorUrl(), apiProperties.getAuthorEmail()))
                .build();
    }

    private RequestParameter authorizationHeader() {
        return new RequestParameterBuilder()
                .name(accessProperties.getAuthorization())
                .description("访问令牌")
                .in("header")
                .required(false)
                .build();
    }
}
