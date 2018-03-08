/*
Navicat MySQL Data Transfer

Source Server         : localDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mrz

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-03-08 14:56:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for adv
-- ----------------------------
DROP TABLE IF EXISTS `adv`;
CREATE TABLE `adv` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '广告ID',
  `image` varchar(100) DEFAULT NULL COMMENT 'Banner图片',
  `org_id` int(11) DEFAULT NULL COMMENT '组织ID',
  `column_id` int(11) DEFAULT NULL COMMENT '栏目ID',
  `level` int(11) DEFAULT NULL COMMENT '页面级别：1，列表页、2、详情页',
  `type` int(11) DEFAULT NULL COMMENT '类型：1、普通banner图；2、URL banner图；3、文章',
  `article_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告表';

-- ----------------------------
-- Records of adv
-- ----------------------------

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(100) DEFAULT NULL COMMENT '文章标题',
  `digest` char(50) DEFAULT NULL COMMENT '文章摘要',
  `image` varchar(100) DEFAULT NULL COMMENT '文章图片',
  `content` text COMMENT '文章内容',
  `publish_time` datetime DEFAULT NULL COMMENT '发表时间',
  `publisher` int(11) DEFAULT NULL COMMENT '发表者',
  `column_id` int(11) DEFAULT NULL COMMENT '所属栏目',
  `top` tinyint(1) DEFAULT NULL COMMENT '是否置顶',
  `like_count` int(11) DEFAULT NULL COMMENT '点赞数',
  `browse_count` int(11) DEFAULT NULL COMMENT '浏览数',
  `comment_count` int(11) DEFAULT NULL COMMENT '评论数',
  `share_count` int(11) DEFAULT NULL COMMENT '分享数',
  `org_id` int(11) DEFAULT NULL COMMENT '组织ID',
  `order` int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for atta
-- ----------------------------
DROP TABLE IF EXISTS `atta`;
CREATE TABLE `atta` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  `master_id` int(11) DEFAULT NULL COMMENT '主体ID',
  `atta_url` varchar(100) DEFAULT NULL,
  `atta_desc` varchar(100) DEFAULT NULL COMMENT '附件描述',
  `atta_type` int(11) DEFAULT NULL COMMENT '求助：\r\n            101、身份证正面\r\n            102、身份证反面\r\n            103、生活照\r\n            104、委托书\r\n            105、申请书\r\n            106、病例\r\n            107、其它资料\r\n            108、报销记录\r\n            109、体检报告\r\n            用户认证：\r\n            201、身份证正面\r\n            202、身份证反面\r\n            206、企业营业执照\r\n            207、授权说明书\r\n            动态图：\r\n            0\r\n            ',
  `upload_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of atta
-- ----------------------------

-- ----------------------------
-- Table structure for authentication_info
-- ----------------------------
DROP TABLE IF EXISTS `authentication_info`;
CREATE TABLE `authentication_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '认证id',
  `type` int(11) DEFAULT NULL COMMENT '1、身份证\r\n            2、',
  `code` varchar(50) DEFAULT NULL COMMENT '证件编码：身份证号、营业执照编号',
  `submitor` int(11) DEFAULT NULL COMMENT '提交者ID',
  `status` int(11) DEFAULT NULL COMMENT '1、未认证\r\n            2、认证中\r\n            3、已认证\r\n            4、已拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证信息表';

-- ----------------------------
-- Records of authentication_info
-- ----------------------------

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Banner ID',
  `image` varchar(100) DEFAULT NULL COMMENT 'Banner图片',
  `org_id` int(11) DEFAULT NULL COMMENT '组织ID',
  `column_id` int(11) DEFAULT NULL COMMENT '栏目ID',
  `level` int(11) DEFAULT NULL COMMENT '页面级别：0，首页、1，列表页、2、详情页',
  `type` int(11) DEFAULT NULL COMMENT '类型：1、普通banner图；2、URL banner图；3、文章',
  `article_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner表';

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `org_id` int(11) DEFAULT NULL COMMENT '组织ID',
  `image` varchar(100) DEFAULT NULL COMMENT '轮播图地址',
  `article_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `order` int(11) DEFAULT NULL COMMENT '轮播图顺序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int(11) DEFAULT NULL COMMENT '轮播图类型',
  `url` varchar(100) DEFAULT NULL COMMENT '轮播图链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮播图';

-- ----------------------------
-- Records of carousel
-- ----------------------------

-- ----------------------------
-- Table structure for column
-- ----------------------------
DROP TABLE IF EXISTS `column`;
CREATE TABLE `column` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '栏目ID',
  `name` varchar(5) DEFAULT NULL COMMENT '栏目名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父栏目ID',
  `url` varchar(100) DEFAULT NULL COMMENT '栏目链接',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织',
  `order` int(11) DEFAULT NULL COMMENT '序号',
  `show` tinyint(1) DEFAULT NULL COMMENT '是否显示',
  `index` tinyint(1) DEFAULT NULL COMMENT '首页是否显示',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `article_type` varchar(10) DEFAULT NULL COMMENT '文章类型',
  `index_column` tinyint(255) DEFAULT NULL COMMENT '首页栏目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目表';

-- ----------------------------
-- Records of column
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `telephone` char(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `content` varchar(500) DEFAULT NULL COMMENT '反馈内容',
  `creator` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈表';

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for func
-- ----------------------------
DROP TABLE IF EXISTS `func`;
CREATE TABLE `func` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '功能权限ID',
  `code` varchar(255) DEFAULT NULL COMMENT '功能权限码',
  `func_name` varchar(10) DEFAULT NULL COMMENT '功能权限名称',
  `url` varchar(255) DEFAULT NULL COMMENT '功能权限URL',
  `is_menu` tinyint(255) DEFAULT NULL COMMENT '是否是菜单',
  `parent_id` int(11) DEFAULT NULL COMMENT '父权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='功能权限表';

-- ----------------------------
-- Records of func
-- ----------------------------
INSERT INTO `func` VALUES ('1', null, '权限系统', null, '1', null);
INSERT INTO `func` VALUES ('2', 'sysUser:list', '系统用户管理', 'sysUser/grid.do', '1', '1');
INSERT INTO `func` VALUES ('3', 'sysUser:add', '添加系统用户', null, '0', '2');
INSERT INTO `func` VALUES ('4', 'sysUser:edit', '修改系统用户', null, '0', '2');
INSERT INTO `func` VALUES ('5', 'sysUser:delete', '删除系统用户', null, '0', '2');
INSERT INTO `func` VALUES ('6', 'sysUser:toggleStatus', '切换系统用户状态', null, '0', '2');
INSERT INTO `func` VALUES ('7', 'sysUser:setRole', '为系统用户分配角色', null, '0', '2');
INSERT INTO `func` VALUES ('8', 'sysUser:resetPassword', '重置系统用户密码', null, '0', '2');
INSERT INTO `func` VALUES ('9', 'role:list', '角色管理', 'role/grid.do', '1', '1');
INSERT INTO `func` VALUES ('10', 'role:add', '添加角色', null, '0', '9');
INSERT INTO `func` VALUES ('11', 'role:edit', '修改角色', null, '0', '9');
INSERT INTO `func` VALUES ('12', 'role:delete', '删除角色', null, '0', '9');
INSERT INTO `func` VALUES ('13', null, '组织管理', null, '1', null);
INSERT INTO `func` VALUES ('14', 'org:root', '总会管理', 'org/rootOrgGrid.do', '1', '13');
INSERT INTO `func` VALUES ('15', 'org:saveRoot', '保存总会信息', null, '0', '14');
INSERT INTO `func` VALUES ('16', 'org:list', '分会管理', 'org/grid.do', '1', '13');
INSERT INTO `func` VALUES ('17', 'org:add', '增加分会', null, '0', '16');
INSERT INTO `func` VALUES ('18', 'org:edit', '修改分会', null, '0', '16');
INSERT INTO `func` VALUES ('19', 'org:delete', '删除分会', null, '0', '16');
INSERT INTO `func` VALUES ('20', null, '内容管理', null, '1', null);
INSERT INTO `func` VALUES ('21', 'column:list', '栏目管理', 'column/grid.do', '1', '20');
INSERT INTO `func` VALUES ('22', 'column:add', '添加栏目', null, '0', '21');
INSERT INTO `func` VALUES ('23', 'column:edit', '修改栏目', null, '0', '21');
INSERT INTO `func` VALUES ('24', 'column:delete', '删除栏目', null, '0', '21');
INSERT INTO `func` VALUES ('25', 'article:list', '文章管理', 'article/grid.do', '1', '20');
INSERT INTO `func` VALUES ('26', 'article:add', '添加文章', null, '0', '25');
INSERT INTO `func` VALUES ('27', 'article:edit', '修改文章', null, '0', '25');
INSERT INTO `func` VALUES ('28', 'article:delete', '删除文章', null, '0', '25');
INSERT INTO `func` VALUES ('29', 'article:copy', '复制文章', null, '0', '25');
INSERT INTO `func` VALUES ('30', 'article:listComment', '查看文章评论', null, '0', '25');
INSERT INTO `func` VALUES ('31', 'article:deleteComment', '删除文章评论', null, '0', '25');
INSERT INTO `func` VALUES ('32', 'feedback:list', '意见反馈', 'feedback/grid.do', '1', '20');
INSERT INTO `func` VALUES ('33', 'carousel:list', '轮播图管理', 'carousel/grid.do', '1', '20');
INSERT INTO `func` VALUES ('34', 'carousel:add', '添加轮播图', null, '0', '33');
INSERT INTO `func` VALUES ('35', 'carousel:edit', '修改轮播图', null, '0', '33');
INSERT INTO `func` VALUES ('36', 'carousel:delete', '删除轮播图', null, '0', '33');
INSERT INTO `func` VALUES ('37', 'banner:list', 'Banner图管理', 'banner/grid.do', '1', '20');
INSERT INTO `func` VALUES ('38', 'banner:add', '添加Banner图', null, '0', '37');
INSERT INTO `func` VALUES ('39', 'banner:edit', '编辑Banner图', null, '0', '37');
INSERT INTO `func` VALUES ('40', 'banner:delete', '删除Banner图', null, '0', '37');
INSERT INTO `func` VALUES ('41', 'adv:list', '广告图管理', 'adv/grid.do', '1', '20');
INSERT INTO `func` VALUES ('42', 'adv:add', '添加广告图', null, '0', '41');
INSERT INTO `func` VALUES ('43', 'adv:edit', '修改广告图', null, '0', '41');
INSERT INTO `func` VALUES ('44', 'adv:delete', '删除广告图', null, '0', '41');

-- ----------------------------
-- Table structure for org
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `name` varchar(10) DEFAULT NULL COMMENT '组织名称',
  `logo` varchar(100) DEFAULT NULL COMMENT 'logo',
  `create_time` datetime DEFAULT NULL COMMENT '建立时间',
  `manager` varchar(10) DEFAULT NULL COMMENT '组织负责人',
  `manager_tel` varchar(20) DEFAULT NULL COMMENT '负责人电话',
  `org_tel` varchar(20) DEFAULT NULL COMMENT '组织联系方式',
  `city` varchar(10) DEFAULT NULL COMMENT '所属城市',
  `address` varchar(100) DEFAULT NULL COMMENT '组织地址',
  `postcode` varchar(10) DEFAULT NULL COMMENT '组织邮编',
  `parent_id` int(11) DEFAULT NULL COMMENT '父组织ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='组织表';

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('1', '张氏总会', 'http://60.205.220.140:8008/mrz.api', '2017-12-06 00:00:00', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '回复ID',
  `type` int(11) DEFAULT NULL COMMENT '回复类型\r\n            1、文章回复\r\n            2、动态回复\r\n            3、捐款回复',
  `parent_id` int(11) DEFAULT NULL COMMENT '父回复ID',
  `topic_id` int(11) DEFAULT NULL COMMENT '主题ID',
  `replier_id` int(11) DEFAULT NULL COMMENT '回复者ID',
  `receiver_id` int(11) DEFAULT NULL,
  `replay_time` datetime DEFAULT NULL COMMENT '回复时间',
  `content` varchar(100) DEFAULT NULL COMMENT '回复内容',
  `like_count` int(11) DEFAULT NULL COMMENT '点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回复表';

-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `updator` int(11) DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '2017-12-06 00:00:00', '1', '2017-12-06 00:00:00', '1');

-- ----------------------------
-- Table structure for role_func_relationship
-- ----------------------------
DROP TABLE IF EXISTS `role_func_relationship`;
CREATE TABLE `role_func_relationship` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色与功能权限关系ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `func_id` int(11) DEFAULT NULL COMMENT '功能权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='角色和功能权限关系表';

-- ----------------------------
-- Records of role_func_relationship
-- ----------------------------
INSERT INTO `role_func_relationship` VALUES ('1', '1', '1');
INSERT INTO `role_func_relationship` VALUES ('2', '1', '2');
INSERT INTO `role_func_relationship` VALUES ('3', '1', '3');
INSERT INTO `role_func_relationship` VALUES ('4', '1', '4');
INSERT INTO `role_func_relationship` VALUES ('5', '1', '5');
INSERT INTO `role_func_relationship` VALUES ('6', '1', '6');
INSERT INTO `role_func_relationship` VALUES ('7', '1', '7');
INSERT INTO `role_func_relationship` VALUES ('8', '1', '8');
INSERT INTO `role_func_relationship` VALUES ('9', '1', '9');
INSERT INTO `role_func_relationship` VALUES ('10', '1', '10');
INSERT INTO `role_func_relationship` VALUES ('11', '1', '11');
INSERT INTO `role_func_relationship` VALUES ('12', '1', '12');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(20) DEFAULT NULL COMMENT '登录名',
  `password` char(64) DEFAULT NULL COMMENT '密码',
  `salt` char(10) DEFAULT NULL COMMENT '盐',
  `real_name` varchar(10) DEFAULT NULL COMMENT '真实姓名',
  `sex` char(1) DEFAULT NULL COMMENT '性别（M：男，F：女）',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `status` char(1) DEFAULT NULL COMMENT '状态（Y：激活，N：禁用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updator` int(11) DEFAULT NULL COMMENT '更新着ID',
  `org_id` int(11) DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'b833e9551fafa80bf2e6e8b1c79aa2d850924734e1d381a08c7304aac7e10f45', 'meUVNfPfoB', 'admin', 'M', '1970-01-01', '15327181091', 'zhao-fan@qq.com', 'Y', '2017-12-06 00:00:00', '1', '2017-12-06 00:00:00', '1', null);

-- ----------------------------
-- Table structure for sys_user_role_relationship
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_relationship`;
CREATE TABLE `sys_user_role_relationship` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统用户和角色关系ID',
  `sys_user_id` int(11) DEFAULT NULL COMMENT '系统用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户和角色关系表';

-- ----------------------------
-- Records of sys_user_role_relationship
-- ----------------------------
INSERT INTO `sys_user_role_relationship` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(20) DEFAULT NULL COMMENT '登录名',
  `password` char(64) DEFAULT NULL COMMENT '密码',
  `salt` char(10) DEFAULT NULL COMMENT '盐',
  `nick_name` varchar(10) DEFAULT NULL COMMENT '昵称',
  `image` varchar(100) DEFAULT NULL COMMENT '头像',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `qq` varchar(100) DEFAULT NULL,
  `wx` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL COMMENT '联系地址',
  `telephone` char(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `authentication_user` tinyint(1) DEFAULT NULL COMMENT '是否用户否实名认证',
  `authentication_company` tinyint(1) DEFAULT NULL COMMENT '是否企业实名认证',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
