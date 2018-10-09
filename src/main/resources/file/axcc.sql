/*
Navicat MySQL Data Transfer

Source Server         : 47.105.71.191
Source Server Version : 50723
Source Host           : 47.105.71.191:3306
Source Database       : axcc

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-10-09 23:00:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `agent_share`
-- ----------------------------
DROP TABLE IF EXISTS `agent_share`;
CREATE TABLE `agent_share` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) NOT NULL COMMENT '会员ID号',
  `parent_id` int(10) NOT NULL COMMENT '父级ID号',
  `agent_money` float(10,2) NOT NULL COMMENT '代理员当月业绩',
  `create_date` date NOT NULL COMMENT '月份时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员业绩表\r\n\r\n1> 记录当月会员到代理员处缴纳排队购车的总费用，每处理一笔业务，增加相应的业绩。\r\n2> 代理员当月实际总业绩 = 会员缴费*5% + 二级代理业绩*3%';

-- ----------------------------
-- Records of agent_share
-- ----------------------------

-- ----------------------------
-- Table structure for `bussiness`
-- ----------------------------
DROP TABLE IF EXISTS `bussiness`;
CREATE TABLE `bussiness` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) NOT NULL COMMENT '普通会员ID号',
  `agent_id` int(10) DEFAULT NULL COMMENT '代理员ID号',
  `admin_id` int(10) DEFAULT NULL COMMENT '管理员ID号',
  `wait_num` int(10) DEFAULT NULL,
  `buy_money` float(10,2) DEFAULT NULL COMMENT '购车费用',
  `buy_type` int(2) NOT NULL COMMENT '购车类型（1：10万；2:20万；3:30万；5:50万）',
  `buy_status` int(2) DEFAULT NULL COMMENT '会员购车状态（0：未排队，1：排队中，2：已出车）',
  `check_status` int(2) DEFAULT NULL COMMENT '管理员审核状态（0：会员已提交申请，未审核；1：审核通过，会员到代理员处缴费；2：审核未通过）',
  `check_time` datetime DEFAULT NULL COMMENT '管理员审核时间',
  `pay_status` int(2) NOT NULL COMMENT '会员缴费态（0：已申请购车，未缴费；1：缴费成功；2：缴费失败）',
  `pay_tine` datetime DEFAULT NULL COMMENT '缴费时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理员业务表\r\n\r\n1> 没有40万的车型\r\n2>会员申请排队购车，管理员审核通过后，会员到代理员缴纳购车所需的费用，\r\n    代理员通过会员手机号查看会员提交的购车申请是否已被管理员审核通过，\r\n    若已通过，可办理业务收取会员的购车费用，并记录到本表中，\r\n    返回“缴费成功”状态，管理员进行排号。\r\n3>由于会员可以到任意代理员处支付费用（不需要指定某个具体的代理员），\r\n    所以代理员需要通过会员手机号查询该会员是否已提交了购车申请，管理员是否已审核通过，\r\n    然后办理业务，支付完成后向管理员反馈支付成功的状态，最后由管理员进行排号';

-- ----------------------------
-- Records of bussiness
-- ----------------------------

-- ----------------------------
-- Table structure for `history`
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID号',
  `user_id` int(10) NOT NULL COMMENT '会员/代理员ID号',
  `user_type` int(2) NOT NULL COMMENT '用户类别（1：代理员；2普通会员）',
  `buy_money` float(10,2) NOT NULL COMMENT '会员购车费用',
  `buy_type` int(2) NOT NULL COMMENT '会员购车类型（1：10万，2:20万，3:30万，5:50万）',
  `buy_status` int(2) NOT NULL COMMENT '会员购车状态（0：未出车；1：已出车，2：已取消）',
  `agent_ag_money` float(10,2) DEFAULT NULL COMMENT '代理员当月获取的二级代理业绩的提成',
  `agent_me_money` float(10,2) DEFAULT NULL COMMENT '代理员当月获取的会员现场签单业务提成',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史记录表\r\n\r\n1> 记录所有会员的购车记录，代理员每月业绩。';

-- ----------------------------
-- Records of history
-- ----------------------------

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID号',
  `title` varchar(50) NOT NULL COMMENT '信息标题',
  `contents` varchar(500) NOT NULL COMMENT '信息内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_delete` int(2) DEFAULT '0' COMMENT '删除标志，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息发布表';

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `money_apply`
-- ----------------------------
DROP TABLE IF EXISTS `money_apply`;
CREATE TABLE `money_apply` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID号',
  `user_id` int(10) NOT NULL COMMENT '会员ID号',
  `apply_money` float(10,2) NOT NULL COMMENT '申请提现的金额',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `user_status` int(2) NOT NULL COMMENT '申请人身份（1：代理员；2：普通会员）',
  `check_status` int(2) NOT NULL COMMENT '审核状态（0：未审核；1：审核通过；2：审核未通过）',
  `create_time` datetime DEFAULT NULL COMMENT '审核时间',
  `description` varchar(200) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现申请表\r\n\r\n1> 会员每周提现一次，代理员每月提现一次。\r\n2> 只能每周一申请，公司审核后发放，周二到账，提现费率为12%';

-- ----------------------------
-- Records of money_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(16) NOT NULL COMMENT '登录名（手机号）',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `real_name` varchar(50) DEFAULT NULL COMMENT '用户真实姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `sex` int(2) NOT NULL DEFAULT '1' COMMENT '性别（1：男，2：女）',
  `card` varchar(50) DEFAULT '未认证' COMMENT '身份证号',
  `image` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `bank_card` varchar(50) DEFAULT '未认证' COMMENT '银行卡号',
  `bank_addr` varchar(100) DEFAULT '未认证' COMMENT '开户行地址',
  `parent_id` varchar(16) DEFAULT NULL COMMENT '推荐人ID/上级代理ID',
  `original` varchar(200) DEFAULT NULL COMMENT '所有直接或间接相关的上级，逗号隔开（1,2,3,4...）',
  `user_role` int(2) NOT NULL DEFAULT '2' COMMENT '用户角色（0：管理员，1：代理员，2：普通会员）',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(2) DEFAULT '0' COMMENT '删除标志（0：未删除；1：已删除）',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `proxy_area` varchar(50) DEFAULT NULL COMMENT '代理区域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of users
-- ----------------------------

-- ----------------------------
-- Table structure for `voucher`
-- ----------------------------
DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(10) NOT NULL COMMENT '会员ID',
  `voucher_money` float(10,2) NOT NULL COMMENT '优惠券金额',
  `voucher_status` int(2) NOT NULL COMMENT '优惠券使用状态（0:未获得，1：未使用、未过期，2：未使用、已过期，3：已使用）',
  `voucher_time` datetime NOT NULL COMMENT '优惠券获取时间',
  `voucher_finish` datetime NOT NULL COMMENT '优惠券到期时间',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券表\r\n\r\n1> 优惠券：第一次排队购车付款后获得，付款金额*0.5，有效期1个月。\r\n     若在有效期内第二次购车车可以使用；若有效期内没有购车，未使用，则第二次购车时重新生成，\r\n     第三次购车时才可以使用。每个账号只生成一张购车优惠券。';

-- ----------------------------
-- Records of voucher
-- ----------------------------
