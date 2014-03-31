function submitDeleteVote(voteid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这个投票吗？");
    $("#idhidden").val(voteid);
    $("#confirmModal").modal("show");
}
function confirmDeleteVote(){
	var voteid = $("#idhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/vote/delete",
  	  data: "voteid="+voteid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/vote/list'",1500);
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

function addItemWindow(){
	$("#itemAdd").modal("show");
	$("#descText").val("");
	$("#upload2single-images").html("");
    $("#upload2single-links").html("");
    $("#upload2single-text").val("");
}

function addItem(){
	var itemName = $("#descText").val();
	var list=$("#upload2single-links").children();
	if(list.length!=0){
		itemPic=list[0].value;
	}else{
		itemPic=null;
	}
	if(itemPic==null && itemName==""){
 	   	  $("#modalMes").html("选项图片和选项文字不能都为空。");
   	      $("#operationMesModal").modal("show");
	}else{
	    var itemUUID = getUUID();
	    var appendHtml = "";
		if(itemPic!=null){
		    appendHtml="<tr id=\""+itemUUID+"\">"
		    + "<td class=\""+itemPic+"\"><img src=\""+itemPic+"_original.jpg\" class=\"pic-preview img-thumbnail img-responsive\"/></td>"
		    + "<td>"+itemName+"</td>"
		    + "<td>"
		    + "<a class=\"btn btn-sm btn-info\" onclick=\"editItemWindow(this)\">编辑</a>"
		    + "<a class=\"btn btn-sm btn-danger col-sm-offset-1\" onclick=\"submitDeleteItem(this)\">删除</a>"
		    + "</td>"
		    + "</tr>";
		}else{
		    appendHtml="<tr id=\""+itemUUID+"\">"
		    + "<td class=\"null\"></td>"
		    + "<td>"+itemName+"</td>"
		    + "<td>"
		    + "<a class=\"btn btn-sm btn-info\" onclick=\"editItemWindow(this)\">编辑</a>"
		    + "<a class=\"btn btn-sm btn-danger col-sm-offset-1\" onclick=\"submitDeleteItem(this)\">删除</a>"
		    + "</td>"
		    + "</tr>";
		}
		$("#itemAdd").modal("hide");
	    $("#items").append(appendHtml);
	}
}

function submitDeleteItem(obj){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这个投票选项吗？");
    $("#idhidden").val($(obj).parent().parent().attr("id"));
    $("#confirmModal").modal("show");
}
function confirmDeleteVoteItem(){
	var voteid = $("#idhidden").val();
    $("#"+voteid).remove();
}

function editItemWindow(obj){
    var itemUUID = $(obj).parent().parent().attr("id");
    $("#uuidhidden").val(itemUUID);
    var item = getItemFromUUID(itemUUID);
    if(item.itemPic!=null){
        add_pic_preview_single("upload3single",item.itemPic);
        add_pic_link_single("upload3single",item.itemPic);    	
    }
    $("#descTextEdit").val(item.itemName);
    $("#itemEdit").modal("show");  
}

function editItem(){
	var itemName = $("#descTextEdit").val();
	var list=$("#upload3single-links").children();
	if(list.length!=0){
		itemPic=list[0].value;
	}else{
		itemPic=null;
	}
	if(itemPic==null && itemName==""){
 	   	  $("#modalMes").html("选项图片和选项文字不能都为空。");
   	      $("#operationMesModal").modal("show");
	}else{
		var UUID = $("#uuidhidden").val();
		var tdChildren = $("#"+UUID).children();
		$(tdChildren[1]).html(itemName);
		$(tdChildren[0]).attr("class",itemPic);
		if(itemPic!=null){
		    var html="<img src=\""+itemPic+"_original.jpg\" class=\"pic-preview img-thumbnail img-responsive\"/>";   
		    $(($("."+itemPic.replace(/\//g,"\\/")))[0]).html(html);
		}else{
			$(tdChildren[0]).html("");
		}
		$("#itemEdit").modal("hide");
	}	
}

function submitVote(operation){
		var vote = new Object();
		vote.voteName = $("#voteName").val();
		vote.voteDesc = $("#voteDesc").val();
		var coverPicStr=$("#upload1single-links").children();
		if(coverPicStr.length!=0){
			vote.coverPic=coverPicStr[0].value;
		}else{
			vote.coverPic=null;
		}
		vote.maxSelected = $("#maxSelected").val();
		
		var itemList = new Array();
		var list = $("#items").children();
		if(operation=="insert"){
			for(var i = 0; i < list.length; i++){
				itemList.push(getItemFromUUID($(list[i]).attr("id")));
			}
		}else{
			for(var i = 0; i < list.length; i++){
				itemList.push(getItemFromUUIDForEdit($(list[i]).attr("id")));
			}
		}

		vote.itemList = itemList;	
		if(!validateVote(vote)){
			return;
		}
		
		var url = "";
		if(operation=="insert"){
			url="store/vote/insert";
		}else{
			vote.voteid=$("#editvoteid").val();
			url="store/vote/update";
		}
	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(vote),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){	   			   
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='store/vote/list'",1500);
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

function validateVote(vote){
	var blankInputArray = new Array();
	if(vote.voteName=="")blankInputArray.push("投票名称");
	if(vote.coverPic==null)blankInputArray.push("投票封面");
	if(vote.voteDesc=="")blankInputArray.push("投票介绍");
    if(!isNum(vote.maxSelected))blankInputArray.push("选项可选数量");
	if(vote.itemList.length==0)blankInputArray.push("投票选项");	
	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}

function getUUID(){
	return (new UUID()).id;
}

function getItemFromUUID(UUID){
	var item = new Object();
	var tdChildren = $("#"+UUID).children();
	item.itemName = $(tdChildren[1]).html();
	item.itemPic = $(tdChildren[0]).attr("class");
	if(item.itemPic == "null"){
		item.itemPic = null;
	}
	return item;
}

function getItemFromUUIDForEdit(UUID){
	var item = new Object();
	var tdChildren = $("#"+UUID).children();
	item.itemName = $(tdChildren[1]).html();
	item.itemPic = $(tdChildren[0]).attr("class");
	if(item.itemPic == "null"){
		item.itemPic = null;
	}
	item.itemid=UUID;
	return item;
}
function isNum(s)
{
    if (s!=null && s!="")
    {
        return !isNaN(s);
    }
    return false;
}
function showBlankInputHtml(blankInputArray){
	var blankInputhtml="";
    $.each(blankInputArray,function(key,val){
    	blankInputhtml=blankInputhtml+val+"<br/>";
    });
	$("#modalTitle").html("为了保证投票效果，您需要完善或更改下列信息：");
	$("#modalMes").html(blankInputhtml);
    $("#operationMesModal").modal("show");

    return;
}

