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
    <title>我的菜单</title>
    
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/menu-order.css" rel="stylesheet">
    <link href="./css/customer/dishdetail.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">
      <div class="menu-info clearfix">
        <p class="menu-info-text-important pull-left">请叫服务员下单</p>
        <c:if test="${sumPrice!=0}"><p id="sumPrice" class="menu-info-price pull-right">${sumPrice}元</p>
        <p class="menu-info-text pull-right">共计： </p></c:if>
      </div>
      <div class="menu-order-model">
        <h5 class="menu-title">我的菜单<br><small>${branch.storeName}</small></h5>
        <c:if test="${type=='my'}">
        <div class="menu-btn-group">
          <a href="customer/dish/branchMenu?openid=${openid}&appid=${appid}&branchid=${branchid}" class="btn btn-xs btn-success"><span class="glyphicon glyphicon-plus"></span> 加菜</a>
          <a href="javascript:void(0)" onclick="deleteAll('${openid}','${branchid}', '${appid}')" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-trash"></span> 清空</a>
        </div>
        </c:if>
        <c:forEach items="${dishMap}" var="mapItem"> 
        <div class="menu-type">
          <h5>${dishClassMap[mapItem.key]}</h5>
          <c:forEach items="${mapItem.value}" var="item">
          <div class="menu-dish">
            <img id="${item.dishid}_dishImg" src="${item.dishPic}_small.jpg" class="dishes-item-img" onclick="showDishDetail('${item.dishid}')"/>
            <input id="${item.dishid}_dishImgID" type="hidden" value="${item.dishPic}">
            <h5 class="menu-dish-info"><div id="${item.dishid}_dishName">${item.dishName}</div><c:if test="${item.price!=0}"><small id="${item.dishid}_dishPrice">${item.price}元/${item.dishUnit}</small></c:if></h5>
            <div id="${item.dishid}_goodNum" class="hiddenRecomNum">${item.recomNum}</div>
            <div class="dishes-num">
            <c:if test="${type=='my'}">
            <img id="${item.dishid}_countMinus" src="./img/common/round_minus.png" class="dishes-num-minus" onclick="minusCountForOrder('${openid}','${branchid}','${item.dishid}')"/>
            <div id="${item.dishid}_count" class="dishes-num-count">${item.count}</div>
            <img id="${item.dishid}_countPlus" src="./img/common/round_plus.png" class="dishes-num-plus" onclick="plusCountForOrder('${openid}','${branchid}','${item.dishid}')"/>
            </c:if>
            <c:if test="${type=='other'}">
            <div id="${item.dishid}_count" class="dishes-num-count-other">${item.count}${item.dishUnit}</div>
            </c:if>
            </div>
             <input id="${item.dishid}_dishDesc" type="hidden" value="${item.dishDesc}"/>
          </div>
          </c:forEach>
        </div>
        </c:forEach>
      </div>
      <button id="shareButton" type="button" class="btn btn-success btn-share" onclick="switch_guide('#guide_bg','#guide_img')">分享给好友</button>
    </div>
    
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 
    <%@ include file="dishDetail.jsp"%>

    <%@ include file="../CommonViews/weifooter.jsp"%>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/modal.min.js"></script> 
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script type="text/javascript" src="js/customer/onlineMenu.js"></script>
    
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