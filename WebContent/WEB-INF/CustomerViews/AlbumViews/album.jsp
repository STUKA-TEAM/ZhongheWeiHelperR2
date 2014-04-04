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
    
    
  
  <link rel="stylesheet" href="swipelib/swipebox.min.css">
  <link rel="stylesheet" href="css/customer/gallery.css">
  <script src="js/customer/jquery-1.10.2.min.js"></script>
  <script src="js/customer/masonry.pkgd.min.js"></script>
  <script src="js/customer/imagesloaded.pkgd.min.js"></script>
  <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
  <link href="./css/customer/mobile-common.css" rel="stylesheet">
  <link href="./css/customer/wei-website-modulelist.css" rel="stylesheet">
  <script type="text/javascript">
  $( window ).load( function()
		  {
		      $( '#container' ).masonry( { itemSelector: '.item' } );
		  });
  </script>

  </head>
  <body>
  <div id="head">${album.albumName}</div>
  <div id="container">
      <c:forEach items="${album.photoList}" var="item">
      <div class="item">
      <a href="${item.imagePath}_original.jpg" class="swipebox" title="${item.imageDesc}">
        <img class="swipeimg" src="${item.imagePath}_original.jpg" alt="image">
      </a>
      </div>
      </c:forEach>
    </div>
    <%@ include file="../CommonViews/weifooter.jsp"%>
    <%@ include file="../WebsiteViews/bottom.jsp"%>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
  <script src="./swipelib/jquery.swipebox.min.js"></script>
  <script type="text/javascript">
  ( function( $ ) {

    $( '.swipebox' ).swipebox();

  } )( jQuery );
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