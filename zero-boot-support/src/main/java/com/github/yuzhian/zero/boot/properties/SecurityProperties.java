package com.github.yuzhian.zero.boot.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    private String keyName;
    /**
     * 安全方案
     */
    private String name;
    /**
     * 请求位置
     */
    private ApiKeyAuthIn in = ApiKeyAuthIn.HEADER;

    /**
     * TODO 目前仅支持header
     */
    @RequiredArgsConstructor
    public enum ApiKeyAuthIn {
        HEADER("header"),
        QUERY("query"),
        COOKIE("cookie");

        @Getter
        private final String value;
    }
}
