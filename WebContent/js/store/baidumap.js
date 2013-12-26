//不能引用，需复制到使用页面
var myGeo = new BMap.Geocoder();  
var map = new BMap.Map("baidumap");
map.centerAndZoom("上海", 12);  
map.enableScrollWheelZoom();
function setPoint(){
//"上海市长宁区天山路318号"
map.clearOverlays();
var address = document.getElementById("address_input").value;
myGeo.getPoint(address, function(point){  
if (point) { 	 
   map.centerAndZoom(point, 16);
   document.getElementById("lng").value = point.lng;
   document.getElementById("lat").value = point.lat;  
   map.addOverlay(new BMap.Marker(point));  
   map.enableScrollWheelZoom(); 
 }  
}, "上海市");
} 
map.addEventListener("click", function(e){
  map.clearOverlays();
  var point=new BMap.Point(e.point.lng, e.point.lat);
  document.getElementById("lng").value = point.lng;
  document.getElementById("lat").value = point.lat;  
  map.centerAndZoom(point, map.getZoom());  
  map.addOverlay(new BMap.Marker(point)); 
});