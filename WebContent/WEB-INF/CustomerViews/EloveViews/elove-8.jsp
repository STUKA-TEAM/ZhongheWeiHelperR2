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
    <title>${elove.title}</title>
    
    <link href="css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="swipelib/swipebox.min.css" rel="stylesheet">
    <link href="css/customer/zhonghe-elove-8.css" rel="stylesheet">
    <link href="css/customer/elove-common.css" rel="stylesheet">
    
    <link href="css/customer/elove-story-8.css" rel="stylesheet">
    
    <link href="css/customer/elove-record-8.css" rel="stylesheet">
    <link href="css/customer/video-js.min.css" rel="stylesheet">

    <link href="css/customer/elove-info-8.css" rel="stylesheet">
  </head>
  <body>
    <div class="nav-btn">
      <span class="glyphicon glyphicon-chevron-right"></span>
    </div>
    <div id="wrapper" class="top-nav">
      <div id="scroller" class="top-navbar">
        <div id="audio" class="audio-item pull-left" onclick="audio_switch(8)">
          <input type="hidden" id="musicPath" value="${elove.music}"/>
          <img src="img/elove/audio_on_8.png" class="audio-logo nav-logo"/>
          <input id="eloveid" type="hidden" value="${elove.eloveid}" />
        </div>
        <div id="story" class="top-nav-item pull-left active">
          <a href="javascript:void(0)"><img src="img/elove/encount_logo_8.png" class="nav-logo"/>幸福宝贝</a>
        </div>
        <div id="dress" class="top-nav-item pull-left">
          <a href="javascript:void(0)"><img src="img/elove/photo_logo_8.png" class="nav-logo"/>宝宝相册</a>
        </div>
        <div id="info" class="top-nav-item pull-left">
          <a href="javascript:void(0)"><img src="img/elove/info_logo_8.png" class="nav-logo"/>宴会信息</a>
        </div>
        <c:if test="${recordImages.size()!=0}">
        <div id="record" class="top-nav-item pull-left">
          <a href="javascript:void(0)"><img src="img/elove/record_logo_8.png" class="nav-logo"/>宴会记录</a>
        </div>
        </c:if>
        <div id="viewAccount" class="top-nav-item pull-left">
          <a href="javascript:void(0)" onclick="switch_guide('#account_guide_bg','#account_guide_img')"><img src="img/elove/viewAcc_logo_8.png" class="nav-logo"/>关注公司</a>
        </div>
        <div id="intro" class="top-nav-item pull-left">
          <a href="customer/elove/intro?eloveid=${elove.eloveid}"><img src="img/elove/intr_logo_8.png" class="nav-logo"/>${elove.sideCorpInfo}</a>
        </div>
      </div>
    </div>
    <div class="top-navbg"></div>
    <div id="content-container">
      <%@ include file="story-8.jsp"%>
    </div>
    <div class="container-fulid">
      <div id="baidumap" style="visibility:hidden;"><div id="pic"></div></div>
    </div>
    <div id="account_guide_bg" class="guide hidden" onclick="close_guide('#account_guide_bg','#account_guide_img')">
      <img id="account_guide_img" class="guide-pic img-responsive hidden" src="img/common/account_guide.png"/>
    </div>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/elove.js"></script>
    <script type="text/javascript" src="js/customer/modal.min.js"></script>    
    <script type="text/javascript" src="js/customer/video.js"></script>  
    <script type="application/javascript" src="js/customer/iscroll.js"></script>
    <script type="text/javascript">
      var myScroll;
      function loaded() {
        myScroll = new iScroll('wrapper', {hScrollbar:false, vScrollbar:false});
      }
      document.addEventListener('DOMContentLoaded', loaded, false);
      $(".nav-btn").click(function(){
    	  myScroll.scrollToPage(3, 0, 300);
    	  $(".nav-btn").addClass("hidden");
      });
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