CREATE SCHEMA IF NOT EXISTS `activity`
  DEFAULT CHARACTER SET utf8;
USE `activity`;

CREATE TABLE `wx_access_token` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `access_token` varchar(600) NOT NULL COMMENT 'access_token凭证',
  `expires_in` bigint(20) DEFAULT NULL COMMENT '凭证有效时间，单位：秒',
  `crt_time` bigint(20) DEFAULT NULL COMMENT '创建时间距离当前时间毫秒数',
  `userid` varchar(128) DEFAULT NULL COMMENT 'userid',
  `create_date` varchar(25) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

CREATE TABLE `tsys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
	`business_id` int(11) DEFAULT NULL COMMENT '商家id',
  `config_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `config_type` varchar(50) DEFAULT NULL COMMENT '类别',
  `config_key` varchar(64) DEFAULT NULL COMMENT '键',
  `config_value` varchar(255) DEFAULT NULL COMMENT '值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_key` (`config_type`,`config_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='系统配置';

CREATE TABLE `tsm_advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
	`business_id` int(11) NOT NULL COMMENT '商家id',
  `page_index` varchar(50) NOT NULL COMMENT '显示位置',
  `img_url` varchar(200) DEFAULT NULL COMMENT '广告图片链接',
  `link_url` varchar(200) DEFAULT NULL COMMENT '跳转链接url',
  `show_index` int(11) DEFAULT NULL COMMENT '显示排序',
  `valid_begin_date` datetime DEFAULT NULL COMMENT '上架时间',
  `valid_end_date` datetime DEFAULT NULL COMMENT '下架时间',
  `share_title` varchar(32) DEFAULT NULL COMMENT '分享标题',
  `share_desc` varchar(128) DEFAULT NULL COMMENT '分享描述',
  `share_icon` varchar(128) DEFAULT NULL COMMENT '分享图标',
  `share_link` varchar(128) DEFAULT NULL COMMENT '分享链接',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='广告表';