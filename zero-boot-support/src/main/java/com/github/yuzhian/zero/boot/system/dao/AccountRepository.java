package com.github.yuzhian.zero.boot.system.dao;

import com.github.yuzhian.zero.boot.system.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
    Account getByUsername(String username);

    Account getByEmail(String email);

    Account getByPhone(String phone);

    int countByUsername(String username);

    int countByEmail(String email);

    int countByPhone(String phone);
}
