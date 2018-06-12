CREATE SCHEMA IF NOT EXISTS `activity`
  DEFAULT CHARACTER SET utf8;
USE `activity`;

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