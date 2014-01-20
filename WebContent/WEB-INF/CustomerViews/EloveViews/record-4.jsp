<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


    <div class="container-fulid">
      <img src="img/elove/records_title_4.png" class="img-responsive title" alt="title background"/>
    </div><!-- title -->
  <div class="record-bg">
    <%@ include file="record-video.jsp"%>
    <div class="container-fulid">
      <div class="container-fulid">
        <img src="img/elove/records_photo_title_4.png" class="img-responsive"/>
      </div>
      <div class="photo-list">
      <c:forEach items="${recordImages}" var="image">
        <img src="${image}_original.jpg" class="center-block"/>
      </c:forEach>
      </div>
    </div><!-- photo -->
      
    <%@ include file="footer.jsp"%>
  </div>
