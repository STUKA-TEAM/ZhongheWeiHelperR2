function submitDeleteWebsite(websiteid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这篇文章吗？");
    $("#websiteidhidden").val(websiteid);
    $("#confirmModal").modal("show");
}
function confirmDelete(){
	var websiteid = $("#websiteidhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/website/delete",
  	  data: "websiteid="+websiteid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/website/home'",1500);
   		  }else{
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
   		  }
   	  },
	  error: function(xhr, status, exception){
   	   	  $("#modalMes").html(status + '</br>' + exception);
   	      $("#operationMesModal").modal("show");
	  }
  });
}

function getStep1Data(){
	var step1Info = new Object();
	step1Info.title=$("#title").val();
	step1Info.getCode=$("#getCode").val();
	step1Info.phone=$("#phone").val();
	step1Info.address=$("#info_addr").val();
	step1Info.lng=$("#lng").val();
	step1Info.lat=$("#lat").val();
	var coverPicStr=$("#upload1single-links").children();
	if(coverPicStr.length!=0){
		step1Info.coverPic=coverPicStr[0].value;
	}else{
		step1Info.coverPic=null;
	}
	step1Info.coverText=$("#coverText").val();
	step1Info.shareTitle=$("#shareTitle").val();
	step1Info.shareContent=$("#shareContent").val();
	step1Info.footerText=$("#footerText").val();
	step1Info.themeId=$("input[name='optionsRadios']:checked").val();
    var linkInputArray=$("#upload1-links").children();
    var linkArray=new Array();
    $.each(linkInputArray,function(key,val){
  	  linkArray.push($(val).val());
    });
    step1Info.imageList=linkArray;
	if(validateStep1(step1Info)){
		return step1Info;		
	}else{
		return null;
	}
}

function validateStep1(step1Info){
	var blankInputArray = new Array();
	if(step1Info.title=="")blankInputArray.push("页面标题");
	if(step1Info.imageList.length==0)blankInputArray.push("介绍图片");
	if(step1Info.getCode=="")blankInputArray.push("获取码");
	if(step1Info.phone=="")blankInputArray.push("联系电话");
	if(step1Info.address=="")blankInputArray.push("地址");
    if(step1Info.coverPic==null)blankInputArray.push("图文消息图片");
	if(step1Info.coverText=="")blankInputArray.push("图文消息文字");
	if(step1Info.shareTitle=="")blankInputArray.push("分享消息标题");
	if(step1Info.shareContent=="")blankInputArray.push("分享消息文字");
	if(step1Info.footerText=="")blankInputArray.push("页面脚注");
	if(typeof(step1Info.themeId)=="undefined")blankInputArray.push("主题风格");	
	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}

function getStep2Data(){
	var step2Info = new Object();
	step2Info.nodeList=nodeList;
	return step2Info;
}

