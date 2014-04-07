function submitVote(maxSelected){
	var voteType = $("#voteType").val();
	var voteContent = new Object();
	var voteAnswer = new Array();
	if(voteType=="radio"){
		var radioValue = $('input[name="optionsRadios"]:checked').val();	
		if(typeof(radioValue) == "undefined"){
			alert("还未选择选项,做出您的选择吧");
			return;
		}else{
			voteAnswer.push(radioValue);
		}
	}else{
		$("input[type=checkbox][name='optionsCheckbox']:checked").each(function(){
			voteAnswer.push($(this).val());
		});
		if(voteAnswer.length>maxSelected){
			alert("最多选择"+maxSelected+"项");
			return;
		}
		if(voteAnswer.length==0){
			alert("还未选择选项,做出您的选择吧");
			return;
		}	
	}
	voteContent.voteid = $("#voteid").val();
	voteContent.openid = "";
	voteContent.contact = $("#contact").val();
	voteContent.answer = voteAnswer;
	  $.ajax({
	   	  type: "POST",
	   	  url: "customer/voteresult",
	   	  data: JSON.stringify(voteContent),
	   	  contentType: "application/json; charset=utf-8",
	   	  success: function (data) {
	   		$("#voteBody").html(data); 
	   	  },
		  error: function(xhr, status, exception){
	   	   	  $("#modalMes").html(status + '</br>' + exception);
	   	      $("#operationMesModal").modal("show");
		  }
   	  });
}