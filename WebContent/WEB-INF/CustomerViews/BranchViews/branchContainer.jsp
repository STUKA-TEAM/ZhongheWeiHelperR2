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
    <meta name="author" content="tuka">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>在线菜单</title>
    
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/dishes-order-branch.css" rel="stylesheet">
    <link href="./css/customer/dishdetail.css" rel="stylesheet">

  </head>
  <body>
  <body>
    <div id="type_wrapper" class="sidebar">
      <input type="hidden" id="currentClass" value="${classList[0].classid}">
      <ul id="scroller" class="sidebar-nav">
        <c:forEach items="${classList}" var="item">
        <li id="${item.classid}_dishClass" class="dishClasLi<c:if test="${item.selected}"> active </c:if>" onclick="changeCurrentClass(this, '${openid}', '${branchid}')"><div id="${item.classid}_dishClassNoticeContainer"><c:if test="${item.dishCount>0}"><div id="${item.classid}_dishClassNotice" class="type-count">${item.dishCount}</div></c:if></div>${item.className}</li>
        </c:forEach>
      </ul>
    </div>
    <div id="dishesListData">
    <div id="dishesContent" class="dishes-content">
      <div class="dishes-list">
      <c:forEach items="${dishList}" var="item" varStatus="xh">
        <div class="dishes-item">
          <c:if test="${not empty item.dishPic}">
          <img id="${item.dishid}_dishImg" src="${item.dishPic}_small.jpg" class="dishes-item-img" onclick="showDishDetail('${item.dishid}', '${xh.index}')"/>
          <input id="${item.dishid}_dishImgID" type="hidden" value="${item.dishPic}">
          </c:if>
          <div class="dishes-item-text">
            <h5 onclick="showDishDetail('${item.dishid}', '${xh.index}')">
              <div id="${item.dishid}_dishName">${item.dishName}</div>
              <c:if test="${item.price!=0}"><small>${item.price}元/${item.dishUnit}</small></c:if>
            </h5>
            <div id="${item.dishid}_good" class="good" onclick="clickLike('${item.dishid}')">
             <img id="${item.dishid}_goodImg" class="goodImg" src="./img/common/like_black.png" />
             <div class="goodText"><div id="${item.dishid}_goodNum" class="goodTextNum">${item.recomNum}</div><div class="goodTextSub">人赞过</div></div>
            </div>
            <input id="${item.dishid}_dishDesc" type="hidden" value="${item.dishDesc}"/>
          </div>

        </div>
      </c:forEach>
      </div>
    </div>
    <div class="modal fade" id="detail" tabindex="-1">
      <div id="myModalSwipe" class="swipe modal-dialog">
        <div class="swipe-wrap modal-content">
        <c:forEach items="${dishList}" var="item">
	      <div id="${item.dishid}_modalbody" class="modal-body">
	        <c:if test="${not empty item.dishPic}">
	          <img id="dishDetailImg" src="${item.dishPic}_original.jpg">
	        </c:if>
	        
	        <div id="dishTopInfo">
	          <div id="dishTitle">${item.dishName}</div>
	          <div id="dishLike">${item.recomNum}人赞过</div>
	        </div>
	        <div id="dishDesc">${item.dishDesc}</div>
	      </div>
        </c:forEach>
        </div>
      </div>
    </div>
    </div>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/modal.min.js"></script> 
    <script type="text/javascript" src="js/customer/jQueryRotateCompressed.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="js/customer/swipe.js"></script>
    <script type="text/javascript" src="js/customer/onlineMenuInWebsite.js"></script>
    
  </body>
</html>