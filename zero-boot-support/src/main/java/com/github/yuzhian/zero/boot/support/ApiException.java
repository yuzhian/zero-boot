package com.github.yuzhian.zero.boot.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author yuzhian
 */
@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final Integer code;
    private final String message;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
