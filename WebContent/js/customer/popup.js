function switchAlert(){
var backgroud=document.getElementById("popupBack");
	backgroud.style.display ="block";
	backgroud.style.visibility = "visible";
var box=document.getElementById("popupBox");
	box.style.display ="block";
	box.style.visibility = "visible";
}
function closeAlert(){
var backgroud=document.getElementById("popupBack");
	backgroud.style.display ="none";
	backgroud.style.visibility = "hidden";
var box=document.getElementById("popupBox");
	box.style.display ="none";
	box.style.visibility = "hidden";
}