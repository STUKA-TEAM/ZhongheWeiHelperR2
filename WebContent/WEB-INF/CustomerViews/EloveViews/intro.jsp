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
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="./css/customer/company.css" rel="stylesheet">
  </head>
  <body>
  
      <div id='mySwipe' class='swipe'>
        <div class='swipe-wrap'>
          <div><img src = 'img/company/company_title.jpg'></div>
          <div><img src = 'img/company/company_title.jpg'></div>
          <div><img src = 'img/company/company_title.jpg'></div>
        </div>
        <nav id="position" class="images-pointer">
          <a class="on"></a>
          <a class="off"></a>
          <a class="off"></a>
        </nav>
      </div>
    <div class="container-fulid">
      <div class="info" onclick="map_switch()">
        <p class="info-t">地址:</p>
        <p class="info-c">上海浦东新区</p>
        <img class="info-i" src="./img/company/green_arrow_right.png" />
      </div>
    </div>
    <div class="container-fulid">
      <div class="info">
        <p class="info-t">电话:</p>
        <p class="info-c"><a class="tip-green" href="tel:12345678901">12345678901</a></p>
        <img class="info-i" src="./img/company/green_arrow_right.png" />
      </div>
    </div>
    <div class="container-fulid">
      <p class="tip">关注我们，获取更多服务与优惠信息  >></p>
    </div>
    <div class="container-fulid attent-bg">
      <div class="attent">         
          <p class="attent-p">关注</p>
      </div>
    </div>
    <div class="container-fulid">
      <p class="tip"><a class="tip-green">查看微官网，了解更多...</a></p>
    </div>
    <div class="container-fulid affix">
      <div class="back">
        <p class="back-p"><a class="tip-green" href="customer/elove/elove">返回Elove</a></p>
      </div>
    </div>
    <div class="container-fulid">
      <div id="baidumap" style="visibility:hidden;"><div id="pic"></div></div>
    </div>
    
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
    <script src="js/customer/swipe.js"></script>
    <script type="text/javascript" src="js/customer/elove-intro.js"></script>
    
    
  </body>
</html>