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
          <li class="active">微官网配置</li>
        </ol>
        <div class="row">
          <c:if test="${website==null}">
          <div class="col-md-2 col-md-offset-1">
            <a href="store/website/wizard/initial/create" class="btn btn-info btn-block">新建微官网</a>
          </div>
          </c:if>
        </div>
        <div class="row website-tab">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>创建时间</th>
                <th>用户获取码</th>
                <th>微官网名称</th>
                <th></th>
              </tr>
              <c:if test="${website!=null}">
              <tr>
                <td><fmt:formatDate value="${website.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${website.getCode}</td>
                <td>${website.title}</td>
                <td><a class="btn btn-sm btn-info" href="store/website/wizard/initial/edit?websiteid=${website.websiteid}">编辑</a>
                <a class="btn btn-sm btn-user" target="_blank" href="customer/website/home?websiteid=${website.websiteid}">预览</a>
                <a class="btn btn-sm btn-info" onclick="viewLink('微官网链接','${appPath}customer/website/home?websiteid=${website.websiteid}')">生成链接</a>
                <a class="btn btn-sm btn-danger" onclick="submitDeleteWebsite('${website.websiteid}')">删除</a></td>
              </tr>
              </c:if>
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
            <input id="websiteidhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDelete()">确认删除</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- 查看链接通用框 -->
    <%@ include file="../CommonViews/viewLink.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/website.js"></script>
  </body>
</html>