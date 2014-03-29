<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="./swipelib/jquery.swipebox.min.js"></script>
<script type="text/javascript">
  ( function( $ ) {

    $( '.swipebox' ).swipebox();

  } )( jQuery );
</script>
<div class="photo-list">
<c:forEach items="${recordImages}" var="image">
<a href="${image}_original.jpg" class="swipebox" title="">
  <img src="${image}_original.jpg" class="center-block originalImg"/>
</a>
</c:forEach>
</div>
