/*
Navicat MySQL Data Transfer

Source Server         : 47.105.71.191
Source Server Version : 50723
Source Host           : 47.105.71.191:3306
Source Database       : axcc

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-01-05 21:53:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `agent_share`
-- ----------------------------
DROP TABLE IF EXISTS `agent_share`;
CREATE TABLE `agent_share` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `agent_id` int(10) NOT NULL COMMENT '代理员ID号',
  `user_id` int(10) NOT NULL COMMENT '会员ID号',
  `business_id` int(10) NOT NULL COMMENT '业务Id',
  `agent_money` float(10,2) NOT NULL COMMENT '代理员业绩',
  `create_date` datetime NOT NULL COMMENT '月份时间',
  `apply_status` int(10) NOT NULL DEFAULT '0' COMMENT '提现状态(0:未提现 1:已提现)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='代理员业绩表\r\n\r\n1> 记录当月会员到代理员处缴纳排队购车的业绩，每处理一笔业务，增加相应的业绩。\r\n2> 代理员当月实际总业绩 = 会员缴费*5%';

-- ----------------------------
-- Records of agent_share
-- ----------------------------
INSERT INTO `agent_share` VALUES ('19', '30', '29', '21', '100000.00', '2018-10-14 00:00:00', '0');
INSERT INTO `agent_share` VALUES ('20', '34', '29', '22', '2000.00', '2018-10-15 00:00:00', '0');
INSERT INTO `agent_share` VALUES ('21', '37', '31', '23', '12000.00', '2018-10-15 00:00:00', '0');
INSERT INTO `agent_share` VALUES ('22', '37', '31', '23', '12000.00', '2018-10-15 00:00:00', '0');
INSERT INTO `agent_share` VALUES ('24', '36', '31', '26', '8000.00', '2018-10-16 00:00:00', '0');
INSERT INTO `agent_share` VALUES ('25', '39', '38', '27', '100000.00', '2018-10-16 00:00:00', '0');
INSERT INTO `agent_share` VALUES ('28', '38', '40', '30', '2012.00', '2018-10-01 00:00:00', '1');
INSERT INTO `agent_share` VALUES ('31', '38', '47', '34', '326.00', '2018-10-17 00:00:00', '1');
INSERT INTO `agent_share` VALUES ('35', '38', '50', '42', '11111.00', '2018-10-20 00:03:12', '1');
INSERT INTO `agent_share` VALUES ('36', '63', '59', '44', '300000.00', '2019-01-05 16:37:40', '0');
INSERT INTO `agent_share` VALUES ('37', '63', '64', '45', '60000.00', '2019-01-05 16:59:45', '0');

-- ----------------------------
-- Table structure for `business`
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) NOT NULL COMMENT '普通会员ID号',
  `agent_id` int(10) DEFAULT NULL COMMENT '代理员ID号',
  `admin_id` int(10) DEFAULT NULL COMMENT '管理员ID号',
  `wait_num` int(10) DEFAULT NULL COMMENT '排位',
  `buy_money` float(10,2) DEFAULT NULL COMMENT '购车费用',
  `buy_type` int(2) NOT NULL COMMENT '购车类型（1：10万；2:20万；3:30万；5:50万）',
  `buy_status` int(2) DEFAULT '0' COMMENT '会员购车状态（0：未排队，1：排队中，2：已出车）',
  `check_status` int(2) DEFAULT NULL COMMENT '管理员审核状态（0：会员已提交申请，未审核；1：审核通过，未缴费；2：审核通过，已缴费；3：审核通过，缴费失败；4：审核未通过）',
  `check_time` datetime DEFAULT NULL COMMENT '管理员审核时间',
  `pay_time` datetime DEFAULT NULL COMMENT '缴费时间',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `is_delete` int(1) DEFAULT '0' COMMENT '是否删除：0 不删除，1 删除',
  `money_apply_status` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='代理员业务表\r\n\r\n1> 没有40万的车型\r\n2>会员申请排队购车，管理员审核通过后，会员到代理员缴纳购车所需的费用，\r\n    代理员通过会员手机号查看会员提交的购车申请是否已被管理员审核通过，\r\n    若已通过，可办理业务收取会员的购车费用，并记录到本表中，\r\n    返回“缴费成功”状态，管理员进行排号。\r\n3>由于会员可以到任意代理员处支付费用（不需要指定某个具体的代理员），\r\n    所以代理员需要通过会员手机号查询该会员是否已提交了购车申请，管理员是否已审核通过，\r\n    然后办理业务，支付完成后向管理员反馈支付成功的状态，最后由管理员进行排号';

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES ('21', '30', '29', null, '1', '100000.00', '1', '1', '2', '2018-11-14 23:00:53', '2018-11-14 23:12:39', '2018-11-14 22:53:02', '0', null);
INSERT INTO `business` VALUES ('22', '34', '29', null, '2', '2000.00', '2', '0', '2', '2018-11-15 00:36:32', '2018-11-15 00:37:55', '2018-11-15 00:25:31', '0', null);
INSERT INTO `business` VALUES ('23', '37', '31', null, null, '12000.00', '1', '0', '2', '2018-11-15 22:04:03', '2018-11-15 22:05:46', '2018-11-15 20:52:59', '0', null);
INSERT INTO `business` VALUES ('26', '36', '31', null, null, '8000.00', '1', '0', '2', '2018-11-16 17:34:14', '2018-11-16 17:36:35', '2018-11-16 17:32:55', '0', null);
INSERT INTO `business` VALUES ('27', '39', '38', null, null, '100000.00', '5', '0', '2', '2018-11-16 22:08:23', '2018-11-16 22:24:31', '2018-11-16 22:02:45', '0', null);
INSERT INTO `business` VALUES ('30', '40', '38', null, null, '2012.00', '1', '0', '2', '2018-11-16 23:46:20', '2018-11-16 23:46:28', '2018-11-16 23:46:12', '0', null);
INSERT INTO `business` VALUES ('34', '47', '38', null, '95', '326.00', '4', '1', '2', '2018-11-17 14:52:49', '2018-11-17 14:53:07', '2018-11-17 14:52:44', '1', null);
INSERT INTO `business` VALUES ('42', '50', '38', null, '95', '11111.00', '2', '1', '2', '2018-11-20 00:02:59', '2018-11-20 00:03:12', '2018-11-20 00:02:48', '0', null);
INSERT INTO `business` VALUES ('43', '60', null, null, null, null, '4', '2', '1', '2019-01-05 16:23:16', null, '2019-01-05 13:53:08', '0', null);
INSERT INTO `business` VALUES ('44', '59', '63', null, null, '300000.00', '3', '0', '2', '2019-01-05 16:23:19', '2019-01-05 16:37:40', '2019-01-05 13:57:05', '0', null);
INSERT INTO `business` VALUES ('45', '64', '63', null, '991', '60000.00', '2', '1', '2', '2019-01-05 16:55:03', '2019-01-05 16:59:45', '2019-01-05 15:18:04', '0', null);
INSERT INTO `business` VALUES ('46', '65', null, null, null, null, '1', '0', '0', null, null, '2019-01-05 16:12:01', '0', null);
INSERT INTO `business` VALUES ('47', '70', null, null, null, null, '3', '0', '0', null, null, '2019-01-05 17:06:20', '0', null);
INSERT INTO `business` VALUES ('48', '71', null, null, null, null, '2', '0', '0', null, null, '2019-01-05 17:16:15', '0', null);
INSERT INTO `business` VALUES ('49', '76', null, null, null, null, '0', '0', '0', null, null, '2019-01-05 18:16:57', '0', null);
INSERT INTO `business` VALUES ('50', '79', null, null, null, null, '3', '0', '0', null, null, '2019-01-05 18:30:32', '0', null);
INSERT INTO `business` VALUES ('51', '84', null, null, null, null, '3', '0', '0', null, null, '2019-01-05 20:03:20', '0', null);

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
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(2) DEFAULT '0' COMMENT '删除标志，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='信息发布表';

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('5', '测试发布', '测试发布', '2018-11-17 15:18:11', null, '0');

-- ----------------------------
-- Table structure for `mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `mobile_code`;
CREATE TABLE `mobile_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(16) DEFAULT NULL COMMENT '注册的手机号',
  `short_code` int(6) DEFAULT NULL COMMENT '短信验证码',
  `create_time` datetime DEFAULT NULL COMMENT '短信验证码发送的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='短信验证码信息表';

-- ----------------------------
-- Records of mobile_code
-- ----------------------------
INSERT INTO `mobile_code` VALUES ('1', '15603321262', '614943', '2019-01-05 12:26:00');
INSERT INTO `mobile_code` VALUES ('2', '15603321262', '656305', '2019-01-05 12:29:16');
INSERT INTO `mobile_code` VALUES ('3', '15603321262', '717539', '2019-01-05 12:34:24');
INSERT INTO `mobile_code` VALUES ('4', '15603321262', '752101', '2019-01-05 12:41:23');
INSERT INTO `mobile_code` VALUES ('5', '15603321262', '426995', '2019-01-05 12:53:08');
INSERT INTO `mobile_code` VALUES ('6', '18800117074', '846860', '2019-01-05 12:59:51');
INSERT INTO `mobile_code` VALUES ('7', '18800117074', '587826', '2019-01-05 13:05:10');
INSERT INTO `mobile_code` VALUES ('8', '13804825156', '748584', '2019-01-05 13:46:49');
INSERT INTO `mobile_code` VALUES ('9', '13804025156', '109262', '2019-01-05 13:47:53');
INSERT INTO `mobile_code` VALUES ('10', '18800117074', '615819', '2019-01-05 13:48:25');
INSERT INTO `mobile_code` VALUES ('11', '15611827983', '788880', '2019-01-05 13:49:42');
INSERT INTO `mobile_code` VALUES ('12', '13671150807', '516303', '2019-01-05 15:12:02');
INSERT INTO `mobile_code` VALUES ('13', '13671150807', '654179', '2019-01-05 15:13:15');
INSERT INTO `mobile_code` VALUES ('14', '15810335440', '359476', '2019-01-05 16:06:57');
INSERT INTO `mobile_code` VALUES ('15', '15911047469', '136063', '2019-01-05 16:14:12');
INSERT INTO `mobile_code` VALUES ('16', '13269309584', '183511', '2019-01-05 16:14:45');
INSERT INTO `mobile_code` VALUES ('17', '15931855599', '866987', '2019-01-05 16:34:11');
INSERT INTO `mobile_code` VALUES ('18', '13120086808', '238844', '2019-01-05 16:39:53');
INSERT INTO `mobile_code` VALUES ('19', '13522338046', '604054', '2019-01-05 16:58:27');
INSERT INTO `mobile_code` VALUES ('20', '18618149908', '446518', '2019-01-05 17:02:25');
INSERT INTO `mobile_code` VALUES ('21', '13522338046', '791007', '2019-01-05 17:07:35');
INSERT INTO `mobile_code` VALUES ('22', '13701255631', '330129', '2019-01-05 17:10:55');
INSERT INTO `mobile_code` VALUES ('23', '13522224478', '420254', '2019-01-05 17:16:31');
INSERT INTO `mobile_code` VALUES ('24', '13520807348', '621452', '2019-01-05 17:18:50');
INSERT INTO `mobile_code` VALUES ('25', '15512913283', '754205', '2019-01-05 17:28:02');
INSERT INTO `mobile_code` VALUES ('26', '18701336637', '550470', '2019-01-05 17:28:28');
INSERT INTO `mobile_code` VALUES ('27', '15512913283', '862595', '2019-01-05 17:34:38');
INSERT INTO `mobile_code` VALUES ('28', '15910252787', '811898', '2019-01-05 17:44:24');
INSERT INTO `mobile_code` VALUES ('29', '15910252787', '366668', '2019-01-05 17:48:46');
INSERT INTO `mobile_code` VALUES ('30', '15524454469', '326004', '2019-01-05 18:02:32');
INSERT INTO `mobile_code` VALUES ('31', '18940795999', '433194', '2019-01-05 18:11:26');
INSERT INTO `mobile_code` VALUES ('32', '18940795999', '352728', '2019-01-05 18:14:24');
INSERT INTO `mobile_code` VALUES ('33', '18940795999', '526821', '2019-01-05 18:16:24');
INSERT INTO `mobile_code` VALUES ('34', '18510089561', '525339', '2019-01-05 18:19:14');
INSERT INTO `mobile_code` VALUES ('35', '18941755655', '272770', '2019-01-05 18:19:20');
INSERT INTO `mobile_code` VALUES ('36', '18941755655', '203436', '2019-01-05 18:22:56');
INSERT INTO `mobile_code` VALUES ('37', '13180574326', '205005', '2019-01-05 19:39:57');
INSERT INTO `mobile_code` VALUES ('38', '13180574326', '902283', '2019-01-05 19:46:39');
INSERT INTO `mobile_code` VALUES ('39', '13180574326', '800844', '2019-01-05 19:48:51');
INSERT INTO `mobile_code` VALUES ('40', '15612338936', '311670', '2019-01-05 19:49:39');
INSERT INTO `mobile_code` VALUES ('41', '13311299205', '338950', '2019-01-05 19:55:18');
INSERT INTO `mobile_code` VALUES ('42', '18518127590', '303873', '2019-01-05 20:06:27');
INSERT INTO `mobile_code` VALUES ('43', '18518127590', '468143', '2019-01-05 21:15:33');

-- ----------------------------
-- Table structure for `money_apply`
-- ----------------------------
DROP TABLE IF EXISTS `money_apply`;
CREATE TABLE `money_apply` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID号',
  `user_id` int(10) NOT NULL COMMENT '申请人ID号',
  `apply_money` float(10,2) NOT NULL COMMENT '申请提现的金额',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `user_status` int(2) NOT NULL COMMENT '申请人身份（1：代理员；2：普通会员）',
  `check_status` int(2) NOT NULL COMMENT '审核状态（0：未审核；1：审核通过；2：审核未通过）',
  `create_time` datetime DEFAULT NULL COMMENT '审核时间',
  `description` varchar(200) DEFAULT NULL COMMENT '描述说明',
  `level1_count` int(2) DEFAULT '0' COMMENT '0代表直推人数没有超过20人，1代表超过20人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='提现申请表\r\n\r\n1> 会员每周提现一次，代理员每月提现一次。\r\n2> 只能每周一申请，公司审核后发放，周二到账，提现费率为12%';

-- ----------------------------
-- Records of money_apply
-- ----------------------------
INSERT INTO `money_apply` VALUES ('27', '34', '5000.00', '2018-11-18 00:12:37', '2', '1', '2018-11-18 22:47:11', null, null);
INSERT INTO `money_apply` VALUES ('33', '38', '116.90', '2018-11-19 22:48:40', '1', '1', '2018-11-19 23:18:51', null, '0');
INSERT INTO `money_apply` VALUES ('39', '38', '555.55', '2018-11-20 00:36:43', '1', '1', '2018-11-20 00:37:19', null, '0');
INSERT INTO `money_apply` VALUES ('40', '50', '0.00', '2018-11-20 00:39:26', '2', '0', null, null, null);

-- ----------------------------
-- Table structure for `money_apply_detail`
-- ----------------------------
DROP TABLE IF EXISTS `money_apply_detail`;
CREATE TABLE `money_apply_detail` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID号',
  `user_id` int(10) NOT NULL COMMENT '会员ID号',
  `apply_money` float(10,2) NOT NULL COMMENT '申请提现的金额',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `apply_status` int(2) NOT NULL DEFAULT '0' COMMENT '提现状态（0：未提现，1：提现中, 2:已提现）',
  `create_time` datetime DEFAULT NULL COMMENT '审核时间',
  `description` varchar(200) DEFAULT NULL COMMENT '描述说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of money_apply_detail
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
  `bank_addr` varchar(100) NOT NULL DEFAULT '未认证' COMMENT '开户行地址',
  `parent_id` varchar(16) DEFAULT NULL COMMENT '推荐人手机号',
  `original` varchar(200) DEFAULT NULL COMMENT '会员所有直接或间接相关的上级推荐人ID号，逗号隔开（1,2,3,4...）',
  `user_role` int(2) NOT NULL DEFAULT '2' COMMENT '用户角色（0：管理员，1：代理员，2：普通会员）',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(2) DEFAULT '0' COMMENT '删除标志（0：未删除；1：已删除）',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `proxy_area` varchar(50) DEFAULT NULL COMMENT '代理区域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '18800110011', '管理员', '管理员', '123456', '1', '511025198108245574', '/opt/core/photo/18800110011/20190105133256424.jpg', '6222023602058313081', '昌平区', null, '2', '0', '2018-11-14 00:53:51', null, '0', null, null);
INSERT INTO `users` VALUES ('2', '18800110022', '管理员2', '管理员2', '123456', '1', '未认证', '/opt/core/photo/18800110022/20181120010206058.png', '未认证', '未认证', null, null, '0', '2018-11-14 00:55:17', null, '0', null, null);
INSERT INTO `users` VALUES ('3', '18800110033', '张三', '张三', '123456', '1', '未认证', null, '未认证', '未认证', '18800110022', '3', '2', '2018-11-14 00:56:35', null, '0', null, null);
INSERT INTO `users` VALUES ('27', '15603311111', 'xin1', null, '123', '1', '未认证', '/opt/core/photo/15603311111/20181114061051804.jpg', '未认证', '未认证', '15603311111', null, '0', '2018-10-21 17:10:33', '2018-10-21 17:10:33', '0', null, null);
INSERT INTO `users` VALUES ('28', '15603311112', '代理员1', null, '123456', '1', '未认证', null, '未认证', '未认证', null, null, '1', '2018-11-14 21:10:34', '2018-11-14 21:10:34', '1', null, '15603311112');
INSERT INTO `users` VALUES ('29', '15603311113', '代理员2', null, '123456', '1', '未认证', '/opt/core/photo/15603311113/20181118231118933.jpg', '未认证', '未认证', null, null, '1', '2018-11-14 21:13:15', '2018-11-14 21:13:15', '0', null, '北京');
INSERT INTO `users` VALUES ('30', '15603311115', '会员1', '张中行', '123', '1', '1802938173949291842', null, '1345683260432467', '北京银行', '15603311111', '3-30', '2', '2018-11-14 21:18:51', '2018-11-14 23:48:59', '0', null, null);
INSERT INTO `users` VALUES ('31', '15603311119', '苏苏苏', null, '123456', '1', '未认证', null, '未认证', '未认证', null, null, '1', '2018-11-14 22:49:55', '2018-11-14 22:49:55', '0', null, '天通苑');
INSERT INTO `users` VALUES ('32', '15603311116', '会员2', null, '123', '1', '未认证', null, '未认证', '未认证', '15603311115', '3-30-32', '2', '2018-11-14 23:26:26', '2018-11-14 23:26:26', '0', null, null);
INSERT INTO `users` VALUES ('33', '13766669999', '会员3', '火狐', '123456', '1', '13062699509092451', 'E:/photo/13766669999/20181115191215132.png', '1222222222222222', '北京', '15603311115', '3-33', '2', '2018-11-14 23:31:31', '2018-11-14 23:56:56', '0', null, null);
INSERT INTO `users` VALUES ('34', '15603311117', '会员4', '杨磊', '1234', '2', '1802938173949291842', '/opt/core/photo/15603311117/20181116003657910.jpg', '6217000010074440', '上海银行', '15603311115', '3-30-34', '2', '2018-11-14 23:38:49', '2018-11-14 23:54:32', '0', null, null);
INSERT INTO `users` VALUES ('35', '13804025156', '佟大大', null, '123456', '1', '未认证', null, '未认证', '未认证', null, null, '1', '2018-11-14 23:51:20', '2018-11-14 23:51:20', '0', null, '辽宁');
INSERT INTO `users` VALUES ('36', '13804025157', '佟二二', '佟士儒', '123456', '1', '210422199908091236', null, '6225555555552654', '北京分行', null, '3-36', '2', '2018-11-14 23:54:09', '2018-11-14 23:54:09', '0', null, '吉林');
INSERT INTO `users` VALUES ('37', '17712345678', '测试1', '时小庚', '123456', '1', '141122197401205195', '/opt/core/photo/17712345678/20181117152607744.png', '6222023602058313081', '天通苑', '13766669999', '3-37', '2', '2018-11-15 19:21:07', '2018-11-15 19:21:07', '0', null, null);
INSERT INTO `users` VALUES ('38', '15603311118', '代理员3', '步步出', '123456', '1', '13062619900212491X', null, '6228480018768265375', '北京', null, null, '1', '2018-11-16 21:51:38', '2018-11-16 21:51:38', '0', null, '昌平区');
INSERT INTO `users` VALUES ('39', '15603311119', '会员9', '刘姓周', '123', '1', '130525199303271938', null, '6228480018731582179', '杭州银行', '15603311117', '3-30-34-39', '2', '2018-11-16 21:58:03', '2018-11-16 21:58:03', '0', null, null);
INSERT INTO `users` VALUES ('40', '13804025158', '佟58', '佟58', '123456', '1', '210422198707050614', null, '6225881247018707', 'sdfsdf', '13804025157', '3-36-40', '2', '2018-11-16 22:57:16', '2018-11-16 22:57:16', '0', null, null);
INSERT INTO `users` VALUES ('46', '13804025159', '佟59', null, '123456', '1', '未认证', null, '未认证', '未认证', '13804025157', '3-36-46', '2', '2018-11-17 14:24:49', '2018-11-17 14:24:49', '0', null, null);
INSERT INTO `users` VALUES ('47', '13804025160', '佟60', '佟60', '123456', '1', '210422198707050614', null, '6225881247018707', '北京', '13804025158', '3-36-40-47', '2', '2018-11-17 14:38:13', '2018-11-17 14:38:13', '1', null, null);
INSERT INTO `users` VALUES ('50', '13804025161', '佟61', '佟61', '123456', '1', '210422198707050614', null, '6225881247018707', '123', '13804025160', '3-36-40-47-50', '2', '2018-11-17 15:47:18', '2018-11-17 15:47:18', '0', null, null);
INSERT INTO `users` VALUES ('52', '15603311110', '会员10', null, '123', '1', '未认证', null, '未认证', '未认证', '15603311115', '3-30-52', '2', '2018-11-17 22:49:47', '2018-11-17 22:49:47', '0', null, null);
INSERT INTO `users` VALUES ('53', '15100990088', '张益达', null, '123456', '1', '未认证', null, '未认证', '未认证', '13804025161', '3-36-40-47-50-53', '2', '2018-11-21 17:03:24', '2018-11-21 17:03:24', '0', null, null);
INSERT INTO `users` VALUES ('59', '18800117074', '唐糖', '杨振杰', '123456', '1', '13062619900212491X', null, '6228480018768265375', '北京银行', '18800110011', '2-59', '2', '2019-01-05 13:48:44', '2019-01-05 13:48:44', '0', null, null);
INSERT INTO `users` VALUES ('60', '15611827983', 'chafeng75', '查凤', '198775', '1', '210422198707050614', null, '6225881247017456', '招商银行', '18800110011', '2-60', '2', '2019-01-05 13:50:43', '2019-01-05 13:50:43', '0', null, null);
INSERT INTO `users` VALUES ('61', '18800220011', '唐鑫', null, '123456', '1', '未认证', null, '未认证', '未认证', null, null, '1', '2019-01-05 14:12:57', '2019-01-05 14:12:57', '0', null, '北京');
INSERT INTO `users` VALUES ('62', '18800220022', '赵民', null, '123456', '1', '未认证', null, '未认证', '未认证', null, null, '1', '2019-01-05 14:14:30', '2019-01-05 14:14:30', '0', null, '北京');
INSERT INTO `users` VALUES ('63', '18800220033', '枉然', null, '123456', '1', '未认证', null, '未认证', '未认证', null, null, '1', '2019-01-05 14:14:50', '2019-01-05 14:14:50', '0', null, '北京');
INSERT INTO `users` VALUES ('64', '13671150807', 'yang', '杨彬', '123456', '1', '130626198601034110', null, '6228489874563215', '中国工商银行', '18800110011', '2-64', '2', '2019-01-05 15:13:37', '2019-01-05 15:13:37', '0', null, null);
INSERT INTO `users` VALUES ('65', '15810335440', '翟秀侠', '翟秀侠', '720041', '2', '132627197201280041', null, '6228270011300307171', '中国农业银行顺义支行', '18800110011', '2-65', '2', '2019-01-05 16:07:56', '2019-01-05 16:07:56', '0', null, null);
INSERT INTO `users` VALUES ('66', '15911047469', '张学军', null, 'zxj050212', '1', '未认证', null, '未认证', '未认证', '15810335440', '2-65-66', '2', '2019-01-05 16:33:48', '2019-01-05 16:33:48', '0', null, null);
INSERT INTO `users` VALUES ('67', '15931855599', '李龙', '李彦龙', '201314', '1', '230126197306283511', null, '6228481269168213775', '涞水县中国农业银行涞水支行', '18800110011', '2-67', '2', '2019-01-05 16:35:01', '2019-01-05 16:35:01', '0', null, null);
INSERT INTO `users` VALUES ('68', '13120086808', '城市猎人', '杨宝玉', '267153', '1', '110111197312033613', null, '6228480010315373612', '中国农业银行北京市大兴区安定镇支行', '18800110011', '2-68', '2', '2019-01-05 16:41:57', '2019-01-05 16:41:57', '0', null, null);
INSERT INTO `users` VALUES ('71', '13701255631', '房胜亚', '房胜亚', '13701255631', '1', '41232819680630343X', null, '6228480018861909176', '中国农业银行大兴区安定支行', '18800110011', '2-71', '2', '2019-01-05 17:11:48', '2019-01-05 17:11:48', '0', null, null);
INSERT INTO `users` VALUES ('72', '13522224478', '蜗牛', '杨宝玉', '267153', '1', '110111197312033613', null, '6228480010315373612', '中国农业银行北京市大兴区安定镇支行', '13120086808', '2-68-72', '2', '2019-01-05 17:17:04', '2019-01-05 17:17:04', '0', null, null);
INSERT INTO `users` VALUES ('73', '13520807348', '张喜奎', null, '123456', '1', '未认证', null, '未认证', '未认证', '13701255631', '2-71-73', '2', '2019-01-05 17:19:43', '2019-01-05 17:19:43', '0', null, null);
INSERT INTO `users` VALUES ('74', '15512913283', '张彦荣', '张彦荣', 'zhangyanrong88', '2', '110221198405217529', null, '6228480018669107577', '中国农业银行水碓西里支行', '18800110011', '2-74', '2', '2019-01-05 17:28:36', '2019-01-05 17:28:36', '0', null, null);
INSERT INTO `users` VALUES ('76', '15910252787', '转运', '张长旺', '287353', '1', '110224197509073835', null, '6228480018889508778', '中国农业银行', '13522224478', '2-68-72-76', '2', '2019-01-05 17:45:07', '2019-01-05 17:45:07', '0', null, null);
INSERT INTO `users` VALUES ('77', '15524454469', '王宝祥', null, '888888', '1', '未认证', null, '未认证', '未认证', '18800110011', '2-77', '2', '2019-01-05 18:03:02', '2019-01-05 18:03:02', '1', null, null);
INSERT INTO `users` VALUES ('78', '18940795999', '小小鸟', null, '888888', '1', '未认证', null, '未认证', '未认证', '18800110011', '2-78', '2', '2019-01-05 18:12:01', '2019-01-05 18:12:01', '1', null, null);
INSERT INTO `users` VALUES ('79', '18510089561', '结缘', '刘阿平', '791219', '2', '132401198002060644', null, '6228480018180566079', '农业银行北京大兴安定支行', '13522224478', '2-68-72-79', '2', '2019-01-05 18:19:40', '2019-01-05 18:19:40', '0', null, null);
INSERT INTO `users` VALUES ('80', '18941755655', 'wgcm', null, '123456', '1', '未认证', null, '未认证', '未认证', '18800110011', '2-80', '2', '2019-01-05 18:19:44', '2019-01-05 18:19:44', '1', null, null);
INSERT INTO `users` VALUES ('81', '18941755655', 'cjmg', null, '123456', '1', '未认证', null, '未认证', '未认证', '18800110011', '2-81', '2', '2019-01-05 18:23:34', '2019-01-05 18:23:34', '0', null, null);
INSERT INTO `users` VALUES ('82', '13180574326', '金泓', '张利平', '780621', '2', '132529197806211441', null, '6228481746500137267', '中国农业银行', '18800110011', '2-82', '2', '2019-01-05 19:40:20', '2019-01-05 19:40:20', '0', null, null);
INSERT INTO `users` VALUES ('83', '15612338936', '金泓', null, '780621', '1', '未认证', null, '未认证', '未认证', '18800110011', '2-83', '2', '2019-01-05 19:49:59', '2019-01-05 19:49:59', '0', null, null);
INSERT INTO `users` VALUES ('84', '13311299205', '马建明', '马建明', '133112', '1', '110109196310060312', null, '6222080200000447253', '曙光支行', '13522224478', '2-68-72-84', '2', '2019-01-05 19:56:07', '2019-01-05 19:56:07', '0', null, null);
INSERT INTO `users` VALUES ('86', '18518127590', '马建明', '马建明', '133112', '1', '110109196310060312', null, '6222080200000447253', '中国工商银行曙光支行', '13522224478', '2-68-72-86', '2', '2019-01-05 21:16:02', '2019-01-05 21:16:02', '0', null, null);

-- ----------------------------
-- Table structure for `users_relate`
-- ----------------------------
DROP TABLE IF EXISTS `users_relate`;
CREATE TABLE `users_relate` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) DEFAULT NULL,
  `buy_money` float(10,2) DEFAULT NULL,
  `check_time` datetime DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL COMMENT '缴费时间',
  `apply_status` int(2) DEFAULT '0' COMMENT '提现状态(0:未提现 1:已提现)',
  `original` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '描述说明',
  `level` int(2) DEFAULT '0',
  `child_id` int(10) DEFAULT NULL,
  `search_type` int(2) DEFAULT '0' COMMENT '查询我的会员可以查询全部数据，查询我的分享奖需要这个值为1并且这个1的值设定是在代理员交钱时输入的。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_relate
-- ----------------------------
INSERT INTO `users_relate` VALUES ('31', '3', '8000.00', '2018-11-16 17:34:14', '2018-11-16 17:32:55', '2018-11-16 17:36:35', '0', '3', null, '1', '36', '1');
INSERT INTO `users_relate` VALUES ('32', '3', '100000.00', '2018-11-16 22:08:23', '2018-11-16 22:02:45', '2018-11-16 22:24:31', '0', '3', null, '3', '39', '1');
INSERT INTO `users_relate` VALUES ('33', '30', '100000.00', '2018-11-16 22:08:23', '2018-11-16 22:02:45', '2018-11-16 22:24:31', '0', '30', null, '2', '39', '1');
INSERT INTO `users_relate` VALUES ('34', '34', '100000.00', '2018-11-16 22:08:23', '2018-11-16 22:02:45', '2018-11-16 22:24:31', '1', '34', null, '1', '39', '1');
INSERT INTO `users_relate` VALUES ('35', '3', '2000.00', '2018-11-16 22:56:47', '2018-11-16 22:56:21', '2018-11-16 22:59:02', '0', '3', null, '2', '40', '1');
INSERT INTO `users_relate` VALUES ('36', '36', '2000.00', '2018-11-16 22:56:47', '2018-11-16 22:56:21', '2018-11-16 22:59:02', '0', '36', null, '1', '40', '1');
INSERT INTO `users_relate` VALUES ('37', '3', '10025.00', '2018-11-16 23:02:53', '2018-11-16 23:02:04', '2018-11-16 23:04:54', '0', '3', null, '2', '40', '1');
INSERT INTO `users_relate` VALUES ('38', '36', '10025.00', '2018-11-16 23:02:53', '2018-11-16 23:02:04', '2018-11-16 23:04:54', '0', '36', null, '1', '40', '1');
INSERT INTO `users_relate` VALUES ('39', '3', '2012.00', '2018-11-16 23:46:20', '2018-11-16 23:46:12', '2018-11-16 23:46:28', '0', '3', null, '2', '40', '1');
INSERT INTO `users_relate` VALUES ('40', '36', '2012.00', '2018-11-16 23:46:20', '2018-11-16 23:46:12', '2018-11-16 23:46:28', '0', '36', null, '1', '40', '1');
INSERT INTO `users_relate` VALUES ('41', '3', null, null, null, null, '0', '3', null, '2', '46', '0');
INSERT INTO `users_relate` VALUES ('42', '36', null, null, null, null, '0', '36', null, '1', '46', '0');
INSERT INTO `users_relate` VALUES ('46', '3', null, null, null, null, '0', '3', null, '3', '48', '0');
INSERT INTO `users_relate` VALUES ('47', '36', null, null, null, null, '0', '36', null, '2', '48', '0');
INSERT INTO `users_relate` VALUES ('48', '46', null, null, null, null, '0', '46', null, '1', '48', '0');
INSERT INTO `users_relate` VALUES ('53', '3', '11111.00', '2018-11-20 00:02:59', '2018-11-20 00:02:48', '2018-11-20 00:03:12', '0', '3', null, '4', '50', '0');
INSERT INTO `users_relate` VALUES ('54', '36', '11111.00', '2018-11-20 00:02:59', '2018-11-20 00:02:48', '2018-11-20 00:03:12', '0', '36', null, '3', '50', '0');
INSERT INTO `users_relate` VALUES ('55', '40', '11111.00', '2018-11-20 00:02:59', '2018-11-20 00:02:48', '2018-11-20 00:03:12', '0', '40', null, '2', '50', '0');
INSERT INTO `users_relate` VALUES ('56', '47', '11111.00', '2018-11-20 00:02:59', '2018-11-20 00:02:48', '2018-11-20 00:03:12', '0', '47', null, '1', '50', '0');
INSERT INTO `users_relate` VALUES ('57', '3', null, null, null, null, '0', '3', null, '2', '52', '0');
INSERT INTO `users_relate` VALUES ('58', '30', null, null, null, null, '0', '30', null, '1', '52', '0');
INSERT INTO `users_relate` VALUES ('59', '3', null, null, null, null, '0', '3', null, '5', '53', '0');
INSERT INTO `users_relate` VALUES ('60', '36', null, null, null, null, '0', '36', null, '4', '53', '0');
INSERT INTO `users_relate` VALUES ('61', '40', null, null, null, null, '0', '40', null, '3', '53', '0');
INSERT INTO `users_relate` VALUES ('62', '47', null, null, null, null, '0', '47', null, '2', '53', '0');
INSERT INTO `users_relate` VALUES ('63', '50', null, null, null, null, '0', '50', null, '1', '53', '0');
INSERT INTO `users_relate` VALUES ('64', '2', null, null, null, null, '0', '2', null, '1', '57', '0');
INSERT INTO `users_relate` VALUES ('65', '2', null, null, null, null, '0', '2', null, '1', '58', '0');
INSERT INTO `users_relate` VALUES ('66', '2', '300000.00', '2019-01-05 16:23:19', '2019-01-05 13:57:05', '2019-01-05 16:37:40', '0', '2', null, '1', '59', '0');
INSERT INTO `users_relate` VALUES ('67', '2', null, null, null, null, '0', '2', null, '1', '60', '0');
INSERT INTO `users_relate` VALUES ('68', '2', '60000.00', '2019-01-05 16:55:03', '2019-01-05 15:18:04', '2019-01-05 16:59:45', '0', '2', null, '1', '64', '0');
INSERT INTO `users_relate` VALUES ('69', '2', null, null, null, null, '0', '2', null, '1', '65', '0');
INSERT INTO `users_relate` VALUES ('70', '2', null, null, null, null, '0', '2', null, '2', '66', '0');
INSERT INTO `users_relate` VALUES ('71', '65', null, null, null, null, '0', '65', null, '1', '66', '0');
INSERT INTO `users_relate` VALUES ('72', '2', null, null, null, null, '0', '2', null, '1', '67', '0');
INSERT INTO `users_relate` VALUES ('73', '2', null, null, null, null, '0', '2', null, '1', '68', '0');
INSERT INTO `users_relate` VALUES ('74', '2', null, null, null, null, '0', '2', null, '2', '69', '0');
INSERT INTO `users_relate` VALUES ('75', '68', null, null, null, null, '0', '68', null, '1', '69', '0');
INSERT INTO `users_relate` VALUES ('76', '2', null, null, null, null, '0', '2', null, '2', '70', '0');
INSERT INTO `users_relate` VALUES ('77', '68', null, null, null, null, '0', '68', null, '1', '70', '0');
INSERT INTO `users_relate` VALUES ('78', '2', null, null, null, null, '0', '2', null, '1', '71', '0');
INSERT INTO `users_relate` VALUES ('79', '2', null, null, null, null, '0', '2', null, '2', '72', '0');
INSERT INTO `users_relate` VALUES ('80', '68', null, null, null, null, '0', '68', null, '1', '72', '0');
INSERT INTO `users_relate` VALUES ('81', '2', null, null, null, null, '0', '2', null, '2', '73', '0');
INSERT INTO `users_relate` VALUES ('82', '71', null, null, null, null, '0', '71', null, '1', '73', '0');
INSERT INTO `users_relate` VALUES ('83', '2', null, null, null, null, '0', '2', null, '1', '74', '0');
INSERT INTO `users_relate` VALUES ('84', '2', null, null, null, null, '0', '2', null, '3', '75', '0');
INSERT INTO `users_relate` VALUES ('85', '68', null, null, null, null, '0', '68', null, '2', '75', '0');
INSERT INTO `users_relate` VALUES ('86', '72', null, null, null, null, '0', '72', null, '1', '75', '0');
INSERT INTO `users_relate` VALUES ('87', '2', null, null, null, null, '0', '2', null, '3', '76', '0');
INSERT INTO `users_relate` VALUES ('88', '68', null, null, null, null, '0', '68', null, '2', '76', '0');
INSERT INTO `users_relate` VALUES ('89', '72', null, null, null, null, '0', '72', null, '1', '76', '0');
INSERT INTO `users_relate` VALUES ('90', '2', null, null, null, null, '0', '2', null, '1', '77', '0');
INSERT INTO `users_relate` VALUES ('91', '2', null, null, null, null, '0', '2', null, '1', '78', '0');
INSERT INTO `users_relate` VALUES ('92', '2', null, null, null, null, '0', '2', null, '3', '79', '0');
INSERT INTO `users_relate` VALUES ('93', '68', null, null, null, null, '0', '68', null, '2', '79', '0');
INSERT INTO `users_relate` VALUES ('94', '72', null, null, null, null, '0', '72', null, '1', '79', '0');
INSERT INTO `users_relate` VALUES ('95', '2', null, null, null, null, '0', '2', null, '1', '80', '0');
INSERT INTO `users_relate` VALUES ('96', '2', null, null, null, null, '0', '2', null, '1', '81', '0');
INSERT INTO `users_relate` VALUES ('97', '2', null, null, null, null, '0', '2', null, '1', '82', '0');
INSERT INTO `users_relate` VALUES ('98', '2', null, null, null, null, '0', '2', null, '1', '83', '0');
INSERT INTO `users_relate` VALUES ('99', '2', null, null, null, null, '0', '2', null, '3', '84', '0');
INSERT INTO `users_relate` VALUES ('100', '68', null, null, null, null, '0', '68', null, '2', '84', '0');
INSERT INTO `users_relate` VALUES ('101', '72', null, null, null, null, '0', '72', null, '1', '84', '0');
INSERT INTO `users_relate` VALUES ('102', '2', null, null, null, null, '0', '2', null, '2', '85', '0');
INSERT INTO `users_relate` VALUES ('103', '68', null, null, null, null, '0', '68', null, '1', '85', '0');
INSERT INTO `users_relate` VALUES ('104', '2', null, null, null, null, '0', '2', null, '3', '86', '0');
INSERT INTO `users_relate` VALUES ('105', '68', null, null, null, null, '0', '68', null, '2', '86', '0');
INSERT INTO `users_relate` VALUES ('106', '72', null, null, null, null, '0', '72', null, '1', '86', '0');

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
  `is_delete` int(2) NOT NULL DEFAULT '0' COMMENT '是否可用：0 可用，1 不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='优惠券表\r\n\r\n1> 优惠券：第一次排队购车付款后获得，付款金额*0.5，有效期1个月。\r\n     若在有效期内第二次购车车可以使用；若有效期内没有购车，未使用，则第二次购车时重新生成，\r\n     第三次购车时才可以使用。每个账号只生成一张购车优惠券。';

-- ----------------------------
-- Records of voucher
-- ----------------------------
INSERT INTO `voucher` VALUES ('14', '30', '2000.00', '1', '2018-11-14 23:12:39', '2018-12-14 23:12:39', null, '0');
INSERT INTO `voucher` VALUES ('15', '34', '4000.00', '1', '2018-11-15 00:37:55', '2018-12-15 00:37:55', null, '0');
INSERT INTO `voucher` VALUES ('16', '37', '6000.00', '1', '2018-11-15 22:05:07', '2018-12-15 22:05:07', null, '0');
INSERT INTO `voucher` VALUES ('17', '36', '2000.00', '1', '2018-11-16 17:14:20', '2018-12-16 17:14:20', null, '0');
INSERT INTO `voucher` VALUES ('18', '39', '10000.00', '1', '2018-11-16 22:24:31', '2018-12-16 22:24:31', null, '0');
INSERT INTO `voucher` VALUES ('19', '40', '8000.00', '1', '2018-11-16 22:59:02', '2018-12-16 22:59:02', null, '0');
INSERT INTO `voucher` VALUES ('20', '47', '6000.00', '1', '2018-11-17 14:42:36', '2018-12-17 14:42:36', null, '1');
INSERT INTO `voucher` VALUES ('21', '49', '10000.00', '1', '2018-11-17 15:38:36', '2018-12-17 15:38:36', null, '0');
INSERT INTO `voucher` VALUES ('22', '50', '8000.00', '1', '2018-11-17 15:49:16', '2018-12-17 15:49:16', null, '0');
INSERT INTO `voucher` VALUES ('23', '59', '6000.00', '1', '2019-01-05 16:37:40', '2019-02-04 16:37:40', null, '0');
INSERT INTO `voucher` VALUES ('24', '64', '4000.00', '1', '2019-01-05 16:59:45', '2019-02-04 16:59:45', null, '0');
