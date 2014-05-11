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
	var sharePic=$("#upload2single-links").children();
	if(sharePic.length!=0){
		step1Info.sharePic=sharePic[0].value;
	}else{
		step1Info.sharePic="";
	}
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
	if(step1Info.sharePic=="")blankInputArray.push("分享消息图片");
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
					  generateNodeLayer(nodeList[0].UUID);
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

//访问Container时初始化nodeList
$(document).ready(function(){
	nodeList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/wizard/getnodes",
		  success: function (data) {
			  if(data=="[]"){
					root = new Object();
					root.UUID = (new UUID()).id;
					root.nodeName = "root";
					root.childrenType = "node";
					root.fatherUUID="null_node";
					$("#fatheruuid").val(root.UUID);
					nodeList.push(root);
					return;
			  }else{
				  nodeList=JSON.parse(data);
				  for(var i=0;i<nodeList.length;i++){
					  nodeList[i].UUID=(new UUID()).id;
				  }
				  for(var i=0;i<nodeList.length;i++){
					  for(var k=0;k<nodeList.length;k++){
						  if(nodeList[i].fatherid==nodeList[k].nodeid){
							  nodeList[i].fatherUUID=nodeList[k].UUID;
						  }
					  } 
				  }
				  if(nodeList.length>0){
					  nodeList[0].fatherUUID="null_node";
				  }
			  }
		  }
		});

});

//添加栏目弹窗
function addNodeWindow(){
	$("#node-Name").val("");
	$("#filetext").val("");
	$("#upload1single-images").html("");
	$("#upload1single-links").html("");
	$("#add_child_column").modal("show");
}

//添加栏目操作
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

