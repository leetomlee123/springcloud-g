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

 Date: 26/10/2018 14:01:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE `user_permission`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `function` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机能名',
  `visible_function` tinyint(1) NULL DEFAULT NULL COMMENT '功能是否显示',
  `add_function` tinyint(1) NULL DEFAULT NULL COMMENT '数据追加功能',
  `update_function` tinyint(1) NULL DEFAULT NULL COMMENT '数据更新功能',
  `delete_function` tinyint(1) NULL DEFAULT NULL COMMENT '数据删除功能',
  `ctime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `utime` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Index_1`(`role`) USING BTREE,
  INDEX `Index_2`(`service_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
