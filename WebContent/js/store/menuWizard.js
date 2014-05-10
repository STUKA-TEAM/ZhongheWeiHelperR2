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
		return true;
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
	if(jsonData == null){
		return null;
	}else{
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
}

function getButtonNodeJson(){
	var firstButtonArray = new Array();
	var blankInputArray = new Array();
	for(var k = 0; k < nodeList.length; k++){
		if(nodeList[k].nodeType==2&&nodeList[k].type=="view"&&nodeList[k].nodeLink==""){
			blankInputArray.push(nodeList[k].nodeName);
		}
		if(nodeList[k].nodeType==1&&nodeList[k].type=="view"&&getChildButtonArray(nodeList[k].UUID).length==0&&nodeList[k].nodeLink==""){
			blankInputArray.push(nodeList[k].nodeName);
		}
		if(nodeList[k].nodeType==2&&nodeList[k].type=="click"&&nodeList[k].nodeKey==""){
			blankInputArray.push(nodeList[k].nodeName);
		}
		if(nodeList[k].nodeType==1&&nodeList[k].type=="click"&&getChildButtonArray(nodeList[k].UUID).length==0&&nodeList[k].nodeKey==""){
			blankInputArray.push(nodeList[k].nodeName);
		}
	}
	if(blankInputArray.length != 0){
		showBlankInputHtml(blankInputArray);
		return null;
	}else{
		for(var i = 0; i < nodeList.length; i++){
			if(nodeList[i].nodeType == 1){
				var button = new Object();
				button.name = nodeList[i].nodeName;
				var childButton = getChildButtonArray(nodeList[i].UUID);
				if(childButton.length>0){
					button.sub_button = childButton;
				}else{
					if(nodeList[i].type=="view"){
						button.type = "view";
						button.url = nodeList[i].nodeLink;						
					}
					if(nodeList[i].type=="click"){
						button.type = "click";
						button.key = nodeList[i].nodeKey;						
					}
				}
				firstButtonArray.push(button);
			}
		}
		var jsonObject = new Object();
		jsonObject.button = firstButtonArray;
		return JSON.stringify(jsonObject);
	}

}

