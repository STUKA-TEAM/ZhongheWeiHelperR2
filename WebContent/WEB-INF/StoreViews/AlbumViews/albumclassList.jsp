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
          <li class="active">相册集管理</li>
        </ol>
        <div class="row website-tab">
          <div class="album-btn-group clearfix col-md-offset-1">
            <a class="btn btn-info pull-left btn-margin"  onclick="addAlbumclass()">新建相册集</a>
          </div>
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>创建时间</th>
                  <th>相册集名称</th>
                  <th>属于该相册集的相册数目</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${classList}" var="item">
                <tr>
                  <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <td>${item.className}</td>
                  <td>${item.albumCount}</td>
                  <td><a class="btn btn-sm btn-info" onclick="editAlbumclass('${item.classid}')">编辑</a>
                      <a class="btn btn-sm btn-info" target="_blank" href="customer/albumclass?classid=${item.classid}">预览</a>
                      <a class="btn btn-sm btn-info" onclick="viewLink('相册集链接','${appPath}customer/albumclass?classid=${item.classid}')">生成链接</a>
                  <a class="btn btn-sm btn-danger" onclick="submitDeleteAlbumclass('${item.classid}')">删除</a></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    
    
    <div class="modal fade" id="album_class_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog" id="album_class_dialog">

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
            <input id="albumclassidhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDeleteAlbumclass()">确认删除</button>
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