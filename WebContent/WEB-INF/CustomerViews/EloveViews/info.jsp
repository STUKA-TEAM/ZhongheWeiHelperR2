<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" container="width=device-width, initial-scale=1.0">
    <meta name="description" container="">
    <meta name="author" container="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>Elove</title>
    
    <!-- 微喜帖css -->
    <link href="css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="css/customer/zhonghe-elove.css" rel="stylesheet">
    <link href="css/customer/elove-info.css" rel="stylesheet">
    
  </head>
  <body class="body-bg">
    <div class="container-fulid">
      <img src="img/elove/info_title.png" class="img-responsive title" alt="title background" />
      <div class="logo-container">
        <img src="img/elove/encounter_photo1.png" class="logo" alt="logo" />
      </div>
    </div>
    
    <div class="container-fulid">
      <div class="message">
        <img src="img/elove/message_title.png" class="message-title" />
        <img src="img/elove/message_content.png" class="message-content" />
      </div>
    </div>
    
    <div class="container-fulid">
      <ul class="info-list">
        <li>
          <div class="info-title">
            <p>婚礼<br>时间</p>
          </div>
          <div class="info-content">
            <p>2013 - 12 - 12</p>
          </div>
        </li>
        <li onclick="map_switch()">
          <div class="info-title">
            <p>婚礼<br>地点</p>
          </div>
          <div class="info-content">
            <p>上海杨浦区国和路</p>
          </div>
        </li>
        <li>
          <div class="info-title">
            <p>电话</p>
          </div>
          <div class="info-content">
            <p><a href="tel:12345678901">12345678901</a></p>
          </div>
        </li>
      </ul>
    </div>
    
    <div class="container-fulid">
      <p>
        <a data-toggle="modal" data-target="#attend" class="btn btn-elove">我要赴宴</a>
        <a data-toggle="modal" data-target="#bless" class="btn btn-elove">送上祝福</a>
        <a onclick="switch_guide()" class="btn btn-elove">分享喜帖</a>
      </p>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="attend" tabindex="-1" role="dialog" aria-labelledby="attendLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <input class="modal-input" type="text" placeholder="称呼" />
            <input class="modal-input" type="text" placeholder="联系方式" />
            <input class="modal-input" type="text" placeholder="人数" />
            <a class="modal-btn btn btn-elove" data-dismiss="modal" onclick="">确 定</a>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="bless" tabindex="-1" role="dialog" aria-labelledby="blessLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <input class="modal-input" type="text" placeholder="称呼" />
            <textarea class="modal-text" rows=4 placeholder="请留下您对我们的祝福吧！" ></textarea>
            <a class="modal-btn btn btn-elove" data-dismiss="modal" onclick="">发送祝福</a>
          </div>
        </div>
      </div>
    </div>

    <div onclick="sidebar()">
      <img src="img/elove/sidebar_guide.png" class="sidebar-guide" />
    </div>

    <div id="sidebar_list" class="sidebar hidden" state="down">
      <ul class="nav nav-pills">
        <li><img src="img/elove/music_switch.png" id="audio" onclick="audio_switch()" /></li>
        <li><a href="#"><span class="sidebar-encount">相知相遇</span></a></li>
        <li><a href="#"><span class="sidebar-photo">婚纱剪影</span></a></li>
        <li><a href="#"><span class="sidebar-info">婚礼信息</span></a></li>
        <li><a href="#"><span class="sidebar-record">婚礼记录</span></a></li>
      </ul>
    </div>

    <div class="container-fulid">
      <div id="baidumap"><div id="pic"></div></div>
    </div>

    <div id="guide_bg" class="guide hidden" onclick="close_guide()">
      <img id="guide_img" class="guide-pic hidden" src="img/common/guide.png"/>
    </div>

    <div class="footer">
      <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
    </div><!-- footer -->
    
    <script type="text/javascript" src="js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/modal.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
    <script type="text/javascript" src="js/customer/elove-info.js"></script>
  </body>
</html>