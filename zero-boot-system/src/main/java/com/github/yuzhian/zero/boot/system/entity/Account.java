package com.github.yuzhian.zero.boot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "sys_account")
@SQLDelete(sql = "update sys_account set del_flag = 1 where user_id = ?")
@Where(clause = "del_flag = 0")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Account", description = "用户登录账号信息")
public class Account {
    @Id
    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "登录名")
    private String username;

    @ApiModelProperty(value = "邮箱登陆")
    private String email;

    @ApiModelProperty(value = "手机登录")
    private String phone;

    @JsonIgnore
    @ApiModelProperty(value = "密码", hidden = true)
    private String password;

    @JsonIgnore
    @ApiModelProperty(value = "删除标记(0:正常 1:删除)", hidden = true)
    private Boolean delFlag;

    @ApiModelProperty(value = "角色集")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_account_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;
}
