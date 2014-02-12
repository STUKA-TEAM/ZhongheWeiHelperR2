# Host: localhost  (Version: 5.5.32)
# Date: 2014-01-24 21:34:52
# Generator: MySQL-Front 5.3  (Build 4.89)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "application"
#

DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL DEFAULT '' COMMENT 'UUID',
  `wechatToken` varchar(10) NOT NULL DEFAULT '' COMMENT '需填入微信公众平台的token',
  `wechatName` varchar(40) NOT NULL DEFAULT '' COMMENT '微信账号名称',
  `wechatOriginalId` varchar(40) NOT NULL DEFAULT '' COMMENT '微信账号原始ID',
  `wechatNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '微信号',
  `address` varchar(40) NOT NULL DEFAULT '' COMMENT '微信账号地址',
  `industry` varchar(20) NOT NULL DEFAULT '' COMMENT '账号所属行业',
  `welcomeType` varchar(10) DEFAULT NULL COMMENT '欢迎页类型{"text" "list"}',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='用于关联微信公众号的应用';

#
# Structure for table "application_authority"
#

DROP TABLE IF EXISTS `application_authority`;
CREATE TABLE `application_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `authid` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

#
# Structure for table "article"
#

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `articleid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `title` varchar(40) NOT NULL COMMENT '文章标题',
  `coverPic` varchar(80) DEFAULT NULL COMMENT '图文消息封面',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `content` text NOT NULL COMMENT '文章内容',
  PRIMARY KEY (`articleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "article_articleclass"
#

DROP TABLE IF EXISTS `article_articleclass`;
CREATE TABLE `article_articleclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleid` int(11) NOT NULL COMMENT '文章id',
  `classid` int(11) NOT NULL COMMENT '文章类别id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "articleclass"
#

