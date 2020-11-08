package com.github.yuzhian.zero.boot.system.service.impl;

import com.github.yuzhian.zero.boot.support.BaseService;
import com.github.yuzhian.zero.boot.system.dao.RoleRepository;
import com.github.yuzhian.zero.boot.system.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService extends BaseService implements IRoleService {
    private final RoleRepository roleRepository;
}
