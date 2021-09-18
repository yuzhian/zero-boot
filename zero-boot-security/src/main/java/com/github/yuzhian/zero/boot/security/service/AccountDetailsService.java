package com.github.yuzhian.zero.boot.security.service;

import com.github.yuzhian.zero.boot.system.entity.Account;
import com.github.yuzhian.zero.boot.system.entity.Permission;
import com.github.yuzhian.zero.boot.system.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yuzhian
 */
@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService, Serializable {
    private final IAccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccount(username);
        if (null == account) throw new UsernameNotFoundException("用户名不存在");

        String[] authorities = Stream.concat(
                account.getRoles().stream().flatMap(role -> role.getPermissions().stream()).map(Permission::getPermission),
                account.getRoles().stream().map(role -> "ROLE_" + role.getRole())
        ).distinct().toArray(String[]::new);
        return User.builder()
                .username(account.getUserId())
                .password(account.getPassword())
                .authorities(authorities)
                .build();
    }
}
