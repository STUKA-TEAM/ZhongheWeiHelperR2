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

var flag = true;
    $(".nav-bar").click(function(){
      if(flag){
        $(".nav-item-home").animate({
          bottom:'112px',
          left:'10px'
        }, 400);
        $(".nav-item-home").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(".nav-item-phone").animate({
          bottom:'10px',
          left:'112px'
        }, 400);
        $(".nav-item-phone").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(".nav-item-gps").animate({
          bottom:'90px',
          left:'53px'
        }, 400);
        $(".nav-item-gps").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(".nav-item-share").animate({
          bottom:'52px',
          left:'91px'
        }, 400);
        $(".nav-item-share").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(this).rotate({
          duration: 400,
          angle: 0,
          animateTo: 225
        });
        flag = false;
      }else{
        $(".nav-item-home").animate({
          bottom:'10px',
          left:'10px'
        }, 400);
        $(".nav-item-home").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(".nav-item-phone").animate({
          bottom:'10px',
          left:'10px'
        }, 400);
        $(".nav-item-phone").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(".nav-item-gps").animate({
          bottom:'10px',
          left:'10px'
        }, 400);
        $(".nav-item-gps").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(".nav-item-share").animate({
          bottom:'10px',
          left:'10px'
        }, 400);
        $(".nav-item-share").rotate({
          duration: 400,
          angle: 0,
          animateTo: 360
        });
        $(this).rotate({
          duration: 400,
          angle: 0,
          animateTo: 180
        });
        flag = true;
      };
    });