package com.github.yuzhian.zero.boot.wechat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(title = "UserTaggingDTO", description = "标签批量操作实体")
public class UserTaggingDTO {
    @Schema(title = "标签ID", description = "标签ID")
    private Long tagId;

    @Schema(title = "用户ID列表", description = "用户ID列表")
    private String[] openIds;
}
