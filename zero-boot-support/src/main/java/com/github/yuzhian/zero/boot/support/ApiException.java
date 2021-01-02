package com.github.yuzhian.zero.boot.support;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义业务异常
 *
 * @author yuzhian
 */
@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ApiException(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public ApiException(String code, String message) {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = code;
        this.message = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
