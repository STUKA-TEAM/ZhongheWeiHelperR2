<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>Elove</title>
    
    <link href="css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/zhonghe-elove-1.css" rel="stylesheet">
    
    <link href="css/customer/elove-story-1.css" rel="stylesheet">
    
    <link href="css/customer/elove-record-1.css" rel="stylesheet">
    <link href="css/customer/video-js.min.css" rel="stylesheet">

    <link href="css/customer/elove-info-1.css" rel="stylesheet">
  </head>
  <body>
    <div id="content-container">
    <div class="container-fulid">
      <img src="img/elove/story_title.png" class="img-responsive title" alt="title background"/>
    </div><!-- title -->
    <div class="content-bg">
      <div class="container-fulid">
        <div class="container-fulid container-margin">
          <div class="container-fulid photo">
            <img src="img/elove/encounter_photo1.jpg" class="img-responsive center-block"/>
            <p class="couple-name">李雷雷<img src="img/elove/heart.png"/> 韩梅梅</p>
          </div>
        </div>
      </div>

      <div class="container-fulid">
        <div class="container-fulid photo-list">
          <img src="img/elove/encounter_photo2.jpg" class="img-responsive center-block"/>
          <img src="img/elove/encounter_photo3.png" class="img-responsive center-block"/>
          <img src="img/elove/encounter_photo4.png" class="img-responsive center-block"/>
        </div>
      </div>

      <div class="container-fulid">
        <a data-toggle="modal" data-target="#bless" class="btn btn-elove btn-story">送上祝福</a>
        <a onclick="switch_guide()" class="btn btn-elove btn-story">分享喜帖</a>
      </div>

      <div id="guide_bg" class="guide hidden" onclick="close_guide()">
        <img id="guide_img" class="guide-pic hidden" src="img/common/guide.png"/>
      </div>

      <div class="footer">
        <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
      </div><!-- footer -->
    </div>

    <!-- Modal -->
    <div class="modal fade" id="bless" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <input class="modal-input" type="text" placeholder="称呼" />
            <textarea class="modal-text" rows=4 placeholder="请留下您对我们的祝福吧！" ></textarea>
            <p>
              <a class="modal-btn btn btn-elove" data-dismiss="modal" onclick="">发送祝福</a>
              <a class="modal-btn btn btn-elove" data-dismiss="modal" onclick="">取消</a>
            </p>
          </div>
        </div>
      </div>
    </div>
    </div>
    <div onclick="sidebar_up()">
      <a class="sidebar-guide" ><img src="img/elove/sidebar_guide.png" /></a>
    </div>
    <div class="sidebar-bg sidebar-bg-miss hidden" onclick="sidebar_dismiss()"></div>
    <div id="sidebar_list" class="sidebar hidden" state="down">
      <div id="audio" class="audio" onclick="audio_switch()">
        <img src="img/elove/audio_on.png" class="audio-logo"/>
        <p id="audio-p">音效开启</p>
      </div>
      <ul class="nav nav-pills">
        <li id="story" class="active"><a href="javascript:void(0)"><span class="sidebar-encount">相知相遇</span></a></li>
        <li id="dress"><a href="javascript:void(0)"><span class="sidebar-photo">婚纱剪影</span></a></li>
        <li id="info"><a href="javascript:void(0)"><span class="sidebar-info">婚礼信息</span></a></li>
        <li id="record"><a href="javascript:void(0)"><span class="sidebar-record">婚礼记录</span></a></li>
        <li id="intro"><a href="customer/elove/intro"><span class="sidebar-intr">公司介绍</span></a></li>
      </ul>
    </div>
    
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/elove.js"></script>
    <script type="text/javascript" src="js/customer/modal.min.js"></script>
    
    <script type="text/javascript" src="js/customer/elove-story.js"></script>
    
    <script type="text/javascript" src="js/customer/video.js"></script>
    
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
    <script type="text/javascript" src="js/customer/elove-info.js"></script>
  </body>
</html>