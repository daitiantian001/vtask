/*
Navicat MySQL Data Transfer

Source Server         : location
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : 001

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2018-04-16 08:07:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for con_test
-- ----------------------------
DROP TABLE IF EXISTS `con_test`;
CREATE TABLE `con_test` (
  `a` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for msg_code
-- ----------------------------
DROP TABLE IF EXISTS `msg_code`;
CREATE TABLE `msg_code` (
  `mobile` varchar(32) NOT NULL,
  `m_code` varchar(32) DEFAULT NULL,
  `u_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for v_platform_dealrecord
-- ----------------------------
DROP TABLE IF EXISTS `v_platform_dealrecord`;
CREATE TABLE `v_platform_dealrecord` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `userId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `type` int(2) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `pId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payType` int(1) DEFAULT NULL,
  `payStatus` int(1) DEFAULT NULL,
  `taskId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `orderNum` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sellerId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_platform_mission
-- ----------------------------
DROP TABLE IF EXISTS `v_platform_mission`;
CREATE TABLE `v_platform_mission` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `taskId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `userId` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `images` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_platform_step
-- ----------------------------
DROP TABLE IF EXISTS `v_platform_step`;
CREATE TABLE `v_platform_step` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `taskId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `taskExplain` text COLLATE utf8mb4_unicode_ci,
  `imgExplain` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `taskLink` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_platform_task
-- ----------------------------
DROP TABLE IF EXISTS `v_platform_task`;
CREATE TABLE `v_platform_task` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `categoryId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `num` int(5) DEFAULT NULL,
  `lastNum` int(11) DEFAULT NULL,
  `price` int(2) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `checkTime` int(2) DEFAULT NULL,
  `deviceType` int(1) DEFAULT NULL,
  `submitType` int(1) DEFAULT NULL,
  `textExplain` text COLLATE utf8mb4_unicode_ci,
  `ImgExplain` text COLLATE utf8mb4_unicode_ci,
  `contactorName` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contactorMobile` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contactorEmail` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `userId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `participate` int(1) DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL,
  `sort` int(3) DEFAULT NULL,
  `describeback` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ratio` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_platform_user
-- ----------------------------
DROP TABLE IF EXISTS `v_platform_user`;
CREATE TABLE `v_platform_user` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `photo` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birthday` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `open_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `union_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `zfb_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identify_type` int(1) DEFAULT NULL,
  `identify_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identify_short_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identify_address` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identify_desc` text COLLATE utf8mb4_unicode_ci,
  `identify_num` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identify_trade` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contactor_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contactor_mobile` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identify_photo` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account` int(11) DEFAULT NULL,
  `used_account` int(11) DEFAULT NULL,
  `frozen_account` int(11) DEFAULT NULL,
  `parent_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publish_type` int(1) DEFAULT NULL,
  `account_status` int(1) DEFAULT NULL,
  `invent_code` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `msg_code` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_platform_user_task
-- ----------------------------
DROP TABLE IF EXISTS `v_platform_user_task`;
CREATE TABLE `v_platform_user_task` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `userId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `taskId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `describeBack` text COLLATE utf8mb4_unicode_ci,
  `imgUrl` text COLLATE utf8mb4_unicode_ci,
  `content` text COLLATE utf8mb4_unicode_ci,
  `createTime` datetime DEFAULT NULL,
  `idCard` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wxName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `realName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_system_category
-- ----------------------------
DROP TABLE IF EXISTS `v_system_category`;
CREATE TABLE `v_system_category` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_system_commission
-- ----------------------------
DROP TABLE IF EXISTS `v_system_commission`;
CREATE TABLE `v_system_commission` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ratio` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_system_module
-- ----------------------------
DROP TABLE IF EXISTS `v_system_module`;
CREATE TABLE `v_system_module` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_system_reject
-- ----------------------------
DROP TABLE IF EXISTS `v_system_reject`;
CREATE TABLE `v_system_reject` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `describes` text CHARACTER SET utf8,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_system_role
-- ----------------------------
DROP TABLE IF EXISTS `v_system_role`;
CREATE TABLE `v_system_role` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_system_rolemodule
-- ----------------------------
DROP TABLE IF EXISTS `v_system_rolemodule`;
CREATE TABLE `v_system_rolemodule` (
  `module_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`module_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for v_system_user
-- ----------------------------
DROP TABLE IF EXISTS `v_system_user`;
CREATE TABLE `v_system_user` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mobile` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `role_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
