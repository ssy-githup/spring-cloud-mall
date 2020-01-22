/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : mall_sys

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2020-01-22 12:27:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gateway_define
-- ----------------------------
DROP TABLE IF EXISTS `gateway_define`;
CREATE TABLE `gateway_define` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `uri` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `predicates` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `filters` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of gateway_define
-- ----------------------------
INSERT INTO `gateway_define` VALUES ('toBaidu', 'http://www.jd.com', '[{\"args\":{\"pattern\":\"/tobaidu\"},\"name\":\"Path\"}]', '[{\"args\":{\"_genkey_0\":\"1\"},\"name\":\"StripPrefix\"}]', null);
INSERT INTO `gateway_define` VALUES ('toProduct', 'lb://SERVICE-PRODUCT', '[{\"args\":{\"pattern\":\"/product/**\"},\"name\":\"Path\"}]', '[{\"args\":{\"_genkey_0\":\"1\"},\"name\":\"StripPrefix\"}]', null);
