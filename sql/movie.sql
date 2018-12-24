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

 Date: 26/10/2018 14:00:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `rate` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ctime` date NULL DEFAULT NULL,
  `region` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `duration` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `director` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `actors` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `release_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ctx_un_mu_ti`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 450 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
