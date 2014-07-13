<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div id="dishesContent" class="dishes-content">
      <div class="dishes-list">
      <c:forEach items="${dishList}" var="item" varStatus="xh">
        <div class="dishes-item">
          <c:if test="${not empty item.dishPic}">
          <img id="${item.dishid}_dishImg" src="${item.dishPic}_small.jpg" class="dishes-item-img" onclick="showDishDetail('${xh.index}')"/>
          <input id="${item.dishid}_dishImgID" type="hidden" value="${item.dishPic}">
          </c:if>
          <div class="dishes-item-text">
            <h5 onclick="showDishDetail('${xh.index}')"><div id="${item.dishid}_dishName">${item.dishName}</div><c:if test="${item.price!=0}"><small>${item.price}元/${item.dishUnit}</small></c:if></h5>
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
          <div>
	        <div id="modalbody" class="modal-body">
	        <c:if test="${not empty item.dishPic}">
	          <img id="dishDetailImg" src="${item.dishPic}_original.jpg">
	        </c:if>
	        </div>
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