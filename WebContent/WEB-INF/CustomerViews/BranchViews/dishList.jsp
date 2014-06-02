<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="dishes-list">
<c:forEach items="${dishList}" var="item">
  <div class="dishes-item">
    <c:if test="${not empty item.dishPic}">
    <img id="${item.dishid}_dishImg" src="${item.dishPic}_small.jpg" class="dishes-item-img" onclick="showDishDetail('${item.dishid}')"/>
    <input id="${item.dishid}_dishImgID" type="hidden" value="${item.dishPic}">
    </c:if>
    <div class="dishes-item-text">
      <h5 onclick="showDishDetail('${item.dishid}')"><div id="${item.dishid}_dishName">${item.dishName}</div><c:if test="${item.price!=0}"><small>${item.price}元/${item.dishUnit}</small></c:if></h5>
      <div id="${item.dishid}_good" class="good" onclick="clickLike('${item.dishid}')">
       <img id="${item.dishid}_goodImg" class="goodImg" src="./img/common/like_black.png" />
       <div class="goodText"><div id="${item.dishid}_goodNum" class="goodTextNum">${item.recomNum}</div><div class="goodTextSub">人赞过</div></div>
      </div>
      <input id="${item.dishid}_dishDesc" type="hidden" value="${item.dishDesc}"/>
    </div>
    <div class="dishes-num">
      <img id="${item.dishid}_countMinus" src="./img/common/round_minus.png" class="dishes-num-minus" onclick="minusCount('${openid}','${branchid}','${item.dishid}')"/>
      <div id="${item.dishid}_count" class="dishes-num-count">${item.count}</div>
      <img id="${item.dishid}_countPlus" src="./img/common/round_plus.png" class="dishes-num-plus" onclick="plusCount('${openid}','${branchid}','${item.dishid}')"/>
    </div>
  </div>
</c:forEach>
</div>