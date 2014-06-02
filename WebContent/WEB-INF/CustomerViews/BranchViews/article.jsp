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
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>${website.title}</title>
    
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="./css/customer/wei-article.css" rel="stylesheet">
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/wei-website-modulelist.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">
      <div class="article-title">
        <h4>${article.title}</h4>
        <small><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></small>
      </div>
      <div class="article-content">
       ${article.content}
      </div>
      <div class="article-btn-group">
        <button onclick="switch_guide('#guide_bg','#guide_img')" type="button" class="btn btn-default article-btn"><img src="./img/icon_lib/share-grey.png" class="btn-icon"> 发送给朋友</button>
        <button onclick="switch_guide('#guide_bg','#guide_img')" type="button" class="btn btn-default article-btn"><img src="./img/icon_lib/icon_timeline.png" class="btn-icon"> 分享到朋友圈</button>
      </div>
    </div>
    <%@ include file="../CommonViews/weifooter.jsp"%>   
    <%@ include file="../WebsiteViews/bottom.jsp"%> 
    <script type="text/javascript" src="./js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/jQueryRotateCompressed.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="./js/customer/bootstrap.min.js"></script>    
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