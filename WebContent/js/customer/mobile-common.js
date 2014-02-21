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