package com.github.yuzhian.zero.boot.message.sms.service.impl;

import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.github.yuzhian.zero.boot.message.sms.service.IAliSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author yuzhian
 */
@Service
@RequiredArgsConstructor
public class AliSmsService implements IAliSmsService {
    private final ISmsService smsService;

    @Override
    public SendSmsResponse send(String phone, String templateCode, String sign, String code) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(sign);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("****TraceId");
        try {
            return smsService.sendSmsRequest(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new SendSmsResponse();
    }

    public SendBatchSmsResponse sendBatch(String templateCode, String code, String phones, String signs) {
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        request.setSysMethod(MethodType.POST);
        request.setPhoneNumberJson("[" + phones + "]");
        request.setSignNameJson("[" + signs + "]");
        request.setTemplateCode(templateCode);
        request.setTemplateParamJson("{\"code\":\"" + code + "\"}");
        // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCodeJson("[\"90997\",\"90998\"]");
        try {
            return smsService.sendSmsBatchRequest(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new SendBatchSmsResponse();
    }

    public QuerySendDetailsResponse querySendDetails(String telephone) {
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(telephone);
        // 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
        request.setSendDate("20200103");
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        try {
            return smsService.querySendDetails(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return new QuerySendDetailsResponse();
    }
}
