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
@ConfigurationProperties(prefix = "zero.wechat.mp")
public class MpProperties {
    /**
     * 公众号 APP_ID
     */
    private String appId;

    /**
     * 公众号 APP_SECRET
     */
    private String secret;

    /**
     * 公众号 TOKEN
     */
    private String token;

    /**
     * 公众号 AES_KEY
     */
    private String aesKey;
}
