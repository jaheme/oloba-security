
---- ↓↓↓↓↓↓↓↓↓↓↓↓↓↓此位置加入最新修改的脚本↓↓↓↓↓↓↓↓↓↓↓↓↓↓


-- 2017-04-20 laiyinghe  DB:hr_saas
-- 新建用户的权限表、用户角色的权限表。
CREATE TABLE `t_privilege_user` (
`user_id`  int UNSIGNED NOT NULL COMMENT '用户ID' ,
`role_id`  int UNSIGNED NOT NULL COMMENT '角色ID' ,
`create_uid`  int UNSIGNED NOT NULL COMMENT '生成此权限的用户ID' ,
`module_name`  varchar(32) NULL DEFAULT '' COMMENT '模块名称PModule.pname' ,
`privilege`  varchar(64) NULL DEFAULT '' COMMENT '权限信息' ,
`update_time`  datetime NULL COMMENT '更新时间' ,
INDEX `privilege_userid_idx` (`user_id`) 
)
;

-- 2017-04-18 laiyinghe  DB:hr_saas
-- 新建角色的权限表。
CREATE TABLE `t_privilege_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(10) unsigned NOT NULL COMMENT '角色ID',
  `module_name` varchar(32) NOT NULL COMMENT '模块名称PModule.pname',
  `privilege` varchar(64) NOT NULL COMMENT '权限信息',
  `create_uid` int(10) unsigned NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `privilege_roleid_idx` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_base_role` (
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) NOT NULL COMMENT '角色描述',
  `create_uid` int(10) unsigned NOT NULL COMMENT '创建用户的id',
  `enabled` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 2017-04-18 laiyinghe  DB:hr_saas
-- 新建基础用户表。
CREATE TABLE `t_base_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '帐号',
  `password` varchar(32) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL COMMENT '姓名或昵称',
  `company_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属公司标识',
  `create_uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建者标识',
  `enabled` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `reg_time` datetime DEFAULT NULL COMMENT '注册时间',
  `login_time` datetime DEFAULT NULL COMMENT '最新登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_indx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

