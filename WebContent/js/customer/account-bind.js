$("#archive_id_confirm").click(function(){
  //username feedback
  $("#account_id_group").addClass("has-error");
  $("#account_id_logo").removeClass("hidden");
  $("#archive_id").attr("placeholder","请输入正确优康网账号");

  //password feedback
  $("#account_pwd_group").addClass("has-error");
  $("#account_pwd_logo").removeClass("hidden");
  $("#account_pwd").attr("placeholder","请输入正确优康网密码");
});
$("#archive_id_cancel").click(function(){
  $("#account_id").val("");
  $("#account_pwd").val("");
});