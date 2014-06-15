function filterByApp(appid){
	location.href="branch/restaurant/dishclass/list?appid="+appid;
}

function submitChange(classid){
	var item = new Object();
	item.classid = classid;
	item.available = $("input[type=radio][name='"+classid+"_dishclass']:checked").val();
	var url = "branch/restaurant/dishclass/branch/update";
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
		   	    	location.href="branch/restaurant/dishclass/list?appid="+$("#appInfo").val();
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

function addClass(){
	  $.ajax({
	   	  type: "GET",
	   	  url: "branch/restaurant/dishclass/add",
	   	  success: function (data) {
		   	   	  $("#paper_type_dialog").html(data);
		   	      $("#paper_type_edit").modal("show");  
	   	  },
		  error: function(xhr, status, exception){
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
 	  });
}
function editClass(classid){
	  $.ajax({
	   	  type: "GET",
	   	  url: "branch/restaurant/dishclass/edit?classid="+classid,
	   	  success: function (data) {
		   	   	  $("#paper_type_dialog").html(data);
		   	      $("#paper_type_edit").modal("show");  
	   	  },
		  error: function(xhr, status, exception){
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}
function submitClass(operation){
	var item = new Object();
	item.className = $("#className").val();
	item.appid = $("#appInfo").val();
	var url = "";
	if(operation=="insert"){
		url="branch/restaurant/dishclass/insert";
	}else{
		item.classid=$("#editclassid").val();
		url="branch/restaurant/dishclass/update";
	}
	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(item),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		  $("#paper_type_edit").modal("hide");
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){	   			   
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='branch/restaurant/dishclass/list?appid="+$("#appInfo").val()+"'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   		  
	   	  },
		  error: function(xhr, status, exception){
			  $("#paper_type_edit").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}

function submitDelete(classid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除吗？");
  $("#idhidden").val(classid);
  $("#confirmModal").modal("show");
}
function confirmDelete(){
	var classid = $("#idhidden").val();
  $.ajax({
	  type: "POST",
	  url: "branch/restaurant/dishclass/delete",
	  data: "classid="+classid,
 	  success: function (data) {
 		  var jsonData=JSON.parse(data);		 
 		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='branch/restaurant/dishclass/list?appid="+$("#appInfo").val()+"'",1500);
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

function supplyAll(){
	var appid = $("#appInfo").val();
	  $.ajax({
		  type: "POST",
		  url: "branch/restaurant/dishclass/supplyAll",
		  data: "appid="+appid,
	 	  success: function (data) {
	 		  var jsonData=JSON.parse(data);		 
	 		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='branch/restaurant/dishclass/list?appid="+$("#appInfo").val()+"'",1500);
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




















