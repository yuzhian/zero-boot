package com.github.yuzhian.zero.boot.system.dao;

import com.github.yuzhian.zero.boot.system.entity.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission, String> {
}
