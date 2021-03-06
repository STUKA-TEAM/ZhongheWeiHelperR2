<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="tuka">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>在线菜单-分店列表</title>
    
    <link href="css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/mobile-common.css" rel="stylesheet">
    <link href="css/customer/store-domain-list.css" rel="stylesheet">

  </head>
  <body>
  <body>
    <div class="container">
    <c:forEach items="${branchList}" var="item">
      <div class="store-model">
        <div class="store-model-content" onclick="location.href='customer/dish/branchMenu?openid=${openid}&appid=${appid}&branchid=${item.branchSid}'">
          <c:if test="${not empty item.imageList[0]}"><img src="${item.imageList[0]}_original.jpg" class="store-image"></c:if>
          <div class="store-text">
            <h4>${item.storeName}</h4>
            <p>${item.address}</p>
          </div>
        </div>
        <div class="store-model-footer">
          <a class="store-nav-4" href="customer/dish/branchMenu?openid=${openid}&appid=${appid}&branchid=${item.branchSid}">
              <img src="./img/icon_lib/calendar02-black.png" class="store-nav-image" alt="article">
              <p class="store-nav-text">菜单</p>
          </a>
          <a class="store-nav-4 store-nav-divide" href="tel:${item.phone}">
            <img src="./img/icon_lib/phone-black.png" class="store-nav-image" alt="article">
            <p class="store-nav-text">预定</p>
          </a>
          <a class="store-nav-4 store-nav-divide" href='http://api.map.baidu.com/marker?location=${item.lat},${item.lng}&amp;title=${item.address}&amp;name=${item.address}&amp;content=${item.address}&amp;output=html'>
            <img src="./img/icon_lib/map02-black.png" class="store-nav-image" alt="article">
            <p class="store-nav-text">导航</p>
          </a>
          <a class="store-nav-4 store-nav-divide" href="customer/dish/showStore?branchid=${item.branchSid}">
              <img src="./img/icon_lib/pic-black.png" class="store-nav-image" alt="article">
              <p class="store-nav-text">实景</p>
          </a>
        </div>
      </div>
      </c:forEach>
    </div>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
  </body>
</html>