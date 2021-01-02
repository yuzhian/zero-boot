package com.github.yuzhian.zero.boot.system.web;

import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.system.dto.RegisterDTO;
import com.github.yuzhian.zero.boot.system.entity.Account;
import com.github.yuzhian.zero.boot.system.service.IAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "账户控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/account")
public class AccountController extends BaseController {
    private final IAccountService accountService;

    @PostMapping
    @ApiOperation(value = "注册接口")
    public ResponseEntity<Account> register(@RequestBody @Valid RegisterDTO dto) {
        Account account = accountService.register(dto);
        return ResponseEntity.ok(account);
    }
}
