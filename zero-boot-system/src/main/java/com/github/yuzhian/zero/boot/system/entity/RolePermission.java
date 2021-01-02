package com.github.yuzhian.zero.boot.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author yuzhian
 */
@Data
@Entity
@IdClass(RolePermissionPK.class)
@Table(name = "sys_role_permission")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "RolePermission", description = "角色权限关联")
public class RolePermission {
    @Id
    @ApiModelProperty(value = "角色主键")
    private String roleId;

    @Id
    @ApiModelProperty(value = "权限主键")
    private String permissionId;
}
