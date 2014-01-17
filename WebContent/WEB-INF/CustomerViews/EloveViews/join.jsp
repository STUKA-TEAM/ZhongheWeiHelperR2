<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML> 
<html>
<head>
<title>亲友赴宴信息</title>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0'/> 
<c:set var="request" value="${pageContext.request}" />
<base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
<link href='css/customer/elove.css' rel='stylesheet'/>

</head>
<body>
<div id="container">
<header>
<div id="headerText">
<c:forEach items="${eloveJoinInfoList}" var="join">
<c:set var="sum" value="${sum + join.number}"/> 
</c:forEach>
已有人 ${sum} 要赴宴
</div>
</header>
<div id = "mainBody">
<c:forEach items="${eloveJoinInfoList}" var="join">
<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">${join.name}</div>
<div class = "time">${join.number}人</div>
</div>
<div class = "phone">
${join.phone}
</div>
</div>
</c:forEach>
</div>

<footer><small>&copy; Elove</small></footer>
</div>
</body>
</html>