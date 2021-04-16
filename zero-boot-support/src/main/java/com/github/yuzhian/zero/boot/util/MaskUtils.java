package com.github.yuzhian.zero.boot.util;

import org.springframework.util.StringUtils;

import javax.transaction.NotSupportedException;
import java.util.regex.Pattern;

import static com.github.yuzhian.zero.boot.constant.RegexConstants.*;

/**
 * 脱敏工具类
 *
 * @author yuzhian
 */
public class MaskUtils {
    private MaskUtils() throws NotSupportedException {
        throw new NotSupportedException("MaskUtils instantiation is not allowed");
    }

    /**
     * @param string 待处理字符串
     * @param ln     左侧保留位数
     * @param to     替换的字符
     * @param rn     右侧保留位数
     */
    public static String replace(String string, int ln, char to, int rn) {
        if (!StringUtils.hasText(string) || string.length() <= ln + rn) {
            return string;
        }
        return string.replaceAll(String.format("(?<=\\w{%d})\\w+(?=\\w{%d})", ln, rn), String.valueOf(to).repeat(string.length() - ln - rn));
    }

    /**
     * @param string 待处理字符串
     * @param ln     左侧保留位数
     * @param to     替换的字符
     */
    public static String replace(String string, int ln, char to) {
        if (!StringUtils.hasText(string) || string.length() <= ln) {
            return string;
        }
        return string.replaceAll(String.format("(?<=\\w{%d})\\w+", ln), String.valueOf(to).repeat(string.length() - ln));
    }

    /**
     * @param string 待处理字符串
     * @param to     替换的字符
     * @param rn     右侧保留位数
     */
    public static String replace(String string, char to, int rn) {
        if (!StringUtils.hasText(string) || string.length() <= rn) {
            return string;
        }
        return string.replaceAll(String.format("\\w+(?=\\w{%d})", rn), String.valueOf(to).repeat(string.length() - rn));
    }

    /**
     * @param name 中文姓名
     * @return string
     */
    public static String chineseName(String name) {
        if (!Pattern.matches("[\u4e00-\u9fa5]{2,}", name)) {
            return name;
        }
        StringBuilder sb = new StringBuilder(name);

        if (name.length() == 2) {
            return sb.replace(1, 2, "*").toString();
        }
        return sb.replace(name.length() - 2, name.length() - 1, "*").toString();
    }

    /**
     * @param ph 手机号
     * @return string
     */
    public static String phone(String ph) {
        if (ph.strip().length() != 11) {
            return ph;
        }
        return ph.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * @param email 邮箱
     * @return string
     */
    public static String email(String email) {
        if (!Pattern.matches(EMAIL, email)) {
            return email;
        }
        return email.replaceAll("(\\w{2})\\w{3,}(\\w)@(\\w+)", "$1***$2@$3");
    }

    /**
     * @param idCard 身份证号
     * @return string
     */
    public static String idCard(String idCard) {
        if (!Pattern.matches(ID_CARD, idCard)) {
            return idCard;
        }
        return idCard.replaceAll("(\\d{6})\\d{8}(\\d{3}\\w)", "$1********$2");
    }

    /**
     * @param no 银行卡卡号
     * @return string
     */
    public static String bankcard(String no) {
        if (!Pattern.matches(BANKCARD, no)) {
            return no;
        }
        return no.replaceAll("(\\d{6})\\d{8}(\\d{3}\\w)", "$1********$2");
    }
}
