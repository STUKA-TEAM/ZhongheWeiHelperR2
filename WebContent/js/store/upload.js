$(document).ready(function(){
	$('.image-file').change(function(){
	    var file = this.files[0];
	    var size = file.size;
	    var type = file.type;
	    //Your validation
	    if(size>2100000){
	    	alert("文件大小超过限制！");
	    	this.parentElement.reset();
	    }
	    if(type!='image/jpeg' && type!='image/png'){
	    	alert('请选择jpg或png格式图片！');
	    	this.parentElement.reset();
	    }
	});
	
	$('.image-upload').click(function(){
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
	        	var id = $('.image-file', form)[0].id;
	        	completeHandler(id, data);
	        },
	        error: function(xhr, status, exception){
	        	errorHandler(status);
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
	function errorHandler(status){
		alert(status);
	}
});