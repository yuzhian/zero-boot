package com.github.yuzhian.zero.boot.wechat.controller;

import com.github.yuzhian.zero.boot.support.ApiException;
import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.wechat.service.IMpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author yuzhian
 */
@Slf4j
@RestController
@Api(tags = "微信公众平台控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/wechat/mp")
public class MpController extends BaseController {
    private final WxMpService wxMpService;
    private final IMpService mpService;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "微信消息验证", notes = "供微信验证服务器资源")
    public String echo(String timestamp, String nonce, String signature, @SuppressWarnings("SpellCheckingInspection") String echostr) {
        // TODO AOP
        // 验证消息签名, 判断是否为公众平台消息
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "WECHAT_SIGNATURE_FAILED", "非法请求");
        }
        return echostr;
    }

    @PostMapping(produces = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "微信消息处理", notes = "微信消息处理")
    public String handleMessage(String timestamp, String nonce, String signature,
                                @RequestParam(value = "encrypt_type", defaultValue = "raw") String encryptType,
                                @RequestParam(value = "msg_signature", required = false) String msgSignature,
                                @RequestBody String xml) {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "WECHAT_SIGNATURE_FAILED", "非法请求");
        }

        WxMpXmlMessage request = switch (encryptType) {
            case "raw" -> WxMpXmlMessage.fromXml(xml); // 明文消息
            case "aes" -> WxMpXmlMessage.fromEncryptedXml(xml, wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature); // AES加密消息, 需配置`zero.wechat.mp.aes-key`
            default -> throw new ApiException(HttpStatus.BAD_REQUEST, "ENCRYPT_TYPE_UNRECOGNIZED", "加密类型不可识别");
        };
        if (log.isDebugEnabled()) log.debug("message encrypt type: {}, content：{} ", encryptType, request.toString());

        // 处理消息并返回
        WxMpXmlOutMessage response = this.mpService.route(request);
        return response == null ? "" : response.toXml();
    }
}
