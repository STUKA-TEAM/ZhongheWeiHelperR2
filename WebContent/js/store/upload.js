$(document).ready(function(){
	$('.image-file').change(function(){
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
	
/*	$('.video-file').change(function(){
	    var file = this.files[0];
	    var type = file.type;
	    //Your validation
	    if(type!='video/mp4' && type!='video/webm'){
	    	alert('请选择mp4或webm格式视频！');
	    	this.parentElement.parentElement.reset();
	    }
	});*/
	
	$('.audio-file').change(function(){
	    var file = this.files[0];
	    var type = file.type;
	    //Your validation
	    if(type!='audio/mp3'){
	    	alert('请选择mp3格式音乐！');
	    	this.parentElement.parentElement.reset();
	    }
	});

	
	$('.image-multi').click(function(){
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
	        	completeHandler(id, data);
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
	
	$('.image-original').click(function(){
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
	        	completeHandler(id, data);
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
	
	$('.image-square').click(function(){
	    var form = this.parentElement.parentElement;
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/resources/upload/image/square',  //Server script to process data
	        type: 'POST',
	        //Ajax events
	        beforeSend: function(xhr, settings){
	        	beforeSendHandler(xhr, form);
	        },
	        success: function(data){
	        	var id = form.id;
	        	completeHandler(id, data);
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
		}
	}
	
	function completeHandler(id, data){
		var result = $.parseJSON(data);
		if(result.status == true){
			successProcess(id, result.link);
		}
		else{
			alert(result.message);
		}
	}
	
	function errorHandler(status, exception){
		alert(status + ' ' + exception);
	}
	
	function successProcess(id, link){
		if(id == 'upload1'){			
			add_pic_preview(id, link);
			add_pic_link(id, link);
		}
		if(id == 'upload1single'){
			add_pic_preview_single(id, link);
			add_pic_link_single(id, link);
		}
		if(id == 'upload2single'){
			add_pic_preview_single(id, link);
			add_pic_link_single(id, link);
		}
		if(id == 'upload3single'){
			add_pic_preview_single(id, link);
			add_pic_link_single(id, link);
		}
	}
	
	$('.video-upload').click(function(){
	    var form = this.parentElement.parentElement;
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/resources/upload/video',  //Server script to process data
	        type: 'POST',
	        //Ajax events
	        beforeSend: function(xhr, settings){
	        	beforeSendHandlerVideo(xhr, form);
	        },
	        success: function(data){
	        	var id = form.id;
	        	completeHandler(id, data);
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
	
	function beforeSendHandlerVideo(xhr, form){
		var length = $('.video-file', form)[0].files.length;
		if(length == 0){
			alert('请选择视频！');
			xhr.abort();
		}
	}
	
	$('.audio-upload').click(function(){
	    var form = this.parentElement.parentElement;
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/resources/upload/audio',  //Server script to process data
	        type: 'POST',
	        //Ajax events
	        beforeSend: function(xhr, settings){
	        	beforeSendHandlerAudio(xhr, form);
	        },
	        success: function(data){
	        	var id = form.id;
	        	completeHandler(id, data);
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
	
	function beforeSendHandlerAudio(xhr, form){
		var length = $('.audio-file', form)[0].files.length;
		if(length == 0){
			alert('请选择音乐！');
			xhr.abort();
		}
	}
}	
);

var deleteThisImage = function(link){
	$("#"+link.replace(/\//g,"\\/")).remove();
	$("#"+link.replace(/\//g,"\\/")+"-input").remove();
};

var add_pic_preview = function(id, link){
  var html_cut1=' <div id='+link +' class="col-md-6 pic-preview-div"><img src="';
  var html_cut2='" class="pic-preview img-thumbnail img-responsive"/>';
  var html_cut3="<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage(\'"+link+"\')\"> </span></div>";
  var pic_preview_html = html_cut1 + getImgPrePath()+link + '_original.jpg' + html_cut2+html_cut3;
  $("#"+id+"-images").append(pic_preview_html);
};

var add_pic_link = function(id, link){
  var html_cut1='<input id="' + link + '-input'+ '" type="hidden" value="';
  var html_cut2='"/>';
  var links_html = html_cut1 + link + html_cut2;
  $("#"+id+"-links").append(links_html);
};

var add_pic_preview_single = function(id, link){
	  var html_cut1=' <div id='+link +' class="col-md-6 pic-preview-div"><img src="';
	  var html_cut2='" class="pic-preview img-thumbnail img-responsive"/>';
	  var html_cut3="<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage(\'"+link+"\')\"> </span></div>";
	  var pic_preview_html = html_cut1 + getImgPrePath()+link + '_original.jpg' + html_cut2+html_cut3;
	  $("#"+id+"-images").html(pic_preview_html);
};

var add_pic_link_single = function(id, link){
  var html_cut1='<input id="' + link + '-input'+ '" type="hidden" value="';
  var html_cut2='"/>';
  var links_html = html_cut1 + link + html_cut2;
  $("#"+id+"-links").html(links_html);
};

var getImgPrePath = function(){
	return "http://localhost";
};
