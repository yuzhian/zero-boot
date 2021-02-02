package com.github.yuzhian.zero.boot.wechat.service.impl;

import com.github.yuzhian.zero.boot.wechat.handler.LogHandler;
import com.github.yuzhian.zero.boot.wechat.handler.MsgHandler;
import com.github.yuzhian.zero.boot.wechat.handler.SubscribeHandler;
import com.github.yuzhian.zero.boot.wechat.service.IMpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author yuzhian
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpService implements IMpService {
    private final WxMpService wxMpService;

    private final LogHandler logHandler;
    private final MsgHandler msgHandler;
    private final SubscribeHandler subscribeHandler;

    private WxMpMessageRouter router;

    @Override
    public WxMpXmlOutMessage route(WxMpXmlMessage inMessage) {
        try {
            return this.router.route(inMessage);
        } catch (Exception e) {
            if (log.isErrorEnabled()) log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public WxMpUser getUserInfo(String openid, String lang) {
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = this.wxMpService.getUserService().userInfo(openid, lang);
        } catch (WxErrorException e) {
            if (log.isErrorEnabled()) log.error(e.getError().toString());
        }
        return wxMpUser;
    }

    @Override
    @PostConstruct
    public void refreshRouter() {
        final WxMpMessageRouter router = new WxMpMessageRouter(this.wxMpService);
        router.rule().handler(this.logHandler).next();                                                      // 所有 -> 日志, 异步
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)   // 关注
                .handler(this.subscribeHandler).end();
        router.rule().async(false).handler(this.msgHandler).end();                                          // 默认, 转发消息给客服人员
        this.router = router;
    }
}
