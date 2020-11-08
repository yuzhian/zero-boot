package com.github.yuzhian.zero.boot.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yuzhian.zero.boot.system.dto.AuthenticationDTO;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * POST JSON 登录实现, 自定义获取 用户名 和 密码 的方式
 *
 * @author yuzhian
 */
public class AccountAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ThreadLocal<AuthenticationDTO> threadLocal = new ThreadLocal<>();

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return this.getAuthentication(request).getAccount();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return this.getAuthentication(request).getPassword();
    }

    /**
     * 获取 HttpServletRequest 请求参数
     * HttpServletRequest 只能读一次, 定义成员变量二次读取; 不能被其他请求访问, 使用 ThreadLocal.
     */
    private AuthenticationDTO getAuthentication(HttpServletRequest request) {
        AuthenticationDTO authenticationDTO = threadLocal.get();
        if (authenticationDTO == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                authenticationDTO = objectMapper.readValue(is, AuthenticationDTO.class);
            } catch (IOException ignore) {
            }
            if (authenticationDTO == null) authenticationDTO = new AuthenticationDTO();
            threadLocal.set(authenticationDTO);
        }
        return authenticationDTO;
    }
}
