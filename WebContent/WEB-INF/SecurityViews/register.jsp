<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form method="Post" action = "${pageContext.request.contextPath}/security/register"
modelAttribute = "registerInfo">
用户名：<form:input path = "username"/>
       <form:errors path="username" ></form:errors>
<br/>
密码：<form:input path = "password"/>
     <form:errors path="password" ></form:errors>
<br/>
邮箱：<form:input path = "email"/>
     <form:errors path="email" ></form:errors>
<br/>
电话：<form:input path = "phone"/>
     <form:errors path="phone" ></form:errors>
<br/>
<input type="submit" value="提交" />
</form:form>
</body>
</html>