package com.github.yuzhian.zero.boot.common.constant;

/**
 * 系统常量配置
 *
 * @author yuzhian
 */
public interface GlobalConfigConstants {
    /**
     * 访问令牌(请求头中携带用于验证帐号信息)
     */
    String AUTHORIZATION = "X-Auth-Token";
    /**
     * session 过期时间
     */
    int SESSION_EXPIRE = 86400; // 24小时无操作

}
