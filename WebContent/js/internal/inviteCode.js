function createCodeWindow(){
    $("#createCode").modal("show");
}
function submitCreateCode(){
	var number = $("#number").val();
	$.ajax({
	  	  type: "POST",
	  	  url: "internal/invite/create",
	  	  data: "number="+number,
	   	  success: function (data) {
	   		  $("#createCode").modal("hide");
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='internal/invite/intial'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   	  },
		  error: function(xhr, status, exception){
			  $("#createCode").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}