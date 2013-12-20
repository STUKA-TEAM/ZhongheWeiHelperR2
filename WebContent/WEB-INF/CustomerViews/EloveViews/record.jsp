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
    <div class="content">
    
      <div class="container">
        <video id="video" width="80%" onclick="playPause()">
    <source src="../../media/elovedome.webm" type="video/webm">
  </video>
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
    <script type="text/javascript">
    	var myVideo=document.getElementById("video"); 

        function playPause()
        { 
        if (myVideo.paused) 
          myVideo.play(); 
        else 
          myVideo.pause(); 
        } 
    </script>
    
  </body>
</html>