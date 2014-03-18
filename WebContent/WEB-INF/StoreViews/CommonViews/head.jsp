<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="微信公众账号功能扩展,微信公众账号托管,微信公众账号接口开发,微官网">
<meta name="description" content="图卡微平台 -最好用的公众账号配置工具，为您提供微官网、微投票、微调研、微喜帖等公众号扩展服务。">
<meta name="author" content="tuka">
<c:set var="request" value="${pageContext.request}" />
<base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
<title>图卡微信公共账号管理平台</title>