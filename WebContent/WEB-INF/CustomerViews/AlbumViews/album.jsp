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
    <script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript" src="jquery.masonry.min.js"></script>
<script>
$(function(){
    $('.container').masonry({
        itemSelector : '.item'    
    });
});
</script>
  </head>
  <body>
  <div id="MainContent">
    <div class="content-left">
      <a href="./img/gallery/full/001.jpg" class="swipebox" title="cookie">
        <img class="swipeimg" src="./img/gallery/full/001.jpg" alt="image">
      </a>
      <a href="./img/gallery/full/002.jpg" class="swipebox" title="">
        <img class="swipeimg" src="./img/gallery/full/002.jpg" alt="image">
      </a>
      <a href="./img/gallery/full/003.jpg" class="swipebox" title="">
        <img class="swipeimg" src="./img/gallery/full/003.jpg" alt="image">
      </a>
      <a href="./img/gallery/full/004.jpg" class="swipebox" title="My Caption4">
        <img class="swipeimg" src="./img/gallery/full/004.jpg" alt="image">
      </a>
    </div>
    <div class="content-right">
      <a href="./img/gallery/full/005.jpg" class="swipebox" title="My Caption5">
        <img class="swipeimg" src="./img/gallery/full/005.jpg" alt="image">
      </a>
      <a href="./img/gallery/full/006.jpg" class="swipebox" title="My Caption6">
        <img class="swipeimg" src="./img/gallery/full/006.jpg" alt="image">
      </a>
      <a href="./img/gallery/full/007.jpg" class="swipebox" title="My Caption7">
        <img class="swipeimg" src="./img/gallery/full/007.jpg" alt="image">
      </a>
      <a href="./img/gallery/full/008.jpg" class="swipebox" title="糖醋排骨 ">
        <img class="swipeimg" src="./img/gallery/full/008.jpg" alt="image">
      </a>
      <a href="./img/gallery/full/009.jpg" class="swipebox" title="My Caption9">
        <img class="swipeimg" src="./img/gallery/full/009.jpg" alt="image">
      </a>
    </div>
  </div>  

  <script src="./swipelib/jquery-2.1.0.min.js"></script>
  <script src="./swipelib/jquery.swipebox.min.js"></script>

    <script type="text/javascript">
  ;( function( $ ) {

    $( '.swipebox' ).swipebox();

  } )( jQuery );
  </script>
  </body>
</html>