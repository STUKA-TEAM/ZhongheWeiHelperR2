<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML> 
<html>
<head>
<title>我们的祝福收件箱</title>
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
已经收到${messageList.size()}条祝福！ 
</div>
</header>
<div id = "mainBody">
<c:forEach items="${messageList}" var="mes">
<div class = "textBox">
<div class = "firstLine">
<div class = "pic"></div>
<div class = "name">${mes.name}</div>
<div class = "time"><fmt:formatDate pattern="yyyy-MM-dd" 
            value="${mes.createTime}" /></div>
</div>
<div class = "content">
${mes.content}
</div>
</div>
</c:forEach>
</div>

<footer><small>&copy; Elove</small></footer>
</div>
</body>
</html>