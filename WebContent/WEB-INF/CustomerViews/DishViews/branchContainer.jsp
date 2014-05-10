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
    <link href="./css/customer/dishes-order.css" rel="stylesheet">

    <script type="application/javascript" src="./js/customer/iscroll.js"></script>
    <script type="text/javascript">
      var type_scroll;
      function loaded() {
        type_scroll = new iScroll('type_wrapper', {hScrollbar:false, vScrollbar:false});
      }
      document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
      document.addEventListener('DOMContentLoaded', loaded, false);
    </script>

  </head>
  <body>
  <body>
    <div id="type_wrapper" class="sidebar">
      <ul id="scroller" class="sidebar-nav">
        <c:forEach items="${classList}" var="item">
        <li <c:if test="${item.selected}"> class="active" </c:if>><c:if test="${item.dishCount>0}"><div class="type-count">${item.dishCount}</div></c:if>${item.className}</li>
        </c:forEach>
      </ul>
    </div>
    <div class="dishes-content">
      <div class="dishes-list">
      <c:forEach items="${dishList}" var="item">
        <div class="dishes-item">
          <img src="./img/yanghe_wine.jpg" class="dishes-item-img"/>
          <div class="dishes-item-text">
            <h5>${item.dishName}<br><c:if test="${item.price!=0}"><small>${item.price}元/${item.dishUnit}</small></c:if></h5>
            <div id="" class="good">
              <img class="goodImg" src="./img/common/like_black.png" />
             <div class="goodText"><p>${item.recomNum}人赞过</p></div>
            </div>
          </div>
          <div class="dishes-num">
            <img alt="1" src="./img/common/round_minus.png" class="dishes-num-minus"/>
            <div id="count_1" class="dishes-num-count">${item.count}</div>
            <img alt="1" src="./img/common/round_plus.png" class="dishes-num-plus"/>
          </div>
        </div>
      </c:forEach>
      </div>
    </div>
    
    <div class="modal fade" id="detail" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div id="modalbody" class="modal-body">
            <img src="/resources/images/a405fc8160c54620b8a4a7b37255376e_original.jpg">
          </div>
          <div id="dishTopInfo">
          <div id="dishTitle">滑蛋牛柳</div>
          <div id="dishLike">300人赞过</div>
          </div>
          <div id="dishDesc">滑蛋牛柳</div>
        </div>
      </div>
    </div>    
    <div class="my-menu">
      <a data-toggle="modal" data-target="#detail" class="btn btn-sm btn-success">我的菜单</a>
    </div>
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/modal.min.js"></script> 
    <script type="text/javascript" src="js/customer/jQueryRotateCompressed.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script type="text/javascript">
      $(".dishes-num-plus").click(function(){
        var dish_id = $(this).attr("alt");
        var count = parseInt($("#count_"+dish_id).text());
        $("#count_"+dish_id).text(count+1);
      });
      $(".dishes-num-minus").click(function(){
        var dish_id = $(this).attr("alt");
        var count = parseInt($("#count_"+dish_id).text());
        $("#count_"+dish_id).text(count==0?0:count-1);
      });
    </script>
  </body>
</html>