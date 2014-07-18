$("#archive_id_confirm").click(function(){
  $(".has-feedback").addClass("has-error");
  $(".glyphicon-remove").removeClass("hidden");
  $("#archive_id").attr("placeholder","请输入正确体检档案号");
});
$("#archive_id_cancel").click(function(){
  $("#archive_id").val("");
});