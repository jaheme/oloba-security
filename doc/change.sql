
---- ������������������������������λ�ü��������޸ĵĽű�����������������������������


-- 2017-04-20 laiyinghe  DB:hr_saas
-- �½��û���Ȩ�ޱ��û���ɫ��Ȩ�ޱ�
CREATE TABLE `t_privilege_user` (
`user_id`  int UNSIGNED NOT NULL COMMENT '�û�ID' ,
`role_id`  int UNSIGNED NOT NULL COMMENT '��ɫID' ,
`create_uid`  int UNSIGNED NOT NULL COMMENT '���ɴ�Ȩ�޵��û�ID' ,
`module_name`  varchar(32) NULL DEFAULT '' COMMENT 'ģ������PModule.pname' ,
`privilege`  varchar(64) NULL DEFAULT '' COMMENT 'Ȩ����Ϣ' ,
`update_time`  datetime NULL COMMENT '����ʱ��' ,
INDEX `privilege_userid_idx` (`user_id`) 
)
;

-- 2017-04-18 laiyinghe  DB:hr_saas
-- �½���ɫ��Ȩ�ޱ�
CREATE TABLE `t_privilege_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(10) unsigned NOT NULL COMMENT '��ɫID',
  `module_name` varchar(32) NOT NULL COMMENT 'ģ������PModule.pname',
  `privilege` varchar(64) NOT NULL COMMENT 'Ȩ����Ϣ',
  `create_uid` int(10) unsigned NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `privilege_roleid_idx` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_base_role` (
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL COMMENT '��ɫ����',
  `role_desc` varchar(255) NOT NULL COMMENT '��ɫ����',
  `create_uid` int(10) unsigned NOT NULL COMMENT '�����û���id',
  `enabled` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '�Ƿ����',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 2017-04-18 laiyinghe  DB:hr_saas
-- �½������û���
CREATE TABLE `t_base_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '�ʺ�',
  `password` varchar(32) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL COMMENT '�������ǳ�',
  `company_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '������˾��ʶ',
  `create_uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '�����߱�ʶ',
  `enabled` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '�Ƿ����',
  `reg_time` datetime DEFAULT NULL COMMENT 'ע��ʱ��',
  `login_time` datetime DEFAULT NULL COMMENT '���µ�¼ʱ��',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_indx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

