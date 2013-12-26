<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>Elove</title>
    
    <!-- 微喜帖css -->
    <link href="css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/zhonghe-elove.css" rel="stylesheet">
    <link href="css/customer/elove-record.css" rel="stylesheet">
  </head>

  <body class="body-bg">
    <div class="container-fulid">
      <img src="img/elove/records_title.png" class="img-responsive" alt="title background"/>
    </div><!-- title -->
    
    <div class="container-fulid">
      <video id="video" class="video" onclick="playPause()">
        <source src="media/elovedome.webm" type="video/webm">
      </video>
    </div><!-- video -->

    <div class="container-fulid">
      <div class="container-fulid">
        <img src="img/elove/records_photo_title.png" class="img-responsive"/>
      </div>
      <div class="photo-list">
        <img src="img/elove/records_photo.jpg" class="center-block" />
      </div>
    </div><!-- photo -->
      
    <div class="footer">
      <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
    </div><!-- footer -->

    <div onclick="sidebar()">
      <img src="img/elove/sidebar_guide.png" class="sidebar-guide" />
    </div>
    <div id="sidebar_list" class="sidebar hidden" state="down">
      <ul class="nav nav-pills">
        <li><img src="img/elove/music_switch.png" id="audio" onclick="audio_switch()" /></li>
        <li><a href="./customer/elove/story"><span class="sidebar-encount">相知相遇</span></a></li>
        <li class="active"><a href="./customer/elove/dress"><span class="sidebar-photo">婚纱剪影</span></a></li>
        <li><a href="./customer/elove/info"><span class="sidebar-info">婚礼信息</span></a></li>
        <li><a href="./customer/elove/record"><span class="sidebar-record">婚礼记录</span></a></li>
      </ul>
    </div>

    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/elove.js"></script>
    <script type="text/javascript" src="js/customer/elove-video.js"></script>
  </body>
</html>