<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="request" value="${pageContext.request}" />
<base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
<title>wizard</title>
</head>
<body>
  <div id="stepContent">
  Step1<input id ="stepData" />
  <button type="button" onclick="nextStep('step2')">下一步</button>
  </div>
   <script type="text/javascript" src="js/jquery-1.10.2.min.js">
  </script>
  <script type="text/javascript">
function getStep1Data(){
	var step1Data = new Object();
	step1Data.data = $("#stepData").val();
	return step1Data;
}  
function getStep2Data(){
	var step2Data = new Object();
	step2Data.data = $("#stepData").val();
	return step2Data;
}
function getStep3Data(){
	var step3Data = new Object();
	step3Data.data = $("#stepData").val();
	return step3Data;
}
function getData(step){
	if(step=="step2"){		
		return getStep1Data();
	}
	if(step=="step3"){		
		return getStep2Data();
	}
	if(step=="finish"){		
		return getStep3Data();
	}
}
function nextStep(nextStep){
  $.ajax({
  type: "POST",
  url: "TestWizardProcess/wizard/"+nextStep,
  data: JSON.stringify(getData(nextStep)),
  contentType: "application/json; charset=utf-8",
  success: function (data) {
	  $("#stepContent").html(data);
  }
});
}
function backStep(backStep){
	  $.ajax({
	  type: "GET",
	  url: "TestWizardProcess/wizard/"+backStep,
	  success: function (data) {
		  $("#stepContent").html(data);
	  }
	});
	}
function cancel(){
	  $.ajax({
	  type: "GET",
	  url: "TestWizardProcess/wizard/cancel",
	  success: function (data) {
		  $("#stepContent").html(data);
	  }
	});
	}
  </script>
 
</body>
</html>
