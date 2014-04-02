function autoScroll(){
  $(".scroll").find(".list").animate({
    marginTop : "-55px",
  },100,function(){
    $(this).css({marginTop : "0px"}).find("li:first").appendTo(this);
  })
};
function scrollStop(){
  clearInterval(task);
  setTimeout(function(){
	  result = $(".scroll li:nth-child(3)").text();
	  $(".scroll li:nth-child(3)").css({color:"#ffffff"});
	  arr = result.split(":");
	  setTimeout("pop_show(arr[0], arr[1])", 1000);
	  state = true;
	},1000);
}
function pop_show(name, content){
  $(".result-pop-bg").fadeIn();
  $(".result-pop").fadeIn();
  $(".bless-name").text(name);
  $(".bless-content").text(content);
}
function pop_hidden(){
  $(".result-pop-bg").fadeOut();
  $(".result-pop").fadeOut();
}
var state = true;
var task;
$().ready(function(){
  $("#start").click(function(){
    if(state){
    	  $(".scroll li").css({color:"#333"});
          state = false;
          task=self.setInterval("autoScroll()",100);
     	  $.ajax({
    	   	  type: "POST",
    	   	  url: "customer/elove/lottery/do",
    	   	  data: "eloveid="+$("#eloveid").val(),
    	   	  success: function (data) {
                 $("#itemList").html(data);
                 setTimeout("scrollStop()", getRandomNum(2500,4000));
    	   	  },
    		  error: function(xhr, status, exception){
    			 setTimeout("scrollStop()", getRandomNum(2500,4000));
    		  }
       	  });              
    }
  });
  $(".result-pop-btn").click(function(){
    pop_hidden();
  });
});
function getRandomNum(Min,Max)
{   
var Range = Max - Min;   
var Rand = Math.random();   
return(Min + Math.round(Rand * Range));   
}   

