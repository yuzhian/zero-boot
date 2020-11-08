package com.github.yuzhian.zero.boot.support;

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
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        if (log.isWarnEnabled()) log.warn("MethodArgumentNotValidException: {}", e.getMessage());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : errors) {
            msg.append(error.getDefaultMessage()).append(",");
        }
        return ResponseEntity.badRequest().body(msg.substring(0, msg.length() - 1));
    }

    /**
     * 权限不足 403
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<String> accessDeniedExceptionHandler(AccessDeniedException e) {
        if (log.isWarnEnabled()) log.warn("AccessDeniedException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    /**
     * 用户不存在 404
     */
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundExceptionHandler(Exception e) {
        if (log.isWarnEnabled()) log.warn("UsernameNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
    }

    /**
     * 请求方式不支持 405
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        if (log.isWarnEnabled()) log.warn("HttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("当前接口不支持" + e.getMethod() + "请求");
    }

    /**
     * 自定义HTTP异常处理
     */
    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<String> httpExceptionHandler(HttpException e) {
        if (log.isWarnEnabled()) log.warn("HttpException: {}", e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    /**
     * 自定义业务异常处理
     */
    @ExceptionHandler(value = ApiException.class)
    public ApiResponse<?> apiExceptionHandler(ApiException e) {
        if (log.isWarnEnabled()) log.warn("ApiException: {}", e.getMessage());
        return new ApiResponse<>(e.getCode(), e.getMessage(), null);
    }

    /**
     * 未处理的异常 500
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> defaultExceptionHandler(Exception e) {
        if (log.isErrorEnabled()) log.error("Exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器异常");
    }
}
