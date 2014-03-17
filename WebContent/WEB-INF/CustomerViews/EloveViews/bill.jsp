<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="tuka">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>Elove使用记录</title>
 
    <link href="css/store/bootstrap.min.css" rel="stylesheet">
    <link href="css/store/bill.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">
      <div class="panel panel-info">
      <div class="panel-heading">Elove使用记录（仅显示未付款Elove）</div>
      </div>
        <!-- Default panel contents -->
        <c:forEach items="${eloveUsedMap}" var="item">
        
        <div class="panel panel-default">
        <div class="panel-heading">微信名：${item.key}</div>
        <table class="table">
          <tr>
            <th>创建日期</th>
            <th>新人姓名</th>
          </tr>
          <c:forEach items="${item.value}" var="elove">
          <tr>
            <td><fmt:formatDate value="${elove.createTime}" pattern="yyyy-MM-dd"/></td>
            <td>${elove.xinLang}/${elove.xinNiang}</td>
          </tr>
          </c:forEach>
        </table>
        </div>

        </c:forEach>        
        <div class="well well-lg">
          <div class="container">
            <div class="clearfix">
              <p class="pull-left"><strong>总计:</strong></p>
              <p class="bill-value pull-left">${notPaySum}套</p>
            </div>
            <div class="clearfix">
              <p class="pull-left"><strong>单价:</strong></p>
              <p class="bill-value pull-left">${elovePrice}元</p>
            </div>
            <div class="clearfix">
              <p class="pull-left"><strong>未付款金额:</strong></p>
              <p class="bill-value pull-left">${notPayMoney}元</p>
            </div>
            <p><strong>如有问题，请联系您的客户经理： </strong>${phoneNum}</p>
          </div>
        </div>
    </div>
    <%@ include file="../CommonViews/footer.jsp"%>
  </body>
</html>