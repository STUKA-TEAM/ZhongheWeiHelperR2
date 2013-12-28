-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 28, 2013 at 04:12 AM
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于关联微信公众号的应用' AUTO_INCREMENT=2 ;

--
-- Dumping data for table `application`
--

INSERT INTO `application` (`id`, `appid`, `wechatToken`, `wechatName`, `wechatOriginalId`, `wechatNumber`, `address`, `industry`) VALUES
(1, '58e697edb5b148c190db1cf35a835ff9', 'test', 'one', 'sdkadhk', '1234', '北京', '航空');

-- --------------------------------------------------------

--
-- Table structure for table `application_authority`
--

CREATE TABLE IF NOT EXISTS `application_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` int(11) NOT NULL DEFAULT '0' COMMENT 'UUID',
  `authid` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `authority`
--

CREATE TABLE IF NOT EXISTS `authority` (
  `authid` int(11) NOT NULL AUTO_INCREMENT COMMENT '需权限资源id',
  `authName` varchar(20) NOT NULL DEFAULT '' COMMENT '需权限资源名称',
  PRIMARY KEY (`authid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='资源权限表' AUTO_INCREMENT=7 ;

--
-- Dumping data for table `authority`
--

INSERT INTO `authority` (`authid`, `authName`) VALUES
(1, '微网站'),
(2, '图文信息'),
(3, '微活动'),
(4, '微留言'),
(5, '微相册'),
(6, 'elove');

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
  `majorImage` varchar(80) DEFAULT NULL COMMENT '介绍图片',
  `corpMoreInfoLink` varchar(80) DEFAULT NULL COMMENT '了解更多链接',
  `lng` decimal(12,6) NOT NULL COMMENT '地址经度',
  `lat` decimal(12,6) NOT NULL COMMENT '地址纬度',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='众合微信助手平台账户' AUTO_INCREMENT=6 ;

--
-- Dumping data for table `storeuser`
--

INSERT INTO `storeuser` (`sid`, `roleid`, `username`, `password`, `createDate`, `storeName`, `email`, `phone`, `cellPhone`, `address`, `majorImage`, `corpMoreInfoLink`, `lng`, `lat`) VALUES
(1, 1, 'byc', '$2a$10$w2a0e8OsEtJ0dPYT//Sc0Oj1NneShu5iXBTppGKi47peA75jtXzVu', '2013-12-16 04:16:00', 'Zhonghe', '1311867063@qq.com', NULL, '13585563683', '浦东新区盛夏路58弄', NULL, 'www.baidu.com', '0.000000', '0.000000'),
(2, 1, 'ben', '$2a$10$w2a0e8OsEtJ0dPYT//Sc0Oj1NneShu5iXBTppGKi47peA75jtXzVu', '2013-12-17 16:00:00', 'test', NULL, NULL, '15895906036', '上海浦东新区', NULL, NULL, '0.000000', '0.000000'),
(3, 0, 'dai', '$2a$10$EIztIdpVyE4tvFSIezMAiOD0/B.GTO.nqkKipmB3aZQNXtKxRc4Cy', '2013-12-26 12:30:47', 'test3', 'lubovbyc@gmail.com', '', '13585563683', '江苏省南京市东南大学', '/resources/images/602d58909a21480ba10f2b7f9b9010b0', 'www.google.com', '118.799820', '32.061919'),
(4, 1, 'zhang', '$2a$10$deyd5xSMYyqLA3/6fXvfDuKM/2vSDz3quZOd4loyv6lH79uD.q5gi', '2013-12-26 12:42:13', 'test4', '1311867063@qq.com', '', '13585563683', '唐山南苑饭店', '', 'www.google.com', '118.183451', '39.650531'),
(5, 1, 'lubovbyc', '$2a$10$ckAtzSNxmQCfbsX78gaV4.iZD.pj1mKxezK6uxAFJI5HeUjwGm6CO', '2013-12-26 15:54:11', 'one', '1311867063@qq.com', '', '13585563683', '上海市浦东新区', '/resources/images/2365eeb8bc8a4205b667a1ac8ace3e22', 'www.baidu.com', '121.638481', '31.230895');

-- --------------------------------------------------------

--
-- Table structure for table `storeuser_application`
--

CREATE TABLE IF NOT EXISTS `storeuser_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL DEFAULT '0' COMMENT '众合微信助手账户id',
  `appid` varchar(32) NOT NULL DEFAULT '' COMMENT '众合微信平台应用id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `storeuser_application`
--

INSERT INTO `storeuser_application` (`id`, `sid`, `appid`) VALUES
(1, 1, '58e697edb5b148c190db1cf35a835ff9');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `store_auth_price`
--

INSERT INTO `store_auth_price` (`id`, `sid`, `authid`, `price`) VALUES
(1, 1, 6, '30.00');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
