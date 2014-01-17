var sidebar_tip = "true";
var sidebar_up = function(){
  if(sidebar_tip=="true"){
    sidebar_tip = "false";
	$(".sidebar-guide-tip").html("&nbsp;");
  }
  if($("#sidebar_list").attr('state')=='down'){
    $("#sidebar_list").removeClass("hidden");
    $("#sidebar_list").addClass("in");
    $("#sidebar_list").attr('state', 'up');
    $(".sidebar-bg").removeClass("sidebar-bg-miss");
    $(".sidebar-bg").removeClass("hidden");
  }
};
var sidebar_dismiss = function(){
  if(sidebar_tip=="true"){
    sidebar_tip = "false";
	$(".sidebar-guide-tip").html("&nbsp;");
  }
  if($("#sidebar_list").attr('state')=='up'){
    $("#sidebar_list").addClass("hidden");
    $("#sidebar_list").removeClass("in");
    $("#sidebar_list").attr('state', 'down');
    $(".sidebar-bg").addClass("sidebar-bg-miss");
    $(".sidebar-bg").addClass("hidden");
  }
};
flag="off";
myAudio = null;
var audio_switch = function(themeid){
 	if(flag=="off"){
 	  if(myAudio == null){
 		  myAudio = new Audio('./media/elovedemomusic.mp3'); 
 	      myAudio.addEventListener('ended', function() {
 	        this.currentTime = 0;
 	        this.play();
 	      }, false);
 	      myAudio.play();
 	  }else{
 		 myAudio.play();
 	  }
 	  if(typeof(themeid) != 'undefined') {
 		 $(".audio-logo").attr("src", "img/elove/audio_on_"+themeid+".png");
 	 	 $("#audio-p").text("音效开启");
 	  }    
      flag="on";
 	}else{
      myAudio.pause();
      $(".audio-logo").attr("src", "img/elove/audio_off_"+themeid+".png");
      $("#audio-p").text("音效关闭");
 	  flag="off";
 	}
};

function changeHover(thisNode){
	$(".active").removeClass("active");
	$(thisNode).addClass("active");
}
$(document).ready(function(){
	  audio_switch();
	  $("#story").click(function(){
		  changeHover("#story");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/story?eloveid="+$("#eloveid").val());
		  sidebar_dismiss();
		});
	  $("#dress").click(function(){
		  changeHover("#dress");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/dress?eloveid="+$("#eloveid").val());
		  sidebar_dismiss();
		});
	  $("#info").click(function(){
		  changeHover("#info");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/info?eloveid="+$("#eloveid").val());
		  sidebar_dismiss();
		});
	  $("#record").click(function(){
		  changeHover("#record");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/record?eloveid="+$("#eloveid").val());
		  sidebar_dismiss();
		});
});

var submitWish = function(){
	var wishName=$("#wishname").val();
	var wishContent=$("#wishcontent").val();
	var eloveid=$("#eloveid").val();
	var wish=new Object();
	wish.name=wishName;
	wish.content=wishContent;
	wish.eloveid=eloveid;
	if(wish.name!=''&&wish.content!=''){
		  $.ajax({
			  type: "POST",
			  url: "customer/elove/addWish",
			  data: JSON.stringify(wish),
			  contentType: "application/json; charset=utf-8",
			  success: function (data) {
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){
	   			 $("#bless").modal("hide");
		   	   	 $("#modalmes").html(jsonData.message+"<br/><a class=\"modal-btn btn btn-elove\" data-dismiss=\"modal\">关闭</a>");
		   	     $("#modalmesbox").modal("show");
	   		  }else{
	   			 $("#bless").modal("hide");
	   			 $("#modalmes").html(jsonData.message+"<br/><a class=\"modal-btn btn btn-elove\" data-dismiss=\"modal\">关闭</a>");
	   			 $("#modalmesbox").modal("show");
	   		  }
			  }
			});
	  }
};

var submitJoin = function(){
	var joinName=$("#joinName").val();
	var joinContact=$("#joinContact").val();
	var joinNum=$("#joinNum").val();
	var eloveid=$("#eloveid").val();
	var join=new Object();
	join.name=joinName;
	join.phone=joinContact;
	join.number=joinNum;
	join.eloveid=eloveid;
	if(join.name!=''&&join.content!=''&join.number!=''){
		  $.ajax({
			  type: "POST",
			  url: "customer/elove/addJoin",
			  data: JSON.stringify(join),
			  contentType: "application/json; charset=utf-8",
			  success: function (data) {
	   	   	  var jsonData = JSON.parse(data);
	   		  if(jsonData.status==true){
		   	   	  $("#attend").modal("hide");
	   			  $("#modalmes").html(jsonData.message+"<br/><a class=\"modal-btn btn btn-elove\" data-dismiss=\"modal\">关闭</a>");
	   			  $("#modalmesbox").modal("show");
	   		  }else{
	   			 $("#attend").modal("hide");
	   			 $("#modalmes").html(jsonData.message+"<br/><a class=\"modal-btn btn btn-elove\" data-dismiss=\"modal\">关闭</a>");
	   			 $("#modalmesbox").modal("show");
	   		  }
			  }
			});
	  }
};

//story sub page
var switch_guide = function(bg, img){
  $(bg).removeClass("hidden");
  $(img).removeClass("hidden");
  window.scrollTo(0, 0);
};
var close_guide = function(bg, img){
  $(bg).addClass("hidden");
  $(img).addClass("hidden");
};

//info sub page
//创建地址解析器实例  

var map_switch = function(){
  var box=document.getElementById("baidumap");
  if(box.style.visibility == "hidden"){
  var map = new BMap.Map("baidumap");  
  map.clearOverlays();
  map.enableScrollWheelZoom(); 
  var point = new BMap.Point($("#lng").val(),$("#lat").val());
  map.centerAndZoom(point, 16);
  map.addOverlay(new BMap.Marker(point)); 
  map.centerAndZoom(point, 16);  
  map.addOverlay(new BMap.Marker(point)); 
    box.style.visibility = "visible";
  }else{
    box.style.visibility = "hidden";
  }
};
