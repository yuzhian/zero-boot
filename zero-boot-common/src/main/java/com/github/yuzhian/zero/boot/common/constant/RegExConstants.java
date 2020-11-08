package com.github.yuzhian.zero.boot.common.constant;

/**
 * 常用正则表达式
 *
 * @author yuzhian
 */
public interface RegExConstants {
    /**
     * 正则: 邮箱
     */
    String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 正则: 手机号
     */
    String MOBILE = "^[1]\\d{10}$";

}
