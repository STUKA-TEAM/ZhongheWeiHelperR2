function editContact(sid){
    $("#store_contact").val($(sid+"_contact").html());
    $("#store_id").val(sid);
    $("#edit_contact").modal("show");
}
function submitEditContact(){
	var contact = $("#store_contact").val();
	var sid = $("#store_id").val();
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