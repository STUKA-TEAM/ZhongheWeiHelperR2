<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

  <c:if test="${recordVideo!=null}">
    <div class=" video-container">
      <div class="container-fulid">
        <video id="my_video_1" class="video-js vjs-default-skin vjs-big-play-centered video" controls preload="auto">
          <source src="${recordVideo}.mp4" type='video/mp4'>
          <source src="${recordVideo}.webm" type='video/webm'>
        </video>
      </div><!-- video -->
    </div>
  </c:if>
