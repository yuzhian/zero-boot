package com.github.yuzhian.zero.boot.wechat.handler;

import com.github.yuzhian.zero.boot.wechat.service.IMpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户关注公众号Handler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeHandler implements WxMpMessageHandler {
    private final IMpService mpService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager)  {
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = mpService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
        } catch (WxErrorException e) {
            if (log.isErrorEnabled()) log.error(e.getError().toString());
        }

        // TODO 用户关注时的业务操作

        return WxMpXmlOutMessage.TEXT()
                .content("亲爱的" + (wxMpUser == null ? "用户" : wxMpUser.getNickname()) + "，感谢您的关注！")
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
