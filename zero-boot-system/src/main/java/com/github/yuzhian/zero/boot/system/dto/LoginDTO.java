package com.github.yuzhian.zero.boot.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * @author yuzhian
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(title = "登录信息", description = "登录请求参数")
public class LoginDTO {

    @Schema(title = "帐号", description = "用户名/邮箱/手机号")
    @Size(min = 4, max = 15, message = "帐号名长度在{min}-{max}之间")
    private String account;

    @Schema(title = "密码")
    @Size(min = 6, max = 18, message = "密码长度在{min}-{max}之间")
    private String password;
}
