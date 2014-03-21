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
          <li class="active">类别管理</li>
        </ol>
        <div class="row">
          <div class="col-md-2 col-md-offset-1">
            <a class="btn btn-info btn-block" href="javascript:void(0);" onclick="addArticleclass()">新建文章类别</a>
          </div>
        </div>
        <div class="row website-tab">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>创建时间</th>
                <th>类别名称</th>
                <th>属于该类的文章数</th>
                <th></th>
              </tr>
              <c:forEach items="${classList}" var="item">
              <tr>
                <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.className}</td>
                <td>${item.articleCount}</td>
                <td><a class="btn btn-sm btn-info" href="javascript:void(0);" onclick="editArticleclass('${item.classid}')">编辑</a>
                <a class="btn btn-sm btn-info" target="_blank" href="customer/articleclass?websiteid=${viewLinkInfo.websiteid}&classid=${item.classid}">预览</a>
                <a class="btn btn-sm btn-info" onclick="viewLink('文章列表链接','${viewLinkInfo.appPath}customer/articleclass?websiteid=${viewLinkInfo.websiteid}&classid=${item.classid}',${viewLinkInfo.websiteid})">生成链接</a>
                <a class="btn btn-sm btn-danger" onclick="submitDeleteArticleclass('${item.classid}')">删除</a></td>
              </tr>
              </c:forEach>
            </table>
          </div>
      </div>
    </div>
    </div>
    

    <%@ include file="../CommonViews/commonDialog.jsp"%>
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
            <input id="articleclassidhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDeleteclass()">确认删除</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    <!-- 文章类别编辑框 -->
    <div class="modal fade" id="paper_type_edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div id="paper_type_dialog" class="modal-dialog">

      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->    
    
    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- 通用提示框 -->
    <%@ include file="../CommonViews/commonDialog.jsp"%>
    <!-- 查看链接通用框 -->
    <%@ include file="../CommonViews/viewLink.jsp"%>    
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/article.js"></script>
    <script type="text/javascript" src="js/store/upload.js"></script>
  </body>
</html>