$(document).ready(function(){
	$(document).on('change','.image-file',function(){
	    var file = this.files[0];
	    var size = file.size;
	    var type = file.type;
	    //Your validation
	    if(size>2100000){
	    	alert("文件大小超过限制！");
	    	this.parentElement.parentElement.reset();
	    }
	    if(type!='image/jpeg' && type!='image/png'){
	    	alert('请选择jpg或png格式图片！');
	    	this.parentElement.parentElement.reset();
	    }
	});
	
	$(document).on('click','.image-original',function(){
	    var form = this.parentElement.parentElement;
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/resources/upload/image/original',  //Server script to process data
	        type: 'POST',
	        //Ajax events
	        beforeSend: function(xhr, settings){
	        	beforeSendHandler(xhr, form);
	        },
	        success: function(data){
	        	var id = form.id;
	        	completeHandler(data ,form);
	        },
	        error: function(xhr, status, exception){
	        	errorHandler(status, exception);
	        },
	        // Form data
	        data: formData,
	        //Options to tell jQuery not to process data or worry about content-type.
	        cache: false,
	        contentType: false,
	        processData: false
	    });
	});

	$(document).on('click','.image-multi',function(){
	    var form = this.parentElement.parentElement;
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/resources/upload/image/multi',  //Server script to process data
	        type: 'POST',
	        //Ajax events
	        beforeSend: function(xhr, settings){
	        	beforeSendHandler(xhr, form);
	        },
	        success: function(data){
	        	var id = form.id;
	        	completeHandler(data, form);
	        },
	        error: function(xhr, status, exception){
	        	errorHandler(status, exception);
	        },
	        // Form data
	        data: formData,
	        //Options to tell jQuery not to process data or worry about content-type.
	        cache: false,
	        contentType: false,
	        processData: false
	    });
	});
	
	function beforeSendHandler(xhr, form){
		var length = $('.image-file', form)[0].files.length;
		if(length == 0){
			alert('请选择图片！');
			xhr.abort();
		}else{
			($(($(form).children())[1])).append("<div id=\""+$(form).attr("id")+"-mes\"class=\"text-success\">正在上传...");			
		}

	}
	
	function completeHandler(data, form){
		var result = $.parseJSON(data);
		if(result.status == true){
			successProcess(result.link,form);
			$("#"+$(form).attr("id")+"-mes").html("上传成功！");
			setTimeout(function(){
				$("#"+$(form).attr("id")+"-mes").remove();
			},1500);
		}
		else{
			alert(result.message);
			$("#"+$(form).attr("id")+"-mes").html("上传失败！");
			setTimeout(function(){
				$("#"+$(form).attr("id")+"-mes").remove();
			},1500);
		}		
	}

	function successProcess(link,form){	
		if(form.id=="uploadAlbumPic"){
			add_album_pic_preview(link);
			add_album_pic_link(link);			
		}
		if(form.id=="upload1single"){
			add_pic_preview_single(form.id, link);
			add_pic_link_single(form.id, link);
		}

	}
	
	function errorHandler(status, exception){
		alert(status + ' ' + exception);
	}
});


var deleteThisImage = function(link){
	$("#"+link.replace(/\//g,"\\/")).remove();
	$("#"+link.replace(/\//g,"\\/")+"-input").remove();
};

var add_album_pic_preview = function(link){
    var appendHtml="<tr id=\""+link+"\">"
				    + "<td><img src=\""+link+"_original.jpg\" class=\"pic-preview img-thumbnail img-responsive\"/></td>"
				    + "<td id=\""+link+"-desc\"></td>"
				    + "<td>"
				    + "<a class=\"btn btn-sm btn-info\" onclick=\"editPicWindow(this)\">编辑</a>"
                    + "<a class=\"btn btn-sm btn-danger col-sm-offset-1\" onclick=\"submitDeletePic(this)\">删除</a>"
                    + "</td>"
                    + "</tr>";
	  $("#uploadImages").append(appendHtml);
};

var add_album_pic_link = function(link){
  var html_cut1= "<input id=\""+link+"-input\" type=\"hidden\" value=\""+link+"\"/>";
  $("#links").append(html_cut1);
};

var add_pic_preview_single = function(id, link){
	  var html_cut1=' <div id='+link +' class="col-md-6 pic-preview-div"><img src="';
	  var html_cut2='" class="pic-preview img-thumbnail img-responsive"/>';
	  var html_cut3="<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage(\'"+link+"\')\"> </span></div>";
	  var pic_preview_html = html_cut1 +link + '_original.jpg' + html_cut2+html_cut3;
	  $("#"+id+"-images").html(pic_preview_html);
};

var add_pic_link_single = function(id, link){
var html_cut1='<input id="' + link + '-input'+ '" type="hidden" value="';
var html_cut2='"/>';
var links_html = html_cut1 + link + html_cut2;
$("#"+id+"-links").html(links_html);
};

function editPicWindow(obj){
	var picUUID = $(obj).parent().parent().attr("id");
	$("#descText").val($("#"+picUUID.replace(/\//g,"\\/")+"-desc").html());
	$("#picUUID").val(picUUID);
	$("#picImgDesc").modal("show");
}
function editPic(){
	var picUUID = $("#picUUID").val();
	$("#"+picUUID.replace(/\//g,"\\/")+"-desc").html($("#descText").val());
	$("#picImgDesc").modal("hide");
}
function submitDeletePic(obj){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这张照片吗？");
    $("#picidhidden").val($(obj).parent().parent().attr("id"));
    $("#confirmModal").modal("show");
}
function confirmDeleteAlbum(){
	var picUUID = $("#picidhidden").val();
    $("#"+picUUID.replace(/\//g,"\\/")).remove();
    $("#"+picUUID.replace(/\//g,"\\/")+"-input").remove();
}

