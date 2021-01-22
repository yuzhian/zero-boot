package com.github.yuzhian.zero.boot.framework.configure.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.yuzhian.zero.boot.context.ObjectMapperHolder;
import com.github.yuzhian.zero.boot.util.HttpServletResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yuzhian
 */
@Component
public class RestfulLogoutSuccessHandler implements LogoutSuccessHandler {
    private static String SUCCESS_MESSAGE;

    static {
        try {
            SUCCESS_MESSAGE = ObjectMapperHolder.writeValueAsString(Map.of("code", "OK", "message", "已登出"));
        } catch (JsonProcessingException ignore) {
        }
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpServletResponseUtils.response(response, SUCCESS_MESSAGE);
    }
}
