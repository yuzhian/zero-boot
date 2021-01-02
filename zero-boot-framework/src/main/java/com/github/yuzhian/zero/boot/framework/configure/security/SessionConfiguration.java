package com.github.yuzhian.zero.boot.framework.configure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yuzhian.zero.boot.properties.AccessProperties;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * 启用 redis session
 *
 * @author yuzhian
 */
@Configuration
@RequiredArgsConstructor
public class SessionConfiguration implements BeanClassLoaderAware {
    private final AccessProperties accessProperties;
    private ClassLoader loader;

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver(accessProperties.getAuthorization());
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    public void setBeanClassLoader(@NotNull ClassLoader classLoader) {
        this.loader = classLoader;
    }
}
