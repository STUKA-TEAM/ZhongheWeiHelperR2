<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>${website.title}</title>
    
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/wei-website-modulelist.css" rel="stylesheet">
    <link href="./css/customer/lottery-wheel.css" rel="stylesheet">
  </head>
  <body>
    <div id="changedContainer">
    <div class="wheel-container">
      <div class="ly-plate">
        <div class="rotate-bg"></div>
        <div class="lottery-star"><img src="./img/lottery_wheel/rotate-static.png" id="lotteryBtn"></div>
      </div>
    </div>
    </div>
    
    <div class="lottery-item-container">
      <p class="lottery-item"><strong>一等奖：</strong>${wheel.itemList[0].itemDesc} 数量：${wheel.itemList[0].itemCount}张</p>
      <p><strong>二等奖：</strong>${wheel.itemList[1].itemDesc} 数量：${wheel.itemList[1].itemCount}张</p>
      <p><strong>三等奖：</strong>${wheel.itemList[2].itemDesc} 数量：${wheel.itemList[2].itemCount}张</p>
    </div>
    <div class="lottery-wiki-container">
      <p>${wheel.wheelDesc}</p>
    </div>
    <input id="wheelid" type="hidden" value="${wheel.wheelid}"/>
    <input id="wheeluuid" type="hidden" value="${wheel.wheeluuid}"/>
    
    <!-- <a class="luckybutton" href="javascript:void(0)" type="button" onclick="cutCount()">减少次数</a> -->
    <%@ include file="../CommonViews/weifooter.jsp"%>   
    <%@ include file="../WebsiteViews/bottom.jsp"%> 
    <script type="text/javascript" src="./js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="./js/customer/jquery.cookie.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>   
    <%@ include file="../CommonViews/lotteryShareJS.jsp"%>
    <script type="text/javascript">
    window.shareInfo = new Object();
    shareInfo.imgUrl = '${message.imageLink}';
    shareInfo.link = '${message.appLink}';
    shareInfo.desc = '${message.shareContent}';
    shareInfo.title = '${message.shareTitle}';
    </script>
  
  <script src="./js/customer/lottery_wheel/jQueryRotate.2.2.js"></script>
  <script src="./js/customer/lottery_wheel/jquery.easing.min.js"></script>
  <script>
  function cutCount(){
	  var count = $.cookie($("#wheeluuid").val());
	  
	  $.cookie($("#wheeluuid").val(), count-10, {path: '/'}); 
	  alert($.cookie($("#wheeluuid").val()));
  }
  $(function(){
    var timeOut = function(){  //超时函数
      $("#lotteryBtn").rotate({
        angle:0, 
        duration: 10000, 
        animateTo: 2160, //这里是设置请求超时后返回的角度，所以应该还是回到最原始的位置，2160是因为我要让它转6圈，就是360*6得来的
        callback:function(){
          alert('网络出现问题，请稍后再试');
        }
      }); 
    }; 
    var rotateFunc = function(awards,angle,data){  //awards:奖项，angle:奖项对应的角度
      $('#lotteryBtn').stopRotate();
      $("#lotteryBtn").rotate({
        angle:0, 
        duration: 5000, 
        animateTo: angle+1440, //angle是图片上各奖项对应的角度，1440是我要让指针旋转4圈。所以最后的结束的角度就是这样子^^
        callback:function(){
        $("#changedContainer").html(data);
        }
      }); 
    };
    
    $("#lotteryBtn").rotate({ 
       bind: 
       { 
        click: function(){
            var data = [1,2,3,0]; //返回的数组
             data = 1;
             $.ajax({
             	  type: "POST",
             	  url: "customer/lottery/wheel/do",
             	  data: "wheelid="+$("#wheelid").val(),
              	  success: function (data) {
                        if(data.indexOf("notLucky") != -1){
                          var angle = [67,112,202,292,337];
                          angle = angle[Math.floor(Math.random()*angle.length)];
                          rotateFunc(0,angle,data);
                          return;
                      }
                        if(data.indexOf("@@1@@") != -1){
                          rotateFunc(1,157,data);
                          return;
                      }
                        if(data.indexOf("@@2@@") != -1){
                            rotateFunc(2,247,data);
                            return;
                        }
                        if(data.indexOf("@@3@@") != -1){
                            rotateFunc(3,22,data);
                            return;
                        }
                        $("#changedContainer").html(data);
              	  },
           	  error: function(xhr, status, exception){
           		timeOut();
           	  }
             });
          }
        }
       });
  });
  </script>
    
  </body>
</html>