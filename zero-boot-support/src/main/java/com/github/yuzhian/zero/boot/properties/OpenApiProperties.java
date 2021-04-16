package com.github.yuzhian.zero.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuzhian
 */
@Component
@ConfigurationProperties(prefix = "zero.doc")
public class OpenApiProperties {
    /**
     * 项目名称
     */
    @Getter
    @Setter
    private String title = "ZERO-BOOT";

    /**
     * 项目简介
     */
    @Getter
    @Setter
    private String description;

    /**
     * 项目版本号
     */
    @Getter
    @Setter
    private String version;

    /**
     * 联系人信息
     */
    @Getter
    private final Contact contact = new Contact();

    /**
     * 项目版本号
     */
    @Getter
    private final License license = new License();

    /**
     * 联系人信息
     */
    @Getter
    @Setter
    public static class Contact {
        /**
         * 姓名
         */
        private String name;

        /**
         * 网址
         */
        private String url;

        /**
         * 邮箱
         */
        private String email;
    }

    /**
     * 许可证
     */
    @Getter
    @Setter
    public static class License {
        /**
         * 名称
         */
        private String name = "MIT License";

        /**
         * 网址
         */
        private String url = "https://choosealicense.com/licenses/mit/";
    }
}