function showBlankInputHtml(blankInputArray){
	var blankInputhtml="";
    $.each(blankInputArray,function(key,val){
    	blankInputhtml=blankInputhtml+val+"<br/>";
    });
	$("#modalTitle").html("为了保证微官网效果，您需要完善下列信息：");
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
		  $.ajax({
			  type: "POST",
			  url: "store/website/wizard/"+nextStep,
			  data: JSON.stringify(shisStepData),
			  contentType: "application/json; charset=utf-8",
			  success: function (data) {
				  if(nextStep!="finish"){
					  $("#operationContent").html(data);
				  }else{
			   	   	  var jsonData = JSON.parse(data);
			   		  if(jsonData.status==true){
			   			  $("#modalTitle").html("提示");
				   	   	  $("#modalMes").html(jsonData.message);
				   	      $("#operationMesModal").modal("show");
				   	      setTimeout("location.href='store/website/home'",1500);
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
function backStep(backStep){
	  $.ajax({
	  type: "GET",
	  url: "store/website/wizard/"+backStep,
	  success: function (data) {
		  $("#operationContent").html(data);
	  }
	});
}
function cancel(){
	  $.ajax({
	  type: "GET",
	  url: "store/website/wizard/cancel",
	  success: function (data) {
		  if(data=="OK")
			  location.href ="store/website/home";
	  }
	});
}

$(document).ready(function(){
	nodeList = new Array();
	root = new Object();
	root.UUID = (new UUID()).id;
	root.nodeName = "root";
	root.childrenType = "node";
	root.fatherUUID="";
	$("#fatheruuid").val(root.UUID);
	nodeList.push(root);
});

function addNodeWindow(){
	$("#node-Name").val("");
	$("#filetext").val("");
	$("#upload1single-images").html("");
	$("#upload1single-links").html("");
	$("#add_child_column").modal("show");
}

function addNode(){
	var node = new Object();
	node.fatherUUID=$("#fatheruuid").val();
	node.UUID=(new UUID()).id;
	node.nodeName=$("#node-Name").val();
	var coverPicStr=$("#upload1single-links").children();
	if(coverPicStr.length!=0){
		node.nodePic=coverPicStr[0].value;
	}else{
		node.nodePic=null;
	}
	node.childrenType = "node";
	nodeList.push(node);
	addNodeItem(node);
	$("#add_child_column").modal("hide");
}
function addNodeItem(node){
	var picHTML = "";
	if(node.nodePic!=null){
		picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-thumbnail img-responsive\"/>";
	}
    var html="<tr id=\""+node.UUID+"\">"
    +"<td>"+node.nodeName+"</td>"
    +"<td>"+picHTML+"</td>"
    +"<td id=\""+node.UUID+"-content"+"\"></td>"
    +"<td><a class=\"btn btn-sm btn-info\" data-toggle=\"modal\" onclick=\"createChildNode(\'"+node.UUID+"\')"+"\">为本栏目添加子栏目</a></td>"
    +"<td><a class=\"btn btn-sm btn-info\" onclick=\"relateArticleWindow(\'"+node.UUID+"\')"+"\">关联文章</a></td>"
    +"<td><a class=\"btn btn-sm btn-info\" data-toggle=\"modal\" data-target=\"#relate_doc_type\" onclick=\""+"relateClassWindow('"+node.UUID+"')"+"\">关联文章类别</a></td>"
    +"<td>" 
    +"<a class=\"btn btn-sm btn-info\" onclick=\""+"editNodeWindow('"+node.UUID+"')"+"\">编辑</a>"
    +"<a class=\"btn btn-sm btn-danger\" onclick=\""+"deleteNode('"+node.UUID+"')"+"\">删除</a>"  
    +"</td>"
    +"</tr>";
    $("#nodeItems").append(html);
}

function createChildNode(uuid){
	generateNodeLayer(uuid);
	var node = getNodeFromUUID(uuid);
	node.childrenType = "node";
	node.classid=null;
	node.articleid=null;
}

function generateNodeLayer(uuid){
	$("#fatheruuid").val(uuid);
	$("#nodeItems").html(getNodeChildHTML(uuid));
	if(getNodeFromUUID(uuid).nodeName == "root"){
		$("#fatherName").html("");			
	}else{
		var fatherName = getNodeFromUUID(getNodeFromUUID(uuid).fatherUUID).nodeName;
		if(fatherName == "root"){
			$("#fatherName").html("根目录");	
		}else{
			$("#fatherName").html(fatherName);	
		}

	}
}

function generateFatherLayer(){
	var fatherUUID = $("#fatheruuid").val();
	var generateKey = getNodeFromUUID(fatherUUID).fatherUUID;
	if(typeof(generateKey)!="undefined"){
		generateNodeLayer(generateKey);

	}else{
		alert("没有上一级！");
	}
	
}

function editNodeWindow(uuid){
	var node = getNodeFromUUID(uuid);
	$("#edit-node-Name").val(node.nodeName);
	$("#edit-filetext").val("");	
	var picHTML = "";
	if(node.nodePic!=null){
		picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-thumbnail img-responsive\"/>";
	}
	var imageHTML = "<div id=\""+node.nodePic+"\" class=\"col-md-6 pic-preview-div\">"
	                +picHTML
	                +"<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage('"+node.nodePic+"')\"> </span>"
	                +"</div>";
	$("#upload2single-images").html(imageHTML);

	var inputHTML = "";
	if(node.nodePic!=null){
		inputHTML = "<input id=\""+node.nodePic+"-input\" type=\"hidden\" value=\""+node.nodePic+"\">";
	}
	$("#upload2single-links").html(inputHTML);
	$("#editNodeUUID").val(uuid);
	$("#edit_child_column").modal("show");
}

function editNode(){
    var node = getNodeFromUUID($("#editNodeUUID").val());
	node.nodeName=$("#edit-node-Name").val();
	var coverPicStr=$("#upload2single-links").children();
	if(coverPicStr.length!=0){
		node.nodePic=coverPicStr[0].value;
	}else{
		node.nodePic=null;
	}
	editNodeItem(node);
	$("#edit_child_column").modal("hide");
}

function editNodeItem(node){
	var list = $("#"+node.UUID).children();
	$(list[0]).html(node.nodeName);
	var picHTML = "";
	if(node.nodePic!=null){
		picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-thumbnail img-responsive\"/>";
	}
	$(list[1]).html(picHTML);
}

function deleteNode(uuid){
	if(getChildNodeList(uuid).length==0){
		var deleteKey = getNodeKeyFromUUID(uuid);
	    nodeList.splice(deleteKey,1);
	    $("#"+uuid).remove();
		return;
	}else{
		var list = getChildNodeList(uuid);
		for(var i=0; i<list.length; i++){
			deleteNode(list[i].UUID);
		}
		var deleteKey = getNodeKeyFromUUID(uuid);
	    nodeList.splice(deleteKey,1);
	    $("#"+uuid).remove();
	    
	}

}

function deleteChildNode(uuid){
	var list = getChildNodeList(uuid);
	for(var i=0; i<list.length; i++){
		deleteNode(list[i].UUID);
	}
}

function relateArticleWindow(uuid){
	var inputList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/articlelist",
		  success: function (data) {		  
			  $("#relateArticle").html(data);
			  inputList = $("input[name=articleOptionsRadios]");
			  var checkedKey=-1;
			  var currentNode = getNodeFromUUID(uuid);
			    $.each(inputList,function(key,val){
			    	if($(inputList[key]).val()==currentNode.articleid){
			    		checkedKey = key;
			    	}
			      });
			    if(checkedKey>=0){
			    	$(inputList[checkedKey]).prop("checked", true);
			    }
				  $("#articleEditCurrentNode").val(uuid);
				  $("#relate_doc").modal("show");
		  }
		});

}

function relateArticle(){
	var checkedVal = $("input[name='articleOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#articleEditCurrentNode").val());
		node.childrenType = "article";
		node.articleid=checkedVal;
		node.classid=null;
		$("#"+node.UUID+"-content").html("关联文章");
		deleteChildNode(node.UUID);
	}
	$("#relate_doc").modal("hide");
}

function relateClassWindow(uuid){
	var inputList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/articleclasslist",
		  success: function (data) {		  
			  $("#relateArticleClass").html(data);
			  inputList = $("input[name=articleClassOptionsRadios]");
			  var checkedKey=-1;
			  var currentNode = getNodeFromUUID(uuid);
			    $.each(inputList,function(key,val){
			    	if($(inputList[key]).val()==currentNode.classid){
			    		checkedKey = key;
			    	}
			      });
			    if(checkedKey>=0){
			    	$(inputList[checkedKey]).prop("checked", true);
			    }
			  $("#articleClassEditCurrentNode").val(uuid);
			  $("#relate_doc_type").modal("show");
		  }
		});


}

