function filterByApp(appid){
	location.href="branch/restaurant/dish/list?appid="+appid+"&classid=0";
}
function filterByDishclass(classid){
	appid=$("#appInfo").val();
	location.href="branch/restaurant/dish/list?appid="+appid+"&classid="+classid;
}

function submitChange(dishid){
	var price = $("#"+dishid+"_price").val();
	if(price == ""){
		$("#modalTitle").html("提示：");
		$("#modalMes").html("菜价不能为空！");
	    $("#operationMesModal").modal("show");
	    return;
	}
	if(isNaN(price)){
		$("#modalTitle").html("提示：");
		$("#modalMes").html("菜价填写有误！");
	    $("#operationMesModal").modal("show");
	    return;
	}
	var item = new Object();
	item.dishid = dishid;
	item.price = price;
	item.available = $("input[type=radio][name='"+dishid+"_dish']:checked").val();
	var url = "branch/restaurant/dish/branch/update";
	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(item),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout(function(){
		   	    	location.href="branch/restaurant/dish/list?appid='+"+$("#appInfo").val()+"+'&classid="+$("#dishclass").val();
		   	      },1500);
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

function submitItem(operation){
	var item = new Object();
	item.dishName=$("#dishName").val();
	var picStr=$("#upload1single-links").children();
	if(picStr.length!=0){
		item.dishPic=picStr[0].value;
	}else{
		item.dishPic=null;
	}
	item.price=$("#price").val();
	item.dishUnit=$("#dishUnit").val();
	item.recomNum=$("#recomNum").val();
	item.dishDesc=$("#dishDesc").val();

	var list = new Array();
	var radioValue=$("input[type=radio][name='options']:checked").val();
	if(typeof radioValue != 'undefined'){
		list.push(radioValue);
	}	
	item.classidList=list;
	
	var url = "";
	if(operation=="insert"){
		if(!validateItem(item))return;
		item.appid=$("#appid").val();
		url="branch/restaurant/dish/insert";
	}else{
		if(!validateUpdateItem(item))return;
		item.dishid=$("#dishid").val();
		url="branch/restaurant/dish/update";
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
		   	      setTimeout("location.href='branch/restaurant/dish/list?appid=initial&classid=0'",1500);
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
	if(item.dishName=="")blankInputArray.push("菜品名称");
    if(item.price=="")blankInputArray.push("默认价格");
	if(item.dishUnit=="")blankInputArray.push("价格单位");
	if(item.recomNum=="")blankInputArray.push("初始推荐数");
    if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}

function validateUpdateItem(item){
	var blankInputArray = new Array();
	if(item.dishName=="")blankInputArray.push("菜品名称");
    if(item.price=="")blankInputArray.push("默认价格");
	if(item.dishUnit=="")blankInputArray.push("价格单位");
	if(item.recomNum=="")blankInputArray.push("初始推荐数");
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
  	  url: "branch/restaurant/dish/delete",
  	  data: "dishid="+itemid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='branch/restaurant/dish/list?appid=initial&classid=0'",1500);
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





