//添加栏目后页面显示相应区块 TODO
function addNodeItem(node){
	var picHTML = "";
	if(node.nodePic!=null){
		picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-responsive\"/>";
	}
    var html="<tr id=\""+node.UUID+"\">"
    +"<td>"+node.nodeName+"</td>"
    +"<td>"+picHTML+"</td>"
    +"<td id=\""+node.UUID+"-content"+"\"></td>"
    +"<td><a class=\"btn btn-sm btn-info operationButton\" onclick=\"createChildNode(\'"+node.UUID+"\')"+"\">为本栏目添加子栏目</a>"
    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateArticleWindow(\'"+node.UUID+"\')"+"\">关联文章</a>"
    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\""+"relateClassWindow('"+node.UUID+"')"+"\">关联文章类别</a>" 
    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateAlbumWindow(\'"+node.UUID+"\')"+"\">关联相册</a>"
    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateAlbumClassWindow(\'"+node.UUID+"\')"+"\">关联相册集</a>"
    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateVoteWindow(\'"+node.UUID+"\')"+"\">关联投票</a>"
    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateLotteryWheelWindow(\'"+node.UUID+"\')"+"\">关联大转盘</a>"
    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateBranchclassWindow(\'"+node.UUID+"\')"+"\">关联分店区域</a></td>"
    +"<td>" 
    +"<a class=\"btn btn-sm btn-info\" onclick=\""+"editNodeWindow('"+node.UUID+"')"+"\">编辑</a>"
    +"<a class=\"btn btn-sm btn-danger\" onclick=\""+"deleteNode('"+node.UUID+"')"+"\">删除</a>"  
    +"</td>"
    +"</tr>";
    $("#nodeItems").append(html);
}

//为参数节点创建子节点 
function createChildNode(uuid){
	generateNodeLayer(uuid);
	var node = getNodeFromUUID(uuid);
	node.childrenType = "node";
	node.childid=null;
}

//为参数节点生成其字节点页面（“下一层”）
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

//为当前页面的父节点生成页面（“上一层”）
function generateFatherLayer(){
	var fatherUUID = $("#fatheruuid").val();
	var generateKey = getNodeFromUUID(fatherUUID).fatherUUID;
	if(generateKey!="null_node"){
		generateNodeLayer(generateKey);

	}else{
		alert("没有上一级！");
	}
	
}

//编辑参数节点弹窗
function editNodeWindow(uuid){
	var node = getNodeFromUUID(uuid);
	$("#edit-node-Name").val(node.nodeName);
	$("#edit-filetext").val("");	
	var picHTML = "";
	if(node.nodePic!=null){
		picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-responsive\"/>";
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

//编辑参数节点
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

//编辑参数节点相应区块
function editNodeItem(node){
	var list = $("#"+node.UUID).children();
	$(list[0]).html(node.nodeName);
	var picHTML = "";
	if(node.nodePic!=null){
		picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-responsive\"/>";
	}
	$(list[1]).html(picHTML);
}

//删除节点
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

//删除子节点
function deleteChildNode(uuid){
	var list = getChildNodeList(uuid);
	for(var i=0; i<list.length; i++){
		deleteNode(list[i].UUID);
	}
}

//关联文章弹窗
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
			    	if($(inputList[key]).val()==currentNode.childid){
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

//关联文章操作 
function relateArticle(){
	var checkedVal = $("input[name='articleOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#articleEditCurrentNode").val());
		node.childrenType = "article";
		node.childid=checkedVal;
		$("#"+node.UUID+"-content").html("关联文章");
		deleteChildNode(node.UUID);
	}
	$("#relate_doc").modal("hide");
}

//关联文章类别弹窗
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
			    	if($(inputList[key]).val()==currentNode.childid){
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

//关联文章类别操作
function relateClass(){
	var checkedVal = $("input[name='articleClassOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#articleClassEditCurrentNode").val());
		node.childrenType = "articleclass";
		node.childid=checkedVal;
		$("#"+node.UUID+"-content").html("关联文章列表");
		deleteChildNode(node.UUID);
	}
	$("#relate_doc_type").modal("hide");
}

//关联相册弹窗
function relateAlbumWindow(uuid){
	var inputList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/albumlist",
		  success: function (data) {		  
			  $("#relateAlbum").html(data);
			  inputList = $("input[name=albumOptionsRadios]");
			  var checkedKey=-1;
			  var currentNode = getNodeFromUUID(uuid);
			    $.each(inputList,function(key,val){
			    	if($(inputList[key]).val()==currentNode.childid){
			    		checkedKey = key;
			    	}
			      });
			    if(checkedKey>=0){
			    	$(inputList[checkedKey]).prop("checked", true);
			    }
				  $("#albumEditCurrentNode").val(uuid);
				  $("#relate_Album").modal("show");
		  }
		});
}
//关联相册
function relateAlbum(){
	var checkedVal = $("input[name='albumOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#albumEditCurrentNode").val());
		node.childrenType = "album";
		node.childid=checkedVal;
		$("#"+node.UUID+"-content").html("关联相册");
		deleteChildNode(node.UUID);
	}
	$("#relate_Album").modal("hide");
}
//关联相册集弹窗
function relateAlbumClassWindow(uuid){
	var inputList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/albumclasslist",
		  success: function (data) {		  
			  $("#relateAlbumClass").html(data);
			  inputList = $("input[name=albumClassOptionsRadios]");
			  var checkedKey=-1;
			  var currentNode = getNodeFromUUID(uuid);
			    $.each(inputList,function(key,val){
			    	if($(inputList[key]).val()==currentNode.childid){
			    		checkedKey = key;
			    	}
			      });
			    if(checkedKey>=0){
			    	$(inputList[checkedKey]).prop("checked", true);
			    }
			  $("#albumClassEditCurrentNode").val(uuid);
			  $("#relate_album_type").modal("show");
		  }
		});
}
//关联相册集
function relateAlbumClass(){
	var checkedVal = $("input[name='albumClassOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#albumClassEditCurrentNode").val());
		node.childrenType = "albumclass";
		node.childid=checkedVal;
		$("#"+node.UUID+"-content").html("关联相册列表");
		deleteChildNode(node.UUID);
	}
	$("#relate_album_type").modal("hide");
}
//关联投票弹窗
function relateVoteWindow(uuid){
	var inputList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/votelist",
		  success: function (data) {		  
			  $("#relateVote").html(data);
			  inputList = $("input[name=voteOptionsRadios]");
			  var checkedKey=-1;
			  var currentNode = getNodeFromUUID(uuid);
			    $.each(inputList,function(key,val){
			    	if($(inputList[key]).val()==currentNode.childid){
			    		checkedKey = key;
			    	}
			      });
			    if(checkedKey>=0){
			    	$(inputList[checkedKey]).prop("checked", true);
			    }
				  $("#voteEditCurrentNode").val(uuid);
				  $("#relate_Vote").modal("show");
		  }
		});
}
//关联投票
function relateVote(){
	var checkedVal = $("input[name='voteOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#voteEditCurrentNode").val());
		node.childrenType = "vote";
		node.childid=checkedVal;
		$("#"+node.UUID+"-content").html("关联投票");
		deleteChildNode(node.UUID);
	}
	$("#relate_Vote").modal("hide");
}
//关联大转盘弹窗
function relateLotteryWheelWindow(uuid){
	var inputList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/lottery/wheellist",
		  success: function (data) {		  
			  $("#relateLotteryWheel").html(data);
			  inputList = $("input[name=lotteryWheelOptionsRadios]");
			  var checkedKey=-1;
			  var currentNode = getNodeFromUUID(uuid);
			    $.each(inputList,function(key,val){
			    	if($(inputList[key]).val()==currentNode.childid){
			    		checkedKey = key;
			    	}
			      });
			    if(checkedKey>=0){
			    	$(inputList[checkedKey]).prop("checked", true);
			    }
				  $("#lotteryWheelEditCurrentNode").val(uuid);
				  $("#relate_LotteryWheel").modal("show");
		  }
		});
}
//关联大转盘
function relateLotteryWheel(){
	var checkedVal = $("input[name='lotteryWheelOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#lotteryWheelEditCurrentNode").val());
		node.childrenType = "lotterywheel";
		node.childid=checkedVal;
		$("#"+node.UUID+"-content").html("关联大转盘");
		deleteChildNode(node.UUID);
	}
	$("#relate_LotteryWheel").modal("hide");
}

