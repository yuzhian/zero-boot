package com.github.yuzhian.zero.boot.config;

import com.github.yuzhian.zero.boot.common.constant.GlobalConfigConstants;
import io.swagger.annotations.ApiOperation;
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
public class SwaggerConfig {

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
                .title("SpringBoot项目基础服务文档")
                .description("SpringBoot项目基础服务后端接口API")
                .contact(new Contact("Zhian Yu", "http://github.com/zhianyu", "yu97@live.com"))
                .version("0.0.1-SNAPSHOT")
                .build();
    }

    private RequestParameter authorizationHeader() {
        return new RequestParameterBuilder()
                .name(GlobalConfigConstants.AUTHORIZATION)
                .description("访问令牌")
                .in("header")
                .required(false)
                .build();
    }

}
