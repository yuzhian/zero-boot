package com.github.yuzhian.zero.boot.wechat.service.impl;

import com.github.yuzhian.zero.boot.wechat.service.IMpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

/**
 * @author yuzhian
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpService implements IMpService {
    private final WxMpService wxMpService;

    @Override
    public WxMpUser getUserInfo(String openid, String lang) throws WxErrorException {
        return wxMpService.getUserService().userInfo(openid, lang);
    }
}
