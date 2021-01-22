package com.github.yuzhian.zero.boot.constant;

/**
 * 正则表达式
 *
 * @author yuzhian
 */
public class RegexConstants {
    /**
     * 邮箱
     */
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 手机号
     */
    public static final String MOBILE = "^[1]\\d{10}$";

    /**
     * 身份证
     */
    public static final String ID_CARD = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    /**
     * 银行卡
     */
    public static final String BANKCARD = "^[1-9]\\d{15,18}$";
}
