/*
 Navicat Premium Data Transfer

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : y

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 19/10/2023 23:05:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum
-- ----------------------------
DROP TABLE IF EXISTS `forum`;
CREATE TABLE `forum`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum
-- ----------------------------
INSERT INTO `forum` VALUES (1, 1, '论坛1');
INSERT INTO `forum` VALUES (2, 2, '论坛2');

-- ----------------------------
-- Table structure for museum
-- ----------------------------
DROP TABLE IF EXISTS `museum`;
CREATE TABLE `museum`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of museum
-- ----------------------------
INSERT INTO `museum` VALUES (1, '博物馆1', '江苏常州', '../../static/place/ChangeZhou.jpg');
INSERT INTO `museum` VALUES (2, '博物馆2', '北京', '../../static/place/Beijing.jpg');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '公告1');

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `museum_id` int(0) NOT NULL,
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '1，预约，2，取消，3，完成',
  `start_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `end_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation` VALUES (1, 1, 1, 2, '2023-09-06 21:28:58', '2023-08-17 18:41:51');
INSERT INTO `reservation` VALUES (6, 1, 1, 4, '2023-10-07 21:49:16', '2023-09-30 00:00:00');
INSERT INTO `reservation` VALUES (8, 1, 2, 2, '2023-09-06 21:29:36', '2023-09-30 00:00:00');
INSERT INTO `reservation` VALUES (9, 1, 1, 2, '2023-09-06 21:17:31', '2023-10-31 00:00:00');
INSERT INTO `reservation` VALUES (10, 13, 2, 3, '2023-10-07 19:58:10', '2023-10-28 00:00:00');
INSERT INTO `reservation` VALUES (11, 1, 1, 3, '2023-10-07 19:59:21', '2023-11-25 00:00:00');
INSERT INTO `reservation` VALUES (12, 1, 2, 2, '2023-10-07 19:56:17', '2023-10-13 02:44:00');
INSERT INTO `reservation` VALUES (13, 1, 2, 2, '2023-10-07 19:53:09', '2023-10-14 03:02:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `online` int(0) NOT NULL DEFAULT 1 COMMENT '是否在线',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别0男1女',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '100' COMMENT '信用分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$.V.hcim3svpAYTX9tC7ykOX2RrdeLgwWvOWIlb0Owo5ScAJ1EajUG', 1, '555', 1, '江苏省常州市武进区', '90');
INSERT INTO `user` VALUES (2, 'xmy', '$2a$10$UKwmY62dNGTwMM9TzTZPGu1M98qIC22Qp/v9dznhIY5keSEzg9jfS', 1, NULL, 0, '江苏省常州市钟楼区', '100');
INSERT INTO `user` VALUES (3, 'awm', '$2a$10$7BeaszbmJ/yHyynMLepJiekeiq6b0.GZ/u2hs09r2metHKGeV.hMS', 1, NULL, 0, '江苏省常州市溧阳市', '100');
INSERT INTO `user` VALUES (4, 'cpdd', '$2a$10$uTA6bDWHLJc.vfuxeOeNzO5WO0FbzZwwPUtMTpH9yputchLmlTJby', 0, NULL, 0, '江苏省常州市新北区', '100');
INSERT INTO `user` VALUES (5, 'iu', '$2a$10$xEgty0cwpET/6rk5yFGPi.JyZc8v3KJyno752jnHLL8GVaKkymdmy', 1, NULL, 0, '江苏省常州市天宁区', '98');
INSERT INTO `user` VALUES (12, '666666', '$2a$10$v5dN3oGaK2hFsOjs5hm1/.daK.n4q6/gOUXRj3sJk8KXnB5iyZZPy', 1, NULL, 0, NULL, '100');
INSERT INTO `user` VALUES (13, 'bbbbb', '$2a$10$dRJnO2.j9VzXfPuEageM6ucJRyAo7DbE6acrLs3UIGNUp6szvctx.', 1, NULL, 0, NULL, '100');

SET FOREIGN_KEY_CHECKS = 1;
