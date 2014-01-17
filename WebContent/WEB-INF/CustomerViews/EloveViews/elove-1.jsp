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
    <title>${elove.title}</title>
    
    <link href="css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/zhonghe-elove-1.css" rel="stylesheet">
    
    <link href="css/customer/elove-story-1.css" rel="stylesheet">
    
    <link href="css/customer/elove-record-1.css" rel="stylesheet">
    <link href="css/customer/video-js.min.css" rel="stylesheet">

    <link href="css/customer/elove-info-1.css" rel="stylesheet">
  </head>
  <body>
    <div id="content-container">
      <%@ include file="story-1.jsp"%>
    </div>
    <div class="container-fulid">
      <div id="baidumap" style="visibility:hidden;"><div id="pic"></div></div>
    </div>
    <div class="sidebar-guide" onclick="sidebar_up()">
      <img src="img/elove/sidebar_guide_1.png" class="sidebar-guide-logo"/>
      <p class="sidebar-guide-tip"><strong>点点我~</strong></p>
    </div>
    <div class="sidebar-bg sidebar-bg-miss hidden" onclick="sidebar_dismiss()"></div>
    <div id="sidebar_list" class="sidebar hidden" state="down">
      <div id="audio" class="audio" onclick="audio_switch(1)">
        <img src="img/elove/audio_on_1.png" class="audio-logo"/>
        <p id="audio-p">音效开启</p>
        <input id="eloveid" type="hidden" value="${elove.eloveid}" />
      </div>
      <ul class="nav nav-pills">
        <li id="story" class="active"><a href="javascript:void(0)"><span class="sidebar-encount">相知相遇</span></a></li>
        <li id="dress"><a href="javascript:void(0)"><span class="sidebar-photo">婚纱剪影</span></a></li>
        <li id="info"><a href="javascript:void(0)"><span class="sidebar-info">婚礼信息</span></a></li>
        <li id="record"><a href="javascript:void(0)"><span class="sidebar-record">婚礼记录</span></a></li>
        <li id="viewAccount"><a href="javascript:void(0)" onclick="switch_guide('#account_guide_bg','#account_guide_img')">
          <span class="sidebar-viewAccount">关注账号</span></a>
        </li>
        <li id="intro"><a href="customer/elove/intro?eloveid=${elove.eloveid}"><span class="sidebar-intr">${elove.sideCorpInfo}</span></a></li>
      </ul>
    </div>
    <div id="account_guide_bg" class="guide hidden" onclick="close_guide('#account_guide_bg','#account_guide_img')">
      <img id="account_guide_img" class="guide-pic img-responsive hidden" src="img/common/account_guide.png"/>
    </div>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
    <script type="text/javascript" src="js/customer/elove.js"></script>
    <script type="text/javascript" src="js/customer/modal.min.js"></script>    
    <script type="text/javascript" src="js/customer/video.js"></script> 
  </body>
</html>