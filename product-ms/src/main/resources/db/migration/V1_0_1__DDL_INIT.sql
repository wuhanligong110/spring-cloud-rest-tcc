CREATE SCHEMA IF NOT EXISTS `product`
  DEFAULT CHARACTER SET utf8;
USE `product`;

CREATE TABLE `tcim_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `business_id` int(11) NOT NULL COMMENT '商家id',
  `categoty_id` int(11) NOT NULL COMMENT '产品分类',
  `consume_type` int(11) DEFAULT '1' COMMENT '消费类型 1：积分购买 2：购物积分兑换',
  `product_name` varchar(20) DEFAULT NULL COMMENT '产品名称',
  `sub_name` varchar(63) DEFAULT NULL COMMENT '副标题',
  `pre_price` int(11) DEFAULT NULL COMMENT '原价',
  `price` bigint(15) DEFAULT '0' COMMENT '产品价格',
  `inventory` int(11) DEFAULT '0' COMMENT '产品总库存',
  `hava_inventory` int(11) DEFAULT '0' COMMENT '已消耗的库存',
  `list_img` varchar(50) DEFAULT NULL COMMENT '列表图片',
  `detail_img` varchar(50) DEFAULT NULL COMMENT '详情图片',
  `if_hot` int(1) DEFAULT '0' COMMENT '是否热门产品  1-是  0-否',
  `product_standard` text COMMENT '产品参数描述（参数之间用|分割，参数名称和参数值用#分割）',
  `sort` int(11) DEFAULT '99999' COMMENT '排序',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签(用#分割)',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品表';

CREATE TABLE `tcim_product_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `img` varchar(50) DEFAULT NULL COMMENT '图片',
  `img_type` int(11) DEFAULT NULL COMMENT '图片类型,1-简介图片',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `isshow` int(11) DEFAULT NULL COMMENT '是否显示,0-不显示，1-显示',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  KEY `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品图片表';

CREATE TABLE `tcim_product_categoty` (
  `categoty_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品分类id',
	`parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '产品父类id',
  `categoty_name` varchar(20) DEFAULT NULL COMMENT '产品分类名称',
  `categoty_img` varchar(50) DEFAULT NULL COMMENT '分类图片',
  `categoty_order` int(2) DEFAULT '0' COMMENT '分类排序',
  `if_show` int(1) DEFAULT '0' COMMENT '是否显示  0-不显示  1-显示',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`categoty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='产品分类表';

CREATE TABLE `tcim_business` (
  `business_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商家id',
  `business_name` varchar(20) DEFAULT NULL COMMENT '商家名称',
  `business_desc` varchar(63) DEFAULT NULL COMMENT '商家描述',
  `logo` varchar(50) DEFAULT NULL COMMENT '商家logo',
  `recommend` int(1) DEFAULT '0' COMMENT '是否推荐 0-否  1-是',
  `sort` int(11) DEFAULT '99999' COMMENT '排序',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签(用#分割)',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`business_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商家表';

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

CREATE TABLE `tsys_business_weixin_config` (
	`id` INT (11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
	`business_id` INT (11) DEFAULT NULL COMMENT '商家id',
	`app_id` VARCHAR (64) DEFAULT NULL COMMENT 'APPID',
	`app_secret` VARCHAR (64) DEFAULT NULL COMMENT 'APPSECRET',
	`mch_id` VARCHAR (64) DEFAULT NULL COMMENT '商户号',
	`pay_key` VARCHAR (64) DEFAULT NULL COMMENT '支付密钥',
	`key_path` VARCHAR (255) DEFAULT NULL COMMENT '密钥文件的存放路径',
	`notify_url` VARCHAR (255) DEFAULT NULL COMMENT '支付结果通用通知',
	`token` VARCHAR (255) DEFAULT NULL COMMENT '公众号接口配置信息中的 Token',
	`expireseconds` VARCHAR (255) DEFAULT NULL COMMENT '带参数临时二维码有效时间(秒)',
	`index_page_url` VARCHAR (255) DEFAULT NULL COMMENT '首页链接地址(微信授权)',
	`templet_type` VARCHAR (255) DEFAULT NULL COMMENT '微信模板消息id',
	`from_user_name` VARCHAR (255) DEFAULT NULL COMMENT '开发者微信号',
	`remark` VARCHAR (255) DEFAULT NULL COMMENT '备注',
	`create_time` datetime DEFAULT NULL COMMENT '用户创建时间',
	`update_time` datetime DEFAULT NULL COMMENT '用户更新时间',
	`operator` VARCHAR (30) DEFAULT NULL COMMENT '操作人',
	PRIMARY KEY (`id`),
	UNIQUE KEY `business` (`business_id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '商家微信配置';

