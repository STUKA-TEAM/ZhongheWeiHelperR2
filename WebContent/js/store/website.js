function submitDeleteWebsite(websiteid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除微官网吗？删除后此微官网已配置的各种资源都将被删除！");
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










