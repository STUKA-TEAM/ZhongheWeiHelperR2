function filterByApp(appid){
	location.href="branch/restaurant/dish/list?appid='+"+appid+"+'&classid=0";
}
function filterByDishclass(classid){
	appid=$("#appInfo").val();
	location.href="branch/restaurant/dish/list?appid='+"+appid+"+'&classid="+classid;
}
function submitItem(operation){
	var item = new Object();
	item.roleid=$("#roleid").val();
	item.username=$("#username").val();
	item.password=$("#password").val();
	item.storeName=$("#storeName").val();
	item.phone=$("#phone").val();
	item.address=$("#address").val();
	item.lng=$("#lng").val();
	item.lat=$("#lat").val();
    var linkInputArray=$("#upload1-links").children();
    var linkArray=new Array();
    $.each(linkInputArray,function(key,val){
  	  linkArray.push($(val).val());
    });
    item.imageList=linkArray;

	var list = new Array();
	$("input[type=checkbox][name='options']:checked").each(function(){
		list.push($(this).val());
	});
	item.classidList=list;
	
	var url = "";
	if(operation=="insert"){
		if(!validateItem(item))return;
		url="store/branch/insert";
	}else{
		if(!validateUpdateItem(item))return;
		item.branchSid=$("#branchSid").val();
		url="store/branch/update";
	}
 	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(item),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {

	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='store/branch/list?classid=0'",1500);
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

function validateItem(item){
	var blankInputArray = new Array();
	if(item.username=="")blankInputArray.push("登录名");
    if(item.password=="")blankInputArray.push("密码");
	if(item.storeName=="")blankInputArray.push("分店名称");
	if(item.phone=="")blankInputArray.push("分店电话");
    if(item.address=="")blankInputArray.push("分店地址");
	if($("#password").val()!=$("#passwordConfirm").val())blankInputArray.push("两次输入的密码不一致");
	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}

function validateUpdateItem(item){
	var blankInputArray = new Array();
	if(item.username=="")blankInputArray.push("登录名");
	if(item.storeName=="")blankInputArray.push("分店名称");
	if(item.phone=="")blankInputArray.push("分店电话");
    if(item.address=="")blankInputArray.push("分店地址");
	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}

function showBlankInputHtml(blankInputArray){
	var blankInputhtml="";
    $.each(blankInputArray,function(key,val){
    	blankInputhtml=blankInputhtml+val+"<br/>";
    });
	$("#modalTitle").html("您还需要完善下列信息：");
	$("#modalMes").html(blankInputhtml);
    $("#operationMesModal").modal("show");
    return;
}

function submitDelete(itemid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除吗？");
    $("#itemidhidden").val(itemid);
    $("#confirmModal").modal("show");
}
function confirmDelete(){
	var itemid = $("#itemidhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/branch/delete",
  	  data: "branchid="+itemid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/branch/list?classid=0'",1500);
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

function changePassword(branchid){
	$("#paper_type_dialog").html();
	$("#paper_type_edit").modal("show"); 
	$("#branchidhidden").val(branchid);
	
}
function submitChange(){
	if($("#password").val()==""){
		$("#modalTitle").html("提示：");
		$("#modalMes").html("密码不能为空！");
	    $("#operationMesModal").modal("show");
	    return;
	}
	if($("#password").val()!= $("#confirm").val()){
		$("#modalTitle").html("提示：");
		$("#modalMes").html("两次输入的密码不一致！");
	    $("#operationMesModal").modal("show");
	    return;
	}
	var branchid = $("#branchidhidden").val();
	var passwd = $("#password").val();
	var url = "store/branch/reset";
	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: "branchid="+branchid+"&passwd="+passwd,
	   	  success: function (data) {
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='store/branch/list?classid=0'",1500);
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






















