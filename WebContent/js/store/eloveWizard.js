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
	step1Info.themeid=$("input[name='optionsRadios']:checked").val();
	var musicStr=$("#upload1music_sigle-links").children();
	if(musicStr.length!=0){
		step1Info.music=musicStr[0].value;
	}else{
		step1Info.music=null;
	}
	if(validateStep1(step1Info)){
		return step1Info;		
	}else{
		return null;
	}
}
function validateStep1(step1Info){
	var blankInputArray = new Array();
	if(step1Info.title=="")blankInputArray.push("Elove标题栏");
	if(step1Info.password=="")blankInputArray.push("新人索取密码");
    if(step1Info.coverPic==null)blankInputArray.push("图文消息图片");
	if(step1Info.coverText=="")blankInputArray.push("图文消息文字");
	if(step1Info.shareTitle=="")blankInputArray.push("分享消息标题");
	if(step1Info.shareContent=="")blankInputArray.push("分享消息内容");
	if(step1Info.majorGroupPhoto==null)blankInputArray.push("新人婚纱照主图片");
	if(step1Info.themeid=="")blankInputArray.push("主题风格");
	if(step1Info.music==null)blankInputArray.push("背景音乐");
	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}
function getStep2Data(){
	var step2Info = new Object();
	step2Info.xinLang=$("#story_groom").val();
	step2Info.xinNiang=$("#story_bride").val();
    var linkInputArray=$("#upload1-links").children();
    var linkArray=new Array();
    $.each(linkInputArray,function(key,val){
  	  linkArray.push($(val).val());
    });
	step2Info.storyImagePath=linkArray;
	var storyTextImagePathStr=$("#upload1single-links").children();
	if(storyTextImagePathStr.length!=0){
		step2Info.storyTextImagePath=storyTextImagePathStr[0].value;
	}else{
		step2Info.storyTextImagePath=null;
	}
	if(validateStep2(step2Info)){
		return step2Info;		
	}else{
		return null;
	}
}
function validateStep2(step2Info){
	var blankInputArray = new Array();
	if(step2Info.xinLang=="")blankInputArray.push("新郎");
	if(step2Info.xinNiang=="")blankInputArray.push("新娘");
    if(step2Info.storyImagePath.length==0)blankInputArray.push("相遇相知图片");
	if(step2Info.storyTextImagePath==null)blankInputArray.push("相遇相知文字图片");
	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}
function getStep3Data(){
	var step3Info = new Object();
    var linkInputArray=$("#upload1-links").children();
    var linkArray=new Array();
    $.each(linkInputArray,function(key,val){
  	  linkArray.push($(val).val());
    });
	step3Info.dressImagePath=linkArray;
	
    var linkInputArray2=$("#upload1video_sigle-links").children();
    var linkArray2=new Array();
    $.each(linkInputArray2,function(key,val){
  	  linkArray2.push($(val).val());
    });
    step3Info.dressVideoPath=linkArray2;
	return step3Info;
}
function getStep4Data(){
	var step4Info = new Object();
	step4Info.weddingDate=$("#info_date").val();
	step4Info.weddingAddress=$("#info_addr").val();
	step4Info.lng=$("#lng").val();
	step4Info.lat=$("#lat").val();
	step4Info.phone=$("#info_phone").val();
	return step4Info;
}  
function getStep5Data(){
	var step5Info = new Object();
    var linkInputArray=$("#upload1-links").children();
    var linkArray=new Array();
    $.each(linkInputArray,function(key,val){
  	  linkArray.push($(val).val());
    });
    step5Info.recordImagePath=linkArray;
	
    var linkInputArray2=$("#upload1video_sigle-links").children();
    var linkArray2=new Array();
    $.each(linkInputArray2,function(key,val){
  	  linkArray2.push($(val).val());
    });
    step5Info.recordVideoPath=linkArray2;
	return step5Info;
}
function getStep6Data(){
	var step6Info = new Object();
	step6Info.footerText=$("#elove_footer").val();
	step6Info.sideCorpInfo=$("#elove_sidebar").val();
	return step6Info;
}
function showBlankInputHtml(blankInputArray){
	var blankInputhtml="";
    $.each(blankInputArray,function(key,val){
    	blankInputhtml=blankInputhtml+val+"<br/>";
    });
	$("#modalTitle").html("为了保证Elove效果，您需要完善下列信息：");
	$("#modalMes").html(blankInputhtml);
    $("#operationMesModal").modal("show");

    return;
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
  var shisStepData=getData(nextStep);
  if(shisStepData!=null){
	  $.ajax({
		  type: "POST",
		  url: "store/elove/wizard/"+nextStep,
		  data: JSON.stringify(shisStepData),
		  contentType: "application/json; charset=utf-8",
		  success: function (data) {
			  if(nextStep!="finish"){
				  $("#operationContent").html(data);
			  }else{
		   	   	  var jsonData = JSON.parse(data);
		   		  if(jsonData.status==true){
			   	   	  $("#modalMes").html(jsonData.message);
			   	      $("#operationMesModal").modal("show");
			   	      setTimeout("location.href='store/elove/detail'",1500);
		   		  }else{
			   	   	  $("#modalMes").html(jsonData.message);
			   	      $("#operationMesModal").modal("show");
		   		  }
			  }
			  
		  }
		});
  }
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