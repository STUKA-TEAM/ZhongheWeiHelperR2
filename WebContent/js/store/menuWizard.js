$(document).ready(function(){
	nodeList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/menu/wizard/getnodes",
		  success: function (data) {
			  var jsonData = JSON.parse(data);
			  if(jsonData.status == false){
					return;
			  }else{
				  nodeList=jsonData.nodeList;
				  for(var i=0;i<nodeList.length;i++){
					  nodeList[i].UUID=getUUID();
				  }
				  for(var i=0;i<nodeList.length;i++){
					  if(nodeList[i].nodeType==1){
						  nodeList[i].fatherUUID="";
					  }else{
						  for(var k=0;k<nodeList.length;k++){
							  if(nodeList[i].fatherid==nodeList[k].nodeid){
								  nodeList[i].fatherUUID=nodeList[k].UUID;
							  }
						  }
					  }					  
				  }
			  }
		  }
		});
});

function getStep1Data(){
	var step1Info = new Object();
	step1Info.appid=$("#appid").val();
	step1Info.appsecret=$("#appsecret").val();
	if(validateStep1(step1Info)){
		return step1Info;		
	}else{
		return null;
	}
}
function validateStep1(step1Info){
	var blankInputArray = new Array();
	if(step1Info.appid=="")blankInputArray.push("AppId");
	if(step1Info.appsecret=="")blankInputArray.push("AppSecret");

	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}

function getStep2Data(){
	var step2Info = new Object();
	var accesstoken = $("#accesstoken").val();
	if(accesstoken==null || accesstoken == ""){
		$("#modalTitle").html("提示");
		$("#modalMes").html("第一步数据有误，请返回第一步填写");
	    $("#operationMesModal").modal("show");
	    return null;
	}
	var jsonData = getButtonNodeJson();
	$.ajax({
		  type: "GET",
		  url: "store/createButton?accesstoken="+accesstoken+"&jsonData="+encodeURIComponent(jsonData),
		  async: false,
		  success: function (data) {
			  if(data=="y"){
				  step2Info.success = true;
			  }else{
				  step2Info.success = false;
			  }			 
			  step2Info.nodeList = nodeList;

		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
				$("#modalTitle").html("出现错误");
				$("#modalMes").html(textStatus);
			    $("#operationMesModal").modal("show");
			    step2Info = null;
		  }
		});	
	return step2Info;
}

function getButtonNodeJson(){
	var firstButtonArray = new Array();
	for(var i = 0; i < nodeList.length; i++){
		if(nodeList[i].nodeType == 1){
			var button = new Object();
			button.name = nodeList[i].nodeName;
			var childButton = getChildButtonArray(nodeList[i].UUID);
			if(childButton.length>0){
				button.sub_button = childButton;
			}else{
				button.type = "view";
				button.url = nodeList[i].nodeLink;
			}
			firstButtonArray.push(button);
		}
	}
	var jsonObject = new Object();
	jsonObject.button = firstButtonArray;
	return JSON.stringify(jsonObject);
}

function getChildButtonArray(UUID){
	var childButtonArray = new Array();
	for(var i = 0; i < nodeList.length; i++){
		if(nodeList[i].fatherUUID == UUID){
			var button = new Object();
			button.type = "view";
			button.name = nodeList[i].nodeName;
			button.url = nodeList[i].nodeLink;
			childButtonArray.push(button);
		}
	}
	return childButtonArray;
}

function showBlankInputHtml(blankInputArray){
	var blankInputhtml="";
    $.each(blankInputArray,function(key,val){
    	blankInputhtml=blankInputhtml+val+"<br/>";
    });
	$("#modalTitle").html("信息不全，您需要完善下列信息：");
	$("#modalMes").html(blankInputhtml);
    $("#operationMesModal").modal("show");

    return;
}

function getData(step){
	if(step=="step2"){
		return getStep1Data();
}
	if(step=="finish"){		
		return getStep2Data();
}
}

