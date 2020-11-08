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
@Table(name = "sys_account")
@SQLDelete(sql = "update sys_account set del_flag = 1 where user_id = ?")
@Where(clause = "del_flag = 0")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Account implements Serializable {
    // 用户主键
    @Id
    private String userId;
    // 登录名
    private String username;
    // 邮箱登陆
    private String email;
    // 手机登录
    private String phone;
    // 密码
    @JsonIgnore
    private String password;
    // 密码盐
    @JsonIgnore
    private String salt;
    // 删除标记(0:正常 1:删除)
    @JsonIgnore
    private Boolean delFlag;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_account_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;
}
