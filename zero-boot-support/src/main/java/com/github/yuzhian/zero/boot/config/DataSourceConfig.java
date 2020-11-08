package com.github.yuzhian.zero.boot.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuzhian
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("allow", "127.0.0.1"); // IP白名单
        registrationBean.addInitParameter("deny", ""); // IP黑名单
        registrationBean.addInitParameter("loginUsername", "root");
        registrationBean.addInitParameter("loginPassword", "!QAZ@WSX");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }
}
