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
@IdClass(AccountRolePK.class)
@Table(name = "sys_account_role")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "AccountRole", description = "账号角色关联")
public class AccountRole {
    @Id
    @ApiModelProperty(value = "用户主键")
    private String userId;

    @Id
    @ApiModelProperty(value = "角色主键")
    private String roleId;
}
