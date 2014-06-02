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
      <%@ include file="./leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li><a href="branch/restaurant/dish/list?appid='initial'&classid=0">菜品管理</a></li>
          <li class="active">新建菜品</li>
        </ol>
          <div class="row website-tab">
            <div class="panel panel-info col-md-10 col-md-offset-1">
              <div class="panel-heading">
                <h4>新建菜品</h4>
              </div>
              <div class="panel-body">
                <div  class="form-horizontal">
                  <div class="form-group">
                    <label for="dishName" class="col-md-3 control-label">菜品名称</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="dishName" placeholder="">
                    </div>
                  </div>
                  
	              <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single">
	                <label class="col-sm-3 control-label">菜品图片</label>
	                <div class="col-sm-9">
	                  <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
	                  <input type="text" name=ye class="form-control file-path">
	                  <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
	                  <input type="button" value="上传" class="image-squaremulti btn btn-sm btn-info">
	                </div>
	              </form>  
	              <div class="form-group">
	                  <div class="col-md-7 col-md-offset-3">
	                    <div class="row" id="upload1single-images">
	                    </div>
	                    <div id="upload1single-links">
	                    </div>
	                  </div>
	              </div> 
	              
                  <div class="form-group">
                    <label for="price" class="col-md-3 control-label">默认价格</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="price" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dishUnit" class="col-md-3 control-label">价格单位</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="dishUnit" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="recomNum" class="col-md-3 control-label">初始推荐数</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="recomNum" placeholder="">
                    </div>
                  </div>
	                <div class="form-group">
	                  <label for="dishDesc" class="col-md-3 control-label">菜品描述</label>
	                  <div class="col-md-7">
	                    <textarea class="form-control" rows="3" id="dishDesc"></textarea>
	                  </div>
	                </div>

					<div class="form-group">
					  <label for="theme_select" class="col-md-3 control-label">菜品类别</label>
					  <div class="col-md-7">
					    <div class="row">
					    <c:forEach items="${classList}" var="item">
					      <div class="col-md-4">
					        <div class="radio">
					          <label>
					            <input type="radio" name="options" value="${item.classid}" <c:if test="${item.selected}">checked</c:if> >${item.className}
					          </label>
					        </div>
					      </div>
					    </c:forEach>
					    </div>
					  </div>
					 </div>
                </div>
                <div class="row">
                  <input id="appid" type="hidden" value="${appid}">
                  <a class="btn btn-info col-md-2 col-md-offset-3 text-center" onclick="submitItem('insert')">创建菜品</a>
                  <a class="btn btn-info col-md-1 col-md-offset-1 text-center" href="branch/restaurant/dish/list?appid='initial'&classid=0">取消</a>
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
    <script type="text/javascript" src="js/branch/restaurant/menu.js"></script>	
  </body>
</html>