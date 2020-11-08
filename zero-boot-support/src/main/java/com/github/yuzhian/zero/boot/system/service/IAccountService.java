package com.github.yuzhian.zero.boot.system.service;

import com.github.yuzhian.zero.boot.system.dto.RegisterDTO;
import com.github.yuzhian.zero.boot.system.entity.Account;

public interface IAccountService {
    Account getAccount(String account);

    Account register(RegisterDTO dto);
}
