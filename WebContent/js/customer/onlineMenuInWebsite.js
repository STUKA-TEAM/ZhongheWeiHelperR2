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

function showDishDetail(index){
	$("#detail").modal("show");
	var elem = document.getElementById('myModalSwipe');
    window.myModalSwipe = Swipe(elem, {
      startSlide: index,
      continuous: true,
    });
}


function changeCurrentClass(obj, openid, branchid){
	var thisId = $(obj).attr("id");
	var classid = thisId.slice(0,thisId.indexOf("_dishClass"));
	  $.ajax({
		  type: "GET",
		  url: "customer/branch/branchMenuByClass",
		  data: "branchid="+branchid+"&classid="+classid,
		  success: function (data) {
			  $("#dishesContent").html(data);
			  var oldClassid = $("#currentClass").val();
			  $("#"+oldClassid+"_dishClass").removeClass("active");
			  $("#currentClass").val(classid);
			  $("#"+classid+"_dishClass").addClass("active");
		  }
		});
}


