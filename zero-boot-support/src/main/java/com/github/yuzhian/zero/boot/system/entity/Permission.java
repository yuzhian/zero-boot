package com.github.yuzhian.zero.boot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_permission")
@SQLDelete(sql = "update sys_permission set del_flag = 1 where permission_id = ?")
@Where(clause = "del_flag = 0")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Permission implements Serializable {
    // 权限主键
    @Id
    private String permissionId;
    // 父权限
    @JsonIgnore
    private String parentId;
    // 权限名称
    private String permission;
    // 权限备注
    private String description;
    // 删除标记(0:正常 1:删除)
    @JsonIgnore
    private Boolean delFlag;

}
