var sidebar_up = function(){
  if($("#sidebar_list").attr('state')=='down'){
    $("#sidebar_list").removeClass("hidden");
    $("#sidebar_list").addClass("in");
    $("#sidebar_list").attr('state', 'up');
    $(".sidebar-bg").removeClass("sidebar-bg-miss");
    $(".sidebar-bg").removeClass("hidden");
  }
};
var sidebar_dismiss = function(){
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
var audio_switch = function(){
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
      
      $(".audio-logo").attr("src", "img/elove/audio_on_2.png");
 	  $("#audio-p").text("音效开启");
      flag="on";
 	}else{
      myAudio.pause();
      $(".audio-logo").attr("src", "img/elove/audio_off_2.png");
      $("#audio-p").text("音效关闭");
 	  flag="off";
 	}
};
function loadStory(){
	//changeHover("#story");
	$("#content-container").load("customer/elove/story");
}
function changeHover(thisNode){
	$(".active").removeClass("active");
	$(thisNode).addClass("active");
}
$(document).ready(function(){
	  audio_switch();
	  $("#story").click(function(){
		  changeHover("#story");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/story");
		  sidebar_dismiss();
		});
	  $("#dress").click(function(){
		  changeHover("#dress");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/dress");
		  sidebar_dismiss();
		});
	  $("#info").click(function(){
		  changeHover("#info");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/info");
		  sidebar_dismiss();
		});
	  $("#record").click(function(){
		  changeHover("#record");
		  window.scrollTo(0, 0);
		  $("#content-container").load("customer/elove/record");
		  sidebar_dismiss();
		});
});

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
var myGeo = new BMap.Geocoder();  
// 将地址解析结果显示在地图上，并调整地图视野
myGeo.getPoint("上海市长宁区天山路318号", function(point){  
 if (point) { 
	 var map = new BMap.Map("baidumap");  
   map.centerAndZoom(point, 16);  
   map.addOverlay(new BMap.Marker(point));  
   map.enableScrollWheelZoom(); 
 }  
}, "上海市"); 
var map_switch = function(){
  var box=document.getElementById("baidumap");
  if(box.style.visibility == "hidden"){
    box.style.visibility = "visible";
  }else{
    box.style.visibility = "hidden";
  }
}
