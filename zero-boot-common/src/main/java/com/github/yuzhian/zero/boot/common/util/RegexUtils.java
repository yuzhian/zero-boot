package com.github.yuzhian.zero.boot.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuzhian
 */
public class RegexUtils {
    /**
     * 验证正则是否匹配
     *
     * @param chars 要匹配的字符串
     * @param regex 正则表达式
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(CharSequence chars, String regex) {
        return chars != null
                && chars.length() > 0
                && Pattern.matches(regex, chars);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param chars 要匹配的字符串
     * @param regex 正则表达式
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(CharSequence chars, String regex) {
        if (chars == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chars);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param chars 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public static String[] getSplits(String chars, String regex) {
        if (chars == null) return null;
        return chars.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param chars       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换正则匹配的第一部分
     */
    public static String getReplaceFirst(String chars, String regex, String replacement) {
        if (chars == null) return null;
        return Pattern.compile(regex).matcher(chars).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param chars       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    public static String getReplaceAll(String chars, String regex, String replacement) {
        if (chars == null) return null;
        return Pattern.compile(regex).matcher(chars).replaceAll(replacement);
    }

}
