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
          <li class="active">编辑相册</li>
        </ol>
        <div class="row website-tab">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <h4>编辑相册</h4>
            </div>
            <div class="panel-body">
              <div  class="form-horizontal">
                 <%@ include file="./albumSubArea.jsp"%>
              </div>
              <div class="row">
                <a class="btn btn-info col-md-2 col-md-offset-3 text-center"  onclick="submitAlbum('edit')">完成编辑</a>
                <a class="btn btn-info col-md-1 col-md-offset-1 text-center" href="store/album/list?classid=0">取消</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="modal fade" id="picImgDesc" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">编辑图片描述</h4>
          </div>
          <div class="modal-body">
            <div  class="form-horizontal">
              <div class="form-group">
                <label class="col-md-3 control-label">图片描述</label>
                <div class="col-md-7">
                  <textarea id="descText" class="form-control" rows="3"></textarea>
                </div>
              </div>
              <input type="hidden" id="picUUID" />
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" onclick="editPic()">确定</button>
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
            <input id="picidhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDeleteAlbum()">确认删除</button>
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
    <script type="text/javascript" src="js/store/albumUpload.js"></script>
  </body>
</html>