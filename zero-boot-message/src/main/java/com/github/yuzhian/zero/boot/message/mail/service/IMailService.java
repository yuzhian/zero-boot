package com.github.yuzhian.zero.boot.message.mail.service;

import freemarker.template.TemplateException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author yuzhian
 */
public interface IMailService {
    /**
     * 发送简单邮件
     *
     * @param to      目标邮箱
     * @param subject 主题
     * @param content 内容
     * @throws MailAuthenticationException 身份验证失败
     * @throws MailParseException          解析消息失败
     * @throws MailSendException           发送消息失败
     */
    void sendSimpleMail(String to, String subject, String content) throws MailException;

    /**
     * 发送HTML邮件
     *
     * @param to          目标邮箱
     * @param subject     主题
     * @param content     内容
     * @throws MailAuthenticationException 身份验证失败
     * @throws MessagingException          消息创建失败
     * @throws MailSendException           发送消息失败
     */
    void sendHtmlMail(String to, String subject, String content) throws MessagingException;

    /**
     * 发送模板邮件
     *
     * @param to          目标邮箱
     * @param subject     主题
     * @param ftl         模板
     * @param dataModel   数据模型
     * @throws MailAuthenticationException 身份验证失败
     * @throws MessagingException          消息创建失败
     * @throws MailSendException           发送消息失败
     */
    void sendTemplateMail(String to, String subject, String ftl, Object dataModel)
            throws MessagingException, IOException, TemplateException;
}
