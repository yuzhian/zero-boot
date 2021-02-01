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

    public HelloApplicationRunListener(SpringApplication application, String[] args) {
    }

    public void starting() {
        if (log.isDebugEnabled()) log.debug("HelloApplicationRunListener.starting()");
    }

    public void environmentPrepared(ConfigurableEnvironment environment) {
        if (log.isDebugEnabled()) log.debug("HelloApplicationRunListener.environmentPrepared()");
    }

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
