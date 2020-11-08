package com.github.yuzhian.zero.boot.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yuzhian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "ApiResponse", description = "基础返回类型")
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "提示信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public static <T> ApiResponse<T> of(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public static <T> ApiResponse<T> of(Integer code, T data) {
        return new ApiResponse<>(code, null, data);
    }

    public static <T> ApiResponse<T> of(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }

}
