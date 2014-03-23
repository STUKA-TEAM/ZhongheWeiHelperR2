function autoScroll(){
  $(".scroll li:nth-child(7)").css({color:"#333"});
  $(".scroll").find(".list").animate({
    marginTop : "-55px",
  },100,function(){
    $(this).css({marginTop : "0px"}).find("li:first").appendTo(this);
  })
};
function scrollStop(){
  clearInterval(task);
  result = $(".scroll li:nth-child(5)").text();
  $(".scroll li:nth-child(5)").css({color:"#ffffff"});
  arr = result.split(":");
  setTimeout("pop_show(arr[0], arr[1])", 1000);
  state = true;
}
function pop_show(name, content){
  $(".result-pop-bg").removeClass("hidden");
  $(".result-pop-bg").addClass("show")
  $(".result-pop").removeClass("hidden");
  $(".result-pop").addClass("show")
  $(".bless-name").text(name);
  $(".bless-content").text(content);
}
function pop_hidden(){
  $(".result-pop-bg").removeClass("show");
  $(".result-pop-bg").addClass("hidden");
  $(".result-pop").removeClass("show");
  $(".result-pop").addClass("hidden");
}
var state = true;
var task;
$().ready(function(){
  $("#start").click(function(){
    if(state){
      state = false;
      task=self.setInterval("autoScroll()",100);
      setTimeout("scrollStop()", 3000);
    }
  });
  $(".result-pop-btn").click(function(){
    pop_hidden();
  });
});
