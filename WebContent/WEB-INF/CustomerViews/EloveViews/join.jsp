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
已有12人要赴宴
</div>
</header>
<div id = "mainBody">

<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">挚友小亮</div>
<div class = "time">3人</div>
</div>
<div class = "phone">
13866666666
</div>
</div>

<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">菲菲</div>
<div class = "time">1人</div>
</div>
<div class = "phone">
13866666666
</div>
</div>

<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">Nika</div>
<div class = "time">3人</div>
</div>
<div class = "phone">
13866666686
</div>
</div>

<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">果果</div>
<div class = "time">1人</div>
</div>
<div class = "phone">
13886666666
</div>
</div>

<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">阿志</div>
<div class = "time">3人</div>
</div>
<div class = "phone">
13866666668
</div>
</div>

<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">小丽</div>
<div class = "time">1人</div>
</div>
<div class = "phone">
13866686666
</div>
</div>

</div>

<footer><small>&copy; Elove</small></footer>
</div>
</body>
</html>