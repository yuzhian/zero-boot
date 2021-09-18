INSERT INTO `sys_account`
VALUES ('113360405795033088', 'james', 'james@me.com', '13777777777',
        '{bcrypt}$2a$10$w/.C6B4MGYMc.K9pO9UdVOfBEZkJf5bhV73hgS6sQMwtIMYZhIF06', b'0');

INSERT INTO `sys_account_role`
VALUES ('113360405795033088', '1');

INSERT INTO `sys_role`
VALUES ('1', 'TESTER', '测试角色', b'0');

INSERT INTO `sys_role_permission`
VALUES ('1', '1');

INSERT INTO `sys_permission`
VALUES ('1', 'HELLO_PERMISSION_GET', '权限接口测试', b'0');
