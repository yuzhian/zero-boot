package com.github.yuzhian.zero.boot.framework.configure.security;

import com.github.yuzhian.zero.boot.properties.SecurityProperties;
import com.github.yuzhian.zero.boot.context.ObjectMapperHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author yuzhian
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final SecurityProperties securityProperties;
    private final AccountDetailsService accountDetailsService;
    private final RedisIndexedSessionRepository sessionRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and().authorizeRequests().anyRequest().permitAll() // 没注解权限的接口都开放
                .and().formLogin().loginProcessingUrl("/login")     // 登录接口地址
                .and().logout().logoutUrl("/logout")                // 注销接口
                .logoutSuccessHandler((request, response, authentication) -> responseObject(response, "已登出"))
                .and().csrf().disable().cors()                      // 禁用 csrf, 启用 cors. csrf 拦截除 GET|HEAD|TRACE|OPTIONS 之外的请求
                .and().addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 认证过滤器
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
    }

    /**
     * 认证过滤器配置
     */
    private UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new AccountAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // 认证成功返回 sessionId
            responseObject(response, Map.of(securityProperties.getName(), request.getSession().getId()));
        });
        authenticationFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            // 认证失败返回
            if (exception instanceof LockedException) {
                responseObject(response, "账户锁定");
            } else if (exception instanceof CredentialsExpiredException) {
                responseObject(response, "密码过期");
            } else if (exception instanceof AccountExpiredException) {
                responseObject(response, "账户过期");
            } else if (exception instanceof DisabledException) {
                responseObject(response, "账户禁用");
            } else if (exception instanceof InternalAuthenticationServiceException) {
                responseObject(response, "用户名或密码错误");
            } else if (exception instanceof SessionAuthenticationException) {
                responseObject(response, exception.getMessage());
            }
        });
        authenticationFilter.setSessionAuthenticationStrategy(authStrategy());
        return authenticationFilter;
    }

    /**
     * 并发会话控制
     */
    private ConcurrentSessionControlAuthenticationStrategy authStrategy() {
        ConcurrentSessionControlAuthenticationStrategy authenticationStrategy =
                new ConcurrentSessionControlAuthenticationStrategy(new SpringSessionBackedSessionRegistry<>(this.sessionRepository));
        authenticationStrategy.setExceptionIfMaximumExceeded(true);
        return authenticationStrategy;
    }

    private void responseObject(HttpServletResponse response, Object res) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(ObjectMapperHolder.writeValueAsString(res));
        out.flush();
        out.close();
    }
}
