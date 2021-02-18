package com.github.yuzhian.zero.boot.security.filter;

import com.github.yuzhian.zero.boot.context.ObjectMapperHolder;
import com.github.yuzhian.zero.boot.system.dto.LoginDTO;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * POST JSON 登录实现, 自定义获取`账号`和`密码`的方式
 *
 * @author yuzhian
 */
public class AccountAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ThreadLocal<LoginDTO> threadLocal = new ThreadLocal<>();

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return this.getAuthentication(request).getAccount();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return this.getAuthentication(request).getPassword();
    }

    /**
     * 使用`JSON`方式从 HttpServletRequest 读取`账号`和`密码`
     */
    private LoginDTO getAuthentication(HttpServletRequest request) {
        LoginDTO loginDTO = threadLocal.get();
        if (null != loginDTO) return loginDTO;

        try (InputStream is = request.getInputStream()) {
            loginDTO = ObjectMapperHolder.getObjectMapper().readValue(is, LoginDTO.class);
        } catch (IOException ignore) {
        }
        if (loginDTO == null) loginDTO = new LoginDTO();
        threadLocal.set(loginDTO);
        return loginDTO;
    }
}
