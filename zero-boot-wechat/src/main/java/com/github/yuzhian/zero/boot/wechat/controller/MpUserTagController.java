package com.github.yuzhian.zero.boot.wechat.controller;

import com.github.yuzhian.zero.boot.wechat.dto.UserTaggingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.tag.WxTagListUser;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Tag(name = "MpUserTagController", description = "微信公众平台用户标签控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/wechat/mp/user_tag")
public class MpUserTagController {
    private final WxMpService wxMpService;

    @GetMapping
    @Operation(summary = "全部标签", description = "获取公众号已创建的标签")
    public List<WxUserTag> listAllTags() throws WxErrorException {
        return wxMpService.getUserTagService().tagGet();
    }

    @GetMapping("/{tagId}/users")
    @Operation(summary = "标签下的用户查询", description = "获取标签下粉丝列表")
    public WxTagListUser listAllTags(@PathVariable Long tagId) throws WxErrorException {
        return wxMpService.getUserTagService().tagListUser(tagId, null);
    }

    @GetMapping("/{openId}/tags")
    @Operation(summary = "用户所属标签查询", description = "获取用户身上的标签列表")
    public List<WxUserTag> listAllTags(@PathVariable String openId) throws WxErrorException {
        List<WxUserTag> tags = listAllTags();
        List<Long> userTagIds = wxMpService.getUserTagService().userTagList(openId);
        return tags.stream().filter(tag -> userTagIds.contains(tag.getId())).collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "创建标签", description = "创建标签, 最多可以创建100个标签")
    public WxUserTag createTag(@RequestBody WxUserTag tag) throws WxErrorException {
        return wxMpService.getUserTagService().tagCreate(tag.getName());
    }

    @PutMapping
    @Operation(summary = "编辑标签", description = "编辑标签")
    public Boolean updateTag(@RequestBody WxUserTag tag) throws WxErrorException {
        return wxMpService.getUserTagService().tagUpdate(tag.getId(), tag.getName());
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "删除标签", description = "删除标签")
    public Boolean updateTag(@PathVariable Long tagId) throws WxErrorException {
        return wxMpService.getUserTagService().tagDelete(tagId);
    }

    @PostMapping("/tagging")
    @Operation(summary = "批量打标签", description = "批量为用户打一个标签")
    public Boolean batchTagging(@RequestBody UserTaggingDTO dto) throws WxErrorException {
        return wxMpService.getUserTagService().batchTagging(dto.getTagId(), dto.getOpenIds());
    }

    @DeleteMapping("/untagging")
    @Operation(summary = "批量移除标签", description = "批量为用户移除一个指定标签")
    public Boolean batchUntagging(@RequestBody UserTaggingDTO dto) throws WxErrorException {
        return wxMpService.getUserTagService().batchTagging(dto.getTagId(), dto.getOpenIds());
    }
}
