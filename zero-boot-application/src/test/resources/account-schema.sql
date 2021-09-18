DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account`
(
    `user_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户主键',
    `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '用户名',
    `email`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '邮箱',
    `phone`    varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '手机号',
    `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
    `del_flag` bit(1)                                                  NULL DEFAULT b'0' COMMENT '删除标记(0正常 1删除)',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '帐号'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `sys_account_role`;
CREATE TABLE `sys_account_role`
(
    `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户主键',
    `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色主键',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '帐号-角色'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色主键',
    `role`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '角色名称',
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '角色备注',
    `del_flag`    bit(1)                                                  NULL DEFAULT b'0' COMMENT '删除标记(0正常 1删除)',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `role_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色主键',
    `permission_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限主键',
    PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色-权限'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `permission_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '权限主键',
    `permission`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '权限名称',
    `description`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '权限备注',
    `del_flag`      bit(1)                                                  NULL DEFAULT b'0' COMMENT '删除标记(0正常 1删除)',
    PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '权限'
  ROW_FORMAT = DYNAMIC;
