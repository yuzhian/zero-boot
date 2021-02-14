package com.github.yuzhian.zero.boot.security.handler;

import com.github.yuzhian.zero.boot.context.ObjectMapperHolder;
import com.github.yuzhian.zero.boot.util.ResponseWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yuzhian
 */
@Component
public class RestfulAuthenticationFailureHandler implements AuthenticationFailureHandler {
    // 异常类型与对应的返回值
    private static final Map<Class<? extends Exception>, String> ERROR_RESPONSE_MAP;

    static {
        ERROR_RESPONSE_MAP = Map.of(
                LockedException.class, ObjectMapperHolder.writeValueAsString(
                        Map.of("code", "ACCOUNT_LOCKED", "message", "账号锁定")
                ),
                AccountExpiredException.class, ObjectMapperHolder.writeValueAsString(
                        Map.of("code", "ACCOUNT_EXPIRED", "message", "账号过期")
                ),
                DisabledException.class, ObjectMapperHolder.writeValueAsString(
                        Map.of("code", "ACCOUNT_DISABLED", "message", "账号禁用")
                ),
                BadCredentialsException.class, ObjectMapperHolder.writeValueAsString(
                        Map.of("code", "BAD_CREDENTIALS", "message", "错误的凭据")
                ),
                InternalAuthenticationServiceException.class, ObjectMapperHolder.writeValueAsString(
                        Map.of("code", "AUTHENTICATION_FAILED", "message", "用户名或密码错误")
                )
        );
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // 需要从异常中取值的返回信息
        if (exception instanceof SessionAuthenticationException) {
            ResponseWriter.response(response, ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "AUTHENTICATION_FAILED", "message", exception.getMessage())));
        } else {
            ResponseWriter.response(response, ERROR_RESPONSE_MAP.get(exception.getClass()));
        }
    }
}
