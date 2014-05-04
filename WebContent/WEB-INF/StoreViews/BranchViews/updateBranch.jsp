<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="../CommonViews/head.jsp"%>
    <!-- Bootstrap core CSS -->
    <link href="./css/store/bootstrap.min.css" rel="stylesheet">
    <link href="./css/store/zhonghe-wechat.css" rel="stylesheet">
    <link href="./css/store/zhonghe-manager.css" rel="stylesheet">
    <link rel="stylesheet" href="./kindeditor/themes/default/default.css" />
    <link rel="shortcut icon" href="./img/favicon.png">
  </head>
  <body>
    <%@ include file="../CommonViews/navBar.jsp"%>
    <div class="row">
      <%@ include file="../CommonViews/leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li><a href="store/branch/list?classid=0">分店管理</a></li>
          <li class="active">编辑分店</li>
        </ol>
          <div class="row website-tab">
            <div class="panel panel-info col-md-10 col-md-offset-1">
              <div class="panel-heading">
                <h4>编辑分店</h4>
              </div>
              <div class="panel-body">
                <div  class="form-horizontal">
                  <div class="form-group">
                    <label for="branch_name" class="col-md-3 control-label">分店名称</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="storeName" placeholder="" value="${branch.storeName}">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="branch_type" class="col-md-3 control-label">行业类别</label>
                    <div class="col-md-7">
                      <select id="roleid" class="form-control">
                        <c:forEach items="${roleList}" var="item">
                        <option value="${item.roleid}" <c:if test="${item.roleid==branch.roleid}"> selected</c:if> >${item.roleLabel}</option>
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="branch_username" class="col-md-3 control-label">登录名</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="username" placeholder=""  value="${branch.username}">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="branch_phone" class="col-md-3 control-label">分店电话</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="phone" placeholder=""  value="${branch.phone}">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="info_addr" class="col-md-3 control-label">分店地址</label>
                    <div class="col-md-7">
                      <div class="input-group">
                        <input type="text" class="form-control" id="address" placeholder=""  value="${branch.address}">
	                    <input type="hidden" id="lng" value="${branch.lng}">
	                    <input type="hidden" id="lat" value="${branch.lat}">                        
                        <span class="input-group-btn">
                          <button class="btn btn-info" type="button" onclick="setPoint()">定位</button>
                        </span>
                      </div><!-- /input-group -->
                    </div>
                  </div>
                  <div id="baidumap"></div>

	              <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1">
	                <label class="col-sm-3 control-label">介绍图片</label>
	                <div class="col-sm-9">
	                  <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
	                  <input type="text" name=ye class="form-control file-path">
	                  <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
	                  <input type="button" value="上传" class="image-original btn btn-sm btn-info">
	                </div>
	              </form>  
	              <div class="form-group">
	                  <div class="col-md-7 col-md-offset-3">
	                    <div class="row" id="upload1-images">
	                    <c:forEach items="${branch.imageList}" var="image">
	                    <div id="${image}" class="col-md-6 pic-preview-div"><img src="${image}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
	                    <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${image}')"> </span>
	                    </div>
	                    </c:forEach>
	                    </div>
	                    <div id="upload1-links">
	                    <c:forEach items="${branch.imageList}" var="image">
	                    <input id="${image}-input" type="hidden" value="${image}"/>
	                    </c:forEach>
	                    </div>
	                  </div>
	              </div> 
					<div class="form-group">
					  <label for="theme_select" class="col-md-3 control-label">所属区域</label>
					  <div class="col-md-7">
					    <div class="row">
					    <c:forEach items="${classList}" var="item">
					      <div class="col-md-4">
					        <div class="radio">
					          <label>
					            <input type="checkbox" name="options" value="${item.classid}" <c:if test="${item.selected}">checked</c:if> >${item.className}
					          </label>
					        </div>
					      </div>
					    </c:forEach>
					    </div>
					  </div>
					 </div>
                </div>
                <div class="row">
                  <input type="hidden" id="branchSid" value="${branch.branchSid}" />
                  <a class="btn btn-info col-md-2 col-md-offset-3 text-center" onclick="submitItem('update')">完成编辑</a>
                  <a class="btn btn-info col-md-1 col-md-offset-1 text-center" href="store/branch/list?classid=0">取消</a>
                </div>
              </div>
            </div>
          </div>      
      </div>
    </div>
    <%@ include file="../CommonViews/commonDialog.jsp"%>
    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/upload.js"></script>
    <script type="text/javascript" src="js/store/branch.js"></script>	
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
    <script type="text/javascript">
    var myGeo = new BMap.Geocoder();  
    var map = new BMap.Map("baidumap"); 
    if('${branch.lng}'!=''){
    	map.clearOverlays();
    	var point = new BMap.Point('${branch.lng}','${branch.lat}');
        map.centerAndZoom(new BMap.Point('${branch.lng}','${branch.lat}'), 16);
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
    var address = document.getElementById("address").value;
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
  </body>
</html>