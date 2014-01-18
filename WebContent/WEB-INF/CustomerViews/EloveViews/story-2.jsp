<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <div class="container-fulid">
      <img src="img/elove/story_title_2.jpg" class="img-responsive title" alt="title background"/>
    </div><!-- title -->
    <div class="content-bg">
      <div class="container-fulid photo-container">
        <div class="container-fulid photo">
          <img src="${elove.majorGroupPhoto}_original.jpg" class="img-responsive center-block"/>
          <p class="couple-name">李雷雷 <img src="img/elove/heart.png"/> 韩梅梅</p>
        </div>
      </div>

      <div class="container-fulid">
        <img src="img/elove/story_meetyou_2.png" class="img-responsive center-block"/>
        <div class="container-fulid photo-list">
        <c:forEach items="${storyImagePath}" var="image">
          <img src="${image}_original.jpg" class="img-responsive center-block"/>
        </c:forEach>
        <img src="${elove.storyTextImagePath}_original.png" class="img-responsive center-block"/>
        </div>
      </div>

      <div class="container-fulid">
        <a data-toggle="modal" data-target="#bless" class="btn btn-elove btn-story">送上祝福</a>
        <a onclick="switch_guide('#guide_bg','#guide_img')" class="btn btn-elove btn-story">分享喜帖</a>
      </div>

      <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
      </div>

    <%@ include file="footer.jsp"%>
    </div>

    <!-- Modal -->
    <%@ include file="story-modal.jsp"%>