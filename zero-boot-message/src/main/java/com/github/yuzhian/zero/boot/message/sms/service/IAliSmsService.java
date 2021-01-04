package com.github.yuzhian.zero.boot.message.sms.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * @author yuzhian
 */
public interface IAliSmsService {
    SendSmsResponse send(String phone, String templateCode, String sign, String code);
}
