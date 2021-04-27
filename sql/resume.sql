/*
 Navicat Premium Data Transfer

 Source Server         : myDB
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : resume

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 27/04/2021 17:48:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for basic_info
-- ----------------------------
DROP TABLE IF EXISTS `basic_info`;
CREATE TABLE `basic_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci DEFAULT NULL,
  `birth` varchar(255) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci DEFAULT NULL,
  `nativeAddress` varchar(255) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci DEFAULT NULL,
  `relativeTel` varchar(255) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gb2312 COLLATE = gb2312_chinese_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
