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
  `if_hot` int(1) DEFAULT '0' COMMENT '是否热门产品  0-是  1-否',
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
	`parent_id` int(11) NOT NULL COMMENT '产品父类id',
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

