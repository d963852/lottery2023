/*
 Navicat Premium Data Transfer

 Source Server         : localhost-mysql8
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : lemis-bvtc

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 01/07/2022 14:54:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `quartz_job`;
CREATE TABLE `quartz_job`  (
  `ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `CREATE_BY` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `DEL_FLAG` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除状态',
  `UPDATE_BY` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `JOB_CLASS_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类名',
  `CRON_EXPRESSION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `PARAMETER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `STATUS` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'quartz定时任务表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `js_sys_menu`(`menu_code`, `parent_code`, `parent_codes`, `tree_sort`, `tree_sorts`, `tree_leaf`, `tree_level`, `tree_names`, `menu_name`, `menu_type`, `menu_href`, `menu_target`, `menu_icon`, `menu_color`, `menu_title`, `permission`, `weight`, `is_show`, `sys_code`, `module_codes`, `status`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `extend_s1`, `extend_s2`, `extend_s3`, `extend_s4`, `extend_s5`, `extend_s6`, `extend_s7`, `extend_s8`, `extend_i1`, `extend_i2`, `extend_i3`, `extend_i4`, `extend_f1`, `extend_f2`, `extend_f3`, `extend_f4`, `extend_d1`, `extend_d2`, `extend_d3`, `extend_d4`) VALUES ('1526402310260281344', '0', '0,', 10290, '0000010290,', '1', 0, '作业监控调度', '作业监控调度', '1', '/quartz/quartzJob', '', 'icon-speedometer', '', '', 'quartz:quartzJob:view,quartz:quartzJob:edit', 60, '1', 'default', 'core', '0', 'system', '2022-05-17 11:20:45', 'system', '2022-05-17 11:20:45', '', '', '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);



INSERT INTO `quartz_job`(`ID`, `CREATE_BY`, `CREATE_TIME`, `DEL_FLAG`, `UPDATE_BY`, `UPDATE_TIME`, `JOB_CLASS_NAME`, `CRON_EXPRESSION`, `PARAMETER`, `DESCRIPTION`, `STATUS`) VALUES ('1234446564624400384', 'system', '2020-03-02 19:52:50', NULL, 'system', '2022-07-01 10:54:20', 'com.jeesite.modules.autoRun.SampleParamJob', '0 0 0/1 * * ?', '定时回收内存垃圾', '定时回收内存垃圾', '0');

INSERT INTO `js_sys_module` VALUES ('portal', '门户', '门户页面', 'com.jeesite.modules.portal.web.PortalController', '5.3.0', NULL, '0', 'system', '2022-05-19 11:11:14', 'system', '2022-05-19 11:45:19', NULL);
INSERT INTO `js_sys_module` VALUES ('quartz', 'quartz作业调度', '简易的作业监控调度模块,动态设置定时任务', 'com.jeesite.modules.quartz.web.QuartzJobController', '5.3.0', NULL, '0', 'system', '2022-05-17 11:18:12', 'system', '2022-05-17 11:18:12', NULL);
