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
    
    <!-- 微喜帖css -->
    <link href="css/customer/zhonghe-wed.css" rel="stylesheet">
    <link href="css/customer/zhonghe-wed-info.css" rel="stylesheet">
  </head>
  <body style="min-height:605px;height:100%;background-image: url(img/elove/footer_bg.png);
background-repeat: no-repeat;
background-position: bottom;">
    <div class="title">
      <img src="img/elove/info_title.png" />
      <img class="logo" src="img/elove/encounter_photo1.png" />
    </div><!-- title -->
    <input type='checkbox' id='sideToggle'>
    <aside>
        <ul class="nav nav-pills sideul">
          <li class="sideli"><div id="customer/elove/audio" onclick="audioSwitch()"></div></li>
          <li class="sideli"><a href="customer/elove/story"><span class="sidebar-encount">相知相遇</span></a></li>
          <li class="sideli"><a href="customer/elove/dress"><span class="sidebar-photo">婚纱剪影</span></a></li>
          <li class="sideli"><a href="customer/elove/info"><span class="sidebar-info">婚礼信息</span></a></li>
          <li class="sideli"><a href="customer/elove/record"><span class="sidebar-record">婚礼纪录</span></a></li>
        </ul>
        
    <script type="text/javascript">
    flag="off";
    function audioSwitch(){
    	if(flag=="off"){
    		myAudio = new Audio('media/elovedemomusic.mp3'); 
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
        <label id='sideMenuControl' for='sideToggle'><img src="img/elove/sidebar_btn.png" /></label>
      </div> 
    <div class="content" >
      <div class="container">
        <div class="message-title">
          <img src="img/elove/message_title.png" />
        </div>
        <div class="message-content">
          <img src="img/elove/message_content.png" />
        </div>
      </div>

      <ul>
        <li>
          <div class="info-title col-xs-2">
            <p>婚礼<br/>时间</p>
          </div>
          <div class="info-content col-xs-10">
            <p>2013 - 12 - 12</p>
          </div>
        </li>
        <li onclick="mapSwitch()">
          <div class="info-title">
            <p>地点<br/>导航</p>
          </div>
          <div class="info-content">
            <p>上海市长宁区天山路318号 </p>
          </div>
        </li>
        <li>
          <div class="info-title">
            <p>联系<br/>电话</p>
          </div>
          <div class="info-content">
           <p> <a href="tel:12345678901" style="color:#d8465d;">12345678901</a></p>      
          </div>
        </li>
      </ul>

      <div class="btn-container">
        <p>
          <a onclick="switchAlert()" class="btn btn-primary">我要赴宴</a>
          <a onclick="switchAlert2()" class="btn btn-primary">送上祝福</a>
          <a onclick="switchGuide()"data-toggle="modal" data-target="#share" class="btn btn-primary">分享喜帖</a>
        </p>
      </div>
      
      <!-- 弹窗开始-->
      <div id="popupBack" class="popupBackClass"></div>
      <div id="popupBox" class="popupBoxClass">
        <div class="popup-content">
          <input class="popup-input" type="text" placeholder="称呼" />
          <input class="popup-input" type="text" placeholder="联系方式" />
          <input class="popup-input" type="text" placeholder="人数" />
          <a class="btn btn-long btn-primary" onclick="closeAlert()">确 定</a>
        </div>
        
      </div>
      <!-- 弹窗结束-->
      
      <!-- 弹窗开始-->
      <div id="popupBack2" class="popupBackClass"></div>
      <div id="popupBox2" class="popupBoxClass">
        <div class="popup-content">
          <input class="popup-input" type="text" placeholder="称呼" />
          <textarea class="popup-text" rows=4 placeholder="请留下您对我们的祝福吧！" ></textarea>
          <a class="btn btn-long btn-primary" onclick="closeAlert2()">发送祝福</a>
        </div>
        
      </div>
      <!-- 弹窗结束-->
      
	<div id="guide" onclick="closeGuide()">
	<img id="guidePic" src="img/common/guide.png"/>
	
	</div>
	
    <div class="footer">
        <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
    </div><!-- footer -->
            
      
           
      
    </div><!-- content -->

   
    <div id="baidumap" style="visibility:hidden;"> <div id="pic"></div></div>

    <script src="js/customer/popup.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
  <script type="text/javascript">
// 创建地址解析器实例 
var myGeo = new BMap.Geocoder();  
// 将地址解析结果显示在地图上，并调整地图视野
myGeo.getPoint("上海市长宁区天山路318号", function(point){  
 if (point) { 
	 var map = new BMap.Map("baidumap");  
   map.centerAndZoom(point, 16);  
   map.addOverlay(new BMap.Marker(point));  
   map.enableScrollWheelZoom(); 
 }  
}, "上海市"); 
</script>
 <script type="text/javascript">
function mapSwitch(){
var box=document.getElementById("baidumap");
if(box.style.visibility == "hidden"){
	box.style.visibility = "visible";
}else{
	box.style.visibility = "hidden";
}
	
}

</script>
  </body>
   
</html>