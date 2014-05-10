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
    <link href="./css/customer/menu-list.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">
      <div class="menu-list-model">
        <h4>我的菜单</h4>
        <div class="table-responsive">
          <table class="table">
            <tbody>
              <c:forEach items="${branchList}" var="item">
              <tr>
                <td>${item.storeName}</td>
                <td><a href="customer/dish/order?branchid=${item.branchSid}&openid=${openid}&appid=${appid}&type=my" class="btn btn-sm btn-success">查看</a></td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>



    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/jQueryRotateCompressed.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
  </body>
</html>