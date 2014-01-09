-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 08, 2014 at 06:16 PM
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于关联微信公众号的应用' AUTO_INCREMENT=4 ;

--
-- Dumping data for table `application`
--

INSERT INTO `application` (`id`, `appid`, `wechatToken`, `wechatName`, `wechatOriginalId`, `wechatNumber`, `address`, `industry`) VALUES
(1, '58e697edb5b148c190db1cf35a835ff9', 'test', 'one', 'sdkadhk', '1234', '北京', '航空'),
(2, 'cca6ac02cb574875a6741aaf69941c1e', 'jdsdhsajd', 'two', 'dsad', 'hdjsadhksa', 'sjdhadaj', 'hsajdhsad'),
(3, 'b673f2fd3eac4c9fb49aad09f8b7178f', 'jdsdhsajd', 'two', 'dsad', 'hdjsadhksa', 'sjdhadaj', 'hsajdhsad');

-- --------------------------------------------------------

--
-- Table structure for table `application_authority`
--

CREATE TABLE IF NOT EXISTS `application_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL COMMENT 'UUID',
  `authid` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `application_authority`
--

INSERT INTO `application_authority` (`id`, `appid`, `authid`) VALUES
(1, 'b673f2fd3eac4c9fb49aad09f8b7178f', 6);

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
(1, '微网站', 'weiwangzhan'),
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `elove_consume_record`
--

CREATE TABLE IF NOT EXISTS `elove_consume_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL,
  `notPayNumber` int(11) NOT NULL COMMENT '未付款数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `elove_join`
--

CREATE TABLE IF NOT EXISTS `elove_join` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eloveid` int(11) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
(1, 1, 'byc', '$2a$10$w2a0e8OsEtJ0dPYT//Sc0Oj1NneShu5iXBTppGKi47peA75jtXzVu', '2013-12-16 04:16:00', 'Zhonghe', '', '', '13585563683', '浦东新区盛夏路58弄', 'www.baidu.com', '121.635941', '31.221395'),
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `storeuser_application`
--

INSERT INTO `storeuser_application` (`id`, `sid`, `appid`) VALUES
(1, 1, '58e697edb5b148c190db1cf35a835ff9'),
(2, 1, 'cca6ac02cb574875a6741aaf69941c1e'),
(3, 1, 'b673f2fd3eac4c9fb49aad09f8b7178f');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
