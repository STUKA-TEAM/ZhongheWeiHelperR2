<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="form-group">
  <label for="article_title" class="col-md-3 control-label">文章标题</label>
  <div class="col-md-7">
    <input type="text" class="form-control" id="article_title" placeholder="" value="${article.title}">
  </div>
</div>

<form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single">
  <label for="story_pic" class="col-md-3 control-label">文章消息图片</label>
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
            <c:if test="${article.coverPic!=null}">
            <div id="${article.coverPic}" class="col-md-6 pic-preview-div"><img src="${article.coverPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
              <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${article.coverPic}')"> </span>
            </div>
            </c:if>
      </div>
      <div id="upload1single-links">
            <c:if test="${article.coverPic!=null}">
            <input id="${article.coverPic}-input" type="hidden" value="${article.coverPic}"/>
            </c:if>
      </div>
    </div>
</div> 
 
<div class="form-group">
  <label for="story_bride" class="col-md-3 control-label">文章内容</label>
  <div class="col-md-7">
    <textarea id="article_content" class="form-control" name="content1" cols="100" rows="8" style="width:700px;height:200px;">${article.content}</textarea>
  </div>
</div>
