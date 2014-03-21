function createCodeWindow(){
    $("#createCode").modal("show");
}
function submitCreateCode(){
	var number = $("#number").val();
	if(number=="" || number < 1 || number > 10){
		  $("#createCode").modal("hide");
   	   	  $("#modalMes").html("输入数量有误！");
   	      $("#operationMesModal").modal("show");
   	      return;
	}
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