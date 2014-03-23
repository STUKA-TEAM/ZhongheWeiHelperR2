function submitDeleteAlbumclass(classid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这个相册集吗？");
    $("#albumclassidhidden").val(classid);
    $("#confirmModal").modal("show");
}
function confirmDeleteAlbumclass(){
	var albumclassid = $("#albumclassidhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/albumclass/delete",
  	  data: "classid="+albumclassid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/albumclass/list'",1500);
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

function addAlbumclass(){
	  $.ajax({
	   	  type: "GET",
	   	  url: "store/albumclass/add",
	   	  success: function (data) {
		   	   	  $("#album_class_dialog").html(data);
		   	      $("#album_class_modal").modal("show");  
	   	  },
		  error: function(xhr, status, exception){
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
 	  });
}
function editAlbumclass(classid){
	  $.ajax({
	   	  type: "GET",
	   	  url: "store/albumclass/edit?classid="+classid,
	   	  success: function (data) {
		   	   	  $("#album_class_dialog").html(data);
		   	      $("#album_class_modal").modal("show");  
	   	  },
		  error: function(xhr, status, exception){
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}

function submitAlbumclass(operation){
	var albumclass = new Object();
	var list = new Array();
	albumclass.className = $("#className").val();
	$("input[type=checkbox][name='options']:checked").each(function(){
		list.push($(this).val());
	});
	albumclass.albumidList = list;
	var url = "";
	if(operation=="insert"){
		url="store/albumclass/insert";
	}else{
		albumclass.classid=$("#editclassid").val();
		url="store/albumclass/update";
	}
	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(albumclass),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		  $("#album_class_modal").modal("hide");
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){	   			   
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='store/albumclass/list'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   		  
	   	  },
		  error: function(xhr, status, exception){
			  $("#album_class_modal").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
	  });
}

function filterAlbumByType(albumclassid){
	location.href="store/album/list?classid="+albumclassid;
}


function submitDeleteAlbum(albumid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这个相册吗？");
    $("#albumidhidden").val(albumid);
    $("#confirmModal").modal("show");
}
function confirmDeleteAlbum(){
	var albumid = $("#albumidhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/album/delete",
  	  data: "albumid="+albumid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/album/list?classid=0'",1500);
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

function submitAlbum(operation){
		var album = new Object();
		album.albumName = $("#album_name").val();
		var coverPicStr=$("#upload1single-links").children();
		if(coverPicStr.length!=0){
			album.coverPic=coverPicStr[0].value;
		}else{
			album.coverPic=null;
		}
		var classidList = new Array();
		$("input[type=checkbox][name='options']:checked").each(function(){
			classidList.push($(this).val());
		});
		album.classidList = classidList;
		var photoInputList = $("#links").children();
		var photoList = new Array();
	    $.each(photoInputList,function(key,val){
	    	var photo = new Object();
	    	photo.imagePath = $(val).val();
	    	photo.imageDesc = $("#"+photo.imagePath.replace(/\//g,"\\/")+"-desc").html();
	    	photoList.push(photo);
	    	
	      });
		album.photoList = photoList;
		
		var url = "";
		if(operation=="insert"){
			url="store/album/insert";
		}else{
			album.albumid=$("#editalbumid").val();
			url="store/album/update";
		}
	  $.ajax({
	   	  type: "POST",
	   	  url: url,
	   	  data: JSON.stringify(album),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){	   			   
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='store/album/list?classid=0'",1500);
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








