function clickLike(dishid){
	  $.ajax({
		  type: "POST",
		  url: "customer/dish/like",
		  data: "dishid="+dishid,
		  success: function (data) {
            $("#"+dishid+"_good").attr("onclick","");
            $("#"+dishid+"_goodImg").attr("src","./img/common/like_gray.png");
            $("#"+dishid+"_goodNum").html(parseInt($("#"+dishid+"_goodNum").html())+1);
		  }
		});
}

function showDishDetail(dishid){
	$("#dishTitle").html($("#"+dishid+"_dishName").html());
	$("#dishLike").html($("#"+dishid+"_goodNum").html()+"人赞过");
	$("#dishDesc").html($("#"+dishid+"_dishDesc").val());
	$("#dishDetailImg").attr("src",$("#"+dishid+"_dishImgID").val()+"_original.jpg");
	$("#detail").modal("show");
}

function minusCount(openid,branchid,dishid){
	var count = parseInt($("#"+dishid+"_count").html());
	var currentClassId = $("#currentClass").val();
	if(count==1){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/delete",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid,
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(count-1);
		  minusDishclassCount(currentClassId);
	}
	if(count>1){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/update",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid+"&status=0",
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(count-1);
		  minusDishclassCount(currentClassId);
	}
	
}

function minusDishclassCount(currentClassId){
	var count = parseInt($("#"+currentClassId+"_dishClassNotice").html());
	if(count==1){
		$("#"+currentClassId+"_dishClassNoticeContainer").html("");
	};
	if(count>1){
		  var noticeHtml = "<div id=\""+currentClassId+"_dishClassNotice\" class=\"type-count\">"+parseInt(count-1)+"</div>";
		  $("#"+currentClassId+"_dishClassNoticeContainer").html(noticeHtml);
	};
}

function plusCount(openid,branchid,dishid){
	var count = parseInt($("#"+dishid+"_count").html());
	var currentClassId = $("#currentClass").val();
	if(count==0){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/insert",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid,
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(parseInt(count)+1);
		  plusDishclassCount(currentClassId);
	}
	if(count>0){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/update",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid+"&status=1",
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(parseInt(count)+1);
		  plusDishclassCount(currentClassId);
	}
}

function plusDishclassCount(currentClassId){
	if($("#"+currentClassId+"_dishClassNoticeContainer").html() == ""){
		var count = 0;
	}else{
		var count = parseInt($("#"+currentClassId+"_dishClassNotice").html());
	}
	var noticeHtml = "<div id=\""+currentClassId+"_dishClassNotice\" class=\"type-count\">"+parseInt(count+1)+"</div>";
	$("#"+currentClassId+"_dishClassNoticeContainer").html(noticeHtml);

}

function changeCurrentClass(obj, openid, branchid){
	var thisId = $(obj).attr("id");
	var classid = thisId.slice(0,thisId.indexOf("_dishClass"));
	  $.ajax({
		  type: "GET",
		  url: "customer/dish/branchMenuByClass",
		  data: "openid="+openid+"&branchid="+branchid+"&classid="+classid,
		  success: function (data) {
			  $("#dishesContent").html(data);
			  var oldClassid = $("#currentClass").val();
			  $("#"+oldClassid+"_dishClass").removeClass("active");
			  $("#currentClass").val(classid);
			  $("#"+classid+"_dishClass").addClass("active");
		  }
		});
}

function deleteAll(openid, branchid, appid){
	  $.ajax({
		  type: "POST",
		  url: "customer/dish/deleteAll",
		  data: "openid="+openid+"&branchid="+branchid,
		  success: function (data) {
			  var jsonData = JSON.parse(data);
			  if(jsonData.status){
				  location.href="customer/dish/branchMenu?openid="+openid+"&appid="+appid+"&branchid="+branchid;
			  }else{
				  alert(jsonData.message);
			  }
		  },
		  error: function(xhr, status, exception){
	   	   	  alert("删除失败，请稍后再试");
		  }
		});
}

function minusCountForOrder(openid,branchid,dishid){
	var count = parseInt($("#"+dishid+"_count").html());
	if(count==1){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/delete",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid,
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(count-1);
		  minusSum(dishid);
	}
	if(count>1){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/update",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid+"&status=0",
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(count-1);
		  minusSum(dishid);
	}
	
}

function minusSum(dishid){
	if($("#sumPrice").length==0 || $("#"+dishid+"_dishPrice").length==0)return;
	var price = parseInt($("#"+dishid+"_dishPrice").html().slice(0,$("#"+dishid+"_dishPrice").html().indexOf("元")));
	var sumPrice = parseInt($("#sumPrice").html().slice(0,$("#sumPrice").html().indexOf("元")));
	$("#sumPrice").html(parseInt(sumPrice-price)+"元");	
}

function plusCountForOrder(openid,branchid,dishid){
	var count = parseInt($("#"+dishid+"_count").html());
	if(count==0){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/insert",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid,
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(parseInt(count)+1);
		  plusSum(dishid);
	}
	if(count>0){
		  $.ajax({
			  type: "POST",
			  url: "customer/dish/update",
			  data: "openid="+openid+"&branchid="+branchid+"&dishid="+dishid+"&status=1",
			  success: function (data) {
			  }
			});
		  $("#"+dishid+"_count").html(parseInt(count)+1);
		  plusSum(dishid);
	}
}

function plusSum(dishid){
	if($("#sumPrice").length==0 || $("#"+dishid+"_dishPrice").length==0)return;
	var price = parseInt($("#"+dishid+"_dishPrice").html().slice(0,$("#"+dishid+"_dishPrice").html().indexOf("元")));
	var sumPrice = parseInt($("#sumPrice").html().slice(0,$("#sumPrice").html().indexOf("元")));
	$("#sumPrice").html(parseInt(sumPrice+price)+"元");	
}
