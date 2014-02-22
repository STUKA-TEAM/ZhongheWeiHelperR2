<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>${website.title}</title>
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="./css/customer/wei-website-2-b1.css" rel="stylesheet">
    <link href="./css/customer/wei-website-b-common.css" rel="stylesheet">
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
  </head>
  <body style="background-image: url('${images[0]}_original.jpg');">
    <div class="container" id="module-list">
      <div class="contentlist clearfix">
      <c:forEach items="${nodes}" var="item">
        <div class="contentlist-item"><a href="customer/website/resources?nodeid=${item.nodeid}" class="noneStyleLinkWhite"><img src="${item.nodePic}_small.jpg" class="contentlist-icon">${item.nodeName}</a></div>
      </c:forEach>
      </div>
    </div>
    <div class="container navlist-right">
      <ul class="list-unstyled navlist">
        <li class="navlist-item"><a class="noneStyleLink"><img src="./img/icon_lib/home-black.png" class="navlist-icon" alt="home"> 首页</a></li>
        <li onclick="location.href='http://api.map.baidu.com/marker?location=${website.lat},${website.lng}&amp;title=${website.address}&amp;name=${website.address}&amp;content=${website.address}&amp;output=html'" class="navlist-item"><a class="noneStyleLink"><img src="./img/icon_lib/gps-black.png" class="navlist-icon" alt="gps"> 导航</a></li>
        <li class="navlist-item"><a class="noneStyleLink" href="tel:${website.phone}"><img src="./img/icon_lib/phone-black.png" class="navlist-icon" alt="phone"> 电话</a></li>
        <li class="navlist-item"><a onclick="switch_guide('#guide_bg','#guide_img')" class="noneStyleLink"><img src="./img/icon_lib/share-black.png" class="navlist-icon" alt="share"> 分享</a></li>
      </ul>
    </div>
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>   
  </body>
</html>