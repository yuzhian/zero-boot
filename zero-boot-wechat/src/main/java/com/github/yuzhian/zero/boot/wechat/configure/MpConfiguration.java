package com.github.yuzhian.zero.boot.wechat.configure;

import com.github.yuzhian.zero.boot.wechat.handler.LogHandler;
import com.github.yuzhian.zero.boot.wechat.handler.MsgHandler;
import com.github.yuzhian.zero.boot.wechat.handler.SubscribeHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxMessageInMemoryDuplicateChecker;
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
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        router.setMessageDuplicateChecker(new WxMessageInMemoryDuplicateChecker());
        return router
                .rule().handler(logHandler).next()
                .rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler).end()
                .rule().async(false).handler(msgHandler).end()
                ;
    }
}
