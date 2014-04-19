$(document).ready(function(){
    var count = $(".nav-item").size();
    $("#scroller").css("width",count*94+"px");
    $(".nav-item").each(function(){
        $(this).animate({
            opacity:'0.7',
            width:'90px'
        }, 1000);
    });
});