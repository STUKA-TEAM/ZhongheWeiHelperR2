function submitForm(){
  	$.ajax({
  	    url: 'j_spring_security_check',
  	    type: 'POST',
  	    data: $('#loginForm').serialize(),
  	    beforeSend: function(xhr, settings){
        	$("#loginMes").html("正在登陆...");
        },
        success: function(data){
        	var jsonData = JSON.parse(data);
  			if(jsonData.status==true){
				location.href=jsonData.message;
	     	}else{
	     		  $("#loginMes").html(jsonData.message);
	        }	  	
        },
        error: function(xhr, status, exception){
        	errorHandler(status, exception);
        }
  	});
}

function submitLogin(){
  	$.ajax({
  	    url: 'j_spring_security_check',
  	    type: 'POST',
  	    data: "j_username="+$("#username").val()+"&j_password="+$("#password").val(),
  	    beforeSend: function(xhr, settings){
        	$("#loginMes").html("正在登陆...");
        },
        success: function(data){
    			var jsonData = JSON.parse(data);
    			if(jsonData.status==true){
    			location.href=jsonData.message;
         	}else{
         		  $("#loginMes").html(jsonData.message);
            }		
        },
        error: function(xhr, status, exception){
        	errorHandler(status, exception);
        }
  	});
}