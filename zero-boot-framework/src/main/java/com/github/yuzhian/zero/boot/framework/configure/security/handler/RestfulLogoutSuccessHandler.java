package com.github.yuzhian.zero.boot.framework.configure.security.handler;

import com.github.yuzhian.zero.boot.util.ResponseWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuzhian
 */
@Component
public class RestfulLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ResponseWriter.response(response, ResponseEntity.ok().build());
    }
}
