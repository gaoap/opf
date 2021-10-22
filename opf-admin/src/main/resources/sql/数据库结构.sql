CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级机构ID，一级机构为0',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='机构管理';

CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='字典表';

CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：未启用   1：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='资源管理：URL、权限等';

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `code` varchar(100) DEFAULT NULL COMMENT '角色代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色管理';

CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色机构';

CREATE TABLE `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色菜单';

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(40) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='用户管理';

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色';

CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`) VALUES ('1','蜀国','0','1','admin','2018-09-23 19:40:54',null,null,'0');
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`) VALUES ('2','吴国','0','2','admin','2018-09-23 19:41:04',null,null,'0');
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`) VALUES ('3','魏国','0','0','admin','2018-09-23 19:40:42',null,null,'0');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `remarks`, `del_flag`) VALUES ('3','male','男','sex','性别','0','admin','2018-09-23 19:52:54',null,null,'性别','0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `remarks`, `del_flag`) VALUES ('4','female','女','sex','性别','1','admin','2018-09-23 19:53:17',null,null,'性别','0');


INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('1','系统管理','0',null,null,'0','el-icon-setting','0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('2','用户管理','1','/sys/user',null,'1','el-icon-service','1',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('3','机构管理','1','/sys/dept',null,'1','el-icon-news','2',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('4','角色管理','1','/sys/role',null,'1','el-icon-view','4',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('5','菜单管理','1','/sys/menu',null,'1','el-icon-menu','5',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('6','数据监控','43','http://139.196.87.48:8001/druid/login.html',null,'1','el-icon-warning','0',null,null,'admin','2018-12-27 11:03:45','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('7','字典管理','1','/sys/dict',null,'1','el-icon-edit-outline','7',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('8','系统日志','1','/sys/log','sys:log:view','1','el-icon-info','8',null,null,'admin','2018-09-23 19:32:28','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('9','查看','2',null,'sys:user:view','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('10','新增','2',null,'sys:user:add','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('11','修改','2',null,'sys:user:edit','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('12','删除','2',null,'sys:user:delete','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('13','查看','3',null,'sys:dept:view','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('14','新增','3',null,'sys:dept:add','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('15','修改','3',null,'sys:dept:edit','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('16','删除','3',null,'sys:dept:delete','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('17','查看','4',null,'sys:role:view','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('18','新增','4',null,'sys:role:add','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('19','修改','4',null,'sys:role:edit','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('20','删除','4',null,'sys:role:delete','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('21','查看','5',null,'sys:menu:view','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('22','新增','5',null,'sys:menu:add','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('23','修改','5',null,'sys:menu:edit','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('24','删除','5',null,'sys:menu:delete','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('28','使用案例','0',null,null,'0','el-icon-picture-outline','6',null,null,'admin','2018-11-15 14:39:43','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('29','国际化','28','/demo/i18n',null,'1','el-icon-edit','1',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('30','换皮肤','28','/demo/theme',null,'1','el-icon-picture','2',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('31','查看','7',null,'sys:dict:view','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('32','新增','7',null,'sys:dict:add','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('33','修改','7',null,'sys:dict:edit','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('34','删除','7',null,'sys:dict:delete','2',null,'0',null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('35','接口文档','0','http://localhost:8001/swagger-ui.html',null,'1','el-icon-document','3',null,null,'admin','2018-12-27 11:04:18','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('38','服务监控','43','http://localhost:8000/','','1','el-icon-view','1','admin','2018-11-02 20:02:15','admin','2018-12-27 11:03:53','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('41','注册中心','44','http://localhost:8500','','1',' el-icon-view','0','admin','2018-11-03 11:06:48','admin','2018-12-27 11:08:11','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('42','代码生成','0','/generator/generator','','1','el-icon-star-on','5','admin','2018-11-15 14:39:30','admin','2018-11-15 14:56:18','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('43','系统监控','0','','','0','el-icon-info','1','admin','2018-12-27 10:57:29','admin','2018-12-27 11:02:26','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('44','服务治理','0','','','0','el-icon-service','2','admin','2018-12-27 11:05:48','admin','2018-12-27 11:06:39','0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('45','配置生效页面测试','0','/opf-admin/config/get',null,'1',null,null,null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('46','配置生效页面测试2',null,'/opf-admin/nacos/test/getValue/',null,'1',null,null,null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('47','获取所有用户',null,'/opf-admin/admin/sysUser/userAll/',null,'1',null,null,null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('48','根据ID查询用户',null,'/opf-admin/admin/sysUser/*',null,'1',null,null,null,null,null,null,'0',null);
INSERT INTO `sys_resource` (`id`, `name`, `parent_id`, `url`, `perms`, `type`, `icon`, `order_num`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `status`) VALUES ('49','检查用户token',null,'/opf-oauth/checkToken/',null,'1',null,null,null,null,null,null,'0',null);

INSERT INTO `sys_role` (`id`, `name`, `remark`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `code`) VALUES ('1','admin','超级管理员','admin','2018-08-14 11:11:11','admin','2018-09-23 19:07:18','0',null);
INSERT INTO `sys_role` (`id`, `name`, `remark`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `code`) VALUES ('2','dev','开发人员','admin','2018-08-14 11:11:11','admin','2018-08-14 11:11:11','0',null);
INSERT INTO `sys_role` (`id`, `name`, `remark`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `code`) VALUES ('3','test','测试人员','admin','2018-08-14 11:11:11','admin','2018-08-14 11:11:11','0',null);
INSERT INTO `sys_role` (`id`, `name`, `remark`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`, `code`) VALUES ('8','mng','部门经理','admin','2018-09-23 19:09:52',null,null,'0',null);


INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`) VALUES ('1','1','45',null,null,null,null);
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`) VALUES ('2','1','46',null,null,null,null);
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`) VALUES ('3','1','47',null,null,null,null);
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`) VALUES ('4','1','48',null,null,null,null);
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`) VALUES ('5','1','49',null,null,null,null);

INSERT INTO `sys_user` (`id`, `name`, `password`, `salt`, `email`, `mobile`, `status`, `dept_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`) VALUES ('1','admin','$2a$10$jzN.Xal1s7ftP.jL7VzE/eL0FjOWpkvZ/SQNMCWkmb50ZI.WTGIZq','','','','1','2','admin','2018-08-14 11:11:11','admin','2018-08-14 11:11:11','0');
INSERT INTO `sys_user` (`id`, `name`, `password`, `salt`, `email`, `mobile`, `status`, `dept_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`, `del_flag`) VALUES ('2','liubei','$2a$10$lTSRtjoitADb.Hy9oVHP7e2Ufg.ph.vZHRgI/JnGuzatOzob7Dc/y',null,null,null,'1','1','admin',null,'admin',null,'0');

INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_by`, `create_time`, `last_update_by`, `last_update_time`) VALUES ('1','1','1',null,null,null,null);


