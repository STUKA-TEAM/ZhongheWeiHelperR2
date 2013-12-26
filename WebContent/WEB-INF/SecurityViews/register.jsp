<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>众合微信公共账号管理平台</title>
    <!-- Bootstrap core CSS -->
    <link href="css/store/bootstrap.min.css" rel="stylesheet">
    <link href="css/store/zhonghe-wechat.css" rel="stylesheet">
    <link rel="shortcut icon" href="img/favicon.jpg">
    <!-- include jQuery + carouFredSel plugin -->
	<script type="text/javascript" src="js/store/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
  </head>
  <body>
    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-white navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#"><img src="img/logo.png" alt="Zhonghe Software" /></a>
        </div>
      </div><!-- container -->
    </div><!-- navbar -->
    
    <div class="container">
      <div class="row">
        <div class="well well-lg col-sm-8 col-sm-offset-2 row">
        <form enctype="multipart/form-data" id="register">
        <input name="file" type="file" class="image-file" accept="image/*"/>
        <input type="button" value="Upload" class="image-multi"/>
        </form>
        
          <form:form class="col-sm-10 col-sm-offset-1 form-horizontal" method="Post" action = "security/register"
  modelAttribute = "userInfo">
            <form:input type="hidden" name="majorImage" path="majorImage" />
            <div class="form-group">
              <label for="username" class="col-sm-2 control-label">用户名</label>
              <div class="col-sm-10">
                <form:input type="text" class="form-control" id="username_input" path="username" placeholder="用户名"/>
                <form:errors path="username" class="input-error"></form:errors>
              </div>
            </div>
            <div class="form-group">
              <label for="password" class="col-sm-2 control-label">密码</label>
              <div class="col-sm-10">
                <form:input type="password" class="form-control" id="password_input" path="password" placeholder="******"/>
                <form:errors path="password" class="input-error"></form:errors>
              </div>
            </div>
            <div class="form-group">
              <label for="confirmPassword" class="col-sm-2 control-label">确认密码</label>
              <div class="col-sm-10">
                <form:input type="password" class="form-control" id="confirmPassword_input" path="confirmPassword" placeholder="******"/>
                <form:errors path="confirmPassword" class="input-error"></form:errors>
              </div>
            </div>
            <div class="form-group">
              <label for="storename" class="col-sm-2 control-label">店铺名称</label>
              <div class="col-sm-10">
                <form:input type="text" class="form-control" id="storename_input" path="storeName" placeholder=""/>
                <form:errors path="storeName" class="input-error"></form:errors>
              </div>
            </div>
            <div class="form-group">
              <label for="email" class="col-sm-2 control-label">邮箱地址</label>
              <div class="col-sm-10">
                <form:input type="email" class="form-control" id="email_input" path="email" placeholder="xxxx@xxx.com"/>
                <form:errors path="email" class="input-error"></form:errors>
              </div>
            </div>
            <div class="form-group">
              <label for="phone" class="col-sm-2 control-label">座机号码</label>
              <div class="col-sm-10">
                <form:input type="text" class="form-control" id="phone_input" path="phone" placeholder=""/>
                <form:errors path="phone" class="input-error"></form:errors>
              </div>
            </div>
            <div class="form-group">
              <label for="cellPhone" class="col-sm-2 control-label">手机号码</label>
              <div class="col-sm-10">
                <form:input type="text" class="form-control" id="cellPhone_input" path="cellPhone" placeholder=""/>
                <form:errors path="cellPhone" class="input-error"></form:errors>
              </div>
            </div>
             <div class="form-group">
              <label for="corpMoreInfoLink" class="col-sm-2 control-label">官网链接</label>
              <div class="col-sm-10">
                <form:input type="text" class="form-control" id="corpMoreInfoLink_input" path="corpMoreInfoLink" placeholder=""/>
                <form:errors path="corpMoreInfoLink" class="input-error"></form:errors>
              </div>
            </div>
            <div class="form-group">
              <label for="address" class="col-sm-2 control-label">商户地址</label>
              <div class="col-sm-10">
                <form:input type="text" class="form-control" id="address_input" path="address" placeholder=""/>
                <form:errors path="address" class="input-error"></form:errors>
                <button type="button" value="定位" onclick="setPoint()">定位</button> 
              </div>
              <div id="baidumap" style="height:400px; width:400px;"></div>
 			  <form:input id="lng" name="lng" type="hidden" path="lng"/>
 		      <form:input id="lat" name="lat" type="hidden" path="lat"/>
            </div>
            <div class="form-group">
                <button type="submit" class="col-sm-offset-1 col-sm-5 btn btn-lg btn-success">注册</button>
                <button id="cancle_register" class="col-sm-offset-1 col-sm-3 btn btn-lg btn-default">重置</button>
            </div>
          </form:form>
        </div>
      </div><!-- /.row -->
    </div> <!-- /.container -->
    
    <div id="footer">
      <div class="container" style="text-align:center">
        <p class="text-muted credit">Copyright ? 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
      </div>
    </div><!-- footer -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/store/bootstrap.min.js"></script>
    <script src="js/store/upload.js"></script>
    <script type="text/javascript">
    var myGeo = new BMap.Geocoder();  
    var map = new BMap.Map("baidumap");
    map.centerAndZoom("上海", 12);  
    map.enableScrollWheelZoom();
    function setPoint(){
    //"上海市长宁区天山路318号"
    map.clearOverlays();
    var address = document.getElementById("address_input").value;
    myGeo.getPoint(address, function(point){  
    if (point) { 	 
       map.centerAndZoom(point, 16);
       document.getElementById("lng").value = point.lng;
       document.getElementById("lat").value = point.lat;  
       map.addOverlay(new BMap.Marker(point));  
       map.enableScrollWheelZoom(); 
     }  
    }, "上海市");
    } 
    map.addEventListener("click", function(e){
      map.clearOverlays();
      var point=new BMap.Point(e.point.lng, e.point.lat);
      document.getElementById("lng").value = point.lng;
      document.getElementById("lat").value = point.lat;  
      map.centerAndZoom(point, 16);  
      map.addOverlay(new BMap.Marker(point)); 
    });
    </script>
  </body>
</html>