package com.github.yuzhian.zero.boot.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author yuzhian
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "RegisterDTO", description = "注册请求参数")
public class RegisterDTO {

    @ApiModelProperty(value = "用户名")
    @Size(min = 4, max = 15, message = "帐号名长度在{min}-{max}之间")
    private String username;

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = "^[1]\\d{10}$", message = "手机号码格式错误")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 18, message = "密码长度在{min}-{max}之间")
    private String password;
}
