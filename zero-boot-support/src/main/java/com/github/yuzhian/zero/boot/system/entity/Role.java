package com.github.yuzhian.zero.boot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role")
@SQLDelete(sql = "update sys_role set del_flag = 1 where role_id = ?")
@Where(clause = "del_flag = 0")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Role implements Serializable {
    // 角色主键
    @Id
    private String roleId;
    // 角色名称
    private String role;
    // 角色备注
    private String description;
    // 删除标记(0:正常 1:删除)
    @JsonIgnore
    private Boolean delFlag;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private List<Permission> permissions;

}
