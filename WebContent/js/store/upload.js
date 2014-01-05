$(document).ready(function(){
	$('.image-file').change(function(){
	    var file = this.files[0];
	    var size = file.size;
	    var type = file.type;
	    //Your validation
	    if(size>2100000){
	    	alert("文件大小超过限制！");
	    	this.parentElement.parentElement.parentElement.reset();
	    }
	    if(type!='image/jpeg' && type!='image/png'){
	    	alert('请选择jpg或png格式图片！');
	    	this.parentElement.parentElement.parentElement.reset();
	    }
	});
	
	/*$('.video-file').change(function(){
	    var file = this.files[0];
	    var type = file.type;
	    //Your validation
	    if(type!='video/mp4' && type!='video/webm'){
	    	alert('请选择mp4或webm格式视频！');
	    	this.parentElement.parentElement.parentElement.reset();
	    }
	});*/
	
	$('.image-multi').click(function(){
	    var form = this.parentElement.parentElement.parentElement;
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
	    var form = this.parentElement;
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
	    var form = this.parentElement;
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
		if(id == 'register'){
			$('input[name="majorImage"]').val(link);
		}
	}
	
	$('.video-upload').click(function(){
	    var form = this.parentElement.parentElement.parentElement;
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
});

var add_pic_preview = function(id, pic_path){
  var pic_preview_html = '<img src="'+pic_path+'" class="pic-preview img-thumbnail img-responsive"/>';
  $("."+id).append(pic_preview_html);
};
