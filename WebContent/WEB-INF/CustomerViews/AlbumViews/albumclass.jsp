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
    <link href="./css/customer/gallery-list.css" rel="stylesheet">
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/wei-website-modulelist.css" rel="stylesheet">
  </head>
  <body>
  
    <div class="container">
      <c:forEach items="${albumList}" var="item">
      <a class="gallery-model noneStyleLinkGray" href="customer/album?albumid=${item.albumid}">
        <div class="gallery-model-content">
          <img src="${item.coverPic}_standard.jpg" class="gallery-image" alt="gallery">
        </div>
        <div class="gallery-model-footer">
          <p class="gallery-name">${item.albumName}</p>
          <p class="gallery-count">${item.photoCount}张</p>
        </div>
      </a>
      </c:forEach>
    </div>
    <%@ include file="../WebsiteViews/bottom.jsp"%>
    <script type="text/javascript" src="./js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/jQueryRotateCompressed.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="./js/customer/bootstrap.min.js"></script>
  </body>
</html>