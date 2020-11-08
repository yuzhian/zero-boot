package com.github.yuzhian.zero.boot.system.service.impl;

import com.github.yuzhian.zero.boot.support.BaseService;
import com.github.yuzhian.zero.boot.system.dao.PermissionRepository;
import com.github.yuzhian.zero.boot.system.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService extends BaseService implements IPermissionService {
    private final PermissionRepository permissionRepository;
}
