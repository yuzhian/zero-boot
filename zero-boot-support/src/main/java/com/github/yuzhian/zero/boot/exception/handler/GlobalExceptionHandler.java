package com.github.yuzhian.zero.boot.exception.handler;

import com.github.yuzhian.zero.boot.exception.ApiException;
import com.github.yuzhian.zero.boot.support.ErrorEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author yuzhian
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 参数校验未通过 400
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorEntity> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        if (log.isWarnEnabled()) log.warn("MethodArgumentNotValidException: {}", e.getMessage());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : errors) {
            msg.append(error.getDefaultMessage()).append(",");
        }
        return ResponseEntity.badRequest().body(new ErrorEntity("ARGUMENT_NOT_VALID", msg.substring(0, msg.length() - 1)));
    }

    /**
     * 权限不足 403
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorEntity> accessDeniedExceptionHandler(AccessDeniedException e) {
        if (log.isWarnEnabled()) log.warn("AccessDeniedException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorEntity("ACCESS_DENIED", e.getMessage()));
    }

    /**
     * 用户不存在 404
     */
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorEntity> usernameNotFoundExceptionHandler(Exception e) {
        if (log.isWarnEnabled()) log.warn("UsernameNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorEntity("USER_NOT_FOUND", "用户不存在"));
    }

    /**
     * 请求方式不支持 405
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorEntity> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        if (log.isWarnEnabled()) log.warn("HttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorEntity("HTTP_REQUEST_METHOD_NOT_SUPPORTED", "当前接口不支持" + e.getMethod() + "请求"));
    }

    /**
     * 自定义业务异常处理
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorEntity> apiExceptionHandler(ApiException e) {
        if (log.isWarnEnabled()) log.warn("ApiException: {}", e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorEntity(e.getCode(), e.getMessage()));
    }

    /**
     * 未处理的异常 500
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorEntity> defaultHandler(Exception e) {
        if (log.isErrorEnabled()) log.error("Exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorEntity("SERVER_ERROR", "服务器异常"));
    }
}
