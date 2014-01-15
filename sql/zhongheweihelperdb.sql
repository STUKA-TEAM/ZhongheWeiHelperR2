-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 15, 2014 at 07:46 AM
-- Server version: 5.5.32
-- PHP Version: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `zhongheweihelperdb`
--
CREATE DATABASE IF NOT EXISTS `zhongheweihelperdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `zhongheweihelperdb`;

-- --------------------------------------------------------

--
-- Table structure for table `application`
--

CREATE TABLE IF NOT EXISTS `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL DEFAULT '' COMMENT 'UUID',
  `wechatToken` varchar(10) NOT NULL DEFAULT '' COMMENT '需填入微信公众平台的token',
  `wechatName` varchar(40) NOT NULL DEFAULT '' COMMENT '微信账号名称',
  `wechatOriginalId` varchar(40) NOT NULL DEFAULT '' COMMENT '微信账号原始ID',
  `wechatNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '微信号',
  `address` varchar(40) NOT NULL DEFAULT '' COMMENT '微信账号地址',
  `industry` varchar(20) NOT NULL DEFAULT '' COMMENT '账号所属行业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于关联微信公众号的应用' AUTO_INCREMENT=17 ;

--
-- Dumping data for table `application`
--

INSERT INTO `application` (`id`, `appid`, `wechatToken`, `wechatName`, `wechatOriginalId`, `wechatNumber`, `address`, `industry`) VALUES
(16, 'c8ed146e83214929931921519a70624d', 'dshdja', 'one', 'sahdsag', 'dshd', 'dshdgdsa', 'shda');

-- --------------------------------------------------------

--
-- Table structure for table `application_authority`
--

CREATE TABLE IF NOT EXISTS `application_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `authid` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `application_authority`
--

INSERT INTO `application_authority` (`id`, `appid`, `authid`) VALUES
(14, 'c8ed146e83214929931921519a70624d', 6);

-- --------------------------------------------------------

--
-- Table structure for table `authority`
--

