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
  	  url: "store/article/delete",
  	  data: "articleid="+articleid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/article/list?classid=0'",1500);
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

function filterArticleByType(articleclassid){
	location.href="store/article/list?classid="+articleclassid;
}

function submitArticle(operation){
	var article = new Object();
	var list = new Array();
	article.title=$("#article_title").val();
	var coverPicStr=$("#upload1single-links").children();
	if(coverPicStr.length!=0){
		article.coverPic=coverPicStr[0].value;
	}else{
		article.coverPic=null;
	}
	article.content=$("#article_content").val();

	$("input[type=checkbox][name='options']:checked").each(function(){
		list.push($(this).val());
	});
	article.classidList=list;
	var url = "";
	if(operation=="insert"){
		url="store/article/insert";
	}else{
		url="store/article/update";
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
		   	      setTimeout("location.href='store/article/list?classid=0'",1500);
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
function submitArticleclass(operation){
	var articleclass = new Object();
	var list = new Array();
	articleclass.className = $("#className").val();
	$("input[type=checkbox][name='options']:checked").each(function(){
		list.push($(this).val());
	});
	articleclass.articleidList = list;
	var url = "";
	if(operation=="insert"){
		url="store/articleclass/insert";
	}else{
		url="store/articleclass/update";
	}
	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(articleclass),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		  $("#paper_type_edit").modal("hide");
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){	   			   
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='store/articleclass/list'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   		  
	   	  },
		  error: function(xhr, status, exception){
			  $("#paper_type_edit").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}

function submitDeleteArticleclass(classid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这个文章类别吗？");
    $("#articleclassidhidden").val(classid);
    $("#confirmModal").modal("show");
}
function confirmDelete(){
	var articleclassid = $("#articleclassidhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/articleclass/delete",
  	  data: "classid="+articleclassid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/articleclass/list'",1500);
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























