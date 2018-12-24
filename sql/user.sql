/*
 Navicat Premium Data Transfer

 Source Server         : cat
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 193.112.113.194:3366
 Source Schema         : books

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 26/10/2018 14:01:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `login_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `head_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `delstatus` int(4) NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `isadmin` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(1) NULL DEFAULT 0 COMMENT '是否可用（1：可用；0：不可用）',
  `ctime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `utime` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `dtime` timestamp(0) NULL DEFAULT NULL COMMENT '删除时间',
  `operator` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Index_1`(`login_name`) USING BTREE,
  INDEX `Index_2`(`password`) USING BTREE,
  INDEX `Index_3`(`status`) USING BTREE,
  INDEX `idx_usr_e`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
