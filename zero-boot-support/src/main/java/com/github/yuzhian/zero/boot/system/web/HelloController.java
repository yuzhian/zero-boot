package com.github.yuzhian.zero.boot.system.web;

import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.support.HttpException;
import com.github.yuzhian.zero.boot.system.dto.AuthenticationDTO;
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
import org.springframework.web.bind.annotation.*;

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
public class HelloController extends BaseController {
    private final RedisTemplate<String, Object> redisTemplate;
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
    public ResponseEntity<Object> testExceptionHandle() {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "测试请求方式错误");
    }

    @PostMapping("/login")
    @ApiOperation(value = "security 登陆接口")
    public Map<String, String> login(@RequestBody @Valid AuthenticationDTO dto) {
        if (log.isInfoEnabled()) log.info("login dto: {}", dto);
        return null;
    }

    @GetMapping("/userinfo")
    @PreAuthorize("isFullyAuthenticated()")
    @ApiOperation(value = "security 获取用户信息")
    public Authentication userinfo() {
        if (log.isInfoEnabled()) log.info("userid: {}, userinfo: {}", super.userid(), super.userinfo());
        return super.userinfo();
    }

    @PostMapping("/logout")
    @ApiOperation(value = "security 注销接口")
    public Map<String, String> logout() {
        return null;
    }

    @GetMapping("/role")
    @PreAuthorize("hasRole('ROLE_TEST')")
    @ApiOperation(value = "权限测试: ROLE_TEST", notes = "需 ROLE_ADMIN 角色")
    public String hasRole() {
        return "hasRole";
    }

    @GetMapping("/authority")
    @PreAuthorize("hasAuthority('SYS_HELLO_AUTHORITY_GET')")
    @ApiOperation(value = "权限测试: SYS_HELLO_AUTHORITY_GET", notes = "需 ADMIN 权限")
    public String hasAuthority() {
        return "hasAuthority";
    }
}
