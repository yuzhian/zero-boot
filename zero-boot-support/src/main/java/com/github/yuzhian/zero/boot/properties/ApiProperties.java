package com.github.yuzhian.zero.boot.properties;

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
@ConfigurationProperties(prefix = "zero.api")
public class ApiProperties {
    /**
     * 项目名称
     */
    private String title = "ZERO-BOOT";

    /**
     * 项目简介
     */
    private String description;

    /**
     * 项目版本号
     */
    private String version;

    /**
     * 作者
     */
    private String author;

    /**
     * 作者url
     */
    private String authorUrl;

    /**
     * 项目名称
     */
    private String authorEmail;
}
