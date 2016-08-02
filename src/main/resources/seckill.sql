/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-05-24 11:04:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`),
  KEY `idx1` (`start_time`),
  KEY `idx2` (`end_time`),
  KEY `idx3` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商品库存ID';

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1', '1000元秒杀iphone6', '100', '2016-05-15 12:29:02', '2016-05-16 08:18:53', '2016-05-16 08:18:53');
INSERT INTO `seckill` VALUES ('2', '500元秒杀ipad2', '200', '2016-05-15 12:30:53', '2016-05-15 12:30:53', '2016-05-15 12:30:53');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL,
  `user_phone` bigint(255) NOT NULL,
  `state` tinyint(255) DEFAULT '-1',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`,`user_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='''状态表示:-1:无效 0 成功 1 已付款 2 已发货'',';

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('1', '1555705', '-1', null);
INSERT INTO `success_killed` VALUES ('2', '1555703', '0', null);
INSERT INTO `success_killed` VALUES ('3', '1555703', '0', null);
