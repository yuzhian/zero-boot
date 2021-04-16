package com.github.yuzhian.zero.boot.application.controller;

import com.github.yuzhian.zero.boot.context.ApplicationContextHolder;
import com.github.yuzhian.zero.boot.exception.ApiException;
import com.github.yuzhian.zero.boot.properties.OpenApiProperties;
import com.github.yuzhian.zero.boot.properties.SecurityProperties;
import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.system.dto.LoginDTO;
import com.github.yuzhian.zero.boot.system.entity.Account;
import com.github.yuzhian.zero.boot.system.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yuzhian
 */
@Slf4j
@RestController
@Profile(value = "dev")
@RequiredArgsConstructor
@Tag(name = "HelloController", description = "功能测试控制器")
public class HelloController extends BaseController {
    private static final String TEST_SpEL_ROLE = "hasRole('ROLE_TEST')";
    private static final String TEST_SpEL_PERMISSION = "hasAuthority('HELLO_PERMISSION_GET')";

    private final RedisTemplate<String, Object> redisTemplate;
    private final SecurityProperties securityProperties;
    private final SessionRegistry sessionRegistry;
    private final IAccountService accountService;

    @GetMapping(value = "/jpa/{account}")
    @Operation(summary = "jpa 测试", description = "账号获取用户信息")
    public Account saveRedis(@PathVariable @NotEmpty(message = "查询账号不可空") String account) {
        return accountService.getAccount(account);
    }

    @Operation(summary = "redis 测试")
    @PostMapping(value = "/redis/{key}")
    public ResponseEntity<Object> testRedis(@PathVariable @NotEmpty(message = "key不可为空") String key,
                                            @RequestBody @NotEmpty(message = "val不可为空") Map<String, Object> val) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, val, 20, TimeUnit.SECONDS);
        return ResponseEntity.ok(Objects.requireNonNull(ops.get(key)));
    }

    @Operation(summary = "异常处理测试")
    @GetMapping(value = "/exception")
    public ResponseEntity<Void> testExceptionHandle() {
        throw new ApiException(HttpStatus.NOT_FOUND, "DEMO", "测试异常");
    }

    @GetMapping("/bean")
    @Operation(summary = "工具类获取Bean")
    public OpenApiProperties bean() {
        return ApplicationContextHolder.getBean(OpenApiProperties.class);
    }

    @PostMapping("/login")
    @Operation(summary = "security 登陆拦截测试")
    public Map<String, String> login(@RequestBody @Valid LoginDTO dto) {
        if (log.isInfoEnabled()) log.info("login dto: {}", dto);
        return null;
    }

    @PostMapping("/logout")
    @Operation(summary = "security 注销拦截测试")
    public Map<String, String> logout() {
        return null;
    }

    @GetMapping({"/expire", "/expire/{sessionId}"})
    @Operation(summary = "主动过期session")
    public ResponseEntity<Void> expire(@PathVariable(required = false) String sessionId, HttpServletRequest request) {
        if (!StringUtils.hasText(sessionId)) {
            sessionId = request.getHeader(securityProperties.getName());
            if (!StringUtils.hasText(sessionId)) {
                return ResponseEntity.badRequest().build();
            }
        }
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        sessionInformation.expireNow();
        return ResponseEntity.status(sessionInformation.isExpired() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/userinfo")
    @PreAuthorize("isFullyAuthenticated()")
    @Operation(summary = "security 获取用户信息")
    public Authentication userinfo() {
        if (log.isInfoEnabled()) log.info("userid: {}, userinfo: {}", super.userid(), super.userinfo());
        return super.userinfo();
    }

    @GetMapping("/role")
    @PreAuthorize(TEST_SpEL_ROLE)
    @Operation(summary = "角色测试", description = TEST_SpEL_ROLE)
    public String hasRole() {
        return TEST_SpEL_ROLE;
    }

    @GetMapping("/permission")
    @PreAuthorize(TEST_SpEL_PERMISSION)
    @Operation(summary = "权限测试", description = TEST_SpEL_PERMISSION)
    public String hasPermission() {
        return TEST_SpEL_PERMISSION;
    }
}
