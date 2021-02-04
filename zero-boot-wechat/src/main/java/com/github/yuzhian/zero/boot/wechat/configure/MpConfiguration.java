package com.github.yuzhian.zero.boot.wechat.configure;

import com.github.yuzhian.zero.boot.wechat.handler.LogHandler;
import com.github.yuzhian.zero.boot.wechat.handler.MsgHandler;
import com.github.yuzhian.zero.boot.wechat.handler.SubscribeHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuzhian
 */
@Configuration
public class MpConfiguration {
    @Bean
    public WxMpMessageRouter wxMpMessageRouter(
            WxMpService wxMpService,
            LogHandler logHandler,
            MsgHandler msgHandler,
            SubscribeHandler subscribeHandler
    ) {
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        router.rule().handler(logHandler).next();                                                           // 所有 -> 日志, 异步
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)   // 关注
                .handler(subscribeHandler).end();
        router.rule().async(false).handler(msgHandler).end();                                               // 默认, 转发消息给客服人员
        return router;
    }
}
