/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : index_evaluation

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 15/04/2019 19:47:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_college
-- ----------------------------
DROP TABLE IF EXISTS `tb_college`;
CREATE TABLE `tb_college`  (
  `college_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学院id',
  `college_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`college_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_college
-- ----------------------------
INSERT INTO `tb_college` VALUES (21, '生命科技学院');
INSERT INTO `tb_college` VALUES (22, '经济与管理学院');
INSERT INTO `tb_college` VALUES (23, '机电学院');
INSERT INTO `tb_college` VALUES (24, '食品学院');
INSERT INTO `tb_college` VALUES (25, '动物科技学院');
INSERT INTO `tb_college` VALUES (26, '园艺园林学院');
INSERT INTO `tb_college` VALUES (27, '资源与环境学院');
INSERT INTO `tb_college` VALUES (28, '信息工程学院');
INSERT INTO `tb_college` VALUES (29, '化学化工学院');
INSERT INTO `tb_college` VALUES (30, '文法学院');
INSERT INTO `tb_college` VALUES (31, '教育科学学院');
INSERT INTO `tb_college` VALUES (32, '艺术学院');
INSERT INTO `tb_college` VALUES (33, '服装学院');
INSERT INTO `tb_college` VALUES (34, '数学科学学院');
INSERT INTO `tb_college` VALUES (35, '外国语学院');
INSERT INTO `tb_college` VALUES (36, '体育学院');

-- ----------------------------
-- Table structure for tb_e_practice
-- ----------------------------
DROP TABLE IF EXISTS `tb_e_practice`;
CREATE TABLE `tb_e_practice`  (
  `employment_practice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '就业创业实践指数id',
  `college` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '学院',
  `m11` double NULL DEFAULT NULL COMMENT '“参赛人数比-1(职业生\r\n涯规划大赛\"',
  `m12` double NULL DEFAULT NULL COMMENT '\"参赛人数比-1\r\n (创业大赛）\"',
  `m13` double NULL DEFAULT NULL COMMENT '\"参赛人数比2\r\n -省创业大赛总人数\"',
  `people_number` double NULL DEFAULT NULL COMMENT 'M1: 参赛人数比47.5',
  `m21` double NULL DEFAULT NULL COMMENT '\"获奖质量积分-1(生\r\n涯规划大赛\"',
  `m22` double NULL DEFAULT NULL COMMENT '\"获奖质量积分-1\r\n (创业大赛）\"',
  `quality` double NULL DEFAULT NULL COMMENT 'M2: 获奖质量比52.5',
  `participation_status` double NULL DEFAULT NULL COMMENT '参赛状态39',
  `m31` double NULL DEFAULT NULL COMMENT '项目数量',
  `project_number` double NULL DEFAULT NULL COMMENT 'M3: 项目数量比47',
  `m41` double NULL DEFAULT NULL COMMENT '项目质量',
  `project_quality` double NULL DEFAULT NULL COMMENT 'M4: 项目质量比53',
  `venture_project` double NULL DEFAULT NULL COMMENT '创业项目28.5',
  `featured_work` double NULL DEFAULT NULL COMMENT '特色工作32.5',
  `practice` double NULL DEFAULT NULL COMMENT '就业创业实践指数',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  PRIMARY KEY (`employment_practice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 625 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_e_practice
-- ----------------------------
INSERT INTO `tb_e_practice` VALUES (593, '生命科技学院', 1, 29, 14, 0.7002, 1.5, 10, 0.7875, 58.0215, 1, 0.094, 2, 0.106, 5.7, 0, 9.7812, 2020);
INSERT INTO `tb_e_practice` VALUES (594, '经济与管理学院', 1, 13, 0, 0.4908, 1, 0, 0.175, 25.9659, 3, 0.282, 3, 0.159, 12.5685, 0, 5.915, 2020);
INSERT INTO `tb_e_practice` VALUES (595, '机电学院', 1, 14, 0, 0.492, 1, 0, 0.175, 26.0133, 1, 0.094, 2, 0.106, 5.7, 0, 4.868, 2020);
INSERT INTO `tb_e_practice` VALUES (596, '食品学院', 1, 34, 0, 0.5163, 0, 0, 0, 20.1359, 4, 0.376, 5, 0.265, 18.2685, 0, 5.8951, 2020);
INSERT INTO `tb_e_practice` VALUES (597, '动物科技学院', 1, 0, 1, 0.4886, 1, 0.42, 0.1971, 26.7392, 1.2, 0.1128, 7, 0.371, 13.7883, 0, 6.221, 2020);
INSERT INTO `tb_e_practice` VALUES (598, '园艺园林学院', 1, 19, 0, 0.4981, 1.5, 0, 0.2625, 29.6627, 1, 0.094, 1, 0.053, 4.1895, 0, 5.1963, 2020);
INSERT INTO `tb_e_practice` VALUES (599, '资源与环境学院', 1, 67, 14, 0.7464, 3, 9.3, 1.0133, 68.6261, 4, 0.376, 5, 0.265, 18.2685, 0, 13.3383, 2020);
INSERT INTO `tb_e_practice` VALUES (600, '信息工程学院', 1, 61, 4, 0.6034, 1.5, 4.5, 0.4987, 42.9835, 5, 0.47, 10, 0.53, 28.5, 0, 10.9727, 2020);
INSERT INTO `tb_e_practice` VALUES (601, '化学化工学院', 1, 8, 1, 0.4983, 0, 0.5, 0.0263, 20.4571, 3, 0.282, 5, 0.265, 15.5895, 0, 5.5331, 2020);
INSERT INTO `tb_e_practice` VALUES (602, '艺术学院', 1, 10, 0, 0.4871, 2, 0, 0.35, 32.6488, 1.2, 0.1128, 4.8, 0.2544, 10.4652, 0, 6.618, 2020);
INSERT INTO `tb_e_practice` VALUES (603, '服装学院', 1, 23, 1, 0.5165, 0, 0.5, 0.0263, 21.1677, 3, 0.282, 6, 0.318, 17.1, 0, 5.8741, 2020);
INSERT INTO `tb_e_practice` VALUES (604, '文法学院', 1, 11, 0, 0.4884, 1, 0, 0.175, 25.8712, 0, 0, 0, 0, 0, 0, 3.9712, 2020);
INSERT INTO `tb_e_practice` VALUES (605, '教育科学学院', 1, 0, 0, 0.475, 1, 0, 0.175, 25.35, 0, 0, 0, 0, 0, 0, 3.8912, 2020);
INSERT INTO `tb_e_practice` VALUES (606, '数学科学学院', 1, 72, 0, 0.5625, 0, 0, 0, 21.9363, 1, 0.094, 2, 0.106, 5.7, 0, 4.2422, 2020);
INSERT INTO `tb_e_practice` VALUES (607, '外国语学院', 1, 6, 0, 0.4823, 1.5, 0, 0.2625, 29.0468, 0, 0, 0, 0, 0, 0, 4.4587, 2020);
INSERT INTO `tb_e_practice` VALUES (608, '体育学院', 1, 24, 0, 0.5042, 0, 0, 0, 19.6621, 0, 0, 0, 0, 0, 0, 3.0181, 2020);
INSERT INTO `tb_e_practice` VALUES (609, '生命科技学院', 1, 29, 14, 0.7002, 1.5, 10, 0.7875, 58.0215, 1, 0.094, 2, 0.106, 5.7, 0, 9.7812, 2018);
INSERT INTO `tb_e_practice` VALUES (610, '经济与管理学院', 1, 13, 0, 0.4908, 1, 0, 0.175, 25.9659, 3, 0.282, 3, 0.159, 12.5685, 0, 5.915, 2018);
INSERT INTO `tb_e_practice` VALUES (611, '机电学院', 1, 14, 0, 0.492, 1, 0, 0.175, 26.0133, 1, 0.094, 2, 0.106, 5.7, 0, 4.868, 2018);
INSERT INTO `tb_e_practice` VALUES (612, '食品学院', 1, 34, 0, 0.5163, 0, 0, 0, 20.1359, 4, 0.376, 5, 0.265, 18.2685, 0, 5.8951, 2018);
INSERT INTO `tb_e_practice` VALUES (613, '动物科技学院', 1, 0, 1, 0.4886, 1, 0.42, 0.1971, 26.7392, 1.2, 0.1128, 7, 0.371, 13.7883, 0, 6.221, 2018);
INSERT INTO `tb_e_practice` VALUES (614, '园艺园林学院', 1, 19, 0, 0.4981, 1.5, 0, 0.2625, 29.6627, 1, 0.094, 1, 0.053, 4.1895, 0, 5.1963, 2018);
INSERT INTO `tb_e_practice` VALUES (615, '资源与环境学院', 1, 67, 14, 0.7464, 3, 9.3, 1.0133, 68.6261, 4, 0.376, 5, 0.265, 18.2685, 0, 13.3383, 2018);
INSERT INTO `tb_e_practice` VALUES (616, '信息工程学院', 1, 61, 4, 0.6034, 1.5, 4.5, 0.4987, 42.9835, 5, 0.47, 10, 0.53, 28.5, 0, 10.9727, 2018);
INSERT INTO `tb_e_practice` VALUES (617, '化学化工学院', 1, 8, 1, 0.4983, 0, 0.5, 0.0263, 20.4571, 3, 0.282, 5, 0.265, 15.5895, 0, 5.5331, 2018);
INSERT INTO `tb_e_practice` VALUES (618, '艺术学院', 1, 10, 0, 0.4871, 2, 0, 0.35, 32.6488, 1.2, 0.1128, 4.8, 0.2544, 10.4652, 0, 6.618, 2018);
INSERT INTO `tb_e_practice` VALUES (619, '服装学院', 1, 23, 1, 0.5165, 0, 0.5, 0.0263, 21.1677, 3, 0.282, 6, 0.318, 17.1, 0, 5.8741, 2018);
INSERT INTO `tb_e_practice` VALUES (620, '文法学院', 1, 11, 0, 0.4884, 1, 0, 0.175, 25.8712, 0, 0, 0, 0, 0, 0, 3.9712, 2018);
INSERT INTO `tb_e_practice` VALUES (621, '教育科学学院', 1, 0, 0, 0.475, 1, 0, 0.175, 25.35, 0, 0, 0, 0, 0, 0, 3.8912, 2018);
INSERT INTO `tb_e_practice` VALUES (622, '数学科学学院', 1, 72, 0, 0.5625, 0, 0, 0, 21.9363, 1, 0.094, 2, 0.106, 5.7, 0, 4.2422, 2018);
INSERT INTO `tb_e_practice` VALUES (623, '外国语学院', 1, 6, 0, 0.4823, 1.5, 0, 0.2625, 29.0468, 0, 0, 0, 0, 0, 0, 4.4587, 2018);
INSERT INTO `tb_e_practice` VALUES (624, '体育学院', 1, 24, 0, 0.5042, 0, 0, 0, 19.6621, 0, 0, 0, 0, 0, 0, 3.0181, 2018);

-- ----------------------------
-- Table structure for tb_e_rate
-- ----------------------------
DROP TABLE IF EXISTS `tb_e_rate`;
CREATE TABLE `tb_e_rate`  (
  `employment_rate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '就业指数id',
  `college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '学院',
  `first_employment_rate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '初次就业率',
  `last_employment_rate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '年终就业率',
  `first_index` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '初次就业率指数',
  `last_index` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '年终就业率指数',
  `employment_rate_index` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最终就业率指数',
  `year` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`employment_rate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 369 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_e_rate
-- ----------------------------
INSERT INTO `tb_e_rate` VALUES (353, '生命科技学院', '0.9123', '0.9081', '22.7619', '53.2147', '18.9562', 2018);
INSERT INTO `tb_e_rate` VALUES (354, '经济与管理学院', '0.9240', '0.9154', '23.0538', '53.6424', '19.1357', 2018);
INSERT INTO `tb_e_rate` VALUES (355, '机电学院', '0.9587', '0.9742', '23.9196', '57.0881', '20.2114', 2018);
INSERT INTO `tb_e_rate` VALUES (356, '食品学院', '0.9271', '0.9605', '23.1311', '56.2853', '19.8144', 2018);
INSERT INTO `tb_e_rate` VALUES (357, '动物科技学院', '0.9705', '0.9794', '24.2140', '57.3928', '20.3609', 2018);
INSERT INTO `tb_e_rate` VALUES (358, '园艺园林学院', '0.9607', '0.9547', '23.9695', '55.9454', '19.9388', 2018);
INSERT INTO `tb_e_rate` VALUES (359, '资源与环境学院', '0.9471', '0.9471', '23.6301', '55.5001', '19.7430', 2018);
INSERT INTO `tb_e_rate` VALUES (360, '信息工程学院', '0.9310', '0.9310', '23.2285', '54.5566', '19.4074', 2018);
INSERT INTO `tb_e_rate` VALUES (361, '化学化工学院', '0.9241', '0.9420', '23.0563', '55.2012', '19.5252', 2018);
INSERT INTO `tb_e_rate` VALUES (362, '文法学院', '0.9745', '0.8314', '24.3138', '48.7200', '18.2219', 2018);
INSERT INTO `tb_e_rate` VALUES (363, '教育科学学院', '0.9531', '0.9193', '23.7798', '53.8710', '19.3739', 2018);
INSERT INTO `tb_e_rate` VALUES (364, '艺术学院', '0.8084', '0.9708', '20.1696', '56.8889', '19.2261', 2018);
INSERT INTO `tb_e_rate` VALUES (365, '服装学院', '0.7888', '0.9609', '19.6806', '56.3087', '18.9593', 2018);
INSERT INTO `tb_e_rate` VALUES (366, '数学科学学院', '0.9583', '0.9444', '23.9096', '55.3418', '19.7732', 2018);
INSERT INTO `tb_e_rate` VALUES (367, '外国语学院', '0.8670', '0.8541', '21.6317', '50.0503', '17.8847', 2018);
INSERT INTO `tb_e_rate` VALUES (368, '体育学院', '0.9371', '0.9580', '23.3806', '56.1388', '19.8401', 2018);

-- ----------------------------
-- Table structure for tb_emp
-- ----------------------------
DROP TABLE IF EXISTS `tb_emp`;
CREATE TABLE `tb_emp`  (
  `em_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '就业状态ID',
  `college` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学院',
  `par_num` int(11) NULL DEFAULT NULL COMMENT '参与调查人数',
  `m_b1111` int(11) NULL DEFAULT NULL COMMENT '专业知识能力：非常强',
  `m_b1112` int(11) NULL DEFAULT NULL COMMENT '专业知识能力：很强',
  `m_b1113` int(11) NULL DEFAULT NULL COMMENT '专业知识能力：一般',
  `m_b1114` int(11) NULL DEFAULT NULL COMMENT '专业知识能力：不强',
  `m_b1115` int(11) NULL DEFAULT NULL COMMENT '专业知识能力：很不强',
  `m_b1121` int(11) NULL DEFAULT NULL COMMENT '通用知识能力：非常强',
  `m_b1122` int(11) NULL DEFAULT NULL COMMENT '通用知识能力：很强',
  `m_b1123` int(11) NULL DEFAULT NULL COMMENT '通用知识能力：一般',
  `m_b1124` int(11) NULL DEFAULT NULL COMMENT '通用知识能力：不强',
  `m_b1125` int(11) NULL DEFAULT NULL COMMENT '通用知识能力：很不强',
  `m_b1131` int(11) NULL DEFAULT NULL COMMENT '求职应聘能力：非常强',
  `m_b1132` int(11) NULL DEFAULT NULL COMMENT '求职应聘能力：很强',
  `m_b1133` int(11) NULL DEFAULT NULL COMMENT '求职应聘能力：一般',
  `m_b1134` int(11) NULL DEFAULT NULL COMMENT '求职应聘能力：不强',
  `m_b1135` int(11) NULL DEFAULT NULL COMMENT '求职应聘能力：很不强',
  `m_b1211` int(11) NULL DEFAULT NULL COMMENT '社会兼职经历：3个月以上',
  `m_b1212` int(11) NULL DEFAULT NULL COMMENT '社会兼职经历：2个月',
  `m_b1213` int(11) NULL DEFAULT NULL COMMENT '社会兼职经历：1个月',
  `m_b1214` int(11) NULL DEFAULT NULL COMMENT '社会兼职经历：半月',
  `m_b1215` int(11) NULL DEFAULT NULL COMMENT '社会兼职经历：1周',
  `m_b1221` int(11) NULL DEFAULT NULL COMMENT '“非学历、费荣誉”证书：3个以上',
  `m_b1222` int(11) NULL DEFAULT NULL COMMENT '“非学历、费荣誉”证书：3个',
  `m_b1223` int(11) NULL DEFAULT NULL COMMENT '“非学历、费荣誉”证书：2个',
  `m_b1224` int(11) NULL DEFAULT NULL COMMENT '“非学历、费荣誉”证书：1个',
  `m_b1225` int(11) NULL DEFAULT NULL COMMENT '“非学历、费荣誉”证书：0个',
  `m_b1231` int(11) NULL DEFAULT NULL COMMENT '社会职务：有',
  `m_b1232` int(11) NULL DEFAULT NULL COMMENT '社会职务：无',
  `m_b1311` int(11) NULL DEFAULT NULL COMMENT '求职积极程度：很积极',
  `m_b1312` int(11) NULL DEFAULT NULL COMMENT '求职积极程度：积极',
  `m_b1313` int(11) NULL DEFAULT NULL COMMENT '求职积极程度：一般',
  `m_b1314` int(11) NULL DEFAULT NULL COMMENT '求职积极程度：不积极',
  `m_b1315` int(11) NULL DEFAULT NULL COMMENT '求职积极程度：很不积极',
  `m_b1321` int(11) NULL DEFAULT NULL COMMENT '自我效能感人数：很自信',
  `m_b1322` int(11) NULL DEFAULT NULL COMMENT '自我效能感人数：自信',
  `m_b1323` int(11) NULL DEFAULT NULL COMMENT '自我效能感人数：一般',
  `m_b1324` int(11) NULL DEFAULT NULL COMMENT '自我效能感人数：不自信',
  `m_b1325` int(11) NULL DEFAULT NULL COMMENT '自我效能感人数：很不自信',
  `m_b211` int(11) NULL DEFAULT NULL COMMENT '就业起薪：2001-3000',
  `m_b212` int(11) NULL DEFAULT NULL COMMENT '就业起薪：3001-4000',
  `m_b213` int(11) NULL DEFAULT NULL COMMENT '就业起薪：4001-5000',
  `m_b214` int(11) NULL DEFAULT NULL COMMENT '就业起薪：5001-6000',
  `m_b215` int(11) NULL DEFAULT NULL COMMENT '就业起薪：6001-7000',
  `m_b216` int(11) NULL DEFAULT NULL COMMENT '就业起薪：7000以上',
  `m_b2211` int(11) NULL DEFAULT NULL COMMENT '专业对口状态：很对口',
  `m_b2212` int(11) NULL DEFAULT NULL COMMENT '专业对口状态：对口',
  `m_b2213` int(11) NULL DEFAULT NULL COMMENT '专业对口状态：一般',
  `m_b2214` int(11) NULL DEFAULT NULL COMMENT '专业对口状态：不对口',
  `m_b2215` int(11) NULL DEFAULT NULL COMMENT '专业对口状态：很不对口',
  `m_b2221` int(11) NULL DEFAULT NULL COMMENT '“能力-岗位”适配度：很匹配',
  `m_b2222` int(11) NULL DEFAULT NULL COMMENT '“能力-岗位”适配度：匹配',
  `m_b2223` int(11) NULL DEFAULT NULL COMMENT '“能力-岗位”适配度：一般',
  `m_b2224` int(11) NULL DEFAULT NULL COMMENT '“能力-岗位”适配度：不匹配',
  `m_b2225` int(11) NULL DEFAULT NULL COMMENT '“能力-岗位”适配度：很不匹配',
  `m_b2311` int(11) NULL DEFAULT NULL COMMENT '月薪兑付状态：正常',
  `m_b2312` int(11) NULL DEFAULT NULL COMMENT '月薪兑付状态：拖欠',
  `m_b2321` int(11) NULL DEFAULT NULL COMMENT '“五险一金”执行状态：正常',
  `m_b2322` int(11) NULL DEFAULT NULL COMMENT '“五险一金”执行状态：拖欠',
  `m_b2331` int(11) NULL DEFAULT NULL COMMENT '成长发展空间：很宽广',
  `m_b2332` int(11) NULL DEFAULT NULL COMMENT '成长发展空间：宽广',
  `m_b2333` int(11) NULL DEFAULT NULL COMMENT '成长发展空间：一般',
  `m_b2334` int(11) NULL DEFAULT NULL COMMENT '成长发展空间：不宽广',
  `m_b2335` int(11) NULL DEFAULT NULL COMMENT '成长发展空间：很不宽广',
  `m_b2341` int(11) NULL DEFAULT NULL COMMENT '工作满意度：很满意',
  `m_b2342` int(11) NULL DEFAULT NULL COMMENT '工作满意度：满意',
  `m_b2343` int(11) NULL DEFAULT NULL COMMENT '工作满意度：一般',
  `m_b2344` int(11) NULL DEFAULT NULL COMMENT '工作满意度：不满意',
  `m_b2345` int(11) NULL DEFAULT NULL COMMENT '工作满意度：很不满意',
  `m_b241` int(11) NULL DEFAULT NULL COMMENT '预期就业年限：10年以上',
  `m_b242` int(11) NULL DEFAULT NULL COMMENT '预期就业年限：8-10年',
  `m_b243` int(11) NULL DEFAULT NULL COMMENT '预期就业年限：3-7年',
  `m_b244` int(11) NULL DEFAULT NULL COMMENT '预期就业年限：2年',
  `m_b245` int(11) NULL DEFAULT NULL COMMENT '预期就业年限：1年',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `b11` double NULL DEFAULT NULL COMMENT '知识能力结构40.75',
  `b12` double NULL DEFAULT NULL COMMENT '标识性优势31.35',
  `b13` double NULL DEFAULT NULL COMMENT '择业精神27.9',
  `b21` double NULL DEFAULT NULL COMMENT '就业起薪28.55',
  `b22` double NULL DEFAULT NULL COMMENT '岗位胜任度24.2',
  `b23` double NULL DEFAULT NULL COMMENT '就业现状满意度28',
  `b24` double NULL DEFAULT NULL COMMENT '预期就业年限19.25',
  `a1` double NULL DEFAULT NULL COMMENT '个体就业潜力44.8',
  `a2` double NULL DEFAULT NULL COMMENT '个体就业表现55.2',
  `employment_status` double NULL DEFAULT NULL COMMENT '就业状态指数25.3',
  PRIMARY KEY (`em_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 952 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_emp
-- ----------------------------
INSERT INTO `tb_emp` VALUES (936, '生命科技学院', 334, 24, 73, 221, 12, 4, 23, 24, 208, 5, 2, 27, 87, 198, 21, 1, 134, 51, 79, 32, 38, 89, 44, 65, 50, 86, 152, 182, 63, 85, 40, 0, 0, 50, 89, 48, 2, 0, 0, 0, 0, 0, 0, 0, 17, 48, 49, 38, 36, 24, 90, 68, 3, 3, 182, 6, 138, 1, 27, 78, 68, 12, 3, 22, 84, 71, 7, 4, 11, 2, 49, 57, 69, 2018, 24.7013, 18.77, 22.6288, 9.03, 15.9232, 23.6739, 8.0481, 29.6129, 27.7232, 14.506);
INSERT INTO `tb_emp` VALUES (937, '经济与管理学院', 413, 30, 108, 255, 15, 5, 33, 34, 268, 4, 1, 38, 110, 246, 18, 1, 124, 66, 118, 51, 54, 147, 63, 99, 61, 43, 221, 192, 91, 127, 49, 1, 0, 60, 130, 70, 5, 1, 0, 0, 0, 0, 0, 0, 53, 94, 80, 36, 5, 41, 120, 104, 3, 0, 263, 5, 213, 6, 49, 103, 100, 15, 1, 35, 110, 112, 9, 2, 22, 5, 61, 100, 80, 2018, 25.4247, 20.0042, 22.4899, 9, 17.7283, 23.7998, 8.5188, 30.4277, 29.0443, 15.0464);
INSERT INTO `tb_emp` VALUES (938, '机电学院', 251, 19, 62, 162, 5, 3, 19, 19, 151, 1, 1, 16, 75, 149, 9, 2, 103, 41, 44, 29, 34, 65, 34, 66, 41, 45, 98, 153, 70, 77, 32, 0, 0, 51, 85, 40, 2, 1, 0, 0, 0, 0, 0, 0, 27, 62, 64, 19, 7, 25, 98, 53, 2, 1, 170, 9, 155, 3, 38, 73, 61, 7, 0, 26, 67, 85, 1, 0, 11, 4, 48, 71, 45, 2018, 24.7884, 18.2952, 22.9704, 9, 17.631, 23.8069, 8.6464, 29.5922, 29.0649, 14.8402);
INSERT INTO `tb_emp` VALUES (939, '食品学院', 197, 11, 39, 145, 2, 0, 13, 14, 136, 5, 0, 12, 51, 116, 16, 2, 100, 23, 33, 16, 25, 65, 28, 39, 27, 38, 114, 83, 48, 61, 18, 0, 0, 26, 60, 35, 4, 0, 0, 0, 0, 0, 0, 0, 23, 29, 25, 39, 11, 16, 57, 49, 4, 1, 124, 3, 97, 2, 17, 47, 58, 4, 1, 17, 45, 59, 5, 1, 9, 2, 27, 33, 56, 2018, 25.142, 20.9504, 22.5913, 9.03, 16.4354, 23.5873, 7.7606, 30.7703, 27.7995, 14.8182);
INSERT INTO `tb_emp` VALUES (940, '动物科技学院', 242, 8, 54, 165, 13, 2, 11, 12, 156, 4, 2, 13, 57, 159, 11, 2, 102, 39, 37, 27, 37, 66, 35, 44, 33, 64, 132, 110, 57, 76, 23, 1, 0, 42, 89, 25, 2, 0, 0, 0, 0, 0, 0, 0, 46, 44, 35, 22, 10, 25, 94, 37, 0, 1, 155, 2, 127, 1, 34, 74, 46, 2, 1, 31, 69, 51, 5, 1, 22, 3, 44, 34, 54, 2018, 23.8885, 19.6348, 23.1155, 9, 18.1954, 24.4772, 9.2204, 29.8542, 30.0633, 15.1591);
INSERT INTO `tb_emp` VALUES (941, '园艺园林学院', 294, 33, 69, 181, 10, 1, 31, 32, 170, 12, 2, 30, 91, 156, 16, 1, 69, 49, 101, 55, 20, 38, 31, 112, 58, 55, 211, 83, 38, 55, 32, 2, 0, 32, 44, 46, 13, 0, 0, 0, 0, 0, 0, 0, 34, 39, 42, 8, 4, 26, 49, 50, 2, 0, 120, 7, 91, 6, 25, 49, 48, 5, 0, 27, 40, 58, 2, 0, 9, 2, 32, 49, 35, 2018, 26.016, 20.3055, 21.5303, 9.03, 18.1482, 23.5462, 8.5488, 30.3976, 29.1574, 15.0674);
INSERT INTO `tb_emp` VALUES (942, '资源与环境学院', 250, 7, 47, 189, 6, 1, 14, 15, 180, 5, 0, 13, 55, 170, 8, 4, 97, 34, 46, 34, 39, 70, 31, 54, 35, 60, 127, 123, 57, 70, 29, 0, 1, 40, 76, 35, 5, 1, 0, 0, 0, 0, 0, 0, 25, 37, 32, 39, 24, 27, 66, 61, 2, 1, 151, 6, 106, 3, 28, 58, 64, 4, 3, 21, 61, 66, 6, 3, 20, 1, 33, 42, 61, 2018, 24.7895, 19.0534, 22.6174, 9.03, 16.3992, 23.5495, 8.5338, 29.7742, 28.1854, 14.6638);
INSERT INTO `tb_emp` VALUES (943, '信息工程学院', 158, 11, 22, 119, 4, 2, 10, 11, 109, 4, 0, 9, 35, 100, 14, 0, 41, 10, 32, 19, 56, 50, 12, 30, 22, 44, 50, 108, 36, 58, 19, 0, 0, 21, 53, 36, 4, 1, 0, 0, 0, 0, 0, 0, 26, 35, 34, 12, 6, 19, 55, 38, 0, 1, 111, 2, 100, 0, 18, 45, 46, 3, 1, 20, 41, 50, 2, 0, 7, 4, 17, 49, 36, 2018, 24.7949, 15.3954, 22.1005, 9.03, 17.8486, 24.0655, 8.0407, 27.9063, 28.9982, 14.3968);
INSERT INTO `tb_emp` VALUES (944, '化学化工学院', 220, 12, 41, 150, 13, 4, 15, 16, 139, 9, 2, 11, 45, 145, 16, 3, 91, 21, 44, 31, 33, 55, 15, 48, 37, 65, 98, 122, 45, 62, 30, 0, 0, 33, 61, 39, 6, 0, 0, 0, 0, 0, 0, 0, 20, 47, 32, 26, 12, 19, 58, 57, 2, 1, 124, 13, 100, 5, 22, 53, 52, 9, 1, 20, 51, 57, 8, 1, 12, 4, 27, 45, 49, 2018, 24.328, 17.9559, 22.2588, 9.03, 16.8482, 22.9675, 8.3182, 28.9151, 27.9931, 14.3978);
INSERT INTO `tb_emp` VALUES (945, '文法学院', 235, 13, 68, 149, 3, 2, 11, 12, 156, 5, 1, 9, 66, 151, 9, 0, 87, 39, 68, 23, 18, 98, 41, 37, 18, 41, 82, 153, 49, 81, 20, 0, 0, 42, 78, 30, 0, 0, 0, 0, 0, 0, 0, 0, 53, 50, 36, 10, 1, 39, 77, 33, 1, 0, 142, 8, 98, 3, 35, 67, 47, 1, 0, 30, 68, 48, 4, 0, 22, 3, 25, 47, 53, 2018, 24.6538, 18.8948, 23.0801, 9.03, 19.336, 24.1315, 8.8293, 29.8496, 30.2909, 15.2156);
INSERT INTO `tb_emp` VALUES (946, '教育科学学院', 86, 1, 19, 65, 1, 0, 2, 3, 67, 0, 0, 2, 27, 55, 2, 0, 43, 12, 12, 10, 9, 36, 11, 21, 5, 13, 48, 38, 21, 30, 9, 0, 0, 13, 33, 13, 1, 0, 0, 0, 0, 0, 0, 0, 21, 17, 11, 9, 2, 12, 36, 12, 0, 0, 59, 1, 37, 1, 3, 31, 19, 5, 2, 1, 35, 22, 1, 1, 5, 0, 13, 19, 23, 2018, 24.6299, 21.422, 22.7798, 9.03, 18.8241, 23.4362, 8.0208, 30.8366, 29.1783, 15.1838);
INSERT INTO `tb_emp` VALUES (947, '艺术学院', 180, 17, 43, 113, 6, 1, 17, 16, 118, 2, 1, 17, 55, 104, 2, 2, 113, 26, 18, 11, 12, 48, 25, 35, 23, 49, 59, 121, 56, 61, 15, 1, 0, 35, 65, 31, 2, 0, 0, 0, 0, 0, 0, 0, 51, 34, 22, 15, 11, 38, 66, 26, 1, 2, 128, 5, 77, 2, 37, 52, 37, 4, 3, 34, 56, 36, 6, 1, 22, 2, 38, 28, 43, 2018, 26.0395, 18.7774, 23.1316, 9.03, 18.8494, 24.301, 9.5816, 30.4409, 30.5311, 15.4259);
INSERT INTO `tb_emp` VALUES (948, '服装学院', 142, 30, 60, 52, 0, 0, 35, 36, 53, 0, 0, 30, 65, 47, 0, 0, 68, 31, 22, 9, 12, 43, 23, 46, 17, 13, 94, 48, 52, 44, 7, 0, 0, 45, 47, 11, 0, 0, 0, 0, 0, 0, 0, 0, 46, 31, 17, 7, 2, 41, 44, 15, 3, 0, 100, 3, 82, 1, 48, 33, 20, 2, 0, 42, 40, 20, 1, 0, 12, 7, 31, 27, 26, 2018, 29.9618, 22.4949, 24.4575, 9.03, 20.0545, 25.4278, 9.7558, 34.4576, 31.9146, 16.7921);
INSERT INTO `tb_emp` VALUES (949, '数学科学学院', 170, 13, 37, 113, 5, 2, 13, 14, 117, 8, 1, 7, 33, 116, 12, 2, 70, 24, 39, 21, 16, 103, 21, 19, 13, 14, 85, 85, 34, 59, 21, 0, 0, 18, 54, 39, 9, 1, 0, 0, 0, 0, 0, 0, 26, 37, 33, 15, 3, 14, 67, 31, 1, 1, 111, 3, 89, 3, 12, 54, 43, 4, 1, 10, 53, 48, 3, 0, 5, 6, 37, 30, 36, 2018, 25.4175, 21.4317, 21.6594, 9.03, 17.9425, 23.6219, 8.6456, 30.6919, 29.139, 15.1372);
INSERT INTO `tb_emp` VALUES (950, '外国语学院', 172, 2, 22, 138, 7, 3, 4, 5, 134, 2, 0, 3, 36, 121, 12, 0, 83, 23, 35, 19, 12, 81, 34, 27, 4, 26, 62, 110, 31, 45, 30, 1, 2, 18, 51, 36, 3, 0, 0, 0, 0, 0, 0, 0, 34, 31, 32, 9, 3, 21, 53, 33, 1, 1, 103, 6, 67, 2, 10, 35, 56, 8, 0, 10, 46, 48, 4, 1, 10, 1, 14, 25, 59, 2018, 23.8136, 19.906, 21.5173, 9.03, 18.4366, 23.053, 7.2408, 29.2261, 28.3223, 14.5597);
INSERT INTO `tb_emp` VALUES (951, '体育学院', 68, 9, 17, 40, 2, 0, 10, 11, 35, 3, 0, 6, 22, 37, 3, 0, 41, 8, 10, 4, 5, 22, 7, 13, 12, 14, 24, 44, 20, 21, 5, 0, 0, 19, 20, 7, 0, 0, 0, 0, 0, 0, 0, 0, 13, 20, 5, 5, 3, 16, 18, 11, 1, 0, 43, 3, 21, 0, 10, 15, 17, 4, 0, 11, 18, 16, 0, 1, 8, 1, 12, 16, 9, 2018, 26.9183, 19.1528, 23.9562, 9.03, 18.9767, 23.8976, 10.1272, 31.3722, 30.6799, 15.6992);

-- ----------------------------
-- Table structure for tb_es
-- ----------------------------
DROP TABLE IF EXISTS `tb_es`;
CREATE TABLE `tb_es`  (
  `es_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用人单位满意度Id',
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学院',
  `level` double NULL DEFAULT NULL COMMENT '毕业生的精神状态与工作水平',
  `level1` int(11) NULL DEFAULT NULL COMMENT '工作水平非常满意人数',
  `level2` int(11) NULL DEFAULT NULL COMMENT '工作水平满意人数',
  `level3` int(11) NULL DEFAULT NULL COMMENT '工作水平一般人数',
  `level4` int(11) NULL DEFAULT NULL COMMENT '工作水平不满意人数',
  `level5` int(11) NULL DEFAULT NULL COMMENT '工作水平非常不满意人数',
  `ability` double NULL DEFAULT NULL COMMENT '毕业生的综合素质能力',
  `ability1` int(11) NULL DEFAULT NULL COMMENT '素质能力非常满意人数',
  `ability2` int(11) NULL DEFAULT NULL COMMENT '素质能力满意人数',
  `ability3` int(11) NULL DEFAULT NULL COMMENT '素质能力一般人数',
  `ability4` int(11) NULL DEFAULT NULL COMMENT '素质能力不满意人数',
  `ability5` int(11) NULL DEFAULT NULL COMMENT '素质能力非常不满意人数',
  `matched` double NULL DEFAULT NULL COMMENT '毕业生的“能力-岗位”匹配度',
  `match1` int(11) NULL DEFAULT NULL COMMENT '匹配度非常满意人数',
  `match2` int(11) NULL DEFAULT NULL COMMENT '匹配度满意人数',
  `match3` int(11) NULL DEFAULT NULL COMMENT '匹配度一般人数',
  `match4` int(11) NULL DEFAULT NULL COMMENT '匹配度不满意人数',
  `match5` int(11) NULL DEFAULT NULL COMMENT '匹配度非常不满意人数',
  `satisfaction` double NULL DEFAULT NULL COMMENT '对毕业生的工作满意度',
  `satisfaction1` int(11) NULL DEFAULT NULL COMMENT '工作满意度非常满意人数',
  `satisfaction2` int(11) NULL DEFAULT NULL COMMENT '工作满意度满意人数',
  `satisfaction3` int(11) NULL DEFAULT NULL COMMENT '工作满意度一般人数',
  `satisfaction4` int(11) NULL DEFAULT NULL COMMENT '工作满意度不满意人数',
  `satisfaction5` int(11) NULL DEFAULT NULL COMMENT '工作满意度非常不满意人数',
  `satisfaction_index` double NULL DEFAULT NULL COMMENT '用人单位满意度指数',
  `year` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`es_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 641 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_es
-- ----------------------------
INSERT INTO `tb_es` VALUES (609, '生命科技学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (610, '经济与管理学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (611, '机电学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (612, '食品学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (613, '动物科技学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (614, '园艺园林学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (615, '资源与环境学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (616, '信息工程学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (617, '化学化工学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (618, '文法学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (619, '教育科学学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (620, '艺术学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (621, '服装学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (622, '数学科学学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (623, '外国语学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (624, '体育学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2020);
INSERT INTO `tb_es` VALUES (625, '生命科技学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (626, '经济与管理学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (627, '机电学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (628, '食品学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (629, '动物科技学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (630, '园艺园林学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (631, '资源与环境学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (632, '信息工程学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (633, '化学化工学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (634, '文法学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (635, '教育科学学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (636, '艺术学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (637, '服装学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (638, '数学科学学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (639, '外国语学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);
INSERT INTO `tb_es` VALUES (640, '体育学院', 16.4801, 82, 76, 20, 0, 0, 20.9371, 81, 76, 19, 2, 0, 19.075, 74, 82, 21, 1, 0, 16.5014, 82, 77, 19, 0, 0, 9.6717, 2018);

-- ----------------------------
-- Table structure for tb_grade
-- ----------------------------
DROP TABLE IF EXISTS `tb_grade`;
CREATE TABLE `tb_grade`  (
  `grade_id` int(11) NOT NULL AUTO_INCREMENT,
  `college_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `college_grade` double NULL DEFAULT 0,
  `major_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `year` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`grade_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43931 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_major
-- ----------------------------
DROP TABLE IF EXISTS `tb_major`;
CREATE TABLE `tb_major`  (
  `major_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '专业id',
  `major_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '专业名字',
  `collage_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '学院名字',
  PRIMARY KEY (`major_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 964 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_major
-- ----------------------------
INSERT INTO `tb_major` VALUES (899, '生物科学', '生命科技学院');
INSERT INTO `tb_major` VALUES (900, '生物技术', '生命科技学院');
INSERT INTO `tb_major` VALUES (901, '生物工程', '生命科技学院');
INSERT INTO `tb_major` VALUES (902, '农学', '生命科技学院');
INSERT INTO `tb_major` VALUES (903, '种子科学与工程', '生命科技学院');
INSERT INTO `tb_major` VALUES (904, '国际经济与贸易', '经济与管理学院');
INSERT INTO `tb_major` VALUES (905, '信息管理与信息系统', '经济与管理学院');
INSERT INTO `tb_major` VALUES (906, '信息管理与信息系统（农村基层管理方向）', '经济与管理学院');
INSERT INTO `tb_major` VALUES (907, '数学与应用数学(统计方向)', '经济与管理学院');
INSERT INTO `tb_major` VALUES (908, '数学与应用数学(金融方向)', '经济与管理学院');
INSERT INTO `tb_major` VALUES (909, '市场营销', '经济与管理学院');
INSERT INTO `tb_major` VALUES (910, '会计学', '经济与管理学院');
INSERT INTO `tb_major` VALUES (911, '人力资源与管理', '经济与管理学院');
INSERT INTO `tb_major` VALUES (912, '机电技术教育', '机电学院');
INSERT INTO `tb_major` VALUES (913, '应用电子技术教育', '机电学院');
INSERT INTO `tb_major` VALUES (914, '机械设计制造及其自动化', '机电学院');
INSERT INTO `tb_major` VALUES (915, '汽车服务工程', '机电学院');
INSERT INTO `tb_major` VALUES (916, '电气工程及其自动化', '机电学院');
INSERT INTO `tb_major` VALUES (917, '烹饪与营养教育', '食品学院');
INSERT INTO `tb_major` VALUES (918, '食品科学与工程', '食品学院');
INSERT INTO `tb_major` VALUES (919, '食品质量与安全', '食品学院');
INSERT INTO `tb_major` VALUES (920, '旅游管理', '食品学院');
INSERT INTO `tb_major` VALUES (921, '动植物检疫', '动物科技学院');
INSERT INTO `tb_major` VALUES (922, '动物科学', '动物科技学院');
INSERT INTO `tb_major` VALUES (923, '动物医学', '动物科技学院');
INSERT INTO `tb_major` VALUES (924, '城乡规划', '园艺园林学院');
INSERT INTO `tb_major` VALUES (925, '园艺', '园艺园林学院');
INSERT INTO `tb_major` VALUES (926, '园林', '园艺园林学院');
INSERT INTO `tb_major` VALUES (927, '环境科学', '资源与环境学院');
INSERT INTO `tb_major` VALUES (928, '植物保护', '资源与环境学院');
INSERT INTO `tb_major` VALUES (929, '农业资源与环境', '资源与环境学院');
INSERT INTO `tb_major` VALUES (930, '教育技术学', '信息工程学院');
INSERT INTO `tb_major` VALUES (931, '通信工程', '信息工程学院');
INSERT INTO `tb_major` VALUES (932, '计算机科学与技术', '信息工程学院');
INSERT INTO `tb_major` VALUES (933, '信息工程', '信息工程学院');
INSERT INTO `tb_major` VALUES (934, '物联网工程', '信息工程学院');
INSERT INTO `tb_major` VALUES (935, '应用化学', '化学化工学院');
INSERT INTO `tb_major` VALUES (936, '材料化学', '化学化工学院');
INSERT INTO `tb_major` VALUES (937, '化学工程与工艺', '化学化工学院');
INSERT INTO `tb_major` VALUES (938, '制药工程', '化学化工学院');
INSERT INTO `tb_major` VALUES (939, '音乐学', '艺术学院');
INSERT INTO `tb_major` VALUES (940, '美术学', '艺术学院');
INSERT INTO `tb_major` VALUES (941, '美术学（书法方向）', '艺术学院');
INSERT INTO `tb_major` VALUES (942, '动画', '艺术学院');
INSERT INTO `tb_major` VALUES (943, '视觉传达设计', '艺术学院');
INSERT INTO `tb_major` VALUES (944, '产品设计（旅游工艺品设计方向）', '艺术学院');
INSERT INTO `tb_major` VALUES (945, '环境设计', '艺术学院');
INSERT INTO `tb_major` VALUES (946, '产品设计', '艺术学院');
INSERT INTO `tb_major` VALUES (947, '工艺美术', '艺术学院');
INSERT INTO `tb_major` VALUES (948, '服装设计与工艺教育', '服装学院');
INSERT INTO `tb_major` VALUES (949, '服装与服饰设计（服装表演方向）', '服装学院');
INSERT INTO `tb_major` VALUES (950, '服装设计与工程', '服装学院');
INSERT INTO `tb_major` VALUES (951, '服装与服饰设计', '服装学院');
INSERT INTO `tb_major` VALUES (952, '法学', '文法学院');
INSERT INTO `tb_major` VALUES (953, '汉语言文学', '文法学院');
INSERT INTO `tb_major` VALUES (954, '汉语国际教育', '文法学院');
INSERT INTO `tb_major` VALUES (955, '教育学', '教育科学学院');
INSERT INTO `tb_major` VALUES (956, '学前教育', '教育科学学院');
INSERT INTO `tb_major` VALUES (957, '数学与应用数学', '数学科学学院');
INSERT INTO `tb_major` VALUES (958, '信息与计算科学', '数学科学学院');
INSERT INTO `tb_major` VALUES (959, '英语', '外国语学院');
INSERT INTO `tb_major` VALUES (960, '英语（翻译方向）', '外国语学院');
INSERT INTO `tb_major` VALUES (961, '商务英语', '外国语学院');
INSERT INTO `tb_major` VALUES (962, '体育教育', '体育学院');
INSERT INTO `tb_major` VALUES (963, '社会体育指导与管理', '体育学院');

-- ----------------------------
-- Table structure for tb_student_quality
-- ----------------------------
DROP TABLE IF EXISTS `tb_student_quality`;
CREATE TABLE `tb_student_quality`  (
  `quality_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '生源质量Id',
  `major_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业名字',
  `fist_volunteer_num` int(11) NULL DEFAULT 0 COMMENT '专业1志愿报考人数',
  `students_num` int(11) NULL DEFAULT 0 COMMENT '学生实际进档人数',
  `after_volunteer_num` int(11) NULL DEFAULT 0 COMMENT '专业2-5志愿报考总人次',
  `average_score` double NULL DEFAULT 0 COMMENT '录取平均分',
  `major_recognition` double NULL DEFAULT NULL COMMENT '专业认可度',
  `college_entrance` double NULL DEFAULT 0 COMMENT '高考成绩',
  `major_advantage` double NULL DEFAULT 0 COMMENT '专业优势',
  `year` int(11) NULL DEFAULT 0 COMMENT '年份',
  `collage_advantage` double NULL DEFAULT NULL COMMENT '学院专业优势',
  `collage_quality` double NULL DEFAULT 0 COMMENT '学院生源质量',
  `yield_rate` double NULL DEFAULT 0 COMMENT '学院报到率',
  `colleage_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学院名字',
  `mark` int(11) NULL DEFAULT 0 COMMENT '文理标识',
  PRIMARY KEY (`quality_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6446 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_student_quality
-- ----------------------------
INSERT INTO `tb_student_quality` VALUES (6398, '英语', 36, 768, 62, 503.012987012987, 66.2273, 84.8919, 33.1506, 2018, 30.2308, 7.5208, 97.54, '外国语学院', 2);
INSERT INTO `tb_student_quality` VALUES (6399, '法学', 44, 768, 65, 505.23636363636365, 75.9701, 85.2672, 35.6395, 2018, 28.0372, 7.1539, 94.36, '文法学院', 2);
INSERT INTO `tb_student_quality` VALUES (6400, '商务英语', 31, 768, 21, 507.39285714285717, 42.0804, 85.6311, 27.3111, 2018, 30.2308, 7.5208, 97.54, '外国语学院', 2);
INSERT INTO `tb_student_quality` VALUES (6401, '学前教育', 10, 768, 18, 374.4237288135593, 18.7555, 63.1903, 17.1058, 2018, 19.3796, 6.2174, 92.97, '教育科学学院', 2);
INSERT INTO `tb_student_quality` VALUES (6402, '人力资源管理', 23, 768, 37, 508.8, 41.1068, 85.8686, 27.1166, 2018, 31.7751, 7.654, 97.05, '经济与管理学院', 2);
INSERT INTO `tb_student_quality` VALUES (6403, '汉语言文学', 25, 768, 35, 505.06349206349205, 42.2732, 85.238, 27.2814, 2018, 28.0372, 7.1539, 94.36, '文法学院', 2);
INSERT INTO `tb_student_quality` VALUES (6404, '旅游管理', 12, 768, 48, 576.7142857142857, 34.6912, 97.3303, 27.7859, 2018, 28.8748, 7.342, 96.62, '食品学院', 2);
INSERT INTO `tb_student_quality` VALUES (6405, '国际经济与贸易', 49, 768, 58, 535.1878787878788, 77.9633, 90.322, 37.13, 2018, 31.7751, 7.654, 97.05, '经济与管理学院', 2);
INSERT INTO `tb_student_quality` VALUES (6406, '教育学', 10, 768, 20, 504.23333333333335, 19.6785, 85.0979, 21.6534, 2018, 19.3796, 6.2174, 92.97, '教育科学学院', 2);
INSERT INTO `tb_student_quality` VALUES (6407, '会计学', 67, 768, 0, 517.3731343283582, 70, 87.3154, 34.5635, 2018, 31.7751, 7.654, 97.05, '经济与管理学院', 2);
INSERT INTO `tb_student_quality` VALUES (6408, '信息管理与信息系统', 30, 768, 30, 504.7625, 45.1894, 85.1872, 27.9943, 2018, 31.7751, 7.654, 97.05, '经济与管理学院', 2);
INSERT INTO `tb_student_quality` VALUES (6409, '服装设计与工艺教育', 16, 768, 6, 506.48387096774195, 19.4856, 85.4777, 21.6805, 2018, 21.6805, 6.6723, 97.83, '服装学院', 2);
INSERT INTO `tb_student_quality` VALUES (6410, '市场营销', 38, 768, 22, 592.5333333333333, 49.8553, 100, 32.0709, 2018, 31.7751, 7.654, 97.05, '经济与管理学院', 2);
INSERT INTO `tb_student_quality` VALUES (6411, '汉语国际教育', 10, 768, 16, 504.0833333333333, 17.8324, 85.0726, 21.1908, 2018, 28.0372, 7.1539, 94.36, '文法学院', 2);
INSERT INTO `tb_student_quality` VALUES (6412, '汽车服务工程', 24, 2259, 32, 506.6607142857143, 33.1177, 87.5754, 25.4729, 2018, 30.9912, 7.5246, 95.95, '机电学院', 1);
INSERT INTO `tb_student_quality` VALUES (6413, '材料化学', 19, 2259, 11, 508.76666666666665, 20.9095, 87.9394, 22.5187, 2018, 23.9769, 6.7758, 95.04, '化学化工学院', 1);
INSERT INTO `tb_student_quality` VALUES (6414, '通信工程', 33, 2259, 27, 507.85, 39.2405, 87.781, 27.031, 2018, 25.6416, 7.0041, 96.36, '信息工程学院', 1);
INSERT INTO `tb_student_quality` VALUES (6415, '信息工程', 19, 2259, 41, 505.73333333333335, 32.0206, 87.4151, 25.1694, 2018, 25.6416, 7.0041, 96.36, '信息工程学院', 1);
INSERT INTO `tb_student_quality` VALUES (6416, '信息与计算科学', 4, 2259, 18, 498.8833333333333, 10.211, 86.2311, 19.5301, 2018, 23.2334, 6.6224, 93.33, '数学科学学院', 1);
INSERT INTO `tb_student_quality` VALUES (6417, '园艺', 4, 2259, 14, 526.6176470588235, 8.7295, 91.0249, 20.1079, 2018, 21.0208, 6.5003, 95.53, '园艺园林学院', 1);
INSERT INTO `tb_student_quality` VALUES (6418, '机械设计制造及其自动化', 79, 2259, 2, 513.1358024691358, 70.7407, 88.6946, 35.019, 2018, 30.9912, 7.5246, 95.95, '机电学院', 1);
INSERT INTO `tb_student_quality` VALUES (6419, '食品科学与工程', 39, 2259, 81, 502.34166666666664, 64.557, 86.8288, 33.1184, 2018, 28.8748, 7.342, 96.62, '食品学院', 1);
INSERT INTO `tb_student_quality` VALUES (6420, '园林', 6, 2259, 25, 549.2321428571429, 14.5757, 94.9338, 22.3276, 2018, 21.0208, 6.5003, 95.53, '园艺园林学院', 1);
INSERT INTO `tb_student_quality` VALUES (6421, '制药工程', 25, 2259, 25, 505.88, 31.4112, 87.4404, 25.0233, 2018, 23.9769, 6.7758, 95.04, '化学化工学院', 1);
INSERT INTO `tb_student_quality` VALUES (6422, '生物技术', 17, 2259, 14, 502.7818181818182, 20.2485, 86.9049, 22.1509, 2018, 20.9907, 6.4055, 93.53, '生命科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6423, '环境科学', 18, 2259, 29, 503.125, 26.6901, 86.9642, 23.7592, 2018, 19.446, 6.2439, 93.4, '资源与环境学院', 1);
INSERT INTO `tb_student_quality` VALUES (6424, '动物医学', 7, 2259, 11, 517.6432748538011, 10.2766, 89.4737, 20.1856, 2018, 18.3257, 6.141, 93.62, '动物科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6425, '风景园林', 6, 2259, 3, 506.46666666666664, 6.4276, 87.5418, 18.8507, 2018, 21.0208, 6.5003, 95.53, '园艺园林学院', 1);
INSERT INTO `tb_student_quality` VALUES (6426, '教育技术学', 8, 2259, 17, 500.06666666666666, 13.3849, 86.4356, 20.3571, 2018, 25.6416, 7.0041, 96.36, '信息工程学院', 1);
INSERT INTO `tb_student_quality` VALUES (6427, '物联网工程', 25, 2259, 35, 506.28333333333336, 35.1149, 87.5102, 25.9551, 2018, 25.6416, 7.0041, 96.36, '信息工程学院', 1);
INSERT INTO `tb_student_quality` VALUES (6428, '植物保护', 1, 2259, 1, 491.45303867403317, 1.2564, 84.9468, 17.0574, 2018, 19.446, 6.2439, 93.4, '资源与环境学院', 1);
INSERT INTO `tb_student_quality` VALUES (6429, '生物工程', 14, 2259, 29, 501.664, 23.1458, 86.7117, 22.8309, 2018, 20.9907, 6.4055, 93.53, '生命科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6430, '应用化学', 13, 2259, 37, 502.76, 25.2227, 86.9012, 23.3831, 2018, 23.9769, 6.7758, 95.04, '化学化工学院', 1);
INSERT INTO `tb_student_quality` VALUES (6431, '计算机科学与技术', 35, 2259, 25, 578.542372881356, 40.2719, 100, 29.6955, 2018, 25.6416, 7.0041, 96.36, '信息工程学院', 1);
INSERT INTO `tb_student_quality` VALUES (6432, '电气工程及其自动化', 64, 2259, 11, 511.10526315789474, 60.7829, 88.3436, 32.4816, 2018, 30.9912, 7.5246, 95.95, '机电学院', 1);
INSERT INTO `tb_student_quality` VALUES (6433, '化学工程与工艺', 14, 2259, 30, 562.1153846153846, 23.5162, 97.1606, 24.9826, 2018, 23.9769, 6.7758, 95.04, '化学化工学院', 1);
INSERT INTO `tb_student_quality` VALUES (6434, '种子科学与工程', 3, 2259, 18, 491.0082644628099, 9.3249, 84.8699, 19.0421, 2018, 20.9907, 6.4055, 93.53, '生命科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6435, '数学与应用数学', 25, 2259, 48, 500.06666666666666, 39.9297, 86.4356, 26.9367, 2018, 23.2334, 6.6224, 93.33, '数学科学学院', 1);
INSERT INTO `tb_student_quality` VALUES (6436, '食品质量与安全', 25, 2259, 33, 504.7758620689655, 34.3741, 87.2496, 25.7201, 2018, 28.8748, 7.342, 96.62, '食品学院', 1);
INSERT INTO `tb_student_quality` VALUES (6437, '城乡规划', 18, 2259, 17, 507.2093023255814, 22.2457, 87.6702, 22.7968, 2018, 21.0208, 6.5003, 95.53, '园艺园林学院', 1);
INSERT INTO `tb_student_quality` VALUES (6438, '生物科学', 9, 2259, 5, 503.56666666666666, 9.8265, 87.0406, 19.5944, 2018, 20.9907, 6.4055, 93.53, '生命科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6439, '农业资源与环境', 3, 2259, 5, 481.4065040650407, 4.5101, 83.2102, 17.5215, 2018, 19.446, 6.2439, 93.4, '资源与环境学院', 1);
INSERT INTO `tb_student_quality` VALUES (6440, '农学', 12, 2259, 12, 516.4628099173553, 15.0774, 89.2697, 21.3353, 2018, 20.9907, 6.4055, 93.53, '生命科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6441, '动物药学', 2, 2259, 0, 504.73333333333335, 1.7722, 87.2422, 17.6378, 2018, 18.3257, 6.141, 93.62, '动物科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6442, '动物科学', 2, 2259, 5, 501.23893805309734, 3.624, 86.6382, 17.9777, 2018, 18.3257, 6.141, 93.62, '动物科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6443, '动植物检疫', 1, 2259, 3, 499.1, 1.9972, 86.2685, 17.5016, 2018, 18.3257, 6.141, 93.62, '动物科技学院', 1);
INSERT INTO `tb_student_quality` VALUES (6444, NULL, 0, 0, 0, 0, 100, 0, 100, 2018, 100, 9.9846, 97.92, '艺术学院', 0);
INSERT INTO `tb_student_quality` VALUES (6445, NULL, 0, 0, 0, 0, 100, 0, 100, 2018, 100, 10.08, 100, '体育学院', 0);

-- ----------------------------
-- Table structure for tb_ts
-- ----------------------------
DROP TABLE IF EXISTS `tb_ts`;
CREATE TABLE `tb_ts`  (
  `ts_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '师资结构ID',
  `college_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学院',
  `stu_num` int(11) NULL DEFAULT NULL COMMENT '在校生数',
  `tea_num` double NULL DEFAULT NULL COMMENT '教师总数',
  `gra_num` int(11) NULL DEFAULT NULL COMMENT '研究生学位教师数',
  `sen_num` int(11) NULL DEFAULT NULL COMMENT '高级职务教师总数',
  `b21` double NULL DEFAULT NULL COMMENT '生师比',
  `b22` double NULL DEFAULT NULL COMMENT '研究生学位教师占教师总数比',
  `b23` double NULL DEFAULT NULL COMMENT '高级职称教师占教师总数比',
  `qualified` int(11) NULL DEFAULT NULL COMMENT '生师比合格值',
  `m1` double NULL DEFAULT NULL COMMENT '生师比积分',
  `m2` double NULL DEFAULT NULL COMMENT '研究生学位教师积分',
  `m3` double NULL DEFAULT NULL COMMENT '高级职务教师积分',
  `w1` double NULL DEFAULT NULL COMMENT '生师比33.7',
  `w2` double NULL DEFAULT NULL COMMENT '高学历教师占比32.9',
  `w3` double NULL DEFAULT NULL COMMENT '高职称教师占比33.9',
  `a2` double NULL DEFAULT NULL COMMENT '师资结构指数11.07',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  PRIMARY KEY (`ts_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1202 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_ts
-- ----------------------------
INSERT INTO `tb_ts` VALUES (1154, '生命科技学院', 2007, 124, 107, 48, 16.185, 0.863, 0.387, 18, 1, 0.8933, 0.5257, 33.7, 29.3887, 17.8223, 8.9569, 2018);
INSERT INTO `tb_ts` VALUES (1155, '经济与管理学院', 2410, 78.5, 72, 29, 30.701, 0.917, 0.369, 18, 0, 0.9495, 0.5017, 0, 31.2379, 17.0088, 5.3409, 2018);
INSERT INTO `tb_ts` VALUES (1156, '机电学院', 1778, 100, 85, 36, 17.78, 0.85, 0.36, 18, 1, 0.8799, 0.4889, 33.7, 28.9493, 16.5748, 8.7701, 2018);
INSERT INTO `tb_ts` VALUES (1157, '食品学院', 1470, 85, 77, 37, 17.294, 0.906, 0.435, 18, 1, 0.9378, 0.5912, 33.7, 30.8525, 20.0414, 9.3645, 2018);
INSERT INTO `tb_ts` VALUES (1158, '动物科技学院', 1430, 91, 83, 67, 15.714, 0.912, 0.736, 18, 1, 0.9442, 1, 33.7, 31.0639, 33.8983, 10.9219, 2018);
INSERT INTO `tb_ts` VALUES (1159, '园艺园林学院', 1525, 72, 63, 31, 21.181, 0.875, 0.431, 18, 0.3638, 0.9058, 0.5848, 12.2601, 29.8007, 19.8232, 6.8506, 2018);
INSERT INTO `tb_ts` VALUES (1160, '资源与环境学院', 1382, 75, 67, 30, 18.427, 0.893, 0.4, 18, 0.9146, 0.9248, 0.5433, 30.822, 30.4251, 18.4164, 8.8188, 2018);
INSERT INTO `tb_ts` VALUES (1161, '信息工程学院', 1734, 83, 79, 20, 20.892, 0.952, 0.241, 18, 0.4216, 0.9853, 0.3273, 14.2079, 32.4166, 11.0942, 6.3895, 2018);
INSERT INTO `tb_ts` VALUES (1162, '化学化工学院', 1201, 71, 61, 35, 16.915, 0.859, 0.493, 18, 1, 0.8894, 0.6695, 33.7, 29.2611, 22.6963, 9.4823, 2018);
INSERT INTO `tb_ts` VALUES (1163, '文法学院', 1072, 62, 58, 28, 17.29, 0.935, 0.452, 18, 1, 0.9684, 0.6134, 33.7, 31.8607, 20.7927, 9.5593, 2018);
INSERT INTO `tb_ts` VALUES (1164, '教育科学学院', 958, 58, 52, 20, 16.517, 0.897, 0.345, 18, 1, 0.9281, 0.4683, 33.7, 30.5347, 15.8762, 8.8683, 2018);
INSERT INTO `tb_ts` VALUES (1165, '艺术学院', 1220, 81.5, 65, 8, 14.969, 0.798, 0.098, 11, 0.2062, 0.8256, 0.1333, 6.9489, 27.1628, 4.5194, 4.2765, 2018);
INSERT INTO `tb_ts` VALUES (1166, '服装学院', 592, 34, 29, 13, 17.412, 0.853, 0.382, 11, 0, 0.883, 0.5193, 0, 29.0494, 17.6039, 5.1645, 2018);
INSERT INTO `tb_ts` VALUES (1167, '数学科学学院', 809, 58, 56, 27, 13.948, 0.966, 0.466, 18, 1, 0.9995, 0.6322, 33.7, 32.8836, 21.4329, 9.7434, 2018);
INSERT INTO `tb_ts` VALUES (1168, '外国语学院', 859, 77, 68, 17, 11.156, 0.883, 0.221, 18, 1, 0.9142, 0.2998, 33.7, 30.0772, 10.1649, 8.1854, 2018);
INSERT INTO `tb_ts` VALUES (1169, '体育学院', 580, 83, 75, 41, 6.988, 0.904, 0.494, 11, 1, 0.9354, 0.6709, 33.7, 30.7753, 22.7432, 9.6551, 2018);
INSERT INTO `tb_ts` VALUES (1170, '生命科技学院', 2007, 124, 107, 48, 16.1855, 0.8629, 0.3871, 18, 1, 0.8933, 0.5257, 33.7, 29.3887, 17.8223, 8.9569, 2020);
INSERT INTO `tb_ts` VALUES (1171, '经济与管理学院', 2410, 78.5, 72, 29, 30.7006, 0.9172, 0.3694, 18, 0, 0.9495, 0.5017, 0, 31.2379, 17.0088, 5.3409, 2020);
INSERT INTO `tb_ts` VALUES (1172, '机电学院', 1778, 100, 85, 36, 17.78, 0.85, 0.36, 18, 1, 0.8799, 0.4889, 33.7, 28.9493, 16.5748, 8.7701, 2020);
INSERT INTO `tb_ts` VALUES (1173, '食品学院', 1470, 85, 77, 37, 17.2941, 0.9059, 0.4353, 18, 1, 0.9378, 0.5912, 33.7, 30.8525, 20.0414, 9.3645, 2020);
INSERT INTO `tb_ts` VALUES (1174, '动物科技学院', 1430, 91, 83, 67, 15.7143, 0.9121, 0.7363, 18, 1, 0.9442, 1, 33.7, 31.0639, 33.8983, 10.9219, 2020);
INSERT INTO `tb_ts` VALUES (1175, '园艺园林学院', 1525, 72, 63, 31, 21.1806, 0.875, 0.4306, 18, 0.3639, 0.9058, 0.5848, 12.2634, 29.8007, 19.8232, 6.8509, 2020);
INSERT INTO `tb_ts` VALUES (1176, '资源与环境学院', 1382, 75, 67, 30, 18.4267, 0.8933, 0.4, 18, 0.9147, 0.9248, 0.5433, 30.8254, 30.4251, 18.4164, 8.8191, 2020);
INSERT INTO `tb_ts` VALUES (1177, '信息工程学院', 1734, 83, 79, 20, 20.8916, 0.9518, 0.241, 18, 0.4217, 0.9853, 0.3273, 14.2113, 32.4166, 11.0942, 6.3898, 2020);
INSERT INTO `tb_ts` VALUES (1178, '化学化工学院', 1201, 71, 61, 35, 16.9155, 0.8592, 0.493, 18, 1, 0.8894, 0.6695, 33.7, 29.2611, 22.6963, 9.4823, 2020);
INSERT INTO `tb_ts` VALUES (1179, '文法学院', 1072, 62, 58, 28, 17.2903, 0.9355, 0.4516, 18, 1, 0.9684, 0.6134, 33.7, 31.8607, 20.7927, 9.5593, 2020);
INSERT INTO `tb_ts` VALUES (1180, '教育科学学院', 958, 58, 52, 20, 16.5172, 0.8966, 0.3448, 18, 1, 0.9281, 0.4683, 33.7, 30.5347, 15.8762, 8.8683, 2020);
INSERT INTO `tb_ts` VALUES (1181, '艺术学院', 1220, 81.5, 65, 8, 14.9693, 0.7975, 0.0982, 11, 0.2061, 0.8256, 0.1333, 6.9456, 27.1628, 4.5194, 4.2761, 2020);
INSERT INTO `tb_ts` VALUES (1182, '服装学院', 592, 34, 29, 13, 17.4118, 0.8529, 0.3824, 11, 0, 0.883, 0.5193, 0, 29.0494, 17.6039, 5.1645, 2020);
INSERT INTO `tb_ts` VALUES (1183, '数学科学学院', 809, 58, 56, 27, 13.9483, 0.9655, 0.4655, 18, 1, 0.9995, 0.6322, 33.7, 32.8836, 21.4329, 9.7434, 2020);
INSERT INTO `tb_ts` VALUES (1184, '外国语学院', 859, 77, 68, 17, 11.1558, 0.8831, 0.2208, 18, 1, 0.9142, 0.2998, 33.7, 30.0772, 10.1649, 8.1854, 2020);
INSERT INTO `tb_ts` VALUES (1185, '体育学院', 580, 83, 75, 41, 6.988, 0.9036, 0.494, 11, 1, 0.9354, 0.6709, 33.7, 30.7753, 22.7432, 9.6551, 2020);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'admin', 'admin');

-- ----------------------------
-- Table structure for tb_year
-- ----------------------------
DROP TABLE IF EXISTS `tb_year`;
CREATE TABLE `tb_year`  (
  `year_id` int(11) NOT NULL AUTO_INCREMENT,
  `year_name` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`year_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_year
-- ----------------------------
INSERT INTO `tb_year` VALUES (1, 2018);
INSERT INTO `tb_year` VALUES (16, 2019);
INSERT INTO `tb_year` VALUES (20, 2020);

SET FOREIGN_KEY_CHECKS = 1;
