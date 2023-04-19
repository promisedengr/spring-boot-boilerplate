/*
 Navicat Premium Data Transfer

 Source Server         : local_Mysql
 Source Server Type    : MySQL
 Source Server Version : 100424
 Source Host           : localhost:3306
 Source Schema         : nichoshop_ryo

 Target Server Type    : MySQL
 Target Server Version : 100424
 File Encoding         : 65001

 Date: 24/08/2022 11:44:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for addresses
-- ----------------------------
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int UNSIGNED NOT NULL,
  `name` varchar(33) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `address1` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `address2` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `city` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `zip` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `country` char(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `address_confirmed` tinyint(1) NOT NULL,
  `status` int NOT NULL,
  `address_type` int NOT NULL,
  `signup` blob NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of addresses
-- ----------------------------
INSERT INTO `addresses` VALUES (13, 40, 'fadfaf', 'fadsfadf', 'fadfdafff', 'fdfdffff', 'KKKLLL', '20222', 'kk', '+1231231123', 0, 1, 1, 0xACED00057372002A636F6D2E6E6963686F73686F702E6D61696E2E726573706F6E73652E5369676E5570526573706F6E73657ED591A11A1F5D4A0200034C0005656D61696C7400124C6A6176612F6C616E672F537472696E673B4C0006726573756C7471007E00014C0008757365726E616D6571007E000178707400066461736B6A657400086173646A666F6965740009616468666575696F77);

-- ----------------------------
-- Table structure for bank_accounts
-- ----------------------------
DROP TABLE IF EXISTS `bank_accounts`;
CREATE TABLE `bank_accounts`  (
  `id` int NOT NULL,
  `user_id` int NULL DEFAULT NULL,
  `account_type` int NULL DEFAULT NULL,
  `street` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `apartment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `zip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `holder_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `iban` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `digit_routing_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `branch_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `insituation_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bank_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `account_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `currency` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bank_accounts
-- ----------------------------

-- ----------------------------
-- Table structure for bids
-- ----------------------------
DROP TABLE IF EXISTS `bids`;
CREATE TABLE `bids`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `bidderId` int UNSIGNED NOT NULL,
  `productId` int UNSIGNED NOT NULL,
  `price` int UNSIGNED NOT NULL,
  `auto` bit(1) NOT NULL DEFAULT b'0',
  `timestamp` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bidder_id`(`bidderId`) USING BTREE,
  INDEX `product_id`(`productId`) USING BTREE,
  CONSTRAINT `bids_ibfk_1` FOREIGN KEY (`bidderId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bids
-- ----------------------------

-- ----------------------------
-- Table structure for businesses
-- ----------------------------
DROP TABLE IF EXISTS `businesses`;
CREATE TABLE `businesses`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int UNSIGNED NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `address_id` int NULL DEFAULT NULL,
  `vat_country` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `vat_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `businesses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of businesses
-- ----------------------------
INSERT INTO `businesses` VALUES (1, 1, '1', 2, 'kk', '1234');

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `leaf` bit(1) NOT NULL DEFAULT b'0',
  `parent_id` int NOT NULL DEFAULT 0,
  `ord` int UNSIGNED NOT NULL DEFAULT 0,
  `conditions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `specifics` blob NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id_idx`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (51, 'First', b'0', 0, 0, '', 0xACED0005737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A6578700000000077040000000078);
INSERT INTO `categories` VALUES (52, '123', b'0', 51, 0, '1,2', 0xACED0005737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A6578700000000077040000000078);

-- ----------------------------
-- Table structure for conditions
-- ----------------------------
DROP TABLE IF EXISTS `conditions`;
CREATE TABLE `conditions`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of conditions
-- ----------------------------
INSERT INTO `conditions` VALUES (1, 'New');
INSERT INTO `conditions` VALUES (2, 'New other(see details)');
INSERT INTO `conditions` VALUES (3, 'New—open box');
INSERT INTO `conditions` VALUES (4, 'New with tags');
INSERT INTO `conditions` VALUES (5, 'New without tags');
INSERT INTO `conditions` VALUES (6, 'New with box');
INSERT INTO `conditions` VALUES (7, 'New without box');
INSERT INTO `conditions` VALUES (8, 'New with defects');
INSERT INTO `conditions` VALUES (9, 'Brand New');
INSERT INTO `conditions` VALUES (10, 'Like New');
INSERT INTO `conditions` VALUES (11, 'Open box');
INSERT INTO `conditions` VALUES (12, 'Used');
INSERT INTO `conditions` VALUES (13, 'Pre-owned');
INSERT INTO `conditions` VALUES (14, 'Very Good');
INSERT INTO `conditions` VALUES (15, 'Good');
INSERT INTO `conditions` VALUES (16, 'Accessible');
INSERT INTO `conditions` VALUES (17, 'Certified pre-owned');
INSERT INTO `conditions` VALUES (18, 'Certified refurbished');
INSERT INTO `conditions` VALUES (19, 'Seller refurbished');
INSERT INTO `conditions` VALUES (20, 'Remanufactured');
INSERT INTO `conditions` VALUES (21, 'Retread');
INSERT INTO `conditions` VALUES (22, 'For parts or not working');
INSERT INTO `conditions` VALUES (23, 'Damaged');

-- ----------------------------
-- Table structure for credit_cards
-- ----------------------------
DROP TABLE IF EXISTS `credit_cards`;
CREATE TABLE `credit_cards`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int UNSIGNED NOT NULL,
  `holder` varchar(33) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `card_type` tinyint NOT NULL,
  `number` bigint UNSIGNED NOT NULL,
  `month` tinyint NOT NULL,
  `year` smallint NOT NULL,
  `code` smallint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `credit_cards_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of credit_cards
-- ----------------------------

-- ----------------------------
-- Table structure for customer_support_logs
-- ----------------------------
DROP TABLE IF EXISTS `customer_support_logs`;
CREATE TABLE `customer_support_logs`  (
  `id` int NOT NULL,
  `from` bigint NULL DEFAULT NULL,
  `to` bigint NULL DEFAULT NULL,
  `cs_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of customer_support_logs
-- ----------------------------

-- ----------------------------
-- Table structure for customer_supports
-- ----------------------------
DROP TABLE IF EXISTS `customer_supports`;
CREATE TABLE `customer_supports`  (
  `id` int NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `lname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `depart` int NULL DEFAULT NULL,
  `sub_depart` int NULL DEFAULT NULL,
  `timezone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contact` tinyint(1) NULL DEFAULT NULL,
  `mon_to_fri` int NULL DEFAULT NULL,
  `saturday` int NULL DEFAULT NULL,
  `sunday` int NULL DEFAULT NULL,
  `currency_id` int NULL DEFAULT NULL,
  `created` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of customer_supports
-- ----------------------------

-- ----------------------------
-- Table structure for duo_confirms
-- ----------------------------
DROP TABLE IF EXISTS `duo_confirms`;
CREATE TABLE `duo_confirms`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `state` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of duo_confirms
-- ----------------------------
INSERT INTO `duo_confirms` VALUES (1, 'Nurusultan11111', '5dfc8bfe4dac83f319e02283c476b7c9d8e2', '2022-07-31');
INSERT INTO `duo_confirms` VALUES (61, 'developeradmin', '8424c37d670f6ef7e176ece503855b4cbefe', '2022-08-23');
INSERT INTO `duo_confirms` VALUES (62, 'developeradmin', 'c370c7c1777b5945e1c9a2a2436e9020e404', '2022-08-23');
INSERT INTO `duo_confirms` VALUES (63, 'developeradmin', '8a00d0e023bb6efc91240d8a6f52ee5a8c4b', '2022-08-23');

-- ----------------------------
-- Table structure for email_confirms
-- ----------------------------
DROP TABLE IF EXISTS `email_confirms`;
CREATE TABLE `email_confirms`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of email_confirms
-- ----------------------------
INSERT INTO `email_confirms` VALUES (1, 61, 'f6ebdc1119a0a9afa53e56cc66301018', '2022-08-19 16:59:54');
INSERT INTO `email_confirms` VALUES (3, 63, '800C514B6D0A8B4BCF4229B7492D98AB', '2022-08-19 21:54:16');
INSERT INTO `email_confirms` VALUES (4, 64, '1E79A8972EC8BFC5E13CCA548FE2EEE8', '2022-08-19 21:58:12');
INSERT INTO `email_confirms` VALUES (5, 65, 'C69CADA3443D3A376105630748523035', '2022-08-19 21:58:44');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `seller_id` int UNSIGNED NOT NULL,
  `sell_id` int UNSIGNED NOT NULL,
  `rating` tinyint NOT NULL,
  `item_as_described` tinyint NOT NULL,
  `communication` tinyint NOT NULL,
  `shipping_time` tinyint NOT NULL,
  `shipping_charges` tinyint NOT NULL,
  `message` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `timestamp` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `seller_id`(`seller_id`) USING BTREE,
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for item_views
-- ----------------------------
DROP TABLE IF EXISTS `item_views`;
CREATE TABLE `item_views`  (
  `product_id` int UNSIGNED NOT NULL,
  `timestamp` bigint UNSIGNED NOT NULL,
  INDEX `product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of item_views
-- ----------------------------

-- ----------------------------
-- Table structure for items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `seller_id` int UNSIGNED NOT NULL,
  `cat_id` int UNSIGNED NOT NULL,
  `title` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `cond_id` tinyint NOT NULL,
  `cond_desc` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `item_desc` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `listing_format` int NOT NULL,
  `now_price` int NOT NULL,
  `quantity` int NOT NULL,
  `duration` int NOT NULL,
  `start_price` int NOT NULL,
  `reserve_price` int NOT NULL,
  `domestic_service` tinyint NOT NULL,
  `domestic_service_cost` int NOT NULL,
  `another_service` tinyint NULL DEFAULT NULL,
  `another_service_cost` int NULL DEFAULT NULL,
  `local_collect` tinyint(1) NULL DEFAULT NULL,
  `international_service` tinyint NULL DEFAULT NULL,
  `international_service_cost` int NULL DEFAULT NULL,
  `dispatch_time` int NULL DEFAULT NULL,
  `item_country` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `item_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `return_days` int NOT NULL,
  `images` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `currency` int NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  `is_multi` tinyint(1) NULL DEFAULT NULL,
  `created_at` date NULL DEFAULT NULL,
  `nsln` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `returns` int NULL DEFAULT NULL,
  `return_accept` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES (9, 1, 1, 'My Item', 1, 'this is optional field.', NULL, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 'Serbia', 'f', 1, '1.png,2.png,3.png', '1.jpg', 1, '1', 0, 0, '2022-08-17', 'This is should be calculated by server.', 1, 1);

-- ----------------------------
-- Table structure for message_folders
-- ----------------------------
DROP TABLE IF EXISTS `message_folders`;
CREATE TABLE `message_folders`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `message_folders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message_folders
-- ----------------------------

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `from_id` int UNSIGNED NULL DEFAULT NULL,
  `from_userid` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_id` int UNSIGNED NOT NULL,
  `subject` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `creation_time` bigint UNSIGNED NOT NULL,
  `flag` bit(1) NOT NULL DEFAULT b'0',
  `msg_read` bit(1) NOT NULL DEFAULT b'0',
  `item_id` int UNSIGNED NULL DEFAULT NULL,
  `item_title` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `folder_id` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `from_id`(`from_id`) USING BTREE,
  INDEX `to_id`(`to_id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE,
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`to_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of messages
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `section_group_code`(`user_id`, `code`) USING BTREE,
  INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `type` enum('customer','customer_support','admin') CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'customer',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `section_group_code`(`user_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 1, 'admin');
INSERT INTO `roles` VALUES (2, 40, 'admin');

-- ----------------------------
-- Table structure for sells
-- ----------------------------
DROP TABLE IF EXISTS `sells`;
CREATE TABLE `sells`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `seller_id` int UNSIGNED NOT NULL,
  `buyer_id` int UNSIGNED NOT NULL,
  `product_id` int UNSIGNED NOT NULL,
  `quantity` smallint UNSIGNED NOT NULL,
  `price` int UNSIGNED NOT NULL,
  `paid` bit(1) NOT NULL DEFAULT b'0',
  `dispatched` bit(1) NOT NULL DEFAULT b'0',
  `sold_time` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `buyer_id`(`buyer_id`) USING BTREE,
  INDEX `seller_id`(`seller_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  CONSTRAINT `sells_ibfk_1` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sells_ibfk_2` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sells
-- ----------------------------

-- ----------------------------
-- Table structure for sessions
-- ----------------------------
DROP TABLE IF EXISTS `sessions`;
CREATE TABLE `sessions`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `userid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `creation_time` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `hash`(`hash`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  CONSTRAINT `sessions_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sessions
-- ----------------------------
INSERT INTO `sessions` VALUES (6, 'developeradmin', 'node0w3xtr5rc9j551bhjsorv32wq90', 1656832334777);
INSERT INTO `sessions` VALUES (7, 'developeradmin', 'node0tzhxv7ej14a2htnraghdw2291', 1656832449803);
INSERT INTO `sessions` VALUES (9, 'nichoshop', 'node0y4oh75ahu0k41o4w7y9c7lia00', 1659003582182);

-- ----------------------------
-- Table structure for specifics
-- ----------------------------
DROP TABLE IF EXISTS `specifics`;
CREATE TABLE `specifics`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `value_type` enum('string','int') CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'string',
  `value_options` blob NULL,
  `default_value` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `created` bigint UNSIGNED NOT NULL,
  `required` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of specifics
-- ----------------------------
INSERT INTO `specifics` VALUES (1, '123', 'string', NULL, '123123', 123, NULL);

-- ----------------------------
-- Table structure for sucs
-- ----------------------------
DROP TABLE IF EXISTS `sucs`;
CREATE TABLE `sucs`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int UNSIGNED NOT NULL,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `created_at` date NOT NULL,
  `suc_type` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `sucs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sucs
-- ----------------------------
INSERT INTO `sucs` VALUES (41, 1, '110346', '2022-08-16', 1);
INSERT INTO `sucs` VALUES (42, 1, '863470', '2022-08-08', 2);
INSERT INTO `sucs` VALUES (43, 1, '940516', '2022-08-06', 4);
INSERT INTO `sucs` VALUES (44, 1, '757035', '2022-07-31', 4);
INSERT INTO `sucs` VALUES (70, 1, '125383', '2022-08-18', 4);
INSERT INTO `sucs` VALUES (71, 1, '319606', '2022-08-18', 4);
INSERT INTO `sucs` VALUES (72, 1, '150179', '2022-08-18', 4);
INSERT INTO `sucs` VALUES (73, 1, '912099', '2022-08-19', 4);
INSERT INTO `sucs` VALUES (74, 1, '537994', '2022-08-19', 4);
INSERT INTO `sucs` VALUES (75, 1, '632971', '2022-08-19', 4);
INSERT INTO `sucs` VALUES (76, 40, '175704', '2022-08-24', 2);
INSERT INTO `sucs` VALUES (77, 40, '563104', '2022-08-24', 4);

-- ----------------------------
-- Table structure for tokens
-- ----------------------------
DROP TABLE IF EXISTS `tokens`;
CREATE TABLE `tokens`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `userid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `creation_time` bigint NOT NULL,
  `hash_session` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `hash`(`hash`) USING BTREE,
  UNIQUE INDEX `hash_session`(`hash_session`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  CONSTRAINT `tokens_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tokens
-- ----------------------------
INSERT INTO `tokens` VALUES (1, 'developeradmin', '8f4b87554efbf4a6e938939617a503ceae9cb347abdfb37abb85f7f8a71185d9', 1653704344342, '7795478cb25cd7599523f70b622501de3a77c4a779ccf19a2ec619682111c739');

-- ----------------------------
-- Table structure for tracking
-- ----------------------------
DROP TABLE IF EXISTS `tracking`;
CREATE TABLE `tracking`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `sell_id` int UNSIGNED NOT NULL,
  `number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `courier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sell_id`(`sell_id`) USING BTREE,
  CONSTRAINT `tracking_ibfk_1` FOREIGN KEY (`sell_id`) REFERENCES `sells` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tracking
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  `email_confirmed` bigint NOT NULL DEFAULT 0,
  `locked` bit(1) NOT NULL DEFAULT b'0',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `deleted_date` bigint UNSIGNED NULL DEFAULT NULL,
  `address_id` int UNSIGNED NULL DEFAULT NULL,
  `from_address_id` int UNSIGNED NULL DEFAULT NULL,
  `to_address_id` int UNSIGNED NULL DEFAULT NULL,
  `return_address_id` int UNSIGNED NULL DEFAULT NULL,
  `payment_address_id` int UNSIGNED NULL DEFAULT NULL,
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `account_type` tinyint NULL DEFAULT NULL,
  `business_id` int NULL DEFAULT NULL,
  `phone_confirmed` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userid`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'developeradmin', '$2a$10$UdP8myyly0OWAb7WRnbHYeqZ3yEgnq19AmYU4qwI0e2nJEq7DFt9S', 'nursultansaudirbaev157@gmail.com', 'Nursultan', 'Saudirbaev', '2022-08-19 22:26:03', 1, b'0', b'0', NULL, 1, 1, 1, 1, NULL, '12525912005', 0, 1, 1);
INSERT INTO `users` VALUES (40, 'nichoshop', '$2a$10$iFWoYQNuIbwJTBxw3srSiONW7sOlQhn4jK1LsoOOlF4zc2pIZfFke', 'younglady19931114@gmail.com', 'Noki', 'Nikolov', '2022-08-24 02:58:52', 0, b'0', b'0', NULL, 13, 13, 13, 13, NULL, '19852362031', 0, NULL, 0);
INSERT INTO `users` VALUES (61, 'Davids.Rich96194us', '$2a$10$DcNtMSUtK8uiKzc.unEfVO0ZvU5WeYRwBFVmQy7qTjvp.V41cWDy6', 'richarddavidson1210@gmail.com', 'Richard', 'Davidson', '2022-08-19 17:02:42', 0, b'0', b'0', NULL, NULL, NULL, NULL, NULL, NULL, '19852362031', 1, NULL, 0);

-- ----------------------------
-- Table structure for variants
-- ----------------------------
DROP TABLE IF EXISTS `variants`;
CREATE TABLE `variants`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id` int UNSIGNED NOT NULL,
  `title` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `condition` enum('new','used') CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'new',
  `price` int UNSIGNED NOT NULL,
  `currency_id` int UNSIGNED NOT NULL,
  `amount` int UNSIGNED NOT NULL,
  `creation_time` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of variants
-- ----------------------------

-- ----------------------------
-- Table structure for watchlist
-- ----------------------------
DROP TABLE IF EXISTS `watchlist`;
CREATE TABLE `watchlist`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int UNSIGNED NOT NULL,
  `product_id` int UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `product_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  CONSTRAINT `watchlist_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of watchlist
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
