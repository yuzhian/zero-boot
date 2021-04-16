package com.github.yuzhian.zero.boot.system.web;

import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.system.dto.RegisterDTO;
import com.github.yuzhian.zero.boot.system.entity.Account;
import com.github.yuzhian.zero.boot.system.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "AccountController", description = "账户控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/account")
public class AccountController extends BaseController {
    private final IAccountService accountService;

    @PostMapping
    @Operation(summary = "注册接口")
    public ResponseEntity<Account> register(@RequestBody @Valid RegisterDTO dto) {
        Account account = accountService.register(dto);
        return ResponseEntity.ok(account);
    }
}
