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
    <link rel="shortcut icon" href="./img/favicon.png">
  </head>
  <body>
    <%@ include file="../CommonViews/navBar.jsp"%>
    <div class="row">
      <%@ include file="../CommonViews/leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li><a href="store/album/list?classid=0">相册管理</a></li>
          <li class="active">新建相册</li>
        </ol>
        <div class="row website-tab">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <h4>新建相册</h4>
            </div>
            <div class="panel-body">
              <div  class="form-horizontal">
                <div class="form-group">
                  <label for="album_name" class="col-md-3 control-label">相册名称</label>
                  <div class="col-md-7">
                    <input type="text" class="form-control" id="album_name" placeholder="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">相册封面</label>
                  <div class="col-md-9">
                    <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
                    <input type="text" name=ye class="form-control file-path-album">
                    <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
                    <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
                  </div>
                </div>
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
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">相册集</label>
                  <div class="col-md-9">
                    <label class="checkbox-inline">
                      <input type="checkbox" id="inlineCheckbox1" value="option1"> 相册1
                    </label>
                    <label class="checkbox-inline">
                      <input type="checkbox" id="inlineCheckbox2" value="option2"> 相册2
                    </label>
                    <label class="checkbox-inline">
                      <input type="checkbox" id="inlineCheckbox3" value="option3"> 相册3
                    </label>
                  </div>
                </div>
                <div class="form-group">
                  <a class="btn btn-info col-md-2 col-md-offset-2 text-center" data-toggle="modal" data-target="#add_photo">添加图片</a>
                </div>
                <div class="form-group">
                  <div class="col-md-8 col-md-offset-2">
                    <table class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th>图片</th>
                          <th>描述</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td><img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/></td>
                          <td>5张</td>
                        </tr>
                        <tr>
                          <td>未添加</td>
                          <td>0张</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <div class="row">
                <a class="btn btn-info col-md-2 col-md-offset-3 text-center" href="./elove-3.html">新建相册</a>
                <a class="btn btn-info col-md-1 col-md-offset-1 text-center" href="./elove-1.html">取消</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    

    <div class="modal fade" id="add_photo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">添加图片</h4>
          </div>
          <div class="modal-body">
            <div  class="form-horizontal">
              <div class="form-group">
                <label class="col-md-3 control-label">相册封面</label>
                <div class="col-md-9">
                  <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
                  <input type="text" name=ye class="form-control file-path-album">
                  <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
                  <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
                </div>
              </div>
              <div class="form-group">
                <div class="col-md-7 col-md-offset-3">
                  <div class="row">
                    <div class="col-md-6">
                      <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                    </div>
                    <div class="col-md-6">
                      <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                    </div>
                    <div class="col-md-6">
                      <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3 control-label">图片描述</label>
                <div class="col-md-7">
                  <textarea class="form-control" rows="3"></textarea>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- 确认删除弹框 -->
    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 id="confirmModalTitle" class="modal-title text-danger"></h4>
          </div>
          <div class="modal-body">
            <h4 id="confirmModalMes" class="modal-title"></h4>
            <input id="articleidhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDelete()">确认删除</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <%@ include file="../CommonViews/footer.jsp"%>
    
    <!-- 通用提示框 -->
    <%@ include file="../CommonViews/commonDialog.jsp"%>

    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/album.js"></script>
    <script type="text/javascript" src="js/store/upload.js"></script>
  </body>
</html>