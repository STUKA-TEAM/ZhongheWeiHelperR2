<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">

    <title>Elove</title>
    
    <!-- 微喜帖css -->
    <link href="../../css/customer/zhonghe-wed.css" rel="stylesheet">
    <link href="../../css/customer/zhonghe-wed-records.css" rel="stylesheet">
  </head>
  <body>
    <div class="title">
      <img src="../../img/elove/records_title.png" />
    </div><!-- title -->
        <input type='checkbox' id='sideToggle'>
    <aside>
        <ul class="nav nav-pills sideul">
          <li class="sideli"><div id="audio" onclick="audioSwitch()"></div></li>
          <li class="sideli"><a href="story"><span class="sidebar-encount">相知相遇</span></a></li>
          <li class="sideli"><a href="dress"><span class="sidebar-photo">婚纱剪影</span></a></li>
          <li class="sideli"><a href="info"><span class="sidebar-info">婚礼信息</span></a></li>
          <li class="sideli"><a href="record"><span class="sidebar-record">婚礼纪录</span></a></li>
        </ul>
        
    <script type="text/javascript">
    flag="off";
    function audioSwitch(){
    	if(flag=="off"){
    		myAudio = new Audio('../../media/elovedemomusic.mp3'); 
            myAudio.addEventListener('ended', function() {
                this.currentTime = 0;
                this.play();
            }, false);
            myAudio.play();
            flag="on";
    	}else{
    		myAudio.pause();
    		flag="off";
    	}
    	
    }
    
    </script>
      </aside>
      <div id='wrap'>
        <label id='sideMenuControl' for='sideToggle'><img src="../../img/elove/sidebar_btn.png" /></label>
      </div> 
    
    <div class="content">
      <div class="container">
        <img class="container-border" src="../../img/elove/records_video.jpg" />
      </div><!-- video -->

      <div class="container">
        <div class="photo-title">
          <img src="../../img/elove/records_photo_title.png" />
        </div>
        <ul class="photo-container">
          <li>
            <img class="container-border" src="../../img/elove/records_photo.jpg" />
          </li>
        </ul>
      </div><!-- photo -->
      
      <div class="footer">
        <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
      </div><!-- footer -->
    </div><!-- content -->
  </body>
</html>