<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="tuka">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>幸运抽奖</title>
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="./css/customer/lottery.css" rel="stylesheet">
  </head>
  <body>
    <div class="container-list center-block">
      <div class="scroll-space"></div>
      <div class="scroll">
        <ul id="itemList" class="list">
          <c:forEach items="${messageList}" var="item">
          <li><p>${item.name}:${item.content}</p></li>
          </c:forEach>
        </ul>
      </div>
      <div class="lottery-btn">
        <img id="start" src="./img/lottery/btn_start.png" class="img-responsive">
      </div>
    </div>
    <div class="result-pop-bg"></div>
    <div class="result-pop">
      <img src="./img/lottery/pop_bg.png" class="img-responsive">
      <dl class="result-pop-text">
        <dt class="bless-name"></dt>
        <dd class="bless-content"></dd>
      </dl>
      <div class="result-pop-btn">
        <img src="./img/lottery/btn-confirm.png" class="img-responsive">
      </div>
    </div>
    <input id="eloveid" type="hidden" value="${eloveid}"/>
    <%@ include file="../CommonViews/footer.jsp"%>
    <script src="./js/customer/jquery-1.10.2.min.js"></script>
    <script src="./js/customer/bootstrap.min.js"></script>
    <script src="./js/customer/lottery.js"></script>

  </body>
</html>