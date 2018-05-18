CREATE SCHEMA IF NOT EXISTS `activity`
  DEFAULT CHARACTER SET utf8;
USE `activity`;

CREATE TABLE `tact_activity_lottery` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
	`activity_code` varchar(50) DEFAULT NULL COMMENT '活动编码',
  `prize_id` int(11) DEFAULT '0' COMMENT '奖品ID',
  `prize_desc` varchar(64) NOT NULL DEFAULT '' COMMENT '奖品描述',
  `variable` int(11) DEFAULT '0' COMMENT '权重',
  `lottery_type` int(6) NOT NULL COMMENT ' 奖励类型 1、现金红包 2、投资奖励红包3、其他4、职级体验券5、奖励金6、个人加拥券',
	`address_type` int(6) DEFAULT '0' COMMENT '收件地址类型 0：不需要收件地址  1：邮寄地址   2：爱奇艺账户',
  `amount` decimal(32,4) DEFAULT '0.0000' COMMENT '金额',
  `status` int(4) DEFAULT '1' COMMENT '状态(0=无效，1=有效 )',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `activity_prize` (`activity_code`,`prize_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动奖项表';