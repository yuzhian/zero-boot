package com.github.yuzhian.zero.boot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_permission")
@SQLDelete(sql = "update sys_permission set del_flag = 1 where permission_id = ?")
@Where(clause = "del_flag = 0")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(title = "权限", description = "访问权限信息")
public class Permission {
    @Id
    @Schema(title = "权限主键")
    private String permissionId;

    @Schema(title = "权限名称")
    private String permission;

    @Schema(title = "权限备注")
    private String description;

    @JsonIgnore
    @Schema(title = "删除标记(0:正常 1:删除)", hidden = true)
    private Boolean delFlag;

    @JsonIgnore
    @Schema(title = "角色列表")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "permission_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;
}
