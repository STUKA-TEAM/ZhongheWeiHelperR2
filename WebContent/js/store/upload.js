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
	        	completeHandler(id, data, form);
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
	
	$(document).on('click','.image-original',function(){
	    var form = this.parentElement.parentElement;
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/resources/upload/image/copy_png',  //Server script to process data
	        type: 'POST',
	        //Ajax events
	        beforeSend: function(xhr, settings){
	        	beforeSendHandler(xhr, form);
	        },
	        success: function(data){
	        	var id = form.id;
	        	completeHandler(id, data ,form);
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
	
	$(document).on('click','.image-original-png',function(){
	    var form = this.parentElement.parentElement;
	    var formData = new FormData(form);
	    $.ajax({
	        url: '/resources/upload/image/copy_png',  //Server script to process data
	        type: 'POST',
	        //Ajax events
	        beforeSend: function(xhr, settings){
	        	beforeSendHandler(xhr, form);
	        },
	        success: function(data){
	        	var id = form.id;
	        	completeHandler(id, data ,form);
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
	
	$(document).on('click','.image-square',function(){
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
	        	completeHandler(id, data, form);
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
		($(($(form).children())[1])).append("<div id=\""+$(form).attr("id")+"-mes\"class=\"text-success\">正在上传...");
	}
	
    $('.video-file').change(function(){
		    var file = this.files[0];
		    var type = file.type;
		    //Your validation
		    if(type!='video/mp4'){
		    	alert('请选择mp4格式视频！');
		    	this.parentElement.parentElement.reset();
		    }
	});
	
	$(document).on('click','.video-upload',function(){
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
	        	completeHandler(id, data, form);
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
		($(($(form).children())[1])).append("<div id=\""+$(form).attr("id")+"-mes\"class=\"text-success\">正在上传...");
	}
	
	$(document).on('change','.audio-file',function(){
	    var file = this.files[0];
	    var type = file.type;
	    //Your validation
	    if(type!='audio/mp3'){
	    	alert('请选择mp3格式音乐！');
	    	this.parentElement.parentElement.reset();
	    }
	});
	
	$(document).on('click','.audio-upload',function(){
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
	        	completeHandler(id, data, form);
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
		($(($(form).children())[1])).append("<div id=\""+$(form).attr("id")+"-mes\"class=\"text-success\">正在上传...");
	}
	
	function completeHandler(id, data, form){
		var result = $.parseJSON(data);
		if(result.status == true){
			successProcess(id, result.link);
		}
		else{
			alert(result.message);
		}
		$("#"+$(form).attr("id")+"-mes").html("上传成功！");
		setTimeout(function(){
			$("#"+$(form).attr("id")+"-mes").remove();
		},1500);		
	}

	function successProcess(id, link){
		if(id == 'upload1'){			
			add_pic_preview(id, link);
			add_pic_link(id, link);
		}
		if(id == 'upload2'){			
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
		if(id == 'upload1single-png'){
			add_pic_preview_single_png(id, link);
			add_pic_link_single_png(id, link);
		}
		if(id == 'upload1music_sigle'){
			add_music_preview_single(id, link);
			add_music_link_single(id, link);
		}
		if(id == 'upload1video_sigle'){
			add_video_preview_single(id, link);
			add_video_link_single(id, link);
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

var add_pic_preview_single_png = function(id, link){
	  var html_cut1=' <div id='+link +' class="col-md-6 pic-preview-div"><img src="';
	  var html_cut2='" class="pic-preview img-thumbnail img-responsive"/>';
	  var html_cut3="<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage(\'"+link+"\')\"> </span></div>";
	  var pic_preview_html = html_cut1 + getImgPrePath()+link + '_original.png' + html_cut2+html_cut3;
	  $("#"+id+"-images").html(pic_preview_html);
};

var add_pic_link_single_png = function(id, link){
var html_cut1='<input id="' + link + '-input'+ '" type="hidden" value="';
var html_cut2='"/>';
var links_html = html_cut1 + link + html_cut2;
$("#"+id+"-links").html(links_html);
};

var add_music_preview_single = function (id, link){
    var html_cut1=' <div id=' +link +' class="col-md-6 pic-preview-div"><div>';
    var html_cut2="<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage(\'"+link+ "\')\"> </span></div></div>" ;
    var music_preview_html = html_cut1 +"已上传&nbsp;&nbsp;&nbsp;" + html_cut2;
    $( "#" +id+"-musics" ).html(music_preview_html);
};

var add_music_link_single = function(id, link){
var html_cut1= '<input id="' + link + '-input' + '" type="hidden" value="' ;
var html_cut2= '"/>' ;
var links_html = html_cut1 + link + html_cut2;
$( "#"+id+ "-links" ).html(links_html);
};

var add_video_preview_single = function (id, link){
    var html_cut1=' <div id=' +link +' class="col-md-10 pic-preview-div"><div>';
    var html_cut2="<span class=\"glyphicon glyphicon-trash\" onclick=\"deleteThisImage(\'"+link+ "\')\"> </span></div></div>" ;
    var music_preview_html = html_cut1 +"已上传，后台正在为您转码，大约10分钟后您便可看到转码后的视频。&nbsp;&nbsp;&nbsp;" + html_cut2;
    $( "#" +id+"-videos" ).html(music_preview_html);
};

var add_video_link_single = function(id, link){
var html_cut1= '<input id="' + link + '-input' + '" type="hidden" value="' ;
var html_cut2= '"/>' ;
var links_html = html_cut1 + link + html_cut2;
$( "#"+id+ "-links" ).html(links_html);
};

var getImgPrePath = function(){
	return "";
};
