package com.github.yuzhian.zero.boot.properties;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuzhian
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "zero.security")
public class SecurityProperties {
    /**
     * 安全方案
     */
    private String key = "default_key";
    /**
     * 安全方案
     */
    private SecurityScheme.Type type = SecurityScheme.Type.APIKEY;
    /**
     * 请求位置
     */
    private SecurityScheme.In in = SecurityScheme.In.HEADER;
    /**
     * 安全方案
     */
    private String name = "X-Auth-Token";
    /**
     * 安全方案
     */
    private String description = "system access token";
}
