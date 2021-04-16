package com.github.yuzhian.zero.boot.application.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author yuzhian
 */
@Slf4j
public class HelloApplicationRunListener implements SpringApplicationRunListener {

    public void contextPrepared(ConfigurableApplicationContext context) {
        if (log.isDebugEnabled()) log.debug("HelloApplicationRunListener.contextPrepared()");
    }

    public void contextLoaded(ConfigurableApplicationContext context) {
        if (log.isDebugEnabled()) log.debug("HelloApplicationRunListener.contextLoaded()");
    }

    public void started(ConfigurableApplicationContext context) {
        if (log.isDebugEnabled()) log.debug("HelloApplicationRunListener.started()");
    }

    public void running(ConfigurableApplicationContext context) {
        if (log.isDebugEnabled()) log.debug("HelloApplicationRunListener.running()");
    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        if (log.isDebugEnabled()) log.debug("HelloApplicationRunListener.failed()");
    }
}
