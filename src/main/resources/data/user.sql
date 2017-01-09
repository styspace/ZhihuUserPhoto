/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : zhihu

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-12-14 16:42:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trade` varchar(100) DEFAULT NULL COMMENT '所在行业',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `company` varchar(100) DEFAULT NULL COMMENT '公司',
  `position` varchar(100) DEFAULT NULL COMMENT '职位',
  `education` varchar(100) DEFAULT NULL COMMENT '教育',
  `location` varchar(200) DEFAULT NULL COMMENT '所在区域',
  `userToken` varchar(200) DEFAULT NULL COMMENT '用户token space-sty',
  `username` varchar(100) NOT NULL COMMENT '用户名 space sty',
  `homePage` varchar(100) DEFAULT NULL COMMENT '个人主页',
  `answers` int(10) unsigned zerofill DEFAULT NULL COMMENT '回答数',
  `articles` int(10) unsigned zerofill DEFAULT NULL COMMENT '文章数',
  `questions` int(10) unsigned zerofill DEFAULT NULL COMMENT '提问数',
  `collections` int(10) unsigned zerofill DEFAULT NULL COMMENT '收藏数',
  `followees` int(10) unsigned zerofill DEFAULT NULL COMMENT '关注人数',
  `followers` int(10) unsigned zerofill DEFAULT NULL COMMENT '粉丝数量',
  `followTips` int(10) unsigned zerofill DEFAULT NULL COMMENT '关注的话题',
  `followColumns` int(10) unsigned zerofill DEFAULT NULL COMMENT '关注的专栏数',
  `followQuestions` int(10) unsigned zerofill DEFAULT NULL COMMENT '关注的专栏',
  `followFavorites` int(10) unsigned zerofill DEFAULT NULL COMMENT '关注的收藏夹 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
