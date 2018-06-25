CREATE SCHEMA IF NOT EXISTS `account`
  DEFAULT CHARACTER SET utf8;
USE `account`;

CREATE TABLE `tcrm_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `business_id` int(11) DEFAULT NULL COMMENT '商家id',
  `parent_id` int(11) DEFAULT NULL COMMENT '直接推荐人',
  `ancestor` text COMMENT '祖先',
  `vip_level` int(4) DEFAULT '1' COMMENT '会员等级',
  `buy_total_money` bigint(15) DEFAULT '0' COMMENT '累计购买金额',
  `withdrawal_credit_total` bigint(15) NOT NULL DEFAULT '0' COMMENT '累计可提现积分',
  `have_withdrawal_credit_total` bigint(15) NOT NULL DEFAULT '0' COMMENT '累计已提现积分',
  `acc_withdrawal_credit` bigint(15) NOT NULL DEFAULT '0' COMMENT '可提现积分',
  `shop_credit_total` bigint(15) NOT NULL DEFAULT '0' COMMENT '累计购物积分',
  `used_shop_credit_total` bigint(15) NOT NULL DEFAULT '0' COMMENT '累计已使用的购物积分',
  `acc_shop_credit` bigint(15) NOT NULL DEFAULT '0' COMMENT '可使用的购物积分',
  `wechat_nickname` varchar(255) DEFAULT NULL,
  `wechat_subscribe` int(11) DEFAULT NULL COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，只有openid和UnionID（在该公众号绑定到了微信开放平台账号时才有）。',
  `wechat_openid` varchar(40) DEFAULT NULL,
  `wechat_sex` int(11) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `wechat_city` varchar(40) DEFAULT NULL,
  `wechat_country` varchar(40) DEFAULT NULL,
  `wechat_province` varchar(40) DEFAULT NULL,
  `wechat_language` varchar(10) DEFAULT NULL,
  `wechat_headimgurl` varchar(512) DEFAULT '' COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
  `wechat_subscribe_time` timestamp NULL DEFAULT NULL,
  `wechat_unionid` varchar(40) DEFAULT NULL,
  `wechat_remark` varchar(200) DEFAULT NULL COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
  `wechat_groupid` varchar(40) DEFAULT NULL,
  `status` int(2) DEFAULT '1' COMMENT '1-可用  0-封停',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `pwd_salt` varchar(128) NOT NULL COMMENT '加密盐',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `ix_openid` (`wechat_openid`) USING BTREE,
  KEY `ix_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000006 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `tcrm_user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `receiving_user_name` varchar(64) NOT NULL COMMENT '收货人姓名',
  `mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `province_name` varchar(16) DEFAULT NULL COMMENT '省份名称',
  `city_name` varchar(16) DEFAULT NULL COMMENT '城市名字',
  `area` varchar(63) DEFAULT NULL COMMENT '收件人地区',
  `receiving_address` varchar(255) DEFAULT NULL COMMENT '收货人详细地址',
  `third_account` varchar(255) DEFAULT NULL COMMENT '第三方账户',
  `type` int(4) NOT NULL DEFAULT '1' COMMENT '地址类型(1:邮寄地址|2:爱奇艺账户)',
  `type_name` varchar(64) DEFAULT NULL COMMENT '地址类型名称',
  `is_default` int(2) NOT NULL DEFAULT '0' COMMENT '是否默认地址 1：是 0：否',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  KEY `user_type_index` (`user_id`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='收货人地址';

CREATE TABLE `tcrm_user_account_bind` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(64) DEFAULT '' COMMENT '用户姓名',
  `reserve_mobile` varchar(32) DEFAULT '' COMMENT '预留手机号码',
  `account_card` varchar(32) DEFAULT NULL COMMENT '账户卡号',
  `account_code` varchar(32) DEFAULT NULL COMMENT '账户编码',
  `account_name` varchar(50) NOT NULL DEFAULT '' COMMENT '账户名称',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `open_account` varchar(120) DEFAULT NULL COMMENT '开户账户（银行对应开户行）',
  `id_card` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `status` int(11) DEFAULT '0' COMMENT '状态(0=不可用，1=可用)',
  `account_type` int(6) DEFAULT NULL COMMENT '账户类型 1：微信 2：支付宝 3：银行卡',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_account` (`user_id`,`account_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='账户绑定表';

CREATE TABLE `tbiz_credit_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `type_name` varchar(50) DEFAULT NULL COMMENT '积分类型名称',
  `type_value` int(10) DEFAULT NULL COMMENT '积分类型(1=充值|2=提现|3=活动奖励|4=红包......)',
	`type_category` int(5) DEFAULT NULL COMMENT '类型分类(1=可提现积分|2=购物积分)',
	`balance_type` int(3) DEFAULT NULL COMMENT '收支类型(1=收入|2=支出)',
  `status` int(1) DEFAULT NULL COMMENT '1=正常|0=已删除',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
	`operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='积分类型表';

CREATE TABLE `tbiz_credit_detail` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(64) DEFAULT NULL COMMENT '用户id',
  `credit_type` int(10) DEFAULT NULL COMMENT '交易类型',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `deal_id` varchar(64) DEFAULT NULL COMMENT '交易号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `credit` bigint(15) DEFAULT NULL COMMENT '交易金额 *100',
  `send_status` int(4) DEFAULT '0' COMMENT '发放状态 0：未发放 1：已发放 2：发放失败',
  `create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  KEY `user_credit` (`user_id`,`credit_type`) USING BTREE,
  KEY `order` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='积分明细表';