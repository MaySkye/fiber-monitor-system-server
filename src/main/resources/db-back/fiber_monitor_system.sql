
CREATE DATABASE `fiber_monitor_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
grant all PRIVILEGES on fiber_monitor_system.* to root@'%' identified by '123456';
flush privileges;
use fiber_monitor_system;
set session sql_mode='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';


/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : fiber_monitor_system

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 17/06/2021 23:14:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarm_device
-- ----------------------------
DROP TABLE IF EXISTS `alarm_device`;
CREATE TABLE `alarm_device`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alarm_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fault_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `alarm_lower_limit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `resource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `site_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT 0,
  `timestamp` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `alarm_upper_limit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS `databasechangelog`;
CREATE TABLE `databasechangelog`  (
  `ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `AUTHOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `FILENAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DATEEXECUTED` datetime(0) NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `MD5SUM` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `COMMENTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LIQUIBASE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CONTEXTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LABELS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of databasechangelog
-- ----------------------------
INSERT INTO `databasechangelog` VALUES ('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2019-06-03 16:05:33', 1, 'EXECUTED', '7:4d85236a8667651805fef548ed3c68ea', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '3.5.4', NULL, NULL, '9549132544');
INSERT INTO `databasechangelog` VALUES ('00000000000002', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2019-06-27 17:27:33', 2, 'EXECUTED', '7:93e3a09dd8d4e15ae9ad4a3c483133e0', 'createTable tableName=jhi_date_time_wrapper', '', NULL, '3.5.4', 'test', NULL, '1627653069');

-- ----------------------------
-- Table structure for databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS `databasechangeloglock`;
CREATE TABLE `databasechangeloglock`  (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime(0) DEFAULT NULL,
  `LOCKEDBY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of databasechangeloglock
-- ----------------------------
INSERT INTO `databasechangeloglock` VALUES (1, b'0', NULL, NULL);

-- ----------------------------
-- Table structure for jhi_authority
-- ----------------------------
DROP TABLE IF EXISTS `jhi_authority`;
CREATE TABLE `jhi_authority`  (
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jhi_authority
-- ----------------------------
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN');
INSERT INTO `jhi_authority` VALUES ('ROLE_MONITOR');
INSERT INTO `jhi_authority` VALUES ('ROLE_USER');

-- ----------------------------
-- Table structure for jhi_persistent_audit_event
-- ----------------------------
DROP TABLE IF EXISTS `jhi_persistent_audit_event`;
CREATE TABLE `jhi_persistent_audit_event`  (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_date` datetime(0) DEFAULT NULL,
  `event_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `principal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`event_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for jhi_persistent_audit_evt_data
-- ----------------------------
DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;
CREATE TABLE `jhi_persistent_audit_evt_data`  (
  `event_id` bigint(20) NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`event_id`, `name`) USING BTREE,
  CONSTRAINT `FK2ehnyx2si4tjd2nt4q7y40v8m` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for jhi_user
-- ----------------------------
DROP TABLE IF EXISTS `jhi_user`;
CREATE TABLE `jhi_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password_hash` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `image_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `activation_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reset_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(0),
  `reset_date` timestamp(0),
  `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_modified_date` timestamp(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_user_login`(`login`) USING BTREE,
  UNIQUE INDEX `ux_user_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jhi_user
-- ----------------------------
INSERT INTO `jhi_user` VALUES (2, 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', '', b'1', 'zh-cn', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
INSERT INTO `jhi_user` VALUES (3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', '527966005@qq.com', '', b'1', 'zh-cn', NULL, '34969480582853358552', 'system', NULL, '2020-05-12 07:30:34', 'anonymousUser', '2020-05-12 07:30:34');
INSERT INTO `jhi_user` VALUES (9, 'wangwei', '$2a$10$4n9m73y0DKePqrvAjU3q5..zU5Wq8kvZU0LojaLkqfItSHcM8UREe', '伟', '王', '550443096@qq.com', NULL, b'1', 'zh-cn', '23962320755844611968', NULL, 'anonymousUser', '2020-05-13 08:25:10', NULL, 'wangwei', '2020-06-13 08:05:45');

-- ----------------------------
-- Table structure for jhi_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `jhi_user_authority`;
CREATE TABLE `jhi_user_authority`  (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`user_id`, `authority_name`) USING BTREE,
  INDEX `fk_authority_name`(`authority_name`) USING BTREE,
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jhi_user_authority
-- ----------------------------
INSERT INTO `jhi_user_authority` VALUES (3, 'ROLE_ADMIN');
INSERT INTO `jhi_user_authority` VALUES (9, 'ROLE_ADMIN');
INSERT INTO `jhi_user_authority` VALUES (9, 'ROLE_MONITOR');
INSERT INTO `jhi_user_authority` VALUES (3, 'ROLE_USER');
INSERT INTO `jhi_user_authority` VALUES (9, 'ROLE_USER');

-- ----------------------------
-- Table structure for site
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site`  (
  `site_id` int(255) NOT NULL AUTO_INCREMENT,
  `site_level` int(255) DEFAULT NULL,
  `site_localx` double(255, 3) DEFAULT NULL,
  `site_localy` double(255, 3) DEFAULT NULL,
  `site_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `site_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `site_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `site_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `connect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `devcount` int(11) DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`site_id`) USING BTREE,
  INDEX `site_name`(`site_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of site
-- ----------------------------
INSERT INTO `site` VALUES (1, 1, 108.947, 34.348, '西安', NULL, '正式', NULL, '武汉；沈阳', 1, NULL);
INSERT INTO `site` VALUES (2, 1, 116.416, 39.908, '北京', NULL, '正式', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (3, 1, 117.235, 31.828, '合肥', NULL, '正式', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (4, 1, 114.316, 30.597, '武汉', NULL, '正式', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (5, 3, 113.636, 34.759, '郑州', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (6, 2, 121.478, 31.236, '上海', NULL, '正式', NULL, '北京；合肥', 1, NULL);
INSERT INTO `site` VALUES (7, 2, 125.331, 43.823, '长春', NULL, '正式', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (8, 2, 109.519, 18.260, '三亚', NULL, '正式', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (9, 2, 86.200, 41.756, '库尔勒', NULL, '正式', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (10, 3, 117.208, 39.093, '天津', NULL, '普通', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (11, 3, 120.217, 30.250, '杭州', NULL, '普通', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (12, 3, 120.598, 31.299, '苏州', NULL, '普通', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (13, 3, 120.711, 28.011, '温州', NULL, '普通', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (14, 3, 123.438, 41.816, '沈阳', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (15, 3, 126.542, 45.807, '哈尔滨', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (16, 3, 87.629, 43.836, '乌鲁木齐', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (17, 3, 98.501, 39.744, '酒泉', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (18, 3, 93.521, 42.838, '哈密', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (19, 3, 104.077, 30.578, '成都', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (20, 3, 102.839, 24.893, '昆明', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (21, 3, 108.378, 22.820, '南宁', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (22, 3, 106.633, 26.655, '贵州', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (23, 3, 113.276, 23.137, '广州', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (24, 3, 112.952, 28.237, '长沙', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (25, 3, 112.125, 32.015, '襄樊', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (26, 3, 118.672, 24.882, '泉州', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (27, 3, 117.126, 36.651, '济南', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (28, 3, 118.793, 32.063, '南京', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (29, 3, 103.845, 36.069, '兰州', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (30, 3, 110.361, 21.283, '湛江', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (31, 3, 112.552, 37.879, '太原', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (32, 3, 114.518, 38.052, '石家庄', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (33, 3, 76.001, 39.478, '喀什', NULL, '测试', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (35, 3, 109.320, 34.150, '蓝田', NULL, '普通', NULL, NULL, 1, NULL);
INSERT INTO `site` VALUES (37, 3, 112.128, 32.016, '襄阳市', '', '普通', '', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for site_line
-- ----------------------------
DROP TABLE IF EXISTS `site_line`;
CREATE TABLE `site_line`  (
  `line_id` int(11) NOT NULL AUTO_INCREMENT,
  `point1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `point2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `line_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `line_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `length` double(255, 2) DEFAULT NULL,
  `stable` double(255, 2) DEFAULT NULL,
  `transspeed` double(255, 2) DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`line_id`) USING BTREE,
  INDEX `point1`(`point1`) USING BTREE,
  INDEX `point2`(`point2`) USING BTREE,
  CONSTRAINT `site_line_ibfk_1` FOREIGN KEY (`point1`) REFERENCES `site` (`site_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `site_line_ibfk_2` FOREIGN KEY (`point2`) REFERENCES `site` (`site_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of site_line
-- ----------------------------
INSERT INTO `site_line` VALUES (1, '喀什', '库尔勒', '喀什-库尔勒', '普通', NULL, NULL, 6.92, 6.69, '正常');
INSERT INTO `site_line` VALUES (2, '库尔勒', '乌鲁木齐', '库尔勒-乌鲁木齐', '普通', NULL, NULL, 6.96, 5.34, '正常');
INSERT INTO `site_line` VALUES (3, '乌鲁木齐', '哈密', '乌鲁木齐-哈密', '普通', NULL, NULL, 4.24, 9.96, '正常');
INSERT INTO `site_line` VALUES (4, '哈密', '酒泉', '哈密-酒泉', '普通', NULL, NULL, 3.70, 8.55, '正常');
INSERT INTO `site_line` VALUES (5, '酒泉', '兰州', '酒泉-兰州', '普通', NULL, NULL, 7.98, 0.97, '正常');
INSERT INTO `site_line` VALUES (6, '兰州', '西安', '兰州-西安', '重点', NULL, NULL, 2.22, 6.39, '正常');
INSERT INTO `site_line` VALUES (7, '西安', '成都', '西安-成都', '重点', NULL, NULL, 5.19, 4.20, '正常');
INSERT INTO `site_line` VALUES (8, '西安', '襄樊', '西安-襄樊', '重点', NULL, NULL, 4.98, 5.84, '正常');
INSERT INTO `site_line` VALUES (9, '西安', '太原', '西安-太原', '重点', '重要', 600.00, 5.75, 4.27, '正常');
INSERT INTO `site_line` VALUES (10, '成都', '昆明', '成都-昆明', '重点', NULL, NULL, 9.47, 3.14, '正常');
INSERT INTO `site_line` VALUES (11, '昆明', '贵州', '昆明-贵州', '重点', NULL, NULL, 8.50, 1.09, '正常');
INSERT INTO `site_line` VALUES (12, '贵州', '南宁', '贵州-南宁', '重点', NULL, NULL, 1.97, 0.37, '正常');
INSERT INTO `site_line` VALUES (13, '南宁', '湛江', '南宁-湛江', '重点', NULL, NULL, 4.85, 6.61, '正常');
INSERT INTO `site_line` VALUES (14, '湛江', '三亚', '湛江-三亚', '重点', NULL, NULL, 6.95, 4.08, '正常');
INSERT INTO `site_line` VALUES (15, '湛江', '广州', '湛江-广州', '重点', NULL, NULL, 0.95, 3.33, '正常');
INSERT INTO `site_line` VALUES (16, '襄樊', '武汉', '襄樊-武汉', '重点', NULL, NULL, 0.45, 6.31, '正常');
INSERT INTO `site_line` VALUES (17, '武汉', '长沙', '武汉-长沙', '重点', NULL, NULL, 0.03, 3.72, '正常');
INSERT INTO `site_line` VALUES (18, '长沙', '广州', '长沙-广州', '重点', NULL, NULL, 8.97, 5.88, '正常');
INSERT INTO `site_line` VALUES (19, '西安', '郑州', '西安-郑州', '重点', NULL, NULL, 4.25, 8.78, '正常');
INSERT INTO `site_line` VALUES (20, '郑州', '武汉', '郑州-武汉', '重点', NULL, NULL, 1.02, 1.09, '正常');
INSERT INTO `site_line` VALUES (21, '太原', '石家庄', '太原-石家庄', '重点', NULL, NULL, 1.82, 3.38, '正常');
INSERT INTO `site_line` VALUES (22, '石家庄', '北京', '石家庄-北京', '重点', NULL, NULL, 7.06, 3.19, '正常');
INSERT INTO `site_line` VALUES (23, '武汉', '合肥', '武汉-合肥', '重点', NULL, NULL, 8.52, 5.91, '正常');
INSERT INTO `site_line` VALUES (24, '合肥', '南京', '合肥-南京', '重点', NULL, NULL, 5.22, 3.75, '正常');
INSERT INTO `site_line` VALUES (25, '北京', '天津', '北京-天津', '重点', NULL, NULL, 4.55, 9.70, '正常');
INSERT INTO `site_line` VALUES (26, '天津', '济南', '天津-济南', '重点', NULL, NULL, 4.10, 7.98, '正常');
INSERT INTO `site_line` VALUES (27, '济南', '南京', '济南-南京', '重点', NULL, NULL, 7.01, 4.32, '正常');
INSERT INTO `site_line` VALUES (28, '南京', '苏州', '南京-苏州', '普通', NULL, NULL, 1.27, 0.41, '正常');
INSERT INTO `site_line` VALUES (29, '苏州', '上海', '苏州-上海', '普通', NULL, NULL, 5.27, 5.22, '正常');
INSERT INTO `site_line` VALUES (30, '上海', '杭州', '上海-杭州', '普通', NULL, NULL, 5.65, 9.00, '正常');
INSERT INTO `site_line` VALUES (31, '杭州', '温州', '杭州-温州', '普通', NULL, NULL, 1.93, 1.63, '正常');
INSERT INTO `site_line` VALUES (32, '温州', '泉州', '温州-泉州', '普通', NULL, NULL, 5.01, 8.56, '正常');
INSERT INTO `site_line` VALUES (33, '广州', '泉州', '广州-泉州', '普通', NULL, NULL, 4.46, 3.50, '正常');
INSERT INTO `site_line` VALUES (34, '北京', '沈阳', '北京-沈阳', '普通', NULL, NULL, 4.90, 4.63, '正常');
INSERT INTO `site_line` VALUES (35, '沈阳', '长春', '沈阳-长春', '普通', NULL, NULL, 3.51, 0.46, '正常');
INSERT INTO `site_line` VALUES (36, '长春', '哈尔滨', '长春-哈尔滨', '普通', NULL, NULL, 3.82, 4.78, '正常');

-- ----------------------------
-- Table structure for telemetry
-- ----------------------------
DROP TABLE IF EXISTS `telemetry`;
CREATE TABLE `telemetry`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alarm_lower_limit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `alarm_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `alarm_upper_limit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `base` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `blocked` bit(1) DEFAULT NULL,
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `detected_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lower_limit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `offset` int(11) DEFAULT NULL,
  `ratio` int(11) DEFAULT NULL,
  `site_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `timestamp` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `upper_limit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
