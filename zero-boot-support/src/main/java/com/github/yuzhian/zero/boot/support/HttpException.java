package com.github.yuzhian.zero.boot.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义HTTP异常
 *
 * @author yuzhian
 */
@Getter
@AllArgsConstructor
public class HttpException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
