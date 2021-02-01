package com.github.yuzhian.zero.boot.application.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhian
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        Map<String, Object> map = new HashMap<>(1);
        map.put("k1", "v1");
        MapPropertySource mapPropertySource = new MapPropertySource("HelloApplicationContextInitializer", map);
        environment.getPropertySources().addLast(mapPropertySource);

        if (log.isInfoEnabled()) log.info("HelloApplicationContextInitializer initialized!");
    }
}