CREATE TABLE IF NOT EXISTS `authority` (
  `authid` int(11) NOT NULL AUTO_INCREMENT COMMENT '需权限资源id',
  `authName` varchar(20) NOT NULL DEFAULT '' COMMENT '需权限资源名称',
  `authPinyin` varchar(25) NOT NULL COMMENT '权限名称拼音',
  PRIMARY KEY (`authid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='资源权限表' AUTO_INCREMENT=7 ;

--
-- Dumping data for table `authority`
--

INSERT INTO `authority` (`authid`, `authName`, `authPinyin`) VALUES
(1, '微网站', 'website'),
(2, '图文信息', 'tuwenxinxi'),
(3, '微活动', 'weihuodong'),
(4, '微留言', 'weiliuyan'),
(5, '微相册', 'weixiangce'),
(6, 'elove', 'elove');

-- --------------------------------------------------------

--
-- Table structure for table `elove`
--

CREATE TABLE IF NOT EXISTS `elove` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `elove`
--

INSERT INTO `elove` (`eloveid`, `appid`, `createTime`, `expiredTime`, `title`, `password`, `coverPic`, `coverText`, `majorGroupPhoto`, `xinNiang`, `xinLang`, `storyTextImagePath`, `music`, `phone`, `weddingDate`, `weddingAddress`, `lng`, `lat`, `shareTitle`, `shareContent`, `footerText`, `sideCorpInfo`, `themeid`) VALUES
(1, 'c8ed146e83214929931921519a70624d', '2014-01-11 06:39:24', '2014-07-10 06:39:24', 'sdadsad', 'adad', '/resources/images/035d7f806fbd474aafd12e98195a8bf1', 'dsadasd', '/resources/images/5c82659ef75d4d1a9356e105ce385dc3', 'sadasa', 'dsad', '/resources/images/5434d642eb8341b5bfe8b04159bed72c', '/resources/media/30e3d4da9f5a4047bfa856a6468ff043', '13585563683', 'qweqewqe', '上海', '121.510321', '31.261016', 'sadadsad', 'safsafsaf', 'dsadsad', 'sadsadsad', 0),
(2, 'c8ed146e83214929931921519a70624d', '2014-01-11 13:16:45', '2014-07-10 13:16:45', 'two', '123', '/resources/images/65dbea1a697847ef8daeadf9773a9623', '你好', '/resources/images/e7b34b661f2e4b3a804d6348545f5272', 'B', 'A', '/resources/images/6a1a63c9cb284db7a66c9fb64ecc4167', '/resources/media/c0713581b4cb42c08cbad077b39ca7c1', '13585563683', '1234', '上海', '121.487899', '31.249162', '哈哈', '呵呵', 'A', 'B', 2);

-- --------------------------------------------------------

--
-- Table structure for table `elove_consume_record`
--

CREATE TABLE IF NOT EXISTS `elove_consume_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL,
  `notPayNumber` int(11) NOT NULL COMMENT '未付款数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `elove_consume_record`
--

INSERT INTO `elove_consume_record` (`id`, `appid`, `notPayNumber`) VALUES
(13, 'c8ed146e83214929931921519a70624d', 2);

-- --------------------------------------------------------

--
-- Table structure for table `elove_images`
--

CREATE TABLE IF NOT EXISTS `elove_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `imagePath` varchar(80) NOT NULL COMMENT '图片路径（未完全）',
  `imageType` varchar(10) NOT NULL COMMENT '图片分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `elove_images`
--

INSERT INTO `elove_images` (`id`, `eloveid`, `imagePath`, `imageType`) VALUES
(1, 1, '/resources/images/9b9dd85ad43d47458ca0c0a112a4c19a', 'story'),
(2, 2, '/resources/images/fcbe8415e7f24a5ea5b6cf38fdc9ae75', 'story'),
(3, 2, '/resources/images/b8b034f86eac4d539e7a143cdc87cfe1', 'dress'),
(4, 2, '/resources/images/ec22afc9a5aa41f4bad460487773494c', 'record');

-- --------------------------------------------------------

--
-- Table structure for table `elove_join`
--

CREATE TABLE IF NOT EXISTS `elove_join` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `name` varchar(20) NOT NULL COMMENT '赴宴人姓名',
  `phone` varchar(14) NOT NULL COMMENT '联系电话',
  `number` int(11) NOT NULL COMMENT '人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `elove_message`
--

CREATE TABLE IF NOT EXISTS `elove_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `name` varchar(20) NOT NULL COMMENT '祝福人姓名',
  `content` varchar(400) NOT NULL COMMENT '祝福内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `elove_theme`
--

CREATE TABLE IF NOT EXISTS `elove_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `themeName` varchar(40) NOT NULL COMMENT '主题名称',
  `themePath` varchar(100) NOT NULL COMMENT '主题css文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `elove_theme`
--

INSERT INTO `elove_theme` (`id`, `themeName`, `themePath`) VALUES
(1, 'theme1', '/test'),
(2, 'theme2', '/test2');

-- --------------------------------------------------------

--
-- Table structure for table `elove_video`
--

CREATE TABLE IF NOT EXISTS `elove_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
  `videoPath` varchar(80) NOT NULL COMMENT '视频路径',
  `videoType` varchar(10) NOT NULL COMMENT '视频分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `elove_video`
--

INSERT INTO `elove_video` (`id`, `eloveid`, `videoPath`, `videoType`) VALUES
(1, 2, '/resources/media/95149aa24a194846a05124cab9f65e10', 'dress'),
(2, 2, '/resources/media/f2411fed9820475199a3977834b13d82', 'record');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE IF NOT EXISTS `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '商户id',
  `message` varchar(600) NOT NULL COMMENT '反馈消息',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `image_temp_record`
--

CREATE TABLE IF NOT EXISTS `image_temp_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imagePath` varchar(80) NOT NULL COMMENT '临时图片路径（未完全）',
  `createDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `appDefaultMaxCount` int(11) NOT NULL DEFAULT '0' COMMENT '可关联公众账号数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `roleName`, `appDefaultMaxCount`) VALUES
(1, 'ROLE_USER', 1);

-- --------------------------------------------------------

--
-- Table structure for table `role_authority`
--

CREATE TABLE IF NOT EXISTS `role_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `authorityid` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  `authorityDefaultCount` int(11) NOT NULL DEFAULT '0' COMMENT '该权限可使用次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `storeimage`
--

CREATE TABLE IF NOT EXISTS `storeimage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '所属用户id',
  `imagePath` varchar(80) NOT NULL COMMENT '图片路径（未完全）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `storeimage`
--

INSERT INTO `storeimage` (`id`, `sid`, `imagePath`) VALUES
(1, 1, '/resources/images/72d9171ef2e5485d9508fbb794706320');

-- --------------------------------------------------------

--
-- Table structure for table `storeuser`
--

CREATE TABLE IF NOT EXISTS `storeuser` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='众合微信助手平台账户' AUTO_INCREMENT=13 ;

--
-- Dumping data for table `storeuser`
--

INSERT INTO `storeuser` (`sid`, `roleid`, `username`, `password`, `createDate`, `storeName`, `email`, `phone`, `cellPhone`, `address`, `corpMoreInfoLink`, `lng`, `lat`) VALUES
(1, 1, 'byc', '$2a$10$w2a0e8OsEtJ0dPYT//Sc0Oj1NneShu5iXBTppGKi47peA75jtXzVu', '2013-12-16 04:16:00', 'Zhonghe', '1311867063@qq.com', '', '13585563683', '浦东新区盛夏路58弄', 'http://www.baidu.com', '121.635941', '31.221395'),
(9, 1, 'ben', '$2a$10$sEGe34AJc7BXrX5961cTUurSDs60uiLx3I1J7zul8JsrXYAtlUlqG', '2013-12-30 08:47:47', 'test', '1311867063@qq.com', '', '13585563683', '江苏南通海安', 'www.baidu.com', '120.473927', '32.553985'),
(10, 1, 'bai', '$2a$10$aaFHwN5KpVvv0NLnDIi4..PjtriVOwTyYN5N/rO5oKFwaHs6KLhe.', '2013-12-30 09:55:57', 'test', '1311867063@qq.com', '', '13585563683', '江苏南通海安', 'www.baidu.com', '120.473927', '32.553985'),
(11, 1, 'lubovabc', '$2a$10$wzRCgNwIAdGKdo62j6monOGIElg6cXgtiRNe4Ew5MhTzYDielSt.u', '2014-01-07 05:33:40', 'test', '1311867063@qq.com', '', '13585563683', '江苏南通海安', 'http://www.baidu.com', '120.473927', '32.553985'),
(12, 1, 'lubovbyc', '$2a$10$RejbON5oLg.w6fv1AhB6nOATWk1heZbvaHBlyX3JF1OnKaTAZAA2.', '2014-01-07 05:35:06', 'test', '1311867063@qq.com', '', '13585563683', '江苏南通海安', 'http://www.baidu.com', '120.473927', '32.553985');

-- --------------------------------------------------------

--
-- Table structure for table `storeuser_application`
--

CREATE TABLE IF NOT EXISTS `storeuser_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL DEFAULT '0' COMMENT '众合微信助手账户id',
  `appid` varchar(32) NOT NULL DEFAULT '' COMMENT '众合微信平台应用id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `storeuser_application`
--

INSERT INTO `storeuser_application` (`id`, `sid`, `appid`) VALUES
(16, 1, 'c8ed146e83214929931921519a70624d');

-- --------------------------------------------------------

--
-- Table structure for table `store_auth_price`
--

CREATE TABLE IF NOT EXISTS `store_auth_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '商户id',
  `authid` int(11) NOT NULL COMMENT '权限id',
  `price` decimal(15,2) NOT NULL COMMENT '单价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `store_auth_price`
--

INSERT INTO `store_auth_price` (`id`, `sid`, `authid`, `price`) VALUES
(1, 1, 6, '30.00'),
(2, 9, 6, '100.00'),
(3, 10, 6, '100.00'),
(4, 11, 6, '100.00'),
(5, 12, 6, '100.00');

-- --------------------------------------------------------

--
-- Table structure for table `video_temp_record`
--

CREATE TABLE IF NOT EXISTS `video_temp_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `videoPath` varchar(80) NOT NULL COMMENT '临时视频路径',
  `createDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=42 ;

--
-- Dumping data for table `video_temp_record`
--

INSERT INTO `video_temp_record` (`id`, `videoPath`, `createDate`) VALUES
(1, '/resources/media/01eba43ab26040c69f8df3e54aca3fb5', '2014-01-09 15:12:20'),
(2, '/resources/media/42bcc130af624b2eb491d98149c62f38', '2014-01-11 04:55:12'),
(3, '/resources/media/ed73898b119743b89186cd8b05955611', '2014-01-11 06:06:30'),
(4, '/resources/media/30e3d4da9f5a4047bfa856a6468ff043', '2014-01-11 06:37:44'),
(5, '/resources/media/f5240513613345dcba3dc59d60342506', '2014-01-11 12:31:28'),
(9, '/resources/media/8625155595d34c42b8a8d563eb672335', '2014-01-13 07:46:05'),
(10, '/resources/media/94c56dbc06134f6da75e5e1086f490a8', '2014-01-13 07:53:06'),
(11, '/resources/media/8e9bf16b9e9249a9a39ceeb667964ccb', '2014-01-13 07:59:03'),
(12, '/resources/media/3b488f4df3504f43a4bbaa3f459aa6e2', '2014-01-13 08:00:56'),
(13, '/resources/media/17451f5647224028835de67431d4485b', '2014-01-13 08:09:58'),
(14, '/resources/media/ebc9d5417c6742eb8c21650461159d14', '2014-01-13 08:24:44'),
(15, '/resources/media/422db2fd4f2048d5a1df180c9624f525', '2014-01-13 08:34:56'),
(16, '/resources/media/0de6bb7934bf4c49926f5e05f8e98979', '2014-01-13 08:42:04'),
(17, '/resources/media/086c787a7f3148e3a420d60275ebd0ae', '2014-01-13 09:08:22'),
(18, '/resources/media/ce8adee761de4a1d8814645eff962b07', '2014-01-13 09:16:42'),
(19, '/resources/media/e9987e559a884bb89ffbaba02940d765', '2014-01-13 09:43:44'),
(20, '/resources/media/980c7b9e84144ba9ae5bc0876b89b3a7', '2014-01-13 09:54:15'),
(21, '/resources/media/046a8e66ef674138a92101604695eb3a', '2014-01-13 09:54:31'),
(22, '/resources/media/809e82cd777d4f7ea05f6cae6cc4f1e8', '2014-01-13 09:57:23'),
(23, '/resources/media/1c315717994a48e085542621c0592f2e', '2014-01-13 09:57:35'),
(24, '/resources/media/9de19c88cfdd4d12be6c59394430a904', '2014-01-13 10:01:42'),
(25, '/resources/media/9bef528926b243079b264ab5c8bfdcf3', '2014-01-13 10:01:47'),
(26, '/resources/media/68983aa1d8404ce68e2f63deeb17db07', '2014-01-13 10:04:53'),
(27, '/resources/media/14a856bffdbe4b0bb75866292c8beae4', '2014-01-13 10:07:07'),
(28, '/resources/media/851b921416ed41258111686fbbe683f8', '2014-01-13 11:20:56'),
(29, '/resources/media/3cd2ecae91a3421988874dce7a81e4ef', '2014-01-13 11:21:10'),
(30, '/resources/media/0a9e16b2fb97498b9daeb5e3d078e351', '2014-01-13 11:29:39'),
(31, '/resources/media/900a1b914916444b9b8e317c1ffb6da4', '2014-01-13 12:22:17'),
(32, '/resources/media/a222fddaf67546ffae35dfa499010512', '2014-01-13 12:22:55'),
(33, '/resources/media/8df7875e2080460e8fbdb3e04dfbe0a9', '2014-01-15 03:32:58'),
(34, '/resources/media/7fb191fe3e15460782705e5cf60b2a2a', '2014-01-15 03:33:12'),
(35, '/resources/media/679ac9b62dc54b10aa3f0c4abd97dcce', '2014-01-15 03:33:25'),
(36, '/resources/media/6036dbecd0c54dae811da0a85e615f4b', '2014-01-15 03:34:25'),
(37, '/resources/media/8b1845f3fdbc41f2adbe96137c63dd8c', '2014-01-15 03:35:13'),
(38, '/resources/media/1b16f841a5b2440e8da3adfe5bdb4822', '2014-01-15 05:47:53'),
(39, '/resources/media/8940e63ce71742acb7cea62c9b30f642', '2014-01-15 05:48:07'),
(40, '/resources/media/b7adac5e68f54396a4e774127d939b6c', '2014-01-15 06:12:30'),
(41, '/resources/media/1b01797c16814a4eb03281eb4cbb5e8e', '2014-01-15 06:13:47');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
