/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : wechatoffical

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 25/09/2020 23:10:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_helpchop
-- ----------------------------
DROP TABLE IF EXISTS `t_helpchop`;
CREATE TABLE `t_helpchop`  (
  `openId` int(11) NOT NULL COMMENT '谁发起的砍价',
  `frendsopenId` int(11) NOT NULL COMMENT '朋友的openId',
  `createTime` datetime(0) NOT NULL COMMENT '帮忙砍价开始时间',
  PRIMARY KEY (`openId`, `frendsopenId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_initiatebargaining
-- ----------------------------
DROP TABLE IF EXISTS `t_initiatebargaining`;
CREATE TABLE `t_initiatebargaining`  (
  `faQiRenOpenId` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发起砍价ID',
  `bangKanOpenId` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '朋友的openId',
  `num` int(6) NOT NULL COMMENT '剩余砍价刀数',
  `price` decimal(10, 2) NOT NULL COMMENT '剩余可砍钱数',
  `createTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发起砍价时间',
  PRIMARY KEY (`faQiRenOpenId`, `bangKanOpenId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_initiatebargaining
-- ----------------------------
INSERT INTO `t_initiatebargaining` VALUES ('o4rBH5ncAXFCmheadFPxqpCy-gF0', 'o4rBH5kuOmHuhqnQf6pcykRVC4QQ', 1, 0.51, '2020-09-20 16:57:49');
INSERT INTO `t_initiatebargaining` VALUES ('o4rBH5ncAXFCmheadFPxqpCy-gF0', 'o4rBH5ncAXFCmheadFPxqpCy-gF0', 0, 1.54, '2020-09-20 16:43:37');
INSERT INTO `t_initiatebargaining` VALUES ('o4rBH5ncAXFCmheadFPxqpCy-gF0', 'o4rBH5v8T5rQr5jzjhpEfNbc4bEY', 1, 3.46, '2020-09-23 22:57:16');

-- ----------------------------
-- Table structure for t_wechat_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat_userinfo`;
CREATE TABLE `t_wechat_userinfo`  (
  `openid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'openId',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `headimgurl` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标地址',
  `city` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属城市',
  `country` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属国家',
  `province` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属省份',
  `language` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `privilege` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'privilege',
  `createtime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建时间',
  `numberOfVisits` int(11) NULL DEFAULT NULL COMMENT '该用户访问次数',
  PRIMARY KEY (`openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_wechat_userinfo
-- ----------------------------
INSERT INTO `t_wechat_userinfo` VALUES ('o4rBH5kuOmHuhqnQf6pcykRVC4QQ', '成文', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJxicwP7DDJ2jTyp0axpCMuQQzBCEWWYXRgga26w6ulictGAklM5F3FGt85wicdxicslMB0M1GEadsrCQ/132', '随州', '中国', '湖北', 'zh_CN', '1', '[]', '2020-09-20 13:30:14', 3);
INSERT INTO `t_wechat_userinfo` VALUES ('o4rBH5ncAXFCmheadFPxqpCy-gF0', '谢兵', 'https://thirdwx.qlogo.cn/mmopen/vi_32/uSS2XfwfyiaaticL8rpfSdsY5BJPVBGxFabVn0rKS33tJhvD3FF3swjnt2W5ibBic1TCWWfO45KibStLJ8WonXOZUUA/132', '武汉', '中国', '湖北', 'zh_CN', '1', '[]', '2020-09-20 12:50:35', 92);
INSERT INTO `t_wechat_userinfo` VALUES ('o4rBH5v8T5rQr5jzjhpEfNbc4bEY', '书语英语部~姜老师', 'https://thirdwx.qlogo.cn/mmopen/vi_32/kLVU3zViaetdicQxsC6ErRMLg4Xbxs7NasQULQC3aM99JDSSpU7R5w3Gm76wlicjVXXLPicPszdU8gzibyhMibrlY3TA/132', '十堰', '中国', '湖北', 'zh_CN', '2', '[]', '2020-09-23 22:56:53', 3);

SET FOREIGN_KEY_CHECKS = 1;
