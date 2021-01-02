package com.github.yuzhian.zero.boot.system.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author yuzhian
 */
@Data
public class RolePermissionPK implements Serializable {
    @Id
    @Column(name = "role_id", nullable = false, length = 32)
    private String roleId;

    @Id
    @Column(name = "permission_id", nullable = false, length = 32)
    private String permissionId;
}
