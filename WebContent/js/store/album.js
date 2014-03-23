function submitDeleteAlbumclass(classid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这个相册集吗？");
    $("#albumclassidhidden").val(classid);
    $("#confirmModal").modal("show");
}
function confirmDeleteAlbumclass(){
	var albumclassid = $("#albumclassidhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/albumclass/delete",
  	  data: "classid="+albumclassid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/albumclass/list'",1500);
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

function addArticleclass(){
	  $.ajax({
	   	  type: "GET",
	   	  url: "store/articleclass/edit/insert",
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
function editArticleclass(classid){
	  $.ajax({
	   	  type: "GET",
	   	  url: "store/articleclass/edit/update?classid="+classid,
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