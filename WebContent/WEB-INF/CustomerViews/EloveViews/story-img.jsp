<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container-fulid photo-list">
<c:forEach items="${storyImagePath}" var="image">
  <img src="${image}_original.jpg" class="img-responsive center-block originalImg"/>
</c:forEach>
<img src="${elove.storyTextImagePath}_original.png" class="img-responsive center-block"/>
</div>