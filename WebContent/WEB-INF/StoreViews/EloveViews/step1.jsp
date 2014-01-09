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
              <input type="text" class="form-control" id="elove_title" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="elove_pwd" class="col-md-3 control-label">新人索取密码</label>
            <div class="col-md-7">
              <input type="text" class="form-control" id="elove_pwd" placeholder="">
            </div>
          </div>
          <form  class="form-group" role="form" enctype="multipart/form-data" id="mutiSizePic">
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
              <div class="row">
                <div class="col-md-4">
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label for="elove_txt" class="col-md-3 control-label">图文消息文字</label>
            <div class="col-md-7">
              <input type="text" class="form-control" id="elove_txt" placeholder="">
            </div>
          </div>               
          <div class="form-group">
            <label for="share_title" class="col-md-3 control-label">分享消息标题</label>
            <div class="col-md-7">
              <input type="text" class="form-control" id="share_title" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="share_content" class="col-md-3 control-label">分享消息内容</label>
            <div class="col-md-7">
              <input type="text" class="form-control" id="share_content" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="dress_pic" class="col-md-3 control-label">新人婚纱主图片</label>
            <div class="col-md-7">
              <input type="text" class="form-control" id="dress_pic" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="theme_select" class="col-md-3 control-label">主题风格选择</label>
            <div class="col-md-7">
              <div class="row">
                <div class="col-md-4">
                  <div class="radio">
                    <label>
                      <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                      简约主题
                    </label>
                  </div>
                  <img src="./img/manager/theme1.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
                <div class="col-md-4">
                  <div class="radio">
                    <label>
                      <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                      温馨主题
                    </label>
                  </div>
                  <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                </div>
              </div>
            </div>
          </div>
          <form  class="form-group" role="form" enctype="multipart/form-data" id="mutiSizePic">
            <label for="music_bg" class="col-md-3 control-label">背景音乐</label>
            <div class="col-md-7">
              <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
              <input type="text" name=ye class="form-control file-path-elove">
              <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
              <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
            </div>
          </form>
          <div class="form-group form-btn">
            <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-2" onclick="cancel()">取消</button>
            <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-1" onclick="nextStep('step2')">下一步</button>
          </div>
        </div>
      </div>
    </div>
  </div>