package com.github.yuzhian.zero.boot.wechat.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author yuzhian
 */
public interface IMpService {
    WxMpUser getUserInfo(String openid, String lang) throws WxErrorException;
}
