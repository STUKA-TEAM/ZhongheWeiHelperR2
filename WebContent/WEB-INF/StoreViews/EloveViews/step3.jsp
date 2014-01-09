<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
  <ol class="breadcrumb">
    <li><a href="store/elove/detail">Elove管理</a></li>
    <li class="active">婚纱剪影</li>
  </ol>
  <div class="row">
    <div class="panel panel-info col-md-10 col-md-offset-1">
      <div class="panel-heading">
        <h4>婚纱剪影</h4>
      </div>
      <div class="panel-body row">
        <div  class="form-horizontal">
          <form class="form-group" role="form" enctype="multipart/form-data" id="mutiSizePic">
            <label for="dress_pic" class="col-md-3 control-label">上传图片</label>
            <div class="col-md-7">
              <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
              <input type="text" name=ye class="form-control file-path-elove">
              <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
              <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
            </div>
          </form>
          <div class="form-group">
            <div class="col-md-7 col-md-offset-3">
              <div class="row">
                <div class="col-md-4">
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
                <div class="col-md-4">
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
                <div class="col-md-4">
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
                <div class="col-md-4">
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
                <div class="col-md-4">
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
                <div class="col-md-4">
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
              </div>
            </div>
          </div>
          <form class="form-group" role="form" enctype="multipart/form-data" id="mutiSizePic">
            <label for="dress_video" class="col-md-3 control-label">上传视频</label>
            <div class="col-md-7">
              <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
              <input type="text" name=ye class="form-control file-path-elove">
              <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
              <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
            </div>
          </form>
          <div class="form-group form-btn">
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-2 text-center" onclick="cancel()">取消</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="backStep('backstep2')">上一步</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="nextStep('step4')">下一步</button>
          </div>
        </div>
      </div>
    </div>
  </div>