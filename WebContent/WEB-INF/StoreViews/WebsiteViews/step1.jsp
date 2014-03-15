<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ol class="breadcrumb">
  <li><a href="store/website/home">微官网配置</a></li>
  <li class="active">新建微官网</li>
</ol>

<div class="row website-tab">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>基本信息</h4>
    </div>
    <div class="panel-body row">
      <div  class="form-horizontal">
        <div class="form-group">
          <label for="story_groom" class="col-md-3 control-label">页面标题</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="title" placeholder="" value="${websiteWizard.title}">
          </div>
        </div>
        <div class="form-group">
          <label for="story_bride" class="col-md-3 control-label">获取码</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="getCode" placeholder="" value="${websiteWizard.getCode}">
          </div>
        </div>
        <div class="form-group">
          <label for="story_bride" class="col-md-3 control-label">联系电话</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="phone" placeholder="" value="${websiteWizard.phone}">
          </div>
        </div>
        <div class="form-group">
          <label for="info_addr" class="col-md-3 control-label">地点</label>
          <div class="col-md-7">
            <div class="input-group">
              <input type="text" class="form-control" id="info_addr" placeholder="" value="${websiteWizard.address}">
              <input type="hidden" id="lng" value="${websiteWizard.lng}"/>
              <input type="hidden" id="lat" value="${websiteWizard.lat}"/>
              <span class="input-group-btn">
                <button class="btn btn-info" type="button" onclick="setPoint()">定位</button>
              </span>
            </div><!-- /input-group -->
          </div>
        </div>
        <div id="baidumap"></div>
        <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single">
          <label for="elove_pic" class="col-md-3 control-label">图文消息图片</label>
          <div class="col-md-7">
            <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
            <input type="text" name=ye class="form-control file-path-elove">
            <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
            <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
          </div>
        </form>
      <div class="form-group">
          <div class="col-md-7 col-md-offset-3">
            <div class="row" id="upload1single-images">
            <c:if test="${websiteWizard.coverPic!=null}">
            <div id="${websiteWizard.coverPic}" class="col-md-6 pic-preview-div"><img src="${websiteWizard.coverPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
              <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${websiteWizard.coverPic}')"> </span>
            </div>
            </c:if>
            </div>
            <div id="upload1single-links">
            <c:if test="${websiteWizard.coverPic!=null}">
            <input id="${websiteWizard.coverPic}-input" type="hidden" value="${websiteWizard.coverPic}"/>
            </c:if>
            </div>
          </div>
      </div>   
        <div class="form-group">
          <label for="story_bride" class="col-md-3 control-label">图文消息文字</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="coverText" placeholder="" value="${websiteWizard.coverText}">
          </div>
        </div>
        <div class="form-group">
          <label for="story_bride" class="col-md-3 control-label">分享消息标题</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="shareTitle" placeholder="" value="${websiteWizard.shareTitle}">
          </div>
        </div>
        <div class="form-group">
          <label for="story_bride" class="col-md-3 control-label">分享消息文字</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="shareContent" placeholder="" value="${websiteWizard.shareContent}">
          </div>
        </div>
        <div class="form-group">
          <label for="story_bride" class="col-md-3 control-label">页面脚注</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="footerText" placeholder="" value="${websiteWizard.footerText}">
          </div>
        </div>
        <div class="form-group">
          <label for="theme_select" class="col-md-3 control-label">主题风格</label>
          <div class="col-md-7">
            <div class="row">
            <c:forEach items="${themeInfoList}" var="themeInfo">
              <div class="col-md-4">
                <div class="radio">
                  <label>
                      <input type="radio" name="optionsRadios" value="${themeInfo.themeid}" <c:if test="${themeInfo.themeid==websiteWizard.themeId}"> checked</c:if>>
                       ${themeInfo.themeName}
                  </label>
                </div>
              </div>
            </c:forEach>
            </div>
          </div>
         </div>
        <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1">
          <label class="col-sm-3 control-label">首页图片</label>
          <div class="col-sm-9">
            <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
            <input type="text" name=ye class="form-control file-path" placeholder="自由配图结构可选多张作为轮播图，简约结构选择一张作为背景图（450px*780px）">
            <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
            <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
          </div>
        </form>  
        <div class="form-group">
            <div class="col-md-7 col-md-offset-3">
              <div class="row" id="upload1-images">
              <c:forEach items="${websiteWizard.imageList}" var="image">
              <div id="${image}" class="col-md-6 pic-preview-div"><img src="${image}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
              <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${image}')"> </span>
              </div>
              </c:forEach>
              </div>
              <div id="upload1-links">
              <c:forEach items="${websiteWizard.imageList}" var="image">
              <input id="${image}-input" type="hidden" value="${image}"/>
              </c:forEach>
              </div>
            </div>
        </div>  
        <div class="form-group form-btn">
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-2 text-center" onclick="cancel()">取消</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="nextStep('step2')">下一步</button>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
var myGeo = new BMap.Geocoder();  
var map = new BMap.Map("baidumap"); 
if('${websiteWizard.lng}'!=''){
	map.clearOverlays();
	var point = new BMap.Point('${websiteWizard.lng}','${websiteWizard.lat}');
    map.centerAndZoom(new BMap.Point('${websiteWizard.lng}','${websiteWizard.lat}'), 16);
	map.addOverlay(new BMap.Marker(point));  
}else{
	myGeo.getPoint("上海", function(point){  
		if (point) { 	 
		   document.getElementById("lng").value = point.lng;
		   document.getElementById("lat").value = point.lat;  
		 }  
		}, "上海市"); 
	map.centerAndZoom("上海", 12);
}
map.enableScrollWheelZoom();

map.addEventListener("click", function(e){
    map.clearOverlays();
    var point=new BMap.Point(e.point.lng, e.point.lat);
    document.getElementById("lng").value = point.lng;
    document.getElementById("lat").value = point.lat;  
    map.centerAndZoom(point, map.getZoom());  
    map.addOverlay(new BMap.Marker(point)); 
  });
function setPoint(){
map.clearOverlays();
var address = document.getElementById("info_addr").value;
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
</script>