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
        <!-- Default panel contents -->
        <div class="panel-heading">Elove使用记录（仅显示未付款Elove）</div>
        <table class="table">
          <tr>
            <th>创建日期</th>
            <th>新人姓名</th>
          </tr>
          <tr>
            <td>2014-1-23</td>
            <td>李雷/韩梅梅</td>
          </tr>
          <tr>
            <td>2014-1-23</td>
            <td>李雷/韩梅梅</td>
          </tr>
          <tr>
            <td>2014-1-23</td>
            <td>李雷/韩梅梅</td>
          </tr>
        </table>        
        <div class="panel-body">
          <div class="container">
            <div class="clearfix">
              <p class="pull-left"><strong>总计:</strong></p>
              <p class="bill-value pull-left">3套</p>
            </div>
            <div class="clearfix">
              <p class="pull-left"><strong>单价:</strong></p>
              <p class="bill-value pull-left">80元</p>
            </div>
            <div class="clearfix">
              <p class="pull-left"><strong>未付款金额:</strong></p>
              <p class="bill-value pull-left">240元</p>
            </div>
            <p><strong>如有问题，请联系您的客户经理白先生 </strong>13818031305</p>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>