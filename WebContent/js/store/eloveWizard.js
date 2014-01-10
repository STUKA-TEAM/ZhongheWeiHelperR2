function getStep1Data(){
	var step1Info = new Object();
	step1Info.title=$("#elove_title").val();
	step1Info.password=$("#elove_pwd").val();
	var coverPicStr=$("#upload1single-links").children();
	if(coverPicStr.length!=0){
		step1Info.coverPic=coverPicStr[0].value;
	}else{
		step1Info.coverPic=null;
	}
	step1Info.coverText=$("#elove_txt").val();
	step1Info.shareTitle=$("#share_title").val();
	step1Info.shareContent=$("#share_content").val();
	var majorGroupPhotoStr=$("#upload2single-links").children();
	if(majorGroupPhotoStr.length!=0){
		step1Info.majorGroupPhoto=majorGroupPhotoStr[0].value;
	}else{
		step1Info.majorGroupPhoto=null;
	}
	step1Info.themeid=$("#optionsRadios").val();
	var musicStr=$("#upload1music_sigle-links").children();
	if(musicStr.length!=0){
		step1Info.music=musicStr[0].value;
	}else{
		step1Info.music=null;
	}
	return step1Info;
}  
function getStep2Data(){
	var step2Info = new Object();
	
	return step2Info;
}
function getStep3Data(){
	var step3Info = new Object();
	return step3Info;
}
function getStep4Data(){
	var step4Info = new Object();
	return step4Info;
}  
function getStep5Data(){
	var step5Info = new Object();
	return step5Info;
}
function getStep6Data(){
	var step6Info = new Object();
	return step6Info;
}
function getData(step){
	if(step=="step2"){		
		return getStep1Data();
}
	if(step=="step3"){		
		return getStep2Data();
}
	if(step=="step4"){		
		return getStep3Data();
}
	if(step=="step5"){		
		return getStep4Data();
}
	if(step=="step6"){		
		return getStep5Data();
}
	if(step=="finish"){		
		return getStep6Data();
}
}
function nextStep(nextStep){
  $.ajax({
  type: "POST",
  url: "store/elove/wizard/"+nextStep,
  data: JSON.stringify(getData(nextStep)),
  contentType: "application/json; charset=utf-8",
  success: function (data) {
	  $("#operationContent").html(data);
  }
});
}
function backStep(backStep){
	  $.ajax({
	  type: "GET",
	  url: "store/elove/wizard/"+backStep,
	  success: function (data) {
		  $("#operationContent").html(data);
	  }
	});
}
function cancel(){
	  $.ajax({
	  type: "GET",
	  url: "store/elove/wizard/cancel",
	  success: function (data) {
		  if(data=="OK")
			  location.href ="store/elove/detail";
	  }
	});
}