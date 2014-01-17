<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>Elove</title>
    
    <!-- 微喜帖css -->
    <link href="css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/company.css" rel="stylesheet">
    <link href="css/customer/elove-story-1.css" rel="stylesheet">
  </head>
  <body>
  
      <div id='mySwipe' class='swipe'>
        <div class='swipe-wrap'>
        <c:forEach items="${userInfo.imageList}" var="images">
          <div><img src = '${images}_standard.jpg'></div>
        </c:forEach>
        </div>
        <nav id="position" class="images-pointer">
          <c:forEach var="x" begin="1" end="${userInfo.imageList.size()}" step="1">
		  <c:if test="${x=='1'}">
		  <a class="on"></a>
		  </c:if>
		  <c:if test="${x!='1'}">
		  <a class="off"></a>
		  </c:if>
          </c:forEach>
        </nav>
      </div>
    <div class="container-fulid">
      <div class="info" onclick="map_switch()">
        <p class="info-t">地址:</p>
        <p class="info-c">${userInfo.address}</p>
        <img class="info-i" src="./img/company/green_arrow_right.png" />
      </div>
    </div>
    <div class="container-fulid">
      <div class="info">
        <p class="info-t">电话:</p>
        <p class="info-c"><a class="tip-green" href="tel:${userInfo.cellPhone}">${userInfo.cellPhone}</a></p>
        <img class="info-i" src="./img/company/green_arrow_right.png" />
      </div>
    </div>
    <div class="container-fulid">
      <p class="tip">
      <a class="tip" href="${userInfo.corpMoreInfoLink}">
                     获取更多服务与优惠信息，请浏览我们的微官网  >>
      </a></p>
      <input id="lng" type="hidden" value="${userInfo.lng}"/>
      <input id="lat" type="hidden" value="${userInfo.lat}"/>
    </div>
    <div class="container-fulid attent-bg">
      <div class="attent">         
          <p class="attent-p">
          <a href="${userInfo.corpMoreInfoLink}">进入微官网</a>
          </p>
      </div>
    </div>
    <div class="container-fulid affix">
      <div class="back">
        <p class="back-p"><a class="tip-green" href="customer/elove/elove?eloveid=${eloveid}">返回Elove</a></p>
      </div>
    </div>
    <div class="container-fulid">
      <div id="baidumap" style="visibility:hidden;"><div id="pic"></div></div>
    </div>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
    <script src="js/customer/swipe.js"></script>
    <script type="text/javascript" src="js/customer/elove-intro.js"></script>
    <script type="text/javascript" src="js/customer/elove.js"></script>
    
  </body>
</html>