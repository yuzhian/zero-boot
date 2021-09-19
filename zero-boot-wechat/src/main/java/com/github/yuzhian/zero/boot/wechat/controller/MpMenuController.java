package com.github.yuzhian.zero.boot.wechat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "MpMenuController", description = "微信公众平台菜单控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/wechat/mp/menu")
public class MpMenuController {
    private final WxMpService wxMpService;

    @GetMapping
    @Operation(summary = "查询菜单", description = "自定义菜单查询")
    public WxMpMenu listAllMenu() throws WxErrorException {
        return wxMpService.getMenuService().menuGet();
    }

    @PostMapping
    @Operation(summary = "创建菜单", description = "自定义菜单创建")
    public String createMenu(@RequestBody WxMenu menu) throws WxErrorException {
        return wxMpService.getMenuService().menuCreate(menu);
    }

    @DeleteMapping
    @Operation(summary = "删除菜单", description = "自定义菜单删除")
    public void removeMenu() throws WxErrorException {
        wxMpService.getMenuService().menuDelete();
    }
}
