function submitDeleteWheel(wheelid){
	$("#confirmModalTitle").html("警告！");
	$("#confirmModalMes").html("您确定删除这个大转盘抽奖吗？");
    $("#idhidden").val(wheelid);
    $("#confirmModal").modal("show");
}
function confirmDeleteWheel(){
	var wheelid = $("#idhidden").val();
    $.ajax({
  	  type: "POST",
  	  url: "store/lottery/wheel/delete",
  	  data: "wheelid="+wheelid,
   	  success: function (data) {
   		  var jsonData=JSON.parse(data);		 
   		  if(jsonData.status==true){
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/lottery/wheel/list'",1500);
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

function submitWheel(operation){
	var wheel = new Object();
	wheel.wheelName = $("#wheelName").val();
	wheel.wheelDesc = $("#wheelDesc").val();
	wheel.maxDayCount = $("#maxDayCount").val();	
	var url = "";
	if(operation=="insert"){
		var item1 = new Object();
		var item2 = new Object();
		var item3 = new Object();
		var itemList = new Array();
		
		item1.itemDesc = $("#itemDesc1").val();
		item1.itemCount = $("#itemCount1").val();
		item1.itemPercent = $("#itemPercent1").val();
		itemList.push(item1);
		item2.itemDesc = $("#itemDesc2").val();
		item2.itemCount = $("#itemCount2").val();
		item2.itemPercent = $("#itemPercent2").val();
		itemList.push(item2);
		item3.itemDesc = $("#itemDesc3").val();
		item3.itemCount = $("#itemCount3").val();
		item3.itemPercent = $("#itemPercent3").val();
		itemList.push(item3);
		wheel.itemList = itemList;
		if(!validateWheel(wheel)){
			return;
		}
		url="store/lottery/wheel/insert";
	}else{
		if(!validateWheelEdit(wheel)){
			return;
		}
		wheel.wheelid=$("#wheelid").val();
		url="store/lottery/wheel/update";
	}
  $.ajax({
   	  type: "POST",
   	  url: url,
   	  data: JSON.stringify(wheel),
   	  contentType: "application/json; charset=utf-8",
   	  success: function (data) {
   	   	  var jsonData = JSON.parse(data);
   		  if(jsonData.status==true){	   			   
	   	   	  $("#modalMes").html(jsonData.message);
	   	      $("#operationMesModal").modal("show");
	   	      setTimeout("location.href='store/lottery/wheel/list'",1500);
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

function validateWheel(wheel){
	var blankInputArray = new Array();
	if(wheel.wheelName=="")blankInputArray.push("活动标题");
	if(wheel.wheelDesc=="")blankInputArray.push("活动说明");
    if(!isNum(wheel.maxDayCount))blankInputArray.push("每天可抽次数");
    
    if(wheel.itemList[0].itemDesc=="")blankInputArray.push("一等奖内容");
    if(!isNum(wheel.itemList[0].itemCount))blankInputArray.push("一等奖中奖人数");
    if(!isNum(wheel.itemList[0].itemPercent))blankInputArray.push("一等奖中奖概率");
    
    if(wheel.itemList[1].itemDesc=="")blankInputArray.push("二等奖内容");
    if(!isNum(wheel.itemList[1].itemCount))blankInputArray.push("二等奖中奖人数");
    if(!isNum(wheel.itemList[1].itemPercent))blankInputArray.push("二等奖中奖概率");
    
    if((wheel.itemList)[2].itemDesc=="")blankInputArray.push("三等奖内容");
    if(!isNum(wheel.itemList[2].itemCount))blankInputArray.push("三等奖中奖人数");
    if(!isNum(wheel.itemList[2].itemPercent))blankInputArray.push("三等奖中奖概率");
	if(blankInputArray.length==0){
		return true;
	}else{
		showBlankInputHtml(blankInputArray);
		return false;
	}
}

function validateWheelEdit(wheel){
	var blankInputArray = new Array();
	if(wheel.wheelName=="")blankInputArray.push("活动标题");
	if(wheel.wheelDesc=="")blankInputArray.push("活动说明");
    if(!isNum(wheel.maxDayCount))blankInputArray.push("每天可抽次数");
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
	$("#modalTitle").html("为了保证抽奖活动效果，您需要完善或更改下列信息：");
	$("#modalMes").html(blankInputhtml);
    $("#operationMesModal").modal("show");

    return;
}

function isNum(s)
{
    if (s!=null && s!="")
    {
        return !isNaN(s);
    }
    return false;
}
