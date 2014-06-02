function submitDeleteArticle(articleid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这篇文章吗？");
    $("#articleidhidden").val(articleid);
    $("#confirmModal").modal("show");
}
function confirmDelete(){
	var articleid = $("#articleidhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "branch/restaurant/article/delete",
  	  data: "articleid="+articleid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='branch/restaurant/article/list'",1500);
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


function submitArticle(operation){
	var article = new Object();
	article.title=$("#article_title").val();
	var coverPicStr=$("#upload1single-links").children();
	if(coverPicStr.length!=0){
		article.coverPic=coverPicStr[0].value;
	}else{
		article.coverPic=null;
	}
	article.content=$("#article_content").val();

	var url = "";
	if(operation=="insert"){
		url="branch/restaurant/article/insert";
	}else{
		article.articleid=$("#editarticleid").val();
		url="branch/restaurant/article/update";
	}
 	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(article),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {

	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='branch/restaurant/article/list'",1500);
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























