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
      $(".audio-logo").attr("src", "img/elove/audio_off.png");
      $("#audio-p").text("音效关闭");
      flag="on";
 	}else{
      myAudio.pause();
 	  $(".audio-logo").attr("src", "img/elove/audio_on.png");
 	  $("#audio-p").text("音效开启");
 	  flag="off";
 	}
};
$(document).ready(function(){
  audio_switch();
});