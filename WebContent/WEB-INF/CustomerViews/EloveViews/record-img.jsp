<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="photo-list">
<c:forEach items="${recordImages}" var="image">
  <img src="${image}_original.jpg" class="center-block originalImg"/>
</c:forEach>
</div>
