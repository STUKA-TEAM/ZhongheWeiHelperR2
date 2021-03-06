<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <div class="container-fulid">
      <img src="img/elove/story_title_1.png" class="img-responsive title" alt="title background"/>
    </div><!-- title -->
    <div class="content-bg">
      <div class="container-fulid">
        <div class="container-fulid photo">
          <img src="${elove.majorGroupPhoto}_original.jpg" class="img-responsive center-block"/>
          <p class="couple-name">${elove.xinLang} <img src="img/elove/heart.png"/> ${elove.xinNiang}</p>
        </div>
      </div>

      <div class="container-fulid">
        <img src="img/elove/story_meetyou_1.png" class="img-responsive center-block"/>
        <%@ include file="story-img.jsp"%>
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