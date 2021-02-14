<h2 align="center">zero-boot</h2>

基于 SpringBoot 的多模块项目

## 项目结构

```
zero-boot
├─zero-boot-application --- 应用模块*
│  └─src
│     └─main
│        ├─java ----------- 启动类
│        └─resources ------ 配置项
├─zero-boot-support ------- 公用模块*, 项目基础配置, 公共封装类, 自定义异常, 工具类等
├─zero-boot-security ------ 安全模块*, 认证授权鉴权配置
├─zero-boot-system -------- 系统模块*, 账户及权限
├─zero-boot-resource ------ 资源模块, 上传/下载/分片上传等
├─zero-boot-message ------- 消息模块, 邮件/短信等
├─zero-boot-netty --------- netty 服务
└─zero-boot-wechat -------- 微信接口
*: 标星为核心模块
```

```ascii
+--------+
| system | ---------------------+---------------------+
+--------+                      |                     |
    ^               get userinfo|                     |
    |                           v                     v
+---------+                +----------+           +-------------+
| support | -------------> | security | --------> | application |
+---------+                +----------+           +-------------+
    |                                                 ^
    v                                                 |
+---------+                                           |
|  other  |                                           |
| modules | ------------------------------------------+
+---------+
```
