package com.github.yuzhian.zero.boot.system.service.impl;

import com.github.yuzhian.zero.boot.common.constant.RegExConstants;
import com.github.yuzhian.zero.boot.common.util.RegexUtils;
import com.github.yuzhian.zero.boot.common.util.Sequence;
import com.github.yuzhian.zero.boot.support.ApiException;
import com.github.yuzhian.zero.boot.support.BaseService;
import com.github.yuzhian.zero.boot.system.dao.AccountRepository;
import com.github.yuzhian.zero.boot.system.dto.RegisterDTO;
import com.github.yuzhian.zero.boot.system.entity.Account;
import com.github.yuzhian.zero.boot.system.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService extends BaseService implements IAccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account getAccount(String account) {
        if (RegexUtils.isMatch(account, RegExConstants.EMAIL)) return accountRepository.getByEmail(account);
        if (RegexUtils.isMatch(account, RegExConstants.MOBILE)) return accountRepository.getByPhone(account);
        return accountRepository.getByUsername(account);
    }

    @Override
    public Account register(RegisterDTO dto) {
        // TODO 选择角色 认证信息 验证码
        if (dto.getPhone() != null && accountRepository.countByPhone(dto.getPhone()) > 0) {
            throw new ApiException(2, "手机号已注册");
        } else if (dto.getEmail() != null && accountRepository.countByEmail(dto.getEmail()) > 0) {
            throw new ApiException(2, "邮箱已注册");
        } else if (dto.getUsername() != null && accountRepository.countByUsername(dto.getUsername()) > 0) {
            throw new ApiException(2, "用户名已注册");
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
