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
    <link href="./css/customer/wei-website-modulelist.css" rel="stylesheet">
  </head>
  <body>
      <div id='mySwipe' class='swipe'>
        <div class='swipe-wrap'>
        <c:forEach items="${imageList}" var="image">
          <div><img src = '${image}_standard.jpg' class="img-responsive center-block"></div>
        </c:forEach>
        </div>
        <nav id="position" class="images-pointer">
          <c:forEach var="x" begin="1" end="${imageList.size()}" step="1">
		  <c:if test="${x=='1'}">
		  <a class="on"></a>
		  </c:if>
		  <c:if test="${x!='1'}">
		  <a class="off"></a>
		  </c:if>
          </c:forEach>
        </nav>
      </div>
    <div class="website-list">
      <ul class="list-unstyled">
		<c:forEach items="${nodeList}" var="item">
        <li class="list-item"><a class="list-link" href="customer/website/resources?nodeid=${item.nodeid}">
        <c:if test="${item.nodePic!=null}"><img src="${item.nodePic}_original.jpg" class="list-item-icon" alt="home"></c:if> ${item.nodeName}</a></li>
        </c:forEach>
      </ul>
    </div>
    <%@ include file="bottom.jsp"%>
    <script type="text/javascript" src="./js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/jQueryRotateCompressed.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="./js/customer/bootstrap.min.js"></script>
    <script src="js/customer/swipe.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	var elem = document.getElementById('mySwipe');
    	selectedId = 0;
    	window.mySwipe = Swipe(elem, {
    	  // startSlide: 4,
    	  auto: 4000,
    	  continuous: true,
    	  // disableScroll: true,
    	  // stopPropagation: true,
    	  callback: function(index, element) {
    		var children = $("#position").children();
    		//alert(childNodes.length);
    	    children[selectedId].className = 'off';
    		selectedId = index;
    		children[selectedId].className = 'on';
    	  },
    	  // transitionEnd: function(index, element) {}
    	});
    }
    );
    </script>
  </body>
</html>


