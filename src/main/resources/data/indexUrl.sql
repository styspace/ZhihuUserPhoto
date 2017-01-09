/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : zhihu

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-12-14 17:13:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for indexurl
-- ----------------------------
DROP TABLE IF EXISTS `indexurl`;
CREATE TABLE `indexurl` (
  `id` int(11) NOT NULL,
  `homepage` varchar(200) DEFAULT NULL COMMENT '用户个人主页url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
