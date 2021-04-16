package com.github.yuzhian.zero.boot.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "错误信息", description = "错误信息响应体")
public class ErrorEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "状态码")
    private String code;

    @Schema(title = "文字信息")
    private String message;
}