function relateClass(){
	var checkedVal = $("input[name='articleClassOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#articleClassEditCurrentNode").val());
		node.childrenType = "articleclass";
		node.classid=checkedVal;
		node.articleid=null;
		$("#"+node.UUID+"-content").html("关联文章列表");
		deleteChildNode(node.UUID);
	}
	$("#relate_doc_type").modal("hide");
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
function getChildNodeList(fatherUUID){
	  var list = new Array();
	  $.each(nodeList,function(key,val){
			if(nodeList[key].fatherUUID==fatherUUID){
				list.push(nodeList[key]);
			}
		  });
	  return list;
}

function getNodeChildHTML(uuid){
	var childList = getChildNodeList(uuid);
	var html = "<tr>"
          +"<th>栏目名称</th>"
          +"<th>栏目图片</th>"
          +"<th>此栏目按钮关联的内容</th>"
          +"<th></th>"
          +"<th></th>"
          +"<th></th>"
          +"<th></th>"
          +"</tr>";
	  $.each(childList,function(key,node){
		    var nodetypeText = ""; 
		    if(node.childrenType=="node"){
		    	nodetypeText = "关联子栏目";
		    }
		    if(node.childrenType=="article"){
		    	nodetypeText = "关联文章";
		    }
		    if(node.childrenType=="articleclass"){
		    	nodetypeText = "关联文章列表";
		    }
			var picHTML = "";
			if(node.nodePic!=null){
				picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-thumbnail img-responsive\"/>";
			}
            html = html + "<tr id=\""+node.UUID+"\">"
		    +"<td>"+node.nodeName+"</td>"
		    +"<td>"+picHTML+"</td>"
		    +"<td id=\""+node.UUID+"-content"+"\">"+nodetypeText+"</td>"
		    +"<td><a class=\"btn btn-sm btn-info\" data-toggle=\"modal\" onclick=\"createChildNode(\'"+node.UUID+"\')"+"\">为本栏目添加子栏目</a></td>"
		    +"<td><a class=\"btn btn-sm btn-info\" onclick=\"relateArticleWindow(\'"+node.UUID+"\')"+"\">关联文章</a></td>"
		    +"<td><a class=\"btn btn-sm btn-info\" data-toggle=\"modal\" data-target=\"#relate_doc_type\" onclick=\""+"relateClassWindow('"+node.UUID+"')"+"\">关联文章类别</a></td>"
		    +"<td>" 
		    +"<a class=\"btn btn-sm btn-info\" onclick=\""+"editNodeWindow('"+node.UUID+"')"+"\">编辑</a>"
		    +"<a class=\"btn btn-sm btn-danger\" onclick=\""+"deleteNode('"+node.UUID+"')"+"\">删除</a>"  
		    +"</td>"
		    +"</tr>";
		  });
	  return html;	
}

function showList(){
	alert(JSON.stringify(nodeList));
}










