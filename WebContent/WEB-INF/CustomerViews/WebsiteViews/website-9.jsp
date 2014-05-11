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
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/wei-website-d-common.css" rel="stylesheet">
  </head>
  <body>
      <div id='mySwipe' class='swipe swipe-bg'>
        <div class='swipe-wrap'>
        <c:forEach items="${images}" var="image">
          <div><img src = '${image}_original.jpg' class="img-responsive img-responsive img-swipe center-block"></div>
        </c:forEach>
        </div>
      </div>
    <div class="container" id="module-list">
      <div class="contentlist clearfix">
      <c:forEach items="${nodes}" var="item">
        <div class="contentlist-item">
          <a href="customer/website/resources?nodeid=${item.nodeid}" class="noneStyleLinkWhite">${item.nodeName}</a>
        </div>
      </c:forEach>
      </div>
    </div>
    <%@ include file="bottom.jsp"%>
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/jQueryRotateCompressed.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="js/customer/swipe.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
        var elem = document.getElementById('mySwipe');
        selectedId = 0;
        window.mySwipe = Swipe(elem, {
          startSlide: 0,
          auto: 4000,
          continuous: true,
        });
    }
    );
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