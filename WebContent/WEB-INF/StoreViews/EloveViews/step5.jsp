<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ol class="breadcrumb">
  <li><a href="store/elove/detail">Elove管理</a></li>
  <li class="active">婚礼记录</li>
</ol>
<div class="row">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>婚礼记录</h4>
    </div>
    <div class="panel-body row">
      <div  class="form-horizontal">
          <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1">
            <label class="col-sm-3 control-label">上传婚礼照片</label>
            <div class="col-md-7">
              <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
              <input type="text" name=ye class="form-control file-path-elove">
              <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
              <input type="button" value="上传" class="image-original btn btn-sm btn-info">
            </div>
          </form>  
          <div class="form-group">
              <div class="col-md-7 col-md-offset-3">
                <div class="row" id="upload1-images">
                <c:forEach items="${eloveWizard.recordImagePath}" var="image">
                <div id="${image}" class="col-md-4 pic-preview-div"><img src="${image}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
                <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${image}')"> </span>
                </div>
                </c:forEach>
                </div>
                <div id="upload1-links">
                <c:forEach items="${eloveWizard.recordImagePath}" var="image">
                <input id="${image}-input" type="hidden" value="${image}"/>
                </c:forEach>
                </div>
              </div>
          </div> 
         <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1video_sigle">
             <label for="elove_pic" class="col-md-3 control-label">上传婚礼视频</label>
             <div class="col-md-7">
               <input type="file" name="file" class="video-file hidden" onchange="ye.value=value" accept="video/*">
               <input type="text" name=ye class="form-control file-path-elove" value="${eloveWizard.recordVideoPath}">
            <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
            <input type="button" value="上传" class="video-upload btn btn-sm btn-info">
          </div>
        </form>
        <div class="form-group">
          <div class="col-md-7 col-md-offset-3">
            <div class="row" id="upload1video_sigle-videos">
            <c:forEach items="${eloveWizard.recordVideoPath}" var="video">
            <div id="${video}" class="col-md-10 pic-preview-div">
              <div>已上传,刚刚上传的视频可能正在后台转码中&nbsp;&nbsp;&nbsp;
              <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${video}')"> </span>
              </div>
            </div>
            </c:forEach>
            </div>
            <div id="upload1video_sigle-links">
            <c:forEach items="${eloveWizard.recordVideoPath}" var="video">
            <input id="${video}-input" type="hidden" value="${video}"/>
            </c:forEach>
            </div>
          </div>
        </div>
        <div class="form-group form-btn">
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-2 text-center" onclick="cancel()">取消</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="backStep('backstep4')">上一步</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="nextStep('step6')">下一步</button>
        </div>
      </div>
    </div>
  </div>
</div>
