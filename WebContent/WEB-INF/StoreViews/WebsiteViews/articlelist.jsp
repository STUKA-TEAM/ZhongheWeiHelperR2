<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forEach items="${articles}" var="item">
<div class="radio">
    <label>
      <input type="radio" name="articleOptionsRadios" value="${item.articleid}">
      ${item.title}&nbsp;<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
    </label>
</div>
</c:forEach>