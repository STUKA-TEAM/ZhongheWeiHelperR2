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
      
      $(".audio-logo").attr("src", "img/elove/audio_on.png");
 	  $("#audio-p").text("音效开启");
      flag="on";
 	}else{
      myAudio.pause();
      $(".audio-logo").attr("src", "img/elove/audio_off.png");
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
		  $("#content-container").load("customer/elove/story");
		});
	  $("#dress").click(function(){
		  changeHover("#dress");
		  $("#content-container").load("customer/elove/dress");
		});
	  $("#info").click(function(){
		  changeHover("#info");
		  $("#content-container").load("customer/elove/info");
		});
	  $("#record").click(function(){
		  changeHover("#record");
		  $("#content-container").load("customer/elove/record");
		});
});