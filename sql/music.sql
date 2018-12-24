/*
 Navicat Premium Data Transfer

 Source Server         : cat
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 193.112.113.194:3366
 Source Schema         : miui

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 26/10/2018 14:00:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for music
-- ----------------------------
DROP TABLE IF EXISTS `music`;
CREATE TABLE `music`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `ctime` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
