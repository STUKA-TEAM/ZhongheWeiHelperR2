function addClass(){
	  $.ajax({
	   	  type: "GET",
	   	  url: "store/dishclass/add",
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
	   	  url: "store/dishclass/edit?classid="+classid,
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
	var list = new Array();
	item.className = $("#className").val();
	$("input[type=checkbox][name='options']:checked").each(function(){
		list.push($(this).val());
	});
	item.dishidList = list;
	var url = "";
	if(operation=="insert"){
		url="store/dishclass/insert";
	}else{
		item.classid=$("#editclassid").val();
		url="store/dishclass/update";
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
		   	      setTimeout("location.href='store/dishclass/list'",1500);
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
  	  url: "store/dishclass/delete",
  	  data: "classid="+classid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/dishclass/list'",1500);
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
