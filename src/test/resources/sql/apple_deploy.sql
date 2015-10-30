
DROP TABLE IF EXISTS `dep_project`;
CREATE TABLE `dep_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT 'master' COMMENT '项目名字',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型 1=java项目 =2脚本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0=无效 1=有效 2=删除',
  `version` varchar(32) DEFAULT NULL COMMENT '线上当前版本，用于快速回滚',
  `nexus_url` varchar(200) DEFAULT '' COMMENT '私服包存放地址',
  `nexus_group` varchar(200) DEFAULT '' COMMENT '私服包分组',
  `nexus_artifact` varchar(200) DEFAULT '' COMMENT '私服包识别号',
  `release_user` varchar(50) NOT NULL COMMENT '目标机器用户',
  `release_to` varchar(200) NOT NULL COMMENT '目标机器的目录，相当于nginx的root，可直接web访问',
  `hosts` text COMMENT '目标机器列表',
  `pre_deploy` text COMMENT '部署前置任务',
  `post_deploy` text COMMENT '同步之前任务',
  `pre_release` text COMMENT '同步之前目标机器执行的任务',
  `post_release` text COMMENT '同步之后目标机器执行的任务',
  `is_audit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否需要审核任务0不需要，1需要',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



INSERT INTO `dep_project` VALUES (1,'biz-user',1,1,'2.0.0','http://114.119.6.193:8081/nexus','com.jiuzhi.biz','biz-user-provider','root','/work/www/biz-user','192.168.1.177,192.168.1.189','','/work/shell/deploy/deploy_post.sh',NULL,NULL,0,'2015-10-29 13:56:09','2015-10-30 16:39:58',NULL,NULL),(2,'biz-message',1,1,'2.0.0','http://114.119.6.193:8081/nexus','com.jiuzhi.biz','biz-message-provider','root','/work/www/biz-message','192.168.1.177,192.168.1.189','','/work/shell/deploy/deploy_post.sh',NULL,NULL,0,'2015-10-29 15:44:45','2015-10-30 16:40:06',NULL,NULL);


DROP TABLE IF EXISTS `dep_record`;
CREATE TABLE `dep_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL DEFAULT '0' COMMENT '任务id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态1：成功，0失败',
  `action` int(11) unsigned DEFAULT '10' COMMENT '任务执行到的阶段',
  `command` text COMMENT '运行命令',
  `duration` int(11) DEFAULT '0' COMMENT '耗时，单位ms',
  `memo` text COMMENT '日志/备注',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


INSERT INTO `dep_record` VALUES (1,1,1,10,NULL,0,NULL,'2015-10-29 18:12:35');


DROP TABLE IF EXISTS `dep_task`;
CREATE TABLE `dep_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL DEFAULT '0' COMMENT '项目id',
  `project_name` varchar(100) NOT NULL DEFAULT '0' COMMENT '项目名称',
  `title` varchar(100) DEFAULT '' COMMENT '任务标题',
  `env` int(11) NOT NULL DEFAULT '1' COMMENT '发布环境 1:开发 2：测试，3：预发布，4：线上',
  `action` int(11) NOT NULL DEFAULT '1' COMMENT '1全新上线，2回滚',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态0：新建提交，1审核通过，2审核拒绝，3上线完成，4上线失败',
  `version` varchar(20) DEFAULT '' COMMENT '上线的版本号',
  `ex_version` varchar(20) DEFAULT '' COMMENT '上一次上线的版本号',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


INSERT INTO `dep_task` VALUES (1,1,'0','user-v2.0.0正式发布',0,1,0,'2.0.0','1.0.0','2015-10-29 18:12:39','2015-10-29 18:13:27',NULL,NULL);
