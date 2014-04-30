<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link href="./css/customer/wei-website-c-common.css" rel="stylesheet">
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
  </head>
  <body style="background-image: url('${images[0]}_original.jpg');">
    <div id="wrapper" class="nav-container">
      <div id="scroller" class="nav-bar">
        <div class="nav-item">
          <a href="customer/website/home?websiteid=${website.websiteid}" class="nav-link">
            <p class="nav-item-text">首页</p>
            <img src="./img/icon_lib/home-white.png" class="nav-icon" alt="home">
          </a>
        </div>
        <div class="nav-item">
          <a onclick="location.href='http://api.map.baidu.com/marker?location=${website.lat},${website.lng}&amp;title=${website.address}&amp;name=${website.address}&amp;content=${website.address}&amp;output=html'" class="nav-link">
            <p class="nav-item-text">导航</p>
            <img src="./img/icon_lib/gps-white.png" class="nav-icon" alt="gps">
          </a>
        </div>
        <div class="nav-item">
          <a href="tel:${website.phone}" class="nav-link">
            <p class="nav-item-text">电话</p>
            <img src="./img/icon_lib/phone-white.png" class="nav-icon" alt="phone">
          </a>
        </div>
        <div class="nav-item">
          <a onclick="switch_guide('#guide_bg','#guide_img')" class="nav-link">
            <p class="nav-item-text">分享</p>
            <img src="./img/icon_lib/share-white.png" class="nav-icon" alt="share">
          </a>
        </div>
        <c:forEach items="${nodes}" var="item">
        <div class="nav-item">
          <a href="customer/website/resources?nodeid=${item.nodeid}" class="nav-link">
            <p class="nav-item-text">${item.nodeName}</p>
            <c:if test="${item.nodePic!=''&&item.nodePic!=null}"><img src="${item.nodePic}_original.jpg" class="nav-icon"></c:if>
          </a>
        </div>
        </c:forEach>
      </div>
    </div>
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 
    <script type="application/javascript" src="js/customer/iscroll.js"></script>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/website-8.js"></script>
    <script type="text/javascript">
      var myScroll;
      function loaded() {
        myScroll = new iScroll('wrapper', {hScrollbar:false, vScrollbar:false});
      }
      document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
      document.addEventListener('DOMContentLoaded', loaded, false);
    </script>
    <%@ include file="../CommonViews/shareJS.jsp"%>
    <script type="text/javascript">
    window.shareInfo = new Object();
    shareInfo.imgUrl = '${message.imageLink}';
    shareInfo.link = '${message.appLink}';
    shareInfo.desc = '${message.shareContent}';
    shareInfo.title = '${message.shareTitle}';
    </script>
  </body>
</html>