//关联分店区域弹窗
function relateBranchclassWindow(uuid){
	var inputList = new Array();
	  $.ajax({
		  type: "GET",
		  url: "store/website/branchclasslist",
		  success: function (data) {		  
			  $("#relateBranchClass").html(data);
			  inputList = $("input[name=branchClassOptionsRadios]");
			  var checkedKey=-1;
			  var currentNode = getNodeFromUUID(uuid);
			    $.each(inputList,function(key,val){
			    	if($(inputList[key]).val()==currentNode.childid){
			    		checkedKey = key;
			    	}
			      });
			    if(checkedKey>=0){
			    	$(inputList[checkedKey]).prop("checked", true);
			    }
			  $("#branchClassEditCurrentNode").val(uuid);
			  $("#relate_branchclass_type").modal("show");
		  }
		});
}

//关联分店区域操作
function relateBranchclass(){
	var checkedVal = $("input[name='branchClassOptionsRadios']:checked").val();
	if(typeof(checkedVal)!="undefined"){
		var node = getNodeFromUUID($("#branchClassEditCurrentNode").val());
		node.childrenType = "branchclass";
		node.childid=checkedVal;
		$("#"+node.UUID+"-content").html("关联分店区域");
		deleteChildNode(node.UUID);
	}
	$("#relate_branchclass_type").modal("hide");
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

//生成参数节点子节点的区块 TODO
function getNodeChildHTML(uuid){
	var childList = getChildNodeList(uuid);
	var html = "<tr>"
          +"<th class=\"col-md-2\">栏目名称</th>"
          +"<th class=\"col-md-2\">栏目图片</th>"
          +"<th class=\"col-md-2\">此栏目按钮关联的内容</th>"
          +"<th class=\"col-md-4\">此栏目可选操作</th>"
          +"<th class=\"col-md-2\"></th>"
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
		    if(node.childrenType=="album"){
		    	nodetypeText = "关联相册";
		    }
		    if(node.childrenType=="albumclass"){
		    	nodetypeText = "关联相册列表";
		    }
		    if(node.childrenType=="vote"){
		    	nodetypeText = "关联投票";
		    }
		    if(node.childrenType=="lotterywheel"){
		    	nodetypeText = "关联大转盘";
		    }
		    if(node.childrenType=="branchclass"){
		    	nodetypeText = "关联分店区域";
		    }
			var picHTML = "";
			if(node.nodePic!=null){
				picHTML = "<img src=\""+node.nodePic+"_original.jpg"+"\" class=\"pic-preview img-responsive\"/>";
			}
            html = html + "<tr id=\""+node.UUID+"\">"
		    +"<td>"+node.nodeName+"</td>"
		    +"<td>"+picHTML+"</td>"
		    +"<td id=\""+node.UUID+"-content"+"\">"+nodetypeText+"</td>"
		    +"<td><a class=\"btn btn-sm btn-info operationButton\" onclick=\"createChildNode(\'"+node.UUID+"\')"+"\">为本栏目添加子栏目</a>"
		    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateArticleWindow(\'"+node.UUID+"\')"+"\">关联文章</a>"
		    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\""+"relateClassWindow('"+node.UUID+"')"+"\">关联文章类别</a>" 
		    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateAlbumWindow(\'"+node.UUID+"\')"+"\">关联相册</a>"
		    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateAlbumClassWindow(\'"+node.UUID+"\')"+"\">关联相册集</a>"
		    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateVoteWindow(\'"+node.UUID+"\')"+"\">关联投票</a>"
		    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateLotteryWheelWindow(\'"+node.UUID+"\')"+"\">关联大转盘</a>"
		    +"<a class=\"btn btn-sm btn-info operationButton\" onclick=\"relateBranchclassWindow(\'"+node.UUID+"\')"+"\">关联分店区域</a></td>"
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


function getSysIcon(operation){
	  $.ajax({
		  type: "GET",
		  url: "/resources/upload/image/iconlib/list",
		  success: function (data) {		  				  
			  var pathList=JSON.parse(data);
			  var liList="";
			  $.each(pathList,function(key,val){
					liList=liList+"<div class=\"col-sm-2\"><input name=\"icon\" type=\"radio\" value=\""+val+"\">"+"<img src=\""+val+"\"/></div>";
				  });
			  $("#iconContent").html(liList);
			  $("#selectType").val(operation);
			  $("#sysIcon").modal("show");					
		  }
		});
}

function selectIcon(){
	var operation = $("#selectType").val();

		$.ajax({
		  	  type: "POST",
		  	  url: "/resources/upload/image/iconlib/copy",
		  	  data: "filepath="+$("input[name=icon]:checked").val(),
		   	  success: function (data) {
		   		  var jsonData=JSON.parse(data);		 
		   		  if(jsonData.status==true){
		   			var picHTML = "";
		   			if(jsonData.link!=null){
		   				picHTML = "<img src=\""+jsonData.link+"_original.jpg"+"\" class=\"pic-preview img-responsive\"/>";
		   			}
		   			var imageHTML = "<div id=\""+jsonData.link+"\" class=\"col-md-6 pic-preview-div\">"
		   			                +picHTML
		   			                +"<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage('"+jsonData.link+"')\"> </span>"
		   			                +"</div>";
		   			

		   			var inputHTML = "";
		   			if(jsonData.link!=null){
		   				inputHTML = "<input id=\""+jsonData.link+"-input\" type=\"hidden\" value=\""+jsonData.link+"\">";
		   			}
		   			
		   			
		   			if(operation=="edit"){
		   				$("#upload2single-images").html(imageHTML);
		   				$("#upload2single-links").html(inputHTML);
		   			}
		   			if(operation=="add"){
		   				$("#upload1single-images").html(imageHTML);
		   				$("#upload1single-links").html(inputHTML);		   				
		   			}
		   			
		   			$("#sysIcon").modal("hide");
			   	      
		   		  }else{
			   	   	  $("#modalMes").html(jsonData.message);
			   	      $("#operationMesModal").modal("show");
		   		  }
		   	  },
			  error: function(xhr, status, exception){
				  $("#editNotPay").modal("hide");
		   	   	  $("#modalMes").html(status + '</br>' + exception);
		   	      $("#operationMesModal").modal("show");
			  }
		  });
}

