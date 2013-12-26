<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
  <head>
    <meta charset="utf-8">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <link rel="shortcut icon" href="img/favicon.png">
  </head>
    <body>
     
        <h1 id="banner">Unauthorized Access !!</h1>
     
        <hr />
     
        <c:if test="${not empty error}">
            <div style="color:red">
                Your fake login attempt was bursted, dare again !!<br /> 
                Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>
     
        <p class="message">登录失败！</p>
        <a href="security/login">重新登录</a> 
    </body>
</html>