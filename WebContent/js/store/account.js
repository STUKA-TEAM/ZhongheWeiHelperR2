    function submitBasicInfoEdit(){
      var userInfo = new Object();
      userInfo.storeName=$("#info_name").val();
      userInfo.email=$("#info_email").val();
      userInfo.phone=$("#info_phone").val();
      userInfo.cellPhone=$("#info_mobile").val();
      userInfo.address=$("#info_addr").val();
      var linkInputArray=$("#upload1-links").children();
      var linkArray=new Array();
      $.each(linkInputArray,function(key,val){
    	  linkArray.push($(val).val());
      });
      userInfo.imageList=linkArray;
      userInfo.corpMoreInfoLink=$("#info_link").val();
      userInfo.lng=$("#lng").val();
      userInfo.lat=$("#lat").val();
   	  $.ajax({
	   	  type: "POST",
	   	  url: "store/userinfo/update",
	   	  data: JSON.stringify(userInfo),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   	   	  $("#info_edit").modal("hide");
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){
		   	   	  $("#modalMes").html("编辑成功！");
		   	      $("#operationMesModal").modal("show");
		   	      setTimeout("location.href='store/account'",1500);
	   		  }else{
		   	   	  $("#modalMes").html(jsonData.message);
		   	      $("#operationMesModal").modal("show");
	   		  }
	   		  
	   	  },
		  error: function(xhr, status, exception){
	   	   	  $("#info_edit").modal("hide");
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
   	  });
    }
    
    function submitInsertNewApp(){
      var appInfo=new Object();
      appInfo.wechatName=$("#wechat_name").val();
      appInfo.wechatOriginalId=$("#wechat_id").val();
      appInfo.wechatNumber=$("#wechat_account").val();
      appInfo.address=$("#wechat_addr").val();
      appInfo.industry=$("#wechat_trade").val();
      if(validateNewApp(appInfo)){
          $.ajax({
          	  type: "POST",
          	  url: "store/app/insert",
          	  data: JSON.stringify(appInfo),
          	  contentType: "application/json; charset=utf-8",
           	  success: function (data) {
           		  $("#related").modal("hide");
           		  var jsonData=JSON.parse(data);
           		  if(jsonData.status==true){
           			  $("#modalTitle").html("提示：");
        	   	   	  $("#modalMes").html("创建成功，已经可以关联新的公众账号，请到腾讯公众平台进行API绑定！");
        	   	      $("#operationMesModal").modal("show");
        	   	      setTimeout("location.href='store/account'",2500);
           		  }else{
           			  $("#modalTitle").html("提示：");
        	   	   	  $("#modalMes").html(jsonData.message);
        	   	      $("#operationMesModal").modal("show");
           		  }
           	  },
        	  error: function(xhr, status, exception){
           	   	  $("#related").modal("hide");
           	   	  $("#modalMes").html(status + '</br>' + exception);
           	      $("#operationMesModal").modal("show");
        	  }
          	}); 
      }else{
    	  return;
      }
  	}
    
    function validateNewApp(appInfo){
    	var blankInputArray = new Array();
    	if(appInfo.wechatName=="")blankInputArray.push("微信账号名称");
        if(appInfo.wechatOriginalId=="")blankInputArray.push("微信账号原始ID");
    	if(appInfo.address=="")blankInputArray.push("微信账号地址");
    	if(appInfo.industry=="")blankInputArray.push("所属行业");
    	if(blankInputArray.length==0){
    		return true;
    	}else{
    		showBlankInputHtml(blankInputArray);
    		return false;
    	}
    }
    function showBlankInputHtml(blankInputArray){
    	var blankInputhtml="";
        $.each(blankInputArray,function(key,val){
        	blankInputhtml=blankInputhtml+val+"<br/>";
        });
    	$("#modalTitle").html("您还需要完善下列信息：");
    	$("#modalMes").html(blankInputhtml);
        $("#operationMesModal").modal("show");
        return;
    }
    function submitDeleteApp(appid){
    	$("#confirmModalTitle").html("警告！");
    	$("#confirmModalMes").html("删除这个APP后，和这个微信账号关联的微官网、Elove等信息均会被删除！您确定吗？");
        $("#appidhidden").val(appid);
        $("#confirmModal").modal("show");
    }
    function confirmDelete(){
    	var appid = $("#appidhidden").val();
        $.ajax({
      	  type: "POST",
      	  url: "store/app/delete",
      	  data: "appid="+appid,
       	  success: function (data) {
       		  var jsonData=JSON.parse(data);		 
       		  if(jsonData.status==true){
    	   	   	  $("#modalMes").html(jsonData.message);
    	   	      $("#operationMesModal").modal("show");
    	   	      setTimeout("location.href='store/account'",1500);
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
    function welcomeMessage(appid){
        $.ajax({
        	  type: "GET",
        	  url: "store/welcome/edit?appid="+appid,
         	  success: function (data) {
         		 $("#welcomeMessageModal").html(data);
         		 $("#welcomeMessageModal").modal("show");
         		 $("#appidSign").val(appid);
         	  },
	      	  error: function(xhr, status, exception){
	         	   	  $("#modalMes").html(status + '</br>' + exception);
	         	      $("#operationMesModal").modal("show");
	      	  }
        });  	
    }
    function addMessageItem(){
    	var messageItemHTML = "<div class=\"thumbnail form-group messageItem\">"
                            + "<input type=\"text\" class=\"form-control\" placeholder=\"消息标题\"/>"
                            + "<input type=\"text\" class=\"form-control\" placeholder=\"消息链接\"/>"
                            + "<button type=\"button\" class=\"btn btn-danger\" onclick=\"deleteItem(this)\">删除</button></div>";
    	$("#messageItems").append(messageItemHTML);
    }
    function deleteItem(thisElement){
    	$(thisElement).parent().remove();
    }
    function submitWelcomeMessage(){
    	var welcomeMes = new Object();
    	var contentList = new Array();
    	if($("#textType").attr("class")=="active"){
    		welcomeMes.type="text";
    		welcomeMes.appid=$("#appidSign").val();
    		var welcomeContent=new Object();
    		welcomeContent.content=$("#welcomeText").val();
    		contentList.push(welcomeContent);
    		welcomeMes.contents=contentList;
    	}else{
    		welcomeMes.type="list";
    		welcomeMes.appid=$("#appidSign").val();
    		var messageItems = $("#messageItems").children();
    		var imageItems = $("#upload2-links").children();
	   	    $.each(messageItems,function(key,val){
	   	    	var welcomeTitleInput = ($(val).children())[0];
	   	    	var welcomeLinkInput = ($(val).children())[1];
	   	    	var welcomeImageInput = imageItems[key];
	   	    	var welcomeContent=new Object();
	   	    	welcomeContent.content=$(welcomeTitleInput).val();
	   	    	welcomeContent.link=$(welcomeLinkInput).val();
	   	    	welcomeContent.coverPic=$(welcomeImageInput).val();
	   	    	contentList.push(welcomeContent);
	   	    });
	   	    welcomeMes.contents=contentList;
    	}
        $.ajax({
        	  type: "POST",
        	  url: "store/welcome/update",
        	  data: JSON.stringify(welcomeMes),
    	   	  contentType: "application/json; charset=utf-8",
         	  success: function (data) {
         		  $("#welcomeMessageModal").modal("hide");
         		  var jsonData=JSON.parse(data);		 
         		  if(jsonData.status==true){
      	   	   	  $("#modalMes").html(jsonData.message);
      	   	      $("#operationMesModal").modal("show");
      	   	      setTimeout("location.href='store/account'",1500);
         		  }else{
      	   	   	  $("#modalMes").html(jsonData.message);
      	   	      $("#operationMesModal").modal("show");
         		  }
         	  },
      	  error: function(xhr, status, exception){
      		      $("#welcomeMessageModal").modal("hide");
         	   	  $("#modalMes").html(status + '</br>' + exception);
         	      $("#operationMesModal").modal("show");
      	  }
        });
    }