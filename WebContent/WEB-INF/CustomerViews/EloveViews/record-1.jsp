<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


    <div class="container-fulid">
      <img src="img/elove/records_title_1.png" class="img-responsive title" alt="title background"/>
    </div><!-- title -->
  <div class="record-bg">
    <div class=" video-container">
      <div class="container-fulid">
        <video id="my_video_1" class="video-js vjs-default-skin vjs-big-play-centered video" controls preload="auto">
          <source src="${recordVideo}.mp4" type='video/mp4'>
          <source src="${recordVideo}.webm" type='video/webm'>
        </video>
      </div><!-- video -->
    </div>
    <div class="container-fulid">
      <div class="container-fulid">
        <img src="img/elove/records_photo_title_1.png" class="img-responsive"/>
      </div>
      <div class="photo-list">
      <c:forEach items="${recordImages}" var="image">
        <img src="${image}_original.jpg" class="center-block"/>
      </c:forEach>
      </div>
    </div><!-- photo -->
      
    <%@ include file="footer.jsp"%>
  </div>
