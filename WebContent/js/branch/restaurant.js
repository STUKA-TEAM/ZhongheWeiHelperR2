function filterByApp(appid){
	location.href="branch/restaurant/dish/list?appid='+"+appid+"+'&classid=0";
}
function filterByDishclass(classid){
	appid=$("#appInfo").val();
	location.href="branch/restaurant/dish/list?appid='+"+appid+"+'&classid="+classid;
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
	var url = "branch/restaurant/dish/update";
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






















