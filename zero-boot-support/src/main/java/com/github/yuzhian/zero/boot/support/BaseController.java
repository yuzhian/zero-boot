package com.github.yuzhian.zero.boot.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author yuzhian
 */
public class BaseController {

    protected Authentication userinfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    protected String userid() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
