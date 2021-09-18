package com.github.yuzhian.zero.boot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role")
@SQLDelete(sql = "update sys_role set del_flag = 1 where role_id = ?")
@Where(clause = "del_flag = 0")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(title = "角色", description = "绑定角色信息")
public class Role {
    @Id
    @Schema(title = "角色主键")
    private String roleId;

    @Schema(title = "角色名称")
    private String role;

    @Schema(title = "角色备注")
    private String description;

    @JsonIgnore
    @Schema(title = "删除标记(0:正常 1:删除)", hidden = true)
    private Boolean delFlag;

    @Schema(title = "权限集")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private List<Permission> permissions;

    @JsonIgnore
    @Schema(title = "用户列表")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_account_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @ToString.Exclude
    private List<Account> accounts;
}