DROP TABLE IF EXISTS `articleclass`;
CREATE TABLE `articleclass` (
  `classid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `className` varchar(20) NOT NULL COMMENT '文章类别名称',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`classid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "authority"
#

DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `authid` int(11) NOT NULL AUTO_INCREMENT COMMENT '需权限资源id',
  `authName` varchar(20) NOT NULL DEFAULT '' COMMENT '需权限资源名称',
  `authPinyin` varchar(25) NOT NULL COMMENT '权限名称拼音',
  PRIMARY KEY (`authid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='资源权限表';

#
# Structure for table "customer_app_count"
#

DROP TABLE IF EXISTS `customer_app_count`;
CREATE TABLE `customer_app_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL DEFAULT '0',
  `appUpperLimit` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "customer_authority"
#

DROP TABLE IF EXISTS `customer_authority`;
CREATE TABLE `customer_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '用户id',
  `authid` int(11) NOT NULL COMMENT '权限id',
  `expiredTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '权限过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "elove"
#

DROP TABLE IF EXISTS `elove`;
CREATE TABLE `elove` (
  `eloveid` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `expiredTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '过期时间',
  `title` varchar(20) NOT NULL COMMENT 'elove标题',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `coverPic` varchar(80) NOT NULL COMMENT '图文消息封面',
  `coverText` varchar(200) NOT NULL COMMENT '图文消息文字',
  `majorGroupPhoto` varchar(80) NOT NULL COMMENT '新人合照',
  `xinNiang` varchar(40) NOT NULL COMMENT '新娘姓名',
  `xinLang` varchar(40) NOT NULL COMMENT '新郎姓名',
  `storyTextImagePath` varchar(80) NOT NULL COMMENT '相遇相知故事',
  `music` varchar(80) NOT NULL COMMENT '背景音乐',
  `phone` varchar(14) NOT NULL COMMENT '电话',
  `weddingDate` varchar(15) NOT NULL COMMENT '婚礼时间',
  `weddingAddress` varchar(40) NOT NULL COMMENT '婚礼地址',
  `lng` decimal(12,6) NOT NULL COMMENT '地址经度',
  `lat` decimal(12,6) NOT NULL COMMENT '地址纬度',
  `shareTitle` varchar(40) NOT NULL COMMENT '分享消息标题',
  `shareContent` varchar(200) NOT NULL COMMENT '分享消息描述',
  `footerText` varchar(80) NOT NULL COMMENT 'Elove底部信息',
  `sideCorpInfo` varchar(80) NOT NULL COMMENT 'Elove侧边滑栏商户信息',
  `themeid` int(11) NOT NULL COMMENT 'elove模板id',
  PRIMARY KEY (`eloveid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "elove_consume_record"
#

DROP TABLE IF EXISTS `elove_consume_record`;
CREATE TABLE `elove_consume_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL,
  `notPayNumber` int(11) NOT NULL COMMENT '未付款数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Structure for table "elove_images"
#

DROP TABLE IF EXISTS `elove_images`;
CREATE TABLE `elove_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `imagePath` varchar(80) NOT NULL COMMENT '图片路径（未完全）',
  `imageType` varchar(10) NOT NULL COMMENT '图片分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Structure for table "elove_join"
#

DROP TABLE IF EXISTS `elove_join`;
CREATE TABLE `elove_join` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `name` varchar(20) NOT NULL COMMENT '赴宴人姓名',
  `phone` varchar(14) NOT NULL COMMENT '联系电话',
  `number` int(11) NOT NULL COMMENT '人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "elove_message"
#

DROP TABLE IF EXISTS `elove_message`;
CREATE TABLE `elove_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `name` varchar(20) NOT NULL COMMENT '祝福人姓名',
  `content` varchar(400) NOT NULL COMMENT '祝福内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "elove_theme"
#

DROP TABLE IF EXISTS `elove_theme`;
CREATE TABLE `elove_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `themeName` varchar(40) NOT NULL COMMENT '主题名称',
  `themePath` varchar(100) NOT NULL COMMENT '主题css文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "elove_video"
#

DROP TABLE IF EXISTS `elove_video`;
CREATE TABLE `elove_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `videoPath` varchar(80) NOT NULL COMMENT '视频路径',
  `videoType` varchar(10) NOT NULL COMMENT '视频分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Structure for table "feedback"
#

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '商户id',
  `message` varchar(600) NOT NULL COMMENT '反馈消息',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "image_temp_record"
#

DROP TABLE IF EXISTS `image_temp_record`;
CREATE TABLE `image_temp_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imagePath` varchar(80) NOT NULL COMMENT '临时图片路径（未完全）',
  `createDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Structure for table "role"
#

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Structure for table "store_auth_price"
#

DROP TABLE IF EXISTS `store_auth_price`;
CREATE TABLE `store_auth_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '商户id',
  `authid` int(11) NOT NULL COMMENT '权限id',
  `price` decimal(15,2) NOT NULL COMMENT '单价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='customer类型user对应的权限价格';

#
# Structure for table "storeimage"
#

DROP TABLE IF EXISTS `storeimage`;
CREATE TABLE `storeimage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '所属用户id',
  `imagePath` varchar(80) NOT NULL COMMENT '图片路径（未完全）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='customer类型user对应的images';

#
# Structure for table "storeuser"
#

DROP TABLE IF EXISTS `storeuser`;
CREATE TABLE `storeuser` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '众合微助手平台账户ID',
  `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '所属角色',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '平台账号用户名',
  `password` varchar(60) NOT NULL DEFAULT '' COMMENT '平台账户密码',
  `createDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `storeName` varchar(40) NOT NULL DEFAULT '' COMMENT '用户名称',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(15) DEFAULT NULL COMMENT '座机号',
  `cellPhone` varchar(14) NOT NULL DEFAULT '' COMMENT '手机号',
  `address` varchar(40) NOT NULL DEFAULT '' COMMENT '地址',
  `corpMoreInfoLink` varchar(150) DEFAULT NULL COMMENT '了解更多链接',
  `lng` decimal(12,6) NOT NULL COMMENT '地址经度',
  `lat` decimal(12,6) NOT NULL COMMENT '地址纬度',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='user类型 (平台内账户类型)';

#
# Structure for table "storeuser_application"
#

DROP TABLE IF EXISTS `storeuser_application`;
CREATE TABLE `storeuser_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL DEFAULT '0' COMMENT '众合微信助手账户id',
  `appid` varchar(32) NOT NULL DEFAULT '' COMMENT '众合微信平台应用id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='customer application (customer类型user对应的application)';

#
# Structure for table "video_temp_record"
#

DROP TABLE IF EXISTS `video_temp_record`;
CREATE TABLE `video_temp_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `videoPath` varchar(80) NOT NULL COMMENT '临时视频路径',
  `createDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

#
# Structure for table "website"
#

DROP TABLE IF EXISTS `website`;
CREATE TABLE `website` (
  `websiteid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `getCode` varchar(20) NOT NULL COMMENT '微官网直接获取码',
  `title` varchar(30) NOT NULL COMMENT '微官网页面标题',
  `phone` varchar(14) NOT NULL COMMENT '联系电话',
  `address` varchar(40) NOT NULL COMMENT '导航地址',
  `lng` decimal(12,5) NOT NULL DEFAULT '0.00000' COMMENT '地址经度',
  `lat` decimal(12,5) NOT NULL DEFAULT '0.00000' COMMENT '地址纬度',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `expiredTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '资源过期时间',
  `rootNodeId` int(11) NOT NULL COMMENT '子模块树根节点',
  `coverPic` varchar(80) NOT NULL COMMENT '微信图文消息图片',
  `coverText` varchar(200) NOT NULL COMMENT '微信图文消息文字',
  `shareTitle` varchar(40) NOT NULL COMMENT '分享消息标题',
  `shareContent` varchar(200) NOT NULL COMMENT '分享消息文字',
  `footerText` varchar(40) NOT NULL COMMENT '微官网脚注',
  `themeId` int(11) NOT NULL COMMENT '微官网主题模板',
  PRIMARY KEY (`websiteid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "website_image"
#

DROP TABLE IF EXISTS `website_image`;
CREATE TABLE `website_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `websiteid` int(11) NOT NULL COMMENT '微官网id',
  `imageType` varchar(10) NOT NULL COMMENT '图片类型',
  `imagePath` varchar(80) NOT NULL COMMENT '图片路径（未完全）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "website_node"
#

DROP TABLE IF EXISTS `website_node`;
CREATE TABLE `website_node` (
  `nodeid` int(11) NOT NULL AUTO_INCREMENT,
  `nodeName` varchar(20) NOT NULL COMMENT '模块名称',
  `nodePic` varchar(80) DEFAULT NULL COMMENT '模块图片',
  `childrenType` varchar(10) NOT NULL COMMENT '子节点类别 (node,article,articleclass)',
  PRIMARY KEY (`nodeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "website_node_article"
#

DROP TABLE IF EXISTS `website_node_article`;
CREATE TABLE `website_node_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleid` int(11) NOT NULL COMMENT '文章id',
  `fatherid` int(11) NOT NULL COMMENT '父节点id ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "website_node_articleclass"
#

DROP TABLE IF EXISTS `website_node_articleclass`;
CREATE TABLE `website_node_articleclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classid` int(11) NOT NULL COMMENT '文章类别id',
  `fatherid` int(11) NOT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "website_node_structure"
#

DROP TABLE IF EXISTS `website_node_structure`;
CREATE TABLE `website_node_structure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nodeid` int(11) NOT NULL COMMENT '节点id',
  `fatherid` int(11) NOT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "website_theme"
#

DROP TABLE IF EXISTS `website_theme`;
CREATE TABLE `website_theme` (
  `themeid` int(11) NOT NULL AUTO_INCREMENT,
  `themeName` varchar(40) NOT NULL COMMENT '主题名称',
  `themePath` varchar(100) NOT NULL COMMENT '主题路径',
  PRIMARY KEY (`themeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "welcome"
#

DROP TABLE IF EXISTS `welcome`;
CREATE TABLE `welcome` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `content` varchar(200) NOT NULL COMMENT '文字内容',
  `coverPic` varchar(80) DEFAULT NULL COMMENT '图片路径',
  `link` varchar(100) DEFAULT NULL COMMENT '链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
