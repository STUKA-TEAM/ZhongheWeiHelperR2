<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>众合微信公共账号管理平台</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/store/bootstrap.min.css" rel="stylesheet">
    <link href="./css/store/zhonghe-wechat.css" rel="stylesheet">
    <link href="./css/store/zhonghe-manager.css" rel="stylesheet">
    <link rel="stylesheet" href="./kindeditor/themes/default/default.css" />
    <link rel="shortcut icon" href="./img/favicon.png">
  </head>
  <body>
    <%@ include file="../CommonViews/navBar.jsp"%>
    <div class="row">
      <%@ include file="../CommonViews/leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li><a href="store/article/list?classid=0">文章管理</a></li>
          <li class="active">新建文章</li>
        </ol>
        
        <div class="row website-tab">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <h4>新建文章</h4>
            </div>
            <div class="panel-body row">
              <div  class="form-horizontal">
                <%@ include file="./articleSubArea.jsp"%>
                <div class="form-group form-btn">
                  <a class="btn btn-lg btn-info col-md-3 col-md-offset-2 text-center" href="javascript:void(0);" onclick="submitArticle('insert')">新建文章</a>
                  <a class="btn btn-lg btn-info col-md-3 col-md-offset-1 text-center" href="store/article/list?classid=0">取消</a>
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
    <script type="text/javascript" src="js/store/article.js"></script>
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