package com.github.yuzhian.zero.boot.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yuzhian.zero.boot.common.constant.GlobalConfigConstants;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * 启用 redis session
 *
 * @author yuzhian
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = GlobalConfigConstants.SESSION_EXPIRE)
public class SessionConfig implements BeanClassLoaderAware {
    private ClassLoader loader;

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    /**
     * sessionId 获取, 使用 Header 方式解析 sessionId
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver(GlobalConfigConstants.AUTHORIZATION);
    }

    /**
     * session 存储, 序列化方式
     */
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
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }
}
