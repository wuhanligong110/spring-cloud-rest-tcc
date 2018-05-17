CREATE SCHEMA IF NOT EXISTS `order`
  DEFAULT CHARACTER SET utf8;
USE `order`;

CREATE TABLE `tbiz_order` (
  `order_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
	`address_id` int(11) NOT NULL COMMENT '收货地址id',
	`amount` bigint(15) DEFAULT NULL COMMENT '*100',
  `is_payed` int(11) DEFAULT '0' COMMENT '是否支付 0=未支付|1=已支付',
  `pay_id` int(1) DEFAULT NULL COMMENT '支付ID',
	`pay_type` int(11) DEFAULT '1' COMMENT '消费类型 1：积分购买 2：购物积分兑换',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`order_id`),
  KEY `ix_user_id` (`user_id`) USING BTREE,
  KEY `ix_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';

CREATE TABLE `tbiz_order_item` (
  `order_item_id` BIGINT(30) NOT NULL AUTO_INCREMENT COMMENT '订单明细id',
  `order_id` BIGINT(20) NOT NULL COMMENT '订单id',
  `product_id` int(11) DEFAULT NULL '产品id',
	`business_id` int(11) NOT NULL COMMENT '商家id',
  `product_count` int(11) DEFAULT NULL COMMENT '产品数量',
	`product_price` int(11) DEFAULT NULL '产品单价',
	`amount` bigint(15) DEFAULT NULL COMMENT '*100',
  `courier_company` varchar(255) DEFAULT NULL COMMENT '快递公司',
  `courier_number` varchar(64) DEFAULT NULL COMMENT '快递单号',
  `send_status` int(11) DEFAULT '0' COMMENT '发货状态 0=未发货 | 1=已发货 | 2=待退货 | 3=已退货',
	`pay_status` int(11) DEFAULT '0' COMMENT '支付状态 0=未支付 | 1=已支付 | 2=待退款 | 3=已退款',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`order_item_id`),
  KEY `ix_order_id` (`order_id`,`business_id`,`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单明细表';
