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
          <li class="active">文章管理</li>
        </ol>
        <div class="row">
          <div class="col-md-2 col-md-offset-1">
            <a href="store/article/edit/insert" class="btn btn-info btn-block">新建文章</a>
          </div>
          <div class="col-md-2">
            <form>
              <select class="form-control account-select" onchange="filterArticleByType(this.options[this.options.selectedIndex].value)">
                <c:forEach items="${classList}" var="item">
                <option value="${item.classid}" <c:if test="${item.selected}">selected</c:if> >${item.className}</option>
                ${item.selected}
				</c:forEach>
              </select>
            </form>
          </div>
        </div>
        <div class="row website-tab">
          <div class="col-md-offset-1 col-md-9 alert alert-warning">提示：“生成链接”获取的链接可以在左侧栏目“账号管理-关联公众账号-配置自定义菜单”中使用。
          </div>
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>创建时间</th>
                <th>文章标题</th>
                <th>文章消息图片</th>
                <th></th>
              </tr>
              <c:forEach items="${articleList}" var="item">
              <tr>
                <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.title}</td>
                <td>
                <c:if test="${item.coverPic!=null}">
                <img src="${item.coverPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
                </c:if>
                </td>
                <td><a class="btn btn-sm btn-info" href="store/article/edit/update?articleid=${item.articleid}">编辑</a>
                <a class="btn btn-sm btn-info" target="_blank" href="customer/article?articleid=${item.articleid}">预览</a>
                <a class="btn btn-sm btn-info" onclick="viewLink('文章链接','${appPath}customer/article?articleid=${item.articleid}')">生成链接</a>
                <a class="btn btn-sm btn-danger" onclick="submitDeleteArticle('${item.articleid}')">删除</a></td>                              
              </tr>
			  </c:forEach>
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
    <script type="text/javascript" src="js/store/article.js"></script>
    <script type="text/javascript" src="js/store/upload.js"></script>
  </body>
</html>