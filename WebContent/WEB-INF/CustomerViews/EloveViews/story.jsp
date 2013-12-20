<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">

    <title>众合微喜帖</title>
    
    <!-- 微喜帖css -->
    <link href="../../css/customer/zhonghe-wed.css" rel="stylesheet">
    <link href="../../css/customer/zhonghe-wed-encounter.css" rel="stylesheet">
  </head>
  <body>
    <div class="title">
      <img src="../../img/elove/records_title.png" />
    </div><!-- title -->

    <div class="content">
      <ul class="container">
        <li>
          <img src="../../img/elove/encounter_photo1.png" />
        </li>
        <li class="photo-logo">
          <img src="../../img/elove/encounter_logo.png" />
        </li>
      </ul><!-- video -->

      <ul class="photo-container">
        <li>
          <img src="../../img/elove/encounter_photo2.jpg" />
        </li>
        <li>
            <img src="../../img/elove/encounter_photo3.png" />
        </li>
        <li>
          <img src="../../img/elove/encounter_photo4.png" />
        </li>
      </ul><!-- photo -->
      
      <div class="footer">
        <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
      </div><!-- footer -->
    </div><!-- content -->
    
    <div id="audio" onclick="audioSwitch()">
    </div>
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
  </body>
</html>