package com.github.yuzhian.zero.boot.security.configure;

import com.github.yuzhian.zero.boot.security.filter.AccountAuthenticationFilter;
import com.github.yuzhian.zero.boot.security.handler.RestfulAuthenticationFailureHandler;
import com.github.yuzhian.zero.boot.security.handler.RestfulAuthenticationSuccessHandler;
import com.github.yuzhian.zero.boot.security.service.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * @author yuzhian
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AccountDetailsService accountDetailsService;
    private final RestfulAuthenticationSuccessHandler authenticationSuccessHandler;
    private final RestfulAuthenticationFailureHandler authenticationFailureHandler;
    private final RedisIndexedSessionRepository sessionRepository;
    private volatile SessionRegistry sessionRegistry;

    @Bean
    public SessionRegistry sessionRegistry() {
        if (null == sessionRegistry) {
            synchronized (this) {
                if (null == sessionRegistry) {
                    sessionRegistry = new SpringSessionBackedSessionRegistry<>(sessionRepository);
                }
            }
        }
        return sessionRegistry;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().permitAll()
                .and().formLogin().loginProcessingUrl("/login")
                .and().logout().logoutUrl("/logout")
                .and().csrf().disable().cors()
                .and().addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(concurrentSessionFilter(), ConcurrentSessionFilter.class)
        ;
    }

    private UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new AccountAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        authenticationFilter.setSessionAuthenticationStrategy(concurrentSessionControlAuthenticationStrategy());
        return authenticationFilter;
    }

    private ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlStrategy =
                new ConcurrentSessionControlAuthenticationStrategy(new SpringSessionBackedSessionRegistry<>(this.sessionRepository));
        concurrentSessionControlStrategy.setMaximumSessions(1);
        // 拦截新登录
        // concurrentSessionControlStrategy.setExceptionIfMaximumExceeded(true);
        return concurrentSessionControlStrategy;
    }

    private ConcurrentSessionFilter concurrentSessionFilter() {
        return new ConcurrentSessionFilter(sessionRegistry());
    }
}
