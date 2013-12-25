var sidebar = function(){
  if($("#sidebar_list").attr('state')=='down'){
    $("#sidebar_list").removeClass("hidden");
    $("#sidebar_list").addClass("in");
    $("#sidebar_list").attr('state', 'up');
  }else if($("#sidebar_list").attr('state')=='up'){
    $("#sidebar_list").addClass("hidden");
    $("#sidebar_list").removeClass("in");
    $("#sidebar_list").attr('state', 'down');
  }
};
flag="off";
var audio_switch = function(){
 	if(flag=="off"){
 		myAudio = new Audio('./media/elovedemomusic.mp3'); 
    myAudio.addEventListener('ended', function() {
      this.currentTime = 0;
      this.play();
    }, false);
    myAudio.play();
    flag="on";
 	}else{
 		myAudio.pause();
 		flag="off";
 	}
}
// 创建地址解析器实例 
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
var switch_guide = function(){
	$("#guide_bg").removeClass("hidden");
  $("#guide_img").removeClass("hidden");
	window.scrollTo(0, 0);
}
var close_guide = function(){
	$("#guide_bg").addClass("hidden");
  $("#guide_img").addClass("hidden");
}