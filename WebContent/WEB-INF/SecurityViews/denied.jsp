<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${not empty error}">
{"status":false,
  "message":"${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}"}
</c:if>
