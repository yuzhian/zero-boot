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
@ConfigurationProperties(prefix = "zero.access")
public class AccessProperties {
    /**
     * 访问令牌(请求头中携带用于验证帐号信息)
     */
    private String authorization = "X-Auth-Token";
}
