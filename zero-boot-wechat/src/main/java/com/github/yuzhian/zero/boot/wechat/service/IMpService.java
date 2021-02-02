package com.github.yuzhian.zero.boot.wechat.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author yuzhian
 */
public interface IMpService {
    WxMpXmlOutMessage route(WxMpXmlMessage inMessage);

    WxMpUser getUserInfo(String openid, String lang);

    void refreshRouter();
}
