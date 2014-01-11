<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ol class="breadcrumb">
  <li><a href="store/elove/detail">Elove管理</a></li>
  <li class="active">基本信息</li>
</ol>
<div class="row">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>基本信息</h4>
    </div>
    <div class="panel-body row">
      <div class="form-horizontal">
        <div class="form-group">
          <label for="elove_title" class="col-md-3 control-label">Elove标题栏</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="elove_title" placeholder="" value="${eloveWizard.title}">
          </div>
        </div>
        <div class="form-group">
          <label for="elove_pwd" class="col-md-3 control-label">新人索取密码</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="elove_pwd" placeholder="" value="${eloveWizard.password}">
          </div>
        </div>
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
            <c:if test="${eloveWizard.coverPic!=null}">
            <div id="${eloveWizard.coverPic}" class="col-md-6 pic-preview-div"><img src="${eloveWizard.coverPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
              <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${eloveWizard.coverPic}')"> </span>
            </div>
            </c:if>
            </div>
            <div id="upload1single-links">
            <c:if test="${eloveWizard.coverPic!=null}">
            <input id="${eloveWizard.coverPic}-input" type="hidden" value="${eloveWizard.coverPic}"/>
            </c:if>
            </div>
          </div>
      </div>                                
        <div class="form-group">
          <label for="elove_txt" class="col-md-3 control-label">图文消息文字</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="elove_txt" placeholder="" value="${eloveWizard.coverText}">
          </div>
        </div>               
        <div class="form-group">
          <label for="share_title" class="col-md-3 control-label">分享消息标题</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="share_title" placeholder="" value="${eloveWizard.shareTitle}">
          </div>
        </div>
        <div class="form-group">
          <label for="share_content" class="col-md-3 control-label">分享消息内容</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="share_content" placeholder="" value="${eloveWizard.shareContent}">
             </div>
           </div>
<form  class="form-group" role="form" enctype="multipart/form-data" id="upload2single">
             <label for="elove_pic" class="col-md-3 control-label">新人婚纱主图片</label>
             <div class="col-md-7">
               <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
               <input type="text" name=ye class="form-control file-path-elove">
               <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
            <input type="button" value="上传" class="image-square btn btn-sm btn-info">
          </div>
        </form>
        <div class="form-group">
          <div class="col-md-7 col-md-offset-3">
            <div class="row" id="upload2single-images">
            <c:if test="${eloveWizard.majorGroupPhoto!=null}">
            <div id="${eloveWizard.majorGroupPhoto}" class="col-md-6 pic-preview-div"><img src="${eloveWizard.majorGroupPhoto}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
              <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${eloveWizard.majorGroupPhoto}')"> </span>
            </div>
            </c:if>
            </div>
            <div id="upload2single-links">
            <c:if test="${eloveWizard.majorGroupPhoto!=null}">
            <input id="${eloveWizard.majorGroupPhoto}-input" type="hidden" value="${eloveWizard.majorGroupPhoto}"/>
            </c:if>
               </div>
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
                       <input type="radio" name="optionsRadios" id="optionsRadios1" value="${themeInfo.themeid}" <c:if test="${themeInfo.themeid==eloveWizard.themeid}"> checked</c:if>>
                       ${themeInfo.themeName}
                     </label>
                   </div>
                 </div>
               </c:forEach>
               </div>
             </div>
           </div>
         <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1music_sigle">
             <label for="elove_pic" class="col-md-3 control-label">背景音乐</label>
             <div class="col-md-7">
               <input type="file" name="file" class="audio-file hidden" onchange="ye.value=value" accept="audio/mp3">
               <input type="text" name=ye class="form-control file-path-elove" value="${eloveWizard.music}">
            <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
            <input type="button" value="上传" class="audio-upload btn btn-sm btn-info">
          </div>
        </form>
        <div class="form-group">
          <div class="col-md-7 col-md-offset-3">
            <div class="row" id="upload1music_sigle-musics">
            <c:if test="${eloveWizard.music!=null}">
            <div id="${eloveWizard.music}" class="col-md-6 pic-preview-div">
              <div>已上传&nbsp;&nbsp;&nbsp;
              <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${eloveWizard.music}')"> </span>
              </div>
            </div>
            </c:if>
            </div>
            <div id="upload1music_sigle-links">
            <c:if test="${eloveWizard.music!=null}">
            <input id="${eloveWizard.music}-input" type="hidden" value="${eloveWizard.music}"/>
            </c:if>
            </div>
          </div>
        </div>
        <div class="form-group form-btn">
      <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-2" onclick="cancel()">取消</button>
      <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-1" onclick="nextStep('step2')">下一步</button>
        </div>
      </div>
    </div>
  </div>
</div>