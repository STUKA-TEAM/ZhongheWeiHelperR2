$(document).ready(function(){
    var count = $(".nav-item").size();
    $("#scroller").css("width",count*92+2+"px");
    $(".nav-item").each(function(){
        $(this).animate({
            opacity:'0.7',
            width:'90px'
        }, 1000);
    });
});