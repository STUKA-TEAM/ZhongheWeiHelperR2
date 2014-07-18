function change_logo(logo_id, collapse_id){
  if($("#"+logo_id).attr("clicked")=="true"){
    $("#"+logo_id).removeClass("glyphicon-minus-sign");
    $("#"+logo_id).addClass("glyphicon-plus-sign");
    $("#"+logo_id).attr("clicked", "false");
  } else{
    $("#"+logo_id).removeClass("glyphicon-plus-sign");
    $("#"+logo_id).addClass("glyphicon-minus-sign");
    $("#"+logo_id).attr("clicked", "true");
  };
}