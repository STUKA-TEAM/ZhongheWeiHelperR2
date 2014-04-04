<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="prize-container">
  <div class="prize-title">
    <img src="./img/lottery_wheel/prize_title.png" class="img-responsive lottery_logo">
  </div>
  <c:if test="${luckyType==1}">
  <p class="prize"><strong>您中了：一等奖 ${itemDesc}</strong></p>
  <p class="prize-token">请您填下您的手机号码，这将是是您到店领奖的凭证：</p>
  <p id="contactContainer" class="prize-token"><input id="contact" type="text" />&nbsp;&nbsp;<a class="luckybutton" href="javascript:void(0)" type="button" onclick="submitPhone()">提交</a></p>
  </c:if>
  
  <c:if test="${luckyType==2}">
  <c:if test="${luckyType==2}">
  <p class="prize"><strong>您中了：二等奖 ${itemDesc}</strong></p>
  <p class="prize-token">请您填下您的手机号码，这将是是您到店领奖的凭证：</p>
  <p id="contactContainer" class="prize-token"><input id="contact" type="text" />&nbsp;&nbsp;<a class="luckybutton" href="javascript:void(0)" type="button" onclick="submitPhone()">提交</a></p>
  </c:if>
  </c:if>
  
  <c:if test="${luckyType==3}">
  <c:if test="${luckyType==3}">
  <p class="prize"><strong>您中了：三等奖 ${itemDesc}</strong></p>
  <p class="prize-token">请您填下您的手机号码，这将是是您到店领奖的凭证：</p>
  <p id="contactContainer" class="prize-token"><input id="contact" type="text" />&nbsp;&nbsp;<a class="luckybutton" href="javascript:void(0)" type="button" onclick="submitPhone()">提交</a></p>
  </c:if>
  </c:if>
  <input id="itemid" type="hidden" value="${itemid}"/>
  <input id="token" type="hidden" value="${token}"/>
  <input type="hidden" value="@@${luckyType}@@"/>
  <input type="hidden" value="@@${luckyType}@@"/>
  <script type="text/javascript">
  function testContact(value){    
	    var reg =  /^0?(13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/ ;  
	    if(reg.test(value)){  
	        return true;  
	    }else{  
	        return false;  
	    }  
	}
  function submitPhone(){
	  var itemid = $("#itemid").val();
	  var token = $("#token").val();
	  var contact = $("#contact").val();
	  if(!testContact(contact)){
		  alert("请输入正确的手机号");
		  return;
	  }
	  $.ajax({
     	  type: "POST",
     	  url: "customer/lottery/wheel/contact",
     	  data: "itemid="+itemid+"&openid="+token+"&contact="+contact,
      	  success: function (jsonData) {
            var data = JSON.parse(jsonData);
            if(data.status){
            	$("#contactContainer").html(data.message+"<br/><a class=\"luckybutton\" href=\"javascript:void(0)\" type=\"button\" onclick=\"window.location.reload();\">再抽一次</a>");
            }else{
            	alert(data.message);
            }
      	  },
   	  error: function(xhr, status, exception){
   		  alert("出现错误稍后再试！");
   	  }
     });
  }
  </script>
</div>