package com.github.yuzhian.zero.boot.message.mail.service.impl;

import com.github.yuzhian.zero.boot.message.mail.service.IMailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author yuzhian
 */
@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;
    private final Configuration configuration;

    @Value(value = "${spring.mail.properties.nickname}")
    private String nickname;
    @Value(value = "${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname + '<' + from + '>');
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(nickname + '<' + from + '>');
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public void sendTemplateMail(String to, String subject, String ftl, Object dataModel)
            throws MessagingException, IOException, TemplateException {
        Template template = configuration.getTemplate(ftl);
        StringWriter out = new StringWriter();
        template.process(dataModel, out);
        this.sendHtmlMail(to, subject, out.toString());
    }
}
