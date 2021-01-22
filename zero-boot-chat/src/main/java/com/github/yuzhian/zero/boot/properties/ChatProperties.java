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
@ConfigurationProperties(prefix = "zero.chat")
public class ChatProperties {
    /**
     * 端口号
     */
    private Integer port;

    /**
     * 服务路径
     */
    private String websocketPath = "/";

    /**
     * 最大不活动时长
     */
    private Integer maxInactive = 30;
}
