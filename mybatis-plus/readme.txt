/*
Navicat MySQL Data Transfer

Source Server         : qiye
Source Server Version : 50628
Source Host           : 192.168.7.68:3309
Source Database       : qk_scaffold

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2018-10-16 17:59:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除； 1:删除)',
  `enabled` tinyint(1) NOT NULL COMMENT '是否启用(0:未启用； 1:启用)',
  `username` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `gender` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别\nMALE：男性\nFEMALE：女性',
  `nickname` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `phone_number` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email_address` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱地址',
  `identity_card` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `modify_at` datetime NOT NULL COMMENT '最后修改时间',
  `modify_by` int(11) NOT NULL COMMENT '最后修改人',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `mobile_number_UNIQUE` (`phone_number`),
  UNIQUE KEY `identity_card_UNIQUE` (`identity_card`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '0', '1', 'super_admin', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', '小丑', '18080916922', '5623695@qq.com', null, '2018-09-03 09:45:43', '1', '2018-10-11 09:17:42', '1', '2018-10-15 19:55:55');
INSERT INTO `user` VALUES ('6', '0', '1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'FAMALE', '强迫症', '13800138009', '5456@qq.com', '510122199305050202', '2018-09-12 09:53:07', '1', '2018-10-11 10:11:20', '6', '2018-10-11 15:11:32');
INSERT INTO `user` VALUES ('7', '0', '1', '13800138000', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', '张三', '13800128000', '83560@qq.com', 'asdfasdf', '2018-09-12 19:55:33', '1', '2018-09-21 15:45:59', '7', '2018-09-19 14:50:37');
INSERT INTO `user` VALUES ('13', '0', '1', '13800138001', '', 'MALE', '士大夫', '18080916910', null, null, '2018-09-13 10:27:53', '1', '2018-09-19 10:56:36', '1', null);
INSERT INTO `user` VALUES ('14', '1', '1', '13800138002', '', 'MALE', '阿瑟东', '13800138002', null, null, '2018-09-13 10:33:32', '1', '2018-09-14 09:40:05', '1', null);
INSERT INTO `user` VALUES ('15', '1', '0', '13800138111', null, 'MALE', '雷震子', '13800138111', null, null, '2018-09-19 09:33:54', '6', '2018-09-21 14:42:41', '6', null);
INSERT INTO `user` VALUES ('16', '0', '1', '13800138012', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', '步惊云', '13800138012', null, null, '2018-09-19 17:14:39', '6', '2018-09-21 16:45:08', '16', '2018-09-21 16:45:08');
INSERT INTO `user` VALUES ('17', '0', '1', 'niefeng', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', '天才大蹦但', '13800138013', '856955@163.com', null, '2018-09-19 17:17:40', '6', '2018-09-25 21:12:50', '17', '2018-09-25 21:12:50');
INSERT INTO `user` VALUES ('18', '0', '0', '13800138019', 'b0baee9d279d34fa1dfd71aadb908c3f', 'MALE', '惊奇队长', '13800138019', null, null, '2018-09-20 10:38:42', '6', '2018-09-21 15:55:10', '6', null);
INSERT INTO `user` VALUES ('19', '0', '1', '13800138456', null, 'MALE', '大牛', '13800138456', null, null, '2018-09-21 11:34:04', '6', '2018-09-21 14:29:25', '6', null);
INSERT INTO `user` VALUES ('20', '0', '1', '13800138695', '25f9e794323b453885f5181f1b624d0b', 'MALE', '技嘉', '13800138695', null, null, '2018-09-21 11:35:25', '6', '2018-09-21 15:47:46', '20', '2018-09-21 15:47:46');
INSERT INTO `user` VALUES ('21', '0', '1', '13800138546', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', '发电公司', '13800138546', null, null, '2018-09-21 11:54:53', '6', '2018-10-10 09:35:53', '21', '2018-10-11 20:00:07');
INSERT INTO `user` VALUES ('22', '0', '1', '18600000000', 'e10adc3949ba59abbe56e057f20f883e', 'FAMALE', '去问问', '18600000000', null, '510122199304050365', '2018-09-21 13:56:43', '17', '2018-09-27 13:57:38', '22', '2018-09-27 13:57:38');
INSERT INTO `user` VALUES ('23', '0', '1', '18600000001', null, 'MALE', '阿桑的大', '18600000001', null, null, '2018-09-21 13:59:34', '17', '2018-09-27 14:26:23', '17', null);
INSERT INTO `user` VALUES ('24', '0', '1', '18600000002', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', '王二', '18600000002', null, null, '2018-09-21 14:16:21', '17', '2018-10-11 17:36:11', '17', '2018-10-15 12:58:24');
INSERT INTO `user` VALUES ('35', '0', '1', '18600000012', 'e10adc3949ba59abbe56e057f20f883e', 'FAMALE', '已已', '18600000012', '1225367475@qq.com', null, '2018-09-21 14:27:40', '17', '2018-09-29 09:58:43', '35', '2018-09-29 09:58:43');
INSERT INTO `user` VALUES ('36', '0', '1', '18600000007', null, 'MALE', '二万人', '18600000007', null, null, '2018-09-21 17:00:57', '17', '2018-09-21 17:00:57', '17', null);
INSERT INTO `user` VALUES ('37', '0', '1', '18628212960', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', 'w', '18628212960', null, null, '2018-09-25 19:06:46', '1', '2018-10-11 09:19:22', '37', '2018-10-16 15:55:10');
INSERT INTO `user` VALUES ('38', '0', '1', '15328107400', '1c88d37be4e1d375f341d906f58288f4', 'MALE', '凉纪', '15328107400', null, null, '2018-09-25 20:06:54', '1', '2018-10-11 09:07:03', '38', '2018-10-16 17:41:21');
INSERT INTO `user` VALUES ('39', '0', '1', '17313155972', 'e10adc3949ba59abbe56e057f20f883e', 'MALE', 'tester', '17313155972', null, null, '2018-09-27 21:59:37', '1', '2018-09-27 21:59:59', '39', '2018-09-27 21:59:59');
INSERT INTO `user` VALUES ('40', '0', '1', '18628212961', 'e10adc3949ba59abbe56e057f20f883e', 'FAMALE', '泰安才', '18628212961', null, null, '2018-10-11 17:35:36', '37', '2018-10-11 17:35:36', '37', null);
