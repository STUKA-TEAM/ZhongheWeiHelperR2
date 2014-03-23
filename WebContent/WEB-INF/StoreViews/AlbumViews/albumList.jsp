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
          <li class="active">相册管理</li>
        </ol>
        <div class="row website-tab">
          <div class="album-btn-group clearfix col-md-offset-1">
            <a class="btn btn-info pull-left btn-margin" href="store/album/add">新建相册</a>
            <form>
              <select class="form-control album-select">
                <option>所有相册</option>
                <option>相册1</option>
                <option>相册2</option>
                <option>相册3</option>
                <option>相册4</option>
              </select>
            </form>
          </div>
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>创建时间</th>
                  <th>相册名称</th>
                  <th>相册封面</th>
                  <th>图片数量</th>
                  <th></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>2014-1-23 22:59:33</td>
                  <td>冷菜</td>
                  <td><img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/></td>
                  <td>5张</td>
                  <td><a class="btn btn-sm btn-info" href="./album-edit.html">编辑</a></td>
                  <td><a class="btn btn-sm btn-danger">删除</a></td>
                </tr>
                <tr>
                  <td>2014-1-23 22:59:33</td>
                  <td>热菜</td>
                  <td>未添加</td>
                  <td>0张</td>
                  <td><a class="btn btn-sm btn-info" href="./album-edit.html">编辑</a></td>
                  <td><a class="btn btn-sm btn-danger">删除</a></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    

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
    <!-- 查看链接通用框 -->
    <%@ include file="../CommonViews/viewLink.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/album.js"></script>
    <script type="text/javascript" src="js/store/upload.js"></script>
  </body>
</html>