function getChildButtonArray(UUID){
	var childButtonArray = new Array();
	for(var i = 0; i < nodeList.length; i++){
		if(nodeList[i].fatherUUID == UUID){
			var button = new Object();
			if(nodeList[i].type=="view"){
				button.type = "view";
				button.url = nodeList[i].nodeLink;						
			}
			if(nodeList[i].type=="click"){
				button.type = "click";
				button.key = nodeList[i].nodeKey;						
			}
			button.name = nodeList[i].nodeName;
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
	for(var i = 0; i < nodeList.length; i++){
		if(nodeList[i].nodeType == 1){
			addSavedFirstMenu(nodeList[i]);
			for(var j = 0; j < nodeList.length; j++){
				if(nodeList[j].nodeType == 2 && nodeList[i].UUID == nodeList[j].fatherUUID){
					addSavedSecondMenu(nodeList[j]);
				}
			}
		}
	}
}

function addSavedFirstMenu(node){
	var addHtml="<li id=\""+node.UUID+"\" class=\"1st\"><a data-toggle=\"collapse\" href=\"#"+node.UUID+"_sub\"><span id=\""+node.UUID+"_name\"  class=\"col-md-6\">"+node.nodeName+"</span>"
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
    +"<div id=\""+node.UUID+"_sub\" class=\"panel-collapse collapse in\">"
    +"<ul id=\""+node.UUID+"_ul\" class=\"nav submenu\">"
    +"</ul>"
    +"</div>"
    +"</li>";	
	$("#menuButtons").append(addHtml);
}

function addSavedSecondMenu(node){
    var addHtml = "<li id=\""+node.UUID+"\" class=\""+node.fatherUUID+"_2nd\">"
    +"<a href=\"javascript:void(0)\"><span id=\""+node.UUID+"_name\"  class=\"col-md-6\">"+node.nodeName+"</span>"				
    +"<button type=\"button\" class=\"btn btn-default btn-xs col-md-offset-2\" onclick=\"editButtonWindow(this)\">"
    +"<span class=\"glyphicon glyphicon-pencil\"></span>"
    +"</button>"
    +"<button type=\"button\" class=\"btn btn-default btn-xs\" onclick=\"deleteButtonWindow(this)\">"
    +"<span class=\"glyphicon glyphicon-trash\"></span>"
    +"</button>"
    +"</a>"
    +"</li>";
    $("#"+node.fatherUUID+"_ul").append(addHtml);
    $("#"+node.fatherUUID+"_sub").collapse("show");
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
   node.nodeType = 1;
   var type = $('input[name="addFirstButtonButtonType"]:checked').val();
   if(type=="view"){
	   node.type="view";
	   node.nodeLink = $("#firstButtonLink").val();
	   node.nodeKey = "";
   }
   if(type=="click"){
	   node.type="click";
	   node.nodeLink = "";
	   node.nodeKey = $("#firstButtonKey").val();
   }
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
	currentFirstNode.nodeKey = "";
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
    node.nodeType = 2;
    var type = $('input[name="addSecondButtonButtonType"]:checked').val();
    if(type=="view"){
 	   node.type="view";
 	   node.nodeLink = $("#secondButtonLink").val();
 	   node.nodeKey = "";
    }
    if(type=="click"){
 	   node.type="click";
 	   node.nodeLink = "";
 	   node.nodeKey = $("#secondButtonKey").val();
    }
    node.fatherUUID = currentFirstMenu;
    nodeList.push(node);
   $("#"+currentFirstMenu+"_ul").append(addHtml);
   $("#"+currentFirstMenu+"_sub").collapse("show");
   $("#addSecondButton").modal("hide");
}

function changeButtonTypeRadioForNew(type){
	var modalBodyHtml1 = "<div class=\"form-group\">"
                        +"<label for=\"firstButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
                        +"<div class=\"col-sm-9\">"
                        +"<input type=\"radio\" name=\"addFirstButtonButtonType\" placeholder=\"\" value=\"view\" checked>跳转链接类型"
				        +"<input type=\"radio\" name=\"addFirstButtonButtonType\" placeholder=\"\" value=\"click\" onchange=\"changeButtonTypeRadioForNew(\'click\')\">回复消息类型"				       
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label for=\"firstButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
                        +"<div class=\"col-sm-9\">"
                        +"<input type=\"text\" class=\"form-control\" id=\"firstButtonName\" placeholder=\"\" value=\"\">"
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label for=\"firstButtonLink\" class=\"col-sm-3 control-label\">关联链接</label>"
                        +"<div class=\"col-sm-9\">"
                        +"<input type=\"text\" class=\"form-control\" id=\"firstButtonLink\" placeholder=\"\" value=\"\">"
                        +"</div>"
                        +"</div>";
	var modalBodyHtml2 = "<div class=\"form-group\">"
        +"<label for=\"firstButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
        +"<div class=\"col-sm-9\">"
        +"<input type=\"radio\" name=\"addFirstButtonButtonType\" placeholder=\"\" value=\"view\" onchange=\"changeButtonTypeRadioForNew(\'view\')\">跳转链接类型"
        +"<input type=\"radio\" name=\"addFirstButtonButtonType\" placeholder=\"\" value=\"click\" checked>回复消息类型"				       
        +"</div>"
        +"</div>"
        +"<div class=\"form-group\">"
        +"<label for=\"firstButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
        +"<div class=\"col-sm-9\">"
        +"<input type=\"text\" class=\"form-control\" id=\"firstButtonName\" placeholder=\"\" value=\"\">"
        +"</div>"
        +"</div>"
        +"<div class=\"form-group\">"
        +"<label for=\"firstButtonLink\" class=\"col-sm-3 control-label\">消息键值</label>"
        +"<div class=\"col-sm-9\">"
        +"<input type=\"text\" class=\"form-control\" id=\"firstButtonKey\" placeholder=\"\" value=\"\">"
        +"</div>"
        +"</div>";
	if(type=="view"){
		$("#addFirstButtonModalBody").html(modalBodyHtml1);
	}
	if(type=="click"){
		$("#addFirstButtonModalBody").html(modalBodyHtml2);
	}
}

function changeButtonTypeRadioForNew2(type){
	var modalBodyHtml1 = "<div class=\"form-group\">"
                        +"<label for=\"secondButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
                        +"<div class=\"col-sm-9\">"
                        +"<input type=\"radio\" name=\"addSecondButtonButtonType\" placeholder=\"\" value=\"view\" checked>跳转链接类型"
				        +"<input type=\"radio\" name=\"addSecondButtonButtonType\" placeholder=\"\" value=\"click\" onchange=\"changeButtonTypeRadioForNew2(\'click\')\">回复消息类型"				       
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label for=\"secondButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
                        +"<div class=\"col-sm-9\">"
                        +"<input type=\"text\" class=\"form-control\" id=\"secondButtonName\" placeholder=\"\" value=\"\">"
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label for=\"secondButtonLink\" class=\"col-sm-3 control-label\">关联链接</label>"
                        +"<div class=\"col-sm-9\">"
                        +"<input type=\"text\" class=\"form-control\" id=\"secondButtonLink\" placeholder=\"\" value=\"\">"
                        +"</div>"
                        +"</div>";
	var modalBodyHtml2 = "<div class=\"form-group\">"
        +"<label for=\"secondButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
        +"<div class=\"col-sm-9\">"
        +"<input type=\"radio\" name=\"addSecondButtonButtonType\" placeholder=\"\" value=\"view\" onchange=\"changeButtonTypeRadioForNew2(\'view\')\">跳转链接类型"
        +"<input type=\"radio\" name=\"addSecondButtonButtonType\" placeholder=\"\" value=\"click\" checked>回复消息类型"				       
        +"</div>"
        +"</div>"
        +"<div class=\"form-group\">"
        +"<label for=\"secondButtonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
        +"<div class=\"col-sm-9\">"
        +"<input type=\"text\" class=\"form-control\" id=\"secondButtonName\" placeholder=\"\" value=\"\">"
        +"</div>"
        +"</div>"
        +"<div class=\"form-group\">"
        +"<label for=\"secondButtonLink\" class=\"col-sm-3 control-label\">消息键值</label>"
        +"<div class=\"col-sm-9\">"
        +"<input type=\"text\" class=\"form-control\" id=\"secondButtonKey\" placeholder=\"\" value=\"\">"
        +"</div>"
        +"</div>";
	if(type=="view"){
		$("#addSecondButtonModalBody").html(modalBodyHtml1);
	}
	if(type=="click"){
		$("#addSecondButtonModalBody").html(modalBodyHtml2);
	}
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
	var secondButtonHtml1 = "<div class=\"form-group\">"
					       +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮类型</label>"
					       +"<div class=\"col-sm-9\">"
					       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"view\" checked>跳转链接类型"
					       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"click\" onchange=\"changeButtonTypeRadio(\'editButtonBody\',\'click\',\'"+thisId+"\')\">回复消息类型"
					       +"</div>"
					       +"</div>"
		                   +"<div class=\"form-group\">"
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
	var secondButtonHtml2 = "<div class=\"form-group\">"
					       +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮类型</label>"
					       +"<div class=\"col-sm-9\">"
					       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"view\" onchange=\"changeButtonTypeRadio(\'editButtonBody\',\'view\',\'"+thisId+"\')\">跳转链接类型"
					       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"click\" checked>回复消息类型"
					       +"</div>"
					       +"</div>"
					        +"<div class=\"form-group\">"
					        +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
					        +"<div class=\"col-sm-9\">"
					        +"<input type=\"text\" class=\"form-control\" id=\"buttonName\" placeholder=\"\" value=\"\">"
					        +"</div>"
					        +"</div>"
					        +"<div class=\"form-group\">"
					        +"<label for=\"buttonLink\" class=\"col-sm-3 control-label\">消息代码</label>"
					        +"<div class=\"col-sm-9\">"
					        +"<input type=\"text\" class=\"form-control\" id=\"buttonKey\" placeholder=\"\" value=\"\">"
					        +"</div>"
					        +"</div>";
    if(buttonNode.nodeType == 2 || $("#"+thisId+"_ul").children().length == 0){
    	if(buttonNode.type=="view"){
        	$("#editButtonBody").html(secondButtonHtml1);
        	$("#buttonLink").val(buttonNode.nodeLink);    		
    	}
    	if(buttonNode.type=="click"){
        	$("#editButtonBody").html(secondButtonHtml2);
        	$("#buttonKey").val(buttonNode.nodeKey);    		
    	}

    }else{	
    	$("#editButtonBody").html(firstButtonHtml);
    }
    $("#currentButton").val(thisId);
	$("#buttonName").val(buttonNode.nodeName);

	$("#editButton").modal("show");
}

function changeButtonTypeRadio(container,type,thisId){
	var buttonNode = getNodeFromUUID(thisId);
	var secondButtonHtml1 = "<div class=\"form-group\">"
	       +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮类型</label>"
	       +"<div class=\"col-sm-9\">"
	       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"view\" checked>跳转链接类型"
	       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"click\" onchange=\"changeButtonTypeRadio(\'editButtonBody\',\'click\',\'"+thisId+"\')\">回复消息类型"
	       +"</div>"
	       +"</div>"
        +"<div class=\"form-group\">"
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
var secondButtonHtml2 = "<div class=\"form-group\">"
	       +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮类型</label>"
	       +"<div class=\"col-sm-9\">"
	       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"view\" onchange=\"changeButtonTypeRadio(\'editButtonBody\',\'view\',\'"+thisId+"\')\">跳转链接类型"
	       +"<input type=\"radio\" name=\"buttonType\" placeholder=\"\" value=\"click\" checked>回复消息类型"
	       +"</div>"
	       +"</div>"
	        +"<div class=\"form-group\">"
	        +"<label for=\"buttonName\" class=\"col-sm-3 control-label\">按钮名称</label>"
	        +"<div class=\"col-sm-9\">"
	        +"<input type=\"text\" class=\"form-control\" id=\"buttonName\" placeholder=\"\" value=\"\">"
	        +"</div>"
	        +"</div>"
	        +"<div class=\"form-group\">"
	        +"<label for=\"buttonLink\" class=\"col-sm-3 control-label\">消息代码</label>"
	        +"<div class=\"col-sm-9\">"
	        +"<input type=\"text\" class=\"form-control\" id=\"buttonKey\" placeholder=\"\" value=\"\">"
	        +"</div>"
	        +"</div>";	
	if(type=="view"){
		$("#"+container).html(secondButtonHtml1);
		$("#buttonName").val(buttonNode.nodeName);
		$("#buttonLink").val(buttonNode.nodeLink);
	}else{
		$("#"+container).html(secondButtonHtml2);
		$("#buttonName").val(buttonNode.nodeName);
		$("#buttonKey").val(buttonNode.nodeKey);
	}
	
}
function editButton(){
    var currentButton = $("#currentButton").val();
    var buttonNode = getNodeFromUUID(currentButton);	
    if(buttonNode.nodeType == 2 || $("#"+currentButton+"_ul").children().length == 0){
    	var type = $('input[name="buttonType"]:checked').val();
    	if(type=="view"){
    		buttonNode.type="view";
    		buttonNode.nodeKey = "";
    		buttonNode.nodeLink = $("#buttonLink").val();
    	}
    	if(type=="click"){
    		buttonNode.type="click";
    		buttonNode.nodeKey = $("#buttonKey").val();
    		buttonNode.nodeLink = "";
    	}   	
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
	var childUUIDList = new Array();
	$.each(nodeList,function(key,val){
			if(nodeList[key].fatherUUID==deleteId){
				childUUIDList.push(nodeList[key].UUID);
			}
		  });
	nodeList.splice(getNodeKeyFromUUID(deleteId),1);
	for(var i = 0; i < childUUIDList.length; i++){
		nodeList.splice(getNodeKeyFromUUID(childUUIDList[i]),1);
		
	}
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