function nextStep(nextStep){
	  var shisStepData=getData(nextStep);
	  if(shisStepData!=null){
		  if(nextStep!="finish"){
			  var accesstoken = $("#accesstoken").val();
			  if(accesstoken != null && accesstoken != ""){
				  shisStepData.accesstoken = accesstoken;
			  }else{
				  var accessTokenMes = getAccessToken($("#appid").val(),$("#appsecret").val());
				  if(accessTokenMes == null){
		   			  $("#modalTitle").html("提示");
			   	   	  $("#modalMes").html("网络异常，稍后再试");
			   	      $("#operationMesModal").modal("show");
			   	      return;
				  }else{
			   		  if(accessTokenMes.status==true){
			   			shisStepData.accesstoken = accessTokenMes.message;
			   		  }else{		   			  
				   			  $("#modalTitle").html("提示");
					   	   	  $("#modalMes").html(accessTokenMes.message);
					   	      $("#operationMesModal").modal("show");
					   	      return;
			   		  } 
				  }
			  }
		  }		  
		  $.ajax({
			  type: "POST",
			  url: "store/menu/wizard/"+nextStep,
			  data: JSON.stringify(shisStepData),
			  contentType: "application/json; charset=utf-8",
			  success: function (data) {
				  if(nextStep!="finish"){
					  $("#operationContent").html(data);
					  generateNodeLayer();				  
				  }else{
			   	   	  var jsonData = JSON.parse(data);
			   		  if(jsonData.status==true){
			   			  $("#modalTitle").html("提示");
				   	   	  $("#modalMes").html(jsonData.message);
				   	      $("#operationMesModal").modal("show");
				   	      setTimeout("location.href='store/account'",1500);
			   		  }else{
			   			  $("#modalTitle").html("提示");
				   	   	  $("#modalMes").html(jsonData.message);
				   	      $("#operationMesModal").modal("show");
			   		  }
				  }
				  
			  }
			});
	  }
	}
function getAccessToken(appid, appSecret){
	var returnData="";
	$.ajax({
		  type: "GET",
		  url: "store/getAccessToken?appid="+appid+"&secret="+appSecret,
		  async: false,
		  success: function (data) {
	   	   	  returnData = JSON.parse(data);
		  },
		  error: function(xhr) {
			  returnData = null;
		  }
		});	
	return returnData;
}
function generateNodeLayer(){
	
}
function backStep(backStep){
	  $.ajax({
	  type: "GET",
	  url: "store/menu/wizard/"+backStep,
	  success: function (data) {
		  $("#operationContent").html(data);
	  }
	});
}
function cancel(){
	  $.ajax({
	  type: "GET",
	  url: "store/menu/wizard/cancel",
	  success: function (data) {
		  if(data=="OK")
			  location.href ="store/account";
	  }
	});
}


function addFirstMenuWindow(){
	if($(".1st").length<=2){
		$("#firstButtonName").val("");
		$("#firstButtonLink").val("");
		$("#addFirstButton").modal("show");
	}else{
		alert("最多创建 3 个一级菜单");
	}
}

function addFirstMenu(){
	var uuid = getUUID();
	var addHtml="<li id=\""+uuid+"\" class=\"1st\"><a data-toggle=\"collapse\" href=\"#"+uuid+"_sub\"><span id=\""+uuid+"_name\"  class=\"col-md-6\">"+$("#firstButtonName").val()+"</span>"
		+"<button type=\"button\" class=\"btn btn-default btn-xs col-md-offset-2\" onclick=\"addSecondMenuWindow(this)\">"
		+  "<span class=\"glyphicon glyphicon-plus\"></span>"
		+"</button>"  
		+"<button type=\"button\" class=\"btn btn-default btn-xs\" onclick=\"editButtonWindow(this)\">"
		+ "<span class=\"glyphicon glyphicon-pencil\"></span>"
		+"</button>"
		+"<button type=\"button\" class=\"btn btn-default btn-xs\" onclick=\"deleteButtonWindow(this)\">"
		+ "<span class=\"glyphicon glyphicon-trash\"></span>"
		+"</button>"                  
        +"</a>" 
        +"<div id=\""+uuid+"_sub\" class=\"panel-collapse collapse in\">"
        +"<ul id=\""+uuid+"_ul\" class=\"nav submenu\">"
        +"</ul>"
        +"</div>"
        +"</li>";
   var node = new Object();
   node.UUID = uuid;
   node.nodeName = $("#firstButtonName").val();
   node.nodeLink = $("#firstButtonLink").val();
   node.nodeType = 1;
   node.fatherUUID = "";
   nodeList.push(node);
   $("#menuButtons").append(addHtml);
   $("#addFirstButton").modal("hide");
}

