package com.github.yuzhian.zero.boot.wechat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "MpUserController", description = "微信公众平台用户管理控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/wechat/mp/user")
public class MpUserController {
    private final WxMpService wxMpService;

    @GetMapping(value = "/{openid}")
    @Operation(summary = "微信用户资料", description = "通过openid获取用户资料")
    public WxMpUser userinfo(@PathVariable String openid) throws WxErrorException {
        return wxMpService.getUserService().userInfo(openid);
    }
}
