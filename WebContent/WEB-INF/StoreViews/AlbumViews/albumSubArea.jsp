<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
     <div class="form-group">
       <label for="album_name" class="col-md-3 control-label">相册名称</label>
       <div class="col-md-7">
         <input type="text" class="form-control" id="album_name" placeholder="" value="${album.albumName}">
       </div>
     </div>
     <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single">
       <label for="elove_pic" class="col-md-3 control-label">相册封面</label>
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
         <c:if test="${album.coverPic!=null}">
         <div id="${album.coverPic}" class="col-md-6 pic-preview-div"><img src="${album.coverPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
           <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${album.coverPic}')"> </span>
         </div>
         </c:if>
         </div>
         <div id="upload1single-links">
         <c:if test="${album.coverPic!=null}">
         <input id="${album.coverPic}-input" type="hidden" value="${album.coverPic}"/>
         </c:if>
         </div>
       </div>
   </div>  
            <div class="form-group">
              <label class="col-md-3 control-label">相册集</label>
              <div class="col-md-9">
                <label class="checkbox-inline">
                  <c:forEach items="${classList}" var="item">
                  <input type="checkbox" name="options" value="${item.classid}" <c:if test="${item.selected}">checked</c:if> >${item.className}
                  </c:forEach>
                </label>
              </div>
            </div>
            <div class="form-group" >
              <div class="col-md-2"></div>
				<form  class="form-group" role="form" enctype="multipart/form-data" id="uploadAlbumPic">
				  <div class="col-md-7">
				    <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
				    <input type="button" value="添加图片" onclick="file.click()" class="btn btn-md btn-info">
				    <input type="button" value="上传" class="image-original btn btn-md btn-info">
				    <input type="text" name=ye class="form-control file-path-elove">
				  </div>
				</form>
			     <div id="links">
			           <c:forEach items="${album.photoList}" var="item">
			           <input id="${item}-input" type="hidden" value="1"/>
			           </c:forEach>
			     </div>                 
            </div>
            <div class="form-group">
              <div class="col-md-8 col-md-offset-2">
                <table class="table table-striped table-bordered">
                  <thead>
                    <tr>
                      <th class="col-md-4">图片</th>
                      <th class="col-md-5">描述</th>
                      <th class="col-md-3">操作</th>
                    </tr>
                  </thead>
                  <tbody id="uploadImages">
                    <c:forEach items="${album.photoList}" var="item">
                    <tr id="${item}">
                      <td><img src="${item}_original.jpg" class="pic-preview img-thumbnail img-responsive"/></td>
                      <td id="${item}-desc"></td>
                      <td>
                      <a class="btn btn-sm btn-info" onclick="editPicWindow(this)">编辑</a>
                      <a class="btn btn-sm btn-danger col-sm-offset-1" onclick="submitDeletePic(this)">删除</a>
                      </td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
            <input id="editalbumid" type="hidden" value="${album.albumid}"/>