package com.github.yuzhian.zero.boot.application.controller;

import com.aliyuncs.utils.StringUtils;
import com.github.yuzhian.zero.boot.context.ApplicationContextHolder;
import com.github.yuzhian.zero.boot.exception.ApiException;
import com.github.yuzhian.zero.boot.properties.ApiProperties;
import com.github.yuzhian.zero.boot.properties.SecurityProperties;
import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.system.dto.LoginDTO;
import com.github.yuzhian.zero.boot.system.entity.Account;
import com.github.yuzhian.zero.boot.system.service.IAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "功能测试控制器")
@SuppressWarnings("SameReturnValue")
public class HelloController extends BaseController {
    private static final String TEST_SpEL_ROLE = "hasRole('ROLE_TEST')";
    private static final String TEST_SpEL_PERMISSION = "hasAuthority('HELLO_PERMISSION_GET')";

    private final RedisTemplate<String, Object> redisTemplate;
    private final SecurityProperties securityProperties;
    private final SessionRegistry sessionRegistry;
    private final IAccountService accountService;

    @GetMapping(value = "/jpa/{account}")
    @ApiOperation(value = "jpa 测试", notes = "账号获取用户信息")
    public Account saveRedis(@PathVariable @NotEmpty(message = "查询账号不可空") String account) {
        return accountService.getAccount(account);
    }

    @ApiOperation(value = "redis 测试")
    @PostMapping(value = "/redis/{key}")
    public ResponseEntity<Object> testRedis(@PathVariable @NotEmpty(message = "key不可为空") String key,
                                            @RequestBody @NotEmpty(message = "val不可为空") Map<String, Object> val) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, val, 20, TimeUnit.SECONDS);
        return ResponseEntity.ok(Objects.requireNonNull(ops.get(key)));
    }

    @ApiOperation(value = "异常处理测试")
    @GetMapping(value = "/exception")
    public ResponseEntity<Void> testExceptionHandle() {
        throw new ApiException(HttpStatus.NOT_FOUND, "DEMO", "测试异常");
    }

    @GetMapping("/bean")
    @ApiOperation(value = "工具类获取Bean")
    public ApiProperties bean() {
        return ApplicationContextHolder.getBean(ApiProperties.class);
    }

    @PostMapping("/login")
    @ApiOperation(value = "security 登陆拦截测试")
    public Map<String, String> login(@RequestBody @Valid LoginDTO dto) {
        if (log.isInfoEnabled()) log.info("login dto: {}", dto);
        return null;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "security 注销拦截测试")
    public Map<String, String> logout() {
        return null;
    }

    @GetMapping({"/expire", "/expire/{sessionId}"})
    @ApiOperation(value = "主动过期session")
    public ResponseEntity<Void> expire(@PathVariable(required = false) String sessionId, HttpServletRequest request) {
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = request.getHeader(securityProperties.getName());
            if (StringUtils.isEmpty(sessionId)) {
                return ResponseEntity.badRequest().build();
            }
        }
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        sessionInformation.expireNow();
        return ResponseEntity.status(sessionInformation.isExpired() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/userinfo")
    @PreAuthorize("isFullyAuthenticated()")
    @ApiOperation(value = "security 获取用户信息")
    public Authentication userinfo() {
        if (log.isInfoEnabled()) log.info("userid: {}, userinfo: {}", super.userid(), super.userinfo());
        return super.userinfo();
    }

    @GetMapping("/role")
    @PreAuthorize(TEST_SpEL_ROLE)
    @ApiOperation(value = "角色测试", notes = TEST_SpEL_ROLE)
    public String hasRole() {
        return TEST_SpEL_ROLE;
    }

    @GetMapping("/permission")
    @PreAuthorize(TEST_SpEL_PERMISSION)
    @ApiOperation(value = "权限测试", notes = TEST_SpEL_PERMISSION)
    public String hasPermission() {
        return TEST_SpEL_PERMISSION;
    }
}
