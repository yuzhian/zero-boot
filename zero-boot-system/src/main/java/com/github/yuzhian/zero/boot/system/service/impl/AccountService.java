package com.github.yuzhian.zero.boot.system.service.impl;

import com.github.yuzhian.zero.boot.constant.RegexConstants;
import com.github.yuzhian.zero.boot.exception.ApiException;
import com.github.yuzhian.zero.boot.support.BaseService;
import com.github.yuzhian.zero.boot.system.dao.AccountRepository;
import com.github.yuzhian.zero.boot.system.dto.RegisterDTO;
import com.github.yuzhian.zero.boot.system.entity.Account;
import com.github.yuzhian.zero.boot.system.service.IAccountService;
import com.github.yuzhian.zero.boot.util.RegexUtils;
import com.github.yuzhian.zero.boot.util.Sequence;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService extends BaseService implements IAccountService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Account getAccount(String account) {
        if (RegexUtils.isMatch(account, RegexConstants.EMAIL)) return accountRepository.getByEmail(account);
        if (RegexUtils.isMatch(account, RegexConstants.MOBILE)) return accountRepository.getByPhone(account);
        return accountRepository.getAllByUsername(account);
    }

    @Override
    public Account register(RegisterDTO dto) {
        // TODO 选择角色 认证信息 验证码
        if (dto.getPhone() != null && accountRepository.countByPhone(dto.getPhone()) > 0) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "REGISTERED_PHONE", "手机号已注册");
        } else if (dto.getEmail() != null && accountRepository.countByEmail(dto.getEmail()) > 0) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "REGISTERED_EMAIL", "邮箱已注册");
        } else if (dto.getUsername() != null && accountRepository.countByUsername(dto.getUsername()) > 0) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "REGISTERED_USERNAME", "用户名已注册");
        }
        Account account = new Account();
        BeanUtils.copyProperties(dto, account);
        account.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        account.setUserId(Sequence.getString());
        account.setDelFlag(false);
        accountRepository.save(account);
        return account;
    }
}
