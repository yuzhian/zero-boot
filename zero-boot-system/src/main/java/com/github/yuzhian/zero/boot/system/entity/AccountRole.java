package com.github.yuzhian.zero.boot.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "账号角色关联", description = "账号角色关联")
public class AccountRole {
    @Id
    @Schema(title = "用户主键")
    private String userId;

    @Id
    @Schema(title = "角色主键")
    private String roleId;
}
