package com.github.yuzhian.zero.boot.wechat.configure;

import com.github.yuzhian.zero.boot.properties.MpProperties;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuzhian
 */
@Configuration
@RequiredArgsConstructor
public class MpConfiguration {
    private final MpProperties mpProperties;

    @Bean(name = "wxMpConfigStorage")
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(mpProperties.getAppId());
        configStorage.setSecret(mpProperties.getSecret());
        configStorage.setToken(mpProperties.getToken());
        configStorage.setAesKey(mpProperties.getAesKey());
        return configStorage;
    }

    @Bean(name = "wxMpService")
    public WxMpService wxMpService(@Qualifier("wxMpConfigStorage") WxMpConfigStorage wxMpConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }
}
