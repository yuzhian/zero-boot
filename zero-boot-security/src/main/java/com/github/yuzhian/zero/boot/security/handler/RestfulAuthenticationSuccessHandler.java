package com.github.yuzhian.zero.boot.security.handler;

import com.github.yuzhian.zero.boot.properties.SecurityProperties;
import com.github.yuzhian.zero.boot.util.ResponseWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yuzhian
 */
@Component
@RequiredArgsConstructor
public class RestfulAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 认证成功返回 sessionId
        ResponseWriter.response(response, Map.of(securityProperties.getName(), request.getSession().getId()));
    }
}
