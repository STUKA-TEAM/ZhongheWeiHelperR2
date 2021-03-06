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
      <%@ include file="./leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li><a href="branch/restaurant/article/list">文章管理</a></li>
          <li class="active">编辑</li>
        </ol>
        
        <div class="row website-tab">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <h4>编辑</h4>
            </div>
            <div class="panel-body row">
              <div  class="form-horizontal">
                <%@ include file="./articleSubArea.jsp"%>
                <div class="form-group form-btn">
                  <input id="editarticleid" type="hidden" value="${article.articleid}">
                  <a class="btn btn-lg btn-info col-md-3 col-md-offset-2 text-center" href="javascript:void(0);" onclick="submitArticle('update')">完成编辑</a>
                  <a class="btn btn-lg btn-info col-md-3 col-md-offset-1 text-center" href="branch/restaurant/article/list">取消</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        
      </div>
    </div>
    <%@ include file="../CommonViews/commonDialog.jsp"%>
    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/upload.js"></script>
    <script type="text/javascript" src="js/branch/restaurant/article.js"></script>
    <script type="text/javascript" src="kindeditor/kindeditor-min.js"></script>
	<script type="text/javascript" src="kindeditor/lang/zh_CN.js"></script>
    <script>
		KindEditor.ready(function(K) {
			K.create('#article_content', {
				uploadJson : '/resources/upload/image/kindeditor',
				allowFileManager : false,
				afterBlur: function(){this.sync();}
			});
		});
	</script>
  </body>
</html>