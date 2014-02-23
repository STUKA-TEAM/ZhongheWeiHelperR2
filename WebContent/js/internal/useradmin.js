function editContact(sid){
    $("#store_contact").val($("#"+sid+"_contact").html());
    $("#store_id").val(sid);
    $("#edit_contact").modal("show");
}
function submitEditContact(){
	var contact = $("#store_contact").val();
	var sid = $("#store_id").val();
	var userInfo = new Object();
	userInfo.contact = contact;
	userInfo.sid = sid;
	$.ajax({
	  	  type: "POST",
	  	  url: "internal/customer/contact/update",
	  	  data: JSON.stringify(userInfo),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		  $("#edit_contact").modal("hide");
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='internal/customer/detail'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   	  },
		  error: function(xhr, status, exception){
			  $("#edit_contact").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}

function editNotPay(sid){
	  $.ajax({
		  type: "GET",
		  url: "internal/customer/elove/notpaylist/query?sid="+sid,
		  success: function (data) {		  
			  $("#eloveNotPayContent").html(data);
			  $("#editNotPay").modal("show");
		  }
		}); 
}

function submitEditNotPay(){
	
	var notPayList = new Array();
	$(".notPayInput").each(function(){
		var eloveNotPay = new Object();
		eloveNotPay.notPayNumber = $(this).val();
		eloveNotPay.appid = $(this).attr("id");
		notPayList.push(eloveNotPay);
	});
	$.ajax({
	  	  type: "POST",
	  	  url: "internal/customer/elove/notpaylist/update",
	  	  data: JSON.stringify(notPayList),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		  $("#editNotPay").modal("hide");
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href=\'internal/customer/edit?sid="+$("#store_id").val()+"\'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   	  },
		  error: function(xhr, status, exception){
			  $("#editNotPay").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}

function editAuthInfo(type){
	if(type=="elove"){
		$("#infoName").html("Elove权限信息更改");
		$("#expired_date").val($("#elove_expired").html());
		$("#price").val($("#elove_price").html());
		$("#infoType").val("elove");
	}
	if(type=="website"){
		$("#infoName").html("微官网权限信息更改");
		$("#expired_date").val($("#website_expired").html());
		$("#price").val($("#website_price").html());
		$("#infoType").val("website");
	}	
	$("#editInfo").modal("show");
}

function submitEditAuthInfo(){
	var info = new Object();
	info.sid=$("#store_id").val();
	info.expiredTime=$("#expired_date").val();
	info.authPinyin=$("#infoType").val();
	info.price=$("#price").val();
	$.ajax({
	  	  type: "POST",
	  	  url: "internal/customer/basic/update",
	  	  data: JSON.stringify(info),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		  $("#editInfo").modal("hide");
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href=\'internal/customer/edit?sid="+$("#store_id").val()+"\'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   	  },
		  error: function(xhr, status, exception){
			  $("#editInfo").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}

function alertMessage(sid){
	$.ajax({
	  	  type: "GET",
	  	  url: "internal/message/elove/alert",
	  	  data: "sid="+sid,
	   	  success: function (data) {
	   		  $("#editInfo").modal("hide");
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
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

function ensureMessage(sid){
	$.ajax({
	  	  type: "GET",
	  	  url: "internal/message/elove/ensure",
	  	  data: "sid="+sid,
	   	  success: function (data) {
	   		  $("#editInfo").modal("hide");
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
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

function sendMessageWindow(sid){
	 $("#messageContent").html("");
	 $("#message_sid").val(sid);
	 $("#sendMessage").modal("show");
	 
}

function sendMessage(){
	$.ajax({
	  	  type: "GET",
	  	  url: "internal/message/elove/alert",
	  	  data: "sid="+$("#message_sid").val()+"&content="+$("#messageContent").val(),
	   	  success: function (data) {
	   		  $("#editInfo").modal("hide");
	   		  var jsonData=JSON.parse(data);		 
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
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



