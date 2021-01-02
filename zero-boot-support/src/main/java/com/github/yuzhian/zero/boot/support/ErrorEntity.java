package com.github.yuzhian.zero.boot.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author yuzhian
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "ErrorEntity", description = "错误信息响应体")
public class ErrorEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private String code;

    @ApiModelProperty(value = "文字信息")
    private String message;
}