function addSecondMenuWindow(obj){
	var thisId = $(obj).parent().parent().attr("id");

	if($("."+thisId+"_2nd").length<=4){
        $("#currentFirstMenu").val(thisId);
		$("#secondButtonName").val("");
		$("#secondButtonLink").val("");
		$("#addSecondButton").modal("show");
	}else{
		alert("每个一级菜单下可创建最多 5 个二级菜单");
	}
}
function addSecondMenu(){
	var currentFirstMenu = $("#currentFirstMenu").val();
	var currentFirstNode = getNodeFromUUID(currentFirstMenu);
	currentFirstNode.nodeLink = "";
	var uuid = getUUID();
    var addHtml = "<li id=\""+uuid+"\" class=\""+currentFirstMenu+"_2nd\">"
                  +"<a href=\"javascript:void(0)\"><span id=\""+uuid+"_name\"  class=\"col-md-6\">"+$("#secondButtonName").val()+"</span>"				
                  +"<button type=\"button\" class=\"btn btn-default btn-xs col-md-offset-2\" onclick=\"editButtonWindow(this)\">"
	              +"<span class=\"glyphicon glyphicon-pencil\"></span>"
	              +"</button>"
	              +"<button type=\"button\" class=\"btn btn-default btn-xs\" onclick=\"deleteButtonWindow(this)\">"
	              +"<span class=\"glyphicon glyphicon-trash\"></span>"
	              +"</button>"
	              +"</a>"
                  +"</li>";
    var node = new Object();
    node.UUID = uuid;
    node.nodeName = $("#secondButtonName").val();
    node.nodeLink = $("#secondButtonLink").val();
    node.nodeType = 2;
    node.fatherUUID = currentFirstMenu;
    nodeList.push(node);
   $("#"+currentFirstMenu+"_ul").append(addHtml);
   $("#"+currentFirstMenu+"_sub").collapse("show");
   $("#addSecondButton").modal("hide");
}

function editButtonWindow(obj){
	var thisId = $(obj).parent().parent().attr("id");
	var buttonNode = getNodeFromUUID(thisId);
	var firstButtonHtml = "<div class=\"form-group\">"
					        +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
					        +"<div class=\"col-sm-9\">"
					        +"<input type=\"text\" class=\"form-control\" id=\"buttonName\" placeholder=\"\" value=\"\">"
					        +"</div>"
					        +"</div>";
	var secondButtonHtml = "<div class=\"form-group\">"
                           +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
                           +"<div class=\"col-sm-9\">"
                           +"<input type=\"text\" class=\"form-control\" id=\"buttonName\" placeholder=\"\" value=\"\">"
                           +"</div>"
                           +"</div>"
                           +"<div class=\"form-group\">"
                           +"<label for=\"buttonLink\" class=\"col-sm-3 control-label\">关联链接</label>"
                           +"<div class=\"col-sm-9\">"
                           +"<input type=\"text\" class=\"form-control\" id=\"buttonLink\" placeholder=\"\" value=\"\">"
                           +"</div>"
                           +"</div>";
    if(buttonNode.nodeType == 2 || $("#"+thisId+"_ul").children().length == 0){
    	$("#editButtonBody").html(secondButtonHtml);
    	$("#buttonLink").val(buttonNode.nodeLink);
    }else{
    	
    	$("#editButtonBody").html(firstButtonHtml);
    }
    $("#currentButton").val(thisId);
	$("#buttonName").val(buttonNode.nodeName);

	$("#editButton").modal("show");
}
function editButton(){
    var currentButton = $("#currentButton").val();
    var buttonNode = getNodeFromUUID(currentButton);	
    if(buttonNode.nodeType == 2 || $("#"+currentButton+"_ul").children().length == 0){
    	buttonNode.nodeLink = $("#buttonLink").val();
    }
    $("#"+currentButton+"_name").html($("#buttonName").val());
    buttonNode.nodeName = $("#buttonName").val();
	$("#editButton").modal("hide");
}
function deleteButtonWindow(obj){
	var thisId = $(obj).parent().parent().attr("id");
    $("#deleteButtonId").val(thisId);
	$("#deleteButtonWindow").modal("show");
}
function deleteButton(){
	var deleteId = $("#deleteButtonId").val();
	$.each(nodeList,function(key,val){
			if(nodeList[key].fatherUUID==deleteId){
				nodeList.splice(key,1);
			}
		  });
	nodeList.splice(getNodeKeyFromUUID(deleteId),1);
	$("#"+deleteId).remove();
	$("#deleteButtonWindow").modal("hide");
}
function getUUID(){
	return (new UUID()).id;
}
function getNodeFromUUID(uuid){
	  var node = null;
	  $.each(nodeList,function(key,val){
			if(nodeList[key].UUID==uuid){
				node = nodeList[key];
			}
		  });
	  return node;
	}	
function getNodeKeyFromUUID(uuid){
	  var nodeKey = null;
	  $.each(nodeList,function(key,val){
			if(nodeList[key].UUID==uuid){
				nodeKey = key;
			}
		  });
	  return nodeKey;
}
function showList(){
	alert(JSON.stringify(nodeList));
}




