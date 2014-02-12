<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
  <ol class="breadcrumb">
    <li><a href="store/elove/detail">Elove管理</a></li>
    <li class="active">相遇相知</li>
  </ol>
  <div class="row">
    <div class="panel panel-info col-md-10 col-md-offset-1">
      <div class="panel-heading">
        <h4>相知相遇（第二步，共六步）</h4>
      </div>
      <div class="panel-body row">
        <div  class="form-horizontal">
          <div class="form-group">
            <label for="story_groom" class="col-md-3 control-label">新郎（照片左）</label>
            <div class="col-md-7">
              <input type="text" class="form-control" id="story_groom" placeholder="" value="${eloveWizard.xinLang}">
            </div>
          </div>
          <div class="form-group">
            <label for="story_bride" class="col-md-3 control-label">新人（照片右）</label>
            <div class="col-md-7">
              <input type="text" class="form-control" id="story_bride" placeholder="" value="${eloveWizard.xinNiang}">
            </div>
          </div>
          <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1">
            <label class="col-sm-3 control-label">相遇相知图片</label>
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
                <c:forEach items="${eloveWizard.storyImagePath}" var="image">
                <div id="${image}" class="col-md-4 pic-preview-div"><img src="${image}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
                <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${image}')"> </span>
                </div>
                </c:forEach>
                </div>
                <div id="upload1-links">
                <c:forEach items="${eloveWizard.storyImagePath}" var="image">
                <input id="${image}-input" type="hidden" value="${image}"/>
                </c:forEach>
                </div>
              </div>
          </div>                       
		<form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single-png">
		  <label for="elove_pic" class="col-md-3 control-label">相遇相知文字图片</label>
		  <div class="col-md-7">
		    <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
		    <input type="text" name=ye class="form-control file-path-elove">
		    <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
		    <input type="button" value="上传" class="image-original-png btn btn-sm btn-info">
		  </div>
		</form>
		<div class="form-group">
		  <div class="col-md-7 col-md-offset-3">
		    <div class="row" id="upload1single-png-images">
		    <c:if test="${eloveWizard.storyTextImagePath!=null}">
		    <div id="${eloveWizard.storyTextImagePath}" class="col-md-6 pic-preview-div"><img src="${eloveWizard.storyTextImagePath}_original.png" class="pic-preview img-thumbnail img-responsive"/>
		      <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${eloveWizard.storyTextImagePath}')"> </span>
		    </div>
		    </c:if>
		    </div>
		    <div id="upload1single-png-links">
		    <c:if test="${eloveWizard.storyTextImagePath!=null}">
		    <input id="${eloveWizard.storyTextImagePath}-input" type="hidden" value="${eloveWizard.storyTextImagePath}"/>
		    </c:if>
		    </div>
		  </div>
		</div>           
          <div class="form-group form-btn">
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-2 text-center" onclick="cancel()">取消</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="backStep('backstep1')">上一步</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="nextStep('step3')">下一步</button>
          </div>
        </div>
      </div>
    </div>
  </div>