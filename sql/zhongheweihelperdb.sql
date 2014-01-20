# Host: localhost  (Version: 5.5.32)
# Date: 2014-01-20 21:46:15
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='用于关联微信公众号的应用';

#
# Data for table "application"
#

INSERT INTO `application` VALUES (16,'c8ed146e83214929931921519a70624d','dshdja','one','sahdsag','dshd','dshdgdsa','shda',NULL),(17,'2ebd55ff9e7c472c9c50b952672f3cd0','dsjdsaad','dhajskd','sdhaskd','hdskhk','khdjshdk','hdhdjsdh',NULL),(18,'f667c84e914d4e338652ecacf083275c','dadadas','dasdsad','sadsad','adsad','dsadsad','adsad',NULL);

#
# Structure for table "application_authority"
#

DROP TABLE IF EXISTS `application_authority`;
CREATE TABLE `application_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `authid` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Data for table "application_authority"
#

INSERT INTO `application_authority` VALUES (14,'c8ed146e83214929931921519a70624d',6),(15,'2ebd55ff9e7c472c9c50b952672f3cd0',6),(16,'f667c84e914d4e338652ecacf083275c',6);

#
# Structure for table "article"
#

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `articleid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `title` varchar(40) NOT NULL COMMENT '文章标题',
  `coverPic` varchar(80) NOT NULL COMMENT '图文消息封面',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `content` text NOT NULL COMMENT '文章内容',
  PRIMARY KEY (`articleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "article"
#


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
# Data for table "article_articleclass"
#


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
# Data for table "articleclass"
#


#
# Structure for table "authority"
#

DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `authid` int(11) NOT NULL AUTO_INCREMENT COMMENT '需权限资源id',
  `authName` varchar(20) NOT NULL DEFAULT '' COMMENT '需权限资源名称',
  `authPinyin` varchar(25) NOT NULL COMMENT '权限名称拼音',
  PRIMARY KEY (`authid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='资源权限表';

#
# Data for table "authority"
#

INSERT INTO `authority` VALUES (1,'微网站','website'),(2,'elove','elove');

#
# Structure for table "customer_app_count"
#

DROP TABLE IF EXISTS `customer_app_count`;
CREATE TABLE `customer_app_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL DEFAULT '0',
  `appUpperLimit` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "customer_app_count"
#


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "customer_authority"
#


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
# Data for table "elove"
#

INSERT INTO `elove` VALUES (1,'c8ed146e83214929931921519a70624d','2014-01-17 20:22:04','2014-07-10 14:39:24','sdadsad','adad','/resources/images/035d7f806fbd474aafd12e98195a8bf1','dsadasd','/resources/images/5c82659ef75d4d1a9356e105ce385dc3','sadasa','dsad','/resources/images/5434d642eb8341b5bfe8b04159bed72c','/resources/media/30e3d4da9f5a4047bfa856a6468ff043','13585563683','qweqewqe','上海',121.510321,31.261016,'sadadsad','safsafsaf','dsadsad','sadsadsad',2),(2,'c8ed146e83214929931921519a70624d','2014-01-11 21:16:45','2014-07-10 21:16:45','two','123','/resources/images/65dbea1a697847ef8daeadf9773a9623','你好','/resources/images/e7b34b661f2e4b3a804d6348545f5272','B','A','/resources/images/6a1a63c9cb284db7a66c9fb64ecc4167','/resources/media/c0713581b4cb42c08cbad077b39ca7c1','13585563683','1234','上海',121.487899,31.249162,'哈哈','呵呵','A','B',2);

#
# Structure for table "elove_consume_record"
#

DROP TABLE IF EXISTS `elove_consume_record`;
CREATE TABLE `elove_consume_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL,
  `notPayNumber` int(11) NOT NULL COMMENT '未付款数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

#
# Data for table "elove_consume_record"
#

INSERT INTO `elove_consume_record` VALUES (13,'c8ed146e83214929931921519a70624d',2),(14,'2ebd55ff9e7c472c9c50b952672f3cd0',0),(15,'f667c84e914d4e338652ecacf083275c',0);

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
# Data for table "elove_images"
#

INSERT INTO `elove_images` VALUES (2,2,'/resources/images/fcbe8415e7f24a5ea5b6cf38fdc9ae75','story'),(3,2,'/resources/images/b8b034f86eac4d539e7a143cdc87cfe1','dress'),(4,2,'/resources/images/ec22afc9a5aa41f4bad460487773494c','record'),(7,1,'/resources/images/9b9dd85ad43d47458ca0c0a112a4c19a','story'),(8,1,'/resources/images/0cc0c56055f044deb67cd47618fd02e3','dress');

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
# Data for table "elove_join"
#


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
# Data for table "elove_message"
#


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
# Data for table "elove_theme"
#

INSERT INTO `elove_theme` VALUES (1,'theme1','/test'),(2,'theme2','/test2');

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
# Data for table "elove_video"
#

INSERT INTO `elove_video` VALUES (1,2,'/resources/media/95149aa24a194846a05124cab9f65e10','dress'),(2,2,'/resources/media/f2411fed9820475199a3977834b13d82','record'),(4,1,'/resources/media/5af1957ee0e04c10a62a314f3c02800a','dress');

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
# Data for table "feedback"
#


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
# Data for table "image_temp_record"
#

INSERT INTO `image_temp_record` VALUES (1,'/resources/images/6a0b6037347e4dc7976fc4c04abb3456','2014-01-19 00:26:47'),(2,'/resources/images/c389d9c05d2b48cfa246b04ef5e4e5fd','2014-01-19 00:31:57'),(3,'/resources/images/d0505a851fa4400db383743eae95ea53','2014-01-19 01:15:30');

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
# Data for table "role"
#

INSERT INTO `role` VALUES (1,'CUSTOMER'),(2,'ADMINISTRATOR'),(3,'AGENT');

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='customer类型user对应的权限价格';

#
# Data for table "store_auth_price"
#

INSERT INTO `store_auth_price` VALUES (1,1,6,30.00),(2,9,6,100.00),(3,10,6,100.00),(4,11,6,100.00),(5,12,6,100.00),(6,13,6,100.00),(7,14,6,100.00),(8,15,6,100.00),(9,16,6,100.00),(10,17,6,100.00),(11,18,6,100.00),(12,19,6,100.00),(13,20,6,100.00),(14,21,6,100.00);

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
# Data for table "storeimage"
#

INSERT INTO `storeimage` VALUES (1,1,'/resources/images/72d9171ef2e5485d9508fbb794706320');

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
  `corpMoreInfoLink` varchar(80) DEFAULT NULL COMMENT '了解更多链接',
  `lng` decimal(12,6) NOT NULL COMMENT '地址经度',
  `lat` decimal(12,6) NOT NULL COMMENT '地址纬度',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='user类型 (平台内账户类型)';

#
# Data for table "storeuser"
#

INSERT INTO `storeuser` VALUES (1,1,'byc','$2a$10$w2a0e8OsEtJ0dPYT//Sc0Oj1NneShu5iXBTppGKi47peA75jtXzVu','2013-12-16 12:16:00','Zhonghe','1311867063@qq.com','','13585563683','浦东新区盛夏路58弄','http://www.baidu.com',121.635941,31.221395),(9,1,'ben','$2a$10$sEGe34AJc7BXrX5961cTUurSDs60uiLx3I1J7zul8JsrXYAtlUlqG','2013-12-30 16:47:47','test','1311867063@qq.com','','13585563683','江苏南通海安','www.baidu.com',120.473927,32.553985),(10,1,'bai','$2a$10$aaFHwN5KpVvv0NLnDIi4..PjtriVOwTyYN5N/rO5oKFwaHs6KLhe.','2013-12-30 17:55:57','test','1311867063@qq.com','','13585563683','江苏南通海安','www.baidu.com',120.473927,32.553985),(11,1,'lubovabc','$2a$10$wzRCgNwIAdGKdo62j6monOGIElg6cXgtiRNe4Ew5MhTzYDielSt.u','2014-01-07 13:33:40','test','1311867063@qq.com','','13585563683','江苏南通海安','http://www.baidu.com',120.473927,32.553985),(12,1,'lubovbyc','$2a$10$RejbON5oLg.w6fv1AhB6nOATWk1heZbvaHBlyX3JF1OnKaTAZAA2.','2014-01-07 13:35:06','test','1311867063@qq.com','','13585563683','江苏南通海安','http://www.baidu.com',120.473927,32.553985),(13,1,'test3','$2a$10$zPyhcXqJ1qI5jZM7R5OWCOEgZ6OZrjuLwFFdWcxSPH.QGXutCVmyC','2014-01-17 23:09:19','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(14,1,'test4','$2a$10$ApNkr6eDQ.CWmC6OYySiduuozyOqEM25/2wATOfXa.I/lHYhzxq5y','2014-01-17 23:29:39','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(15,1,'zhang','$2a$10$YA1WkGS6pgiBWPrKUnxUvuzE82Uc0YdAQEigN3zYjipgZvaZQzqbC','2014-01-17 23:31:06','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(16,1,'zhang2','$2a$10$K6R34XigJ.l54m7Piq9r9eIjgEumDiW2hPk9WmASfN2TdkbUxTDPy','2014-01-17 23:35:29','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(17,1,'zhang3','$2a$10$s8nBtPittqIFx62qZa6YtOTcZjJqApmWXPevWGwy5miUDp/G268i.','2014-01-17 23:37:04','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(18,1,'zhang4','$2a$10$69QuPufOpQo6R7PJt6dbfOywWyI.LUefQSE0Ze.aomPMX49xwAISe','2014-01-17 23:40:36','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(19,1,'zhang5','$2a$10$nIqLBSxkJFj10qZPxToYX.HozlQ5SeVp5XdE3u5Sx90Th3mrrDsgC','2014-01-17 23:42:04','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(20,1,'vicky','$2a$10$pqdccDzX/omJIqXSl6rcnefxBlIU7IP4UF6pFXjuObhEcX1BbzqX6','2014-01-17 23:49:01','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162),(21,1,'vicky2','$2a$10$jqgRJ7Rl8TLDXS9MJv8NGemSfxiNf0KOYHMWEoqr/iTqBoMk54OPG','2014-01-18 14:21:36','test','1311867063@qq.com','','13585563683','上海市','http://www.baidu.com',121.487899,31.249162);

#
# Structure for table "storeuser_application"
#

DROP TABLE IF EXISTS `storeuser_application`;
CREATE TABLE `storeuser_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL DEFAULT '0' COMMENT '众合微信助手账户id',
  `appid` varchar(32) NOT NULL DEFAULT '' COMMENT '众合微信平台应用id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='customer application (customer类型user对应的application)';

#
# Data for table "storeuser_application"
#

INSERT INTO `storeuser_application` VALUES (16,1,'c8ed146e83214929931921519a70624d'),(17,20,'2ebd55ff9e7c472c9c50b952672f3cd0'),(18,15,'f667c84e914d4e338652ecacf083275c');

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
# Data for table "video_temp_record"
#

INSERT INTO `video_temp_record` VALUES (1,'/resources/media/01eba43ab26040c69f8df3e54aca3fb5','2014-01-09 23:12:20'),(2,'/resources/media/42bcc130af624b2eb491d98149c62f38','2014-01-11 12:55:12'),(3,'/resources/media/ed73898b119743b89186cd8b05955611','2014-01-11 14:06:30'),(5,'/resources/media/f5240513613345dcba3dc59d60342506','2014-01-11 20:31:28'),(9,'/resources/media/8625155595d34c42b8a8d563eb672335','2014-01-13 15:46:05'),(10,'/resources/media/94c56dbc06134f6da75e5e1086f490a8','2014-01-13 15:53:06'),(11,'/resources/media/8e9bf16b9e9249a9a39ceeb667964ccb','2014-01-13 15:59:03'),(12,'/resources/media/3b488f4df3504f43a4bbaa3f459aa6e2','2014-01-13 16:00:56'),(13,'/resources/media/17451f5647224028835de67431d4485b','2014-01-13 16:09:58'),(14,'/resources/media/ebc9d5417c6742eb8c21650461159d14','2014-01-13 16:24:44'),(15,'/resources/media/422db2fd4f2048d5a1df180c9624f525','2014-01-13 16:34:56'),(16,'/resources/media/0de6bb7934bf4c49926f5e05f8e98979','2014-01-13 16:42:04'),(17,'/resources/media/086c787a7f3148e3a420d60275ebd0ae','2014-01-13 17:08:22'),(18,'/resources/media/ce8adee761de4a1d8814645eff962b07','2014-01-13 17:16:42'),(19,'/resources/media/e9987e559a884bb89ffbaba02940d765','2014-01-13 17:43:44'),(20,'/resources/media/980c7b9e84144ba9ae5bc0876b89b3a7','2014-01-13 17:54:15'),(21,'/resources/media/046a8e66ef674138a92101604695eb3a','2014-01-13 17:54:31'),(22,'/resources/media/809e82cd777d4f7ea05f6cae6cc4f1e8','2014-01-13 17:57:23'),(23,'/resources/media/1c315717994a48e085542621c0592f2e','2014-01-13 17:57:35'),(24,'/resources/media/9de19c88cfdd4d12be6c59394430a904','2014-01-13 18:01:42'),(25,'/resources/media/9bef528926b243079b264ab5c8bfdcf3','2014-01-13 18:01:47'),(26,'/resources/media/68983aa1d8404ce68e2f63deeb17db07','2014-01-13 18:04:53'),(27,'/resources/media/14a856bffdbe4b0bb75866292c8beae4','2014-01-13 18:07:07'),(28,'/resources/media/851b921416ed41258111686fbbe683f8','2014-01-13 19:20:56'),(29,'/resources/media/3cd2ecae91a3421988874dce7a81e4ef','2014-01-13 19:21:10'),(30,'/resources/media/0a9e16b2fb97498b9daeb5e3d078e351','2014-01-13 19:29:39'),(31,'/resources/media/900a1b914916444b9b8e317c1ffb6da4','2014-01-13 20:22:17'),(32,'/resources/media/a222fddaf67546ffae35dfa499010512','2014-01-13 20:22:55'),(33,'/resources/media/8df7875e2080460e8fbdb3e04dfbe0a9','2014-01-15 11:32:58'),(34,'/resources/media/7fb191fe3e15460782705e5cf60b2a2a','2014-01-15 11:33:12'),(35,'/resources/media/679ac9b62dc54b10aa3f0c4abd97dcce','2014-01-15 11:33:25'),(36,'/resources/media/6036dbecd0c54dae811da0a85e615f4b','2014-01-15 11:34:25'),(37,'/resources/media/8b1845f3fdbc41f2adbe96137c63dd8c','2014-01-15 11:35:13'),(38,'/resources/media/1b16f841a5b2440e8da3adfe5bdb4822','2014-01-15 13:47:53'),(39,'/resources/media/8940e63ce71742acb7cea62c9b30f642','2014-01-15 13:48:07'),(40,'/resources/media/b7adac5e68f54396a4e774127d939b6c','2014-01-15 14:12:30'),(41,'/resources/media/1b01797c16814a4eb03281eb4cbb5e8e','2014-01-15 14:13:47'),(42,'/resources/media/5220dfe3ff764829bf2de7de2c4f5564','2014-01-15 16:59:35'),(43,'/resources/media/7ad4874c007a47c480f009a2f5c2c360','2014-01-15 16:59:48'),(44,'/resources/media/4ccf477cb61d45feadf7dee04ba03388','2014-01-15 17:01:05'),(45,'/resources/media/e031c99eafc14db1af4939d2c41e6f09','2014-01-15 17:17:03'),(46,'/resources/media/9e3f01de71674814b2a14840698ecd42','2014-01-17 19:40:30'),(47,'/resources/media/798a2f3e26ed4caebad7bea30a040cfb','2014-01-17 19:56:33'),(48,'/resources/media/3e2ed6045ac241aca7addc734f8e38b2','2014-01-17 19:57:19'),(49,'/resources/media/dbc8d3629c7c41f4914620b9fbe094b7','2014-01-17 20:20:36');

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
  `lng` decimal(12,6) NOT NULL COMMENT '地址经度',
  `lat` decimal(12,6) NOT NULL COMMENT '地址纬度',
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
# Data for table "website"
#


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
# Data for table "website_image"
#


#
# Structure for table "website_node"
#

DROP TABLE IF EXISTS `website_node`;
CREATE TABLE `website_node` (
  `nodeid` int(11) NOT NULL AUTO_INCREMENT,
  `nodeName` varchar(20) NOT NULL COMMENT '模块名称',
  `nodePic` varchar(80) NOT NULL COMMENT '模块图片',
  `childrenType` varchar(10) NOT NULL COMMENT '子节点类别 (node,article,articleclass)',
  PRIMARY KEY (`nodeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "website_node"
#


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
# Data for table "website_node_article"
#


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
# Data for table "website_node_articleclass"
#


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
# Data for table "website_node_structure"
#


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
# Data for table "website_theme"
#


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

#
# Data for table "welcome"
#

