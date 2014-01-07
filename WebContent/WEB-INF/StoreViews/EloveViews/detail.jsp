<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <link rel="shortcut icon" href="./img/favicon.png">
  </head>
  <body>
  
    <c:forEach items = "${infoList}" var = "eloveInfo" >
    ${eloveInfo.eloveid}
    </c:forEach>
    <%@ include file="../CommonViews/navBar.jsp"%>
    <div class="row">
      <%@ include file="../CommonViews/leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li class="active">Elove管理</li>
        </ol>
        <div class="row">
          <div class="col-md-2 col-md-offset-1">
            <a href="./store/elove/wizard/initial/create" class="btn btn-info btn-block">新建Elove</a>
          </div>
          <div class="col-md-2">
            <a class="btn btn-info btn-block" data-toggle="modal" data-target="#elove_count">消费统计</a>
          </div>
        </div>
        <div class="row elove-tab">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>创建时间</th>
                <th>过期时间</th>
                <th>状态</th>
                <th>密码</th>
                <th>新郎</th>
                <th>新娘</th>
                <th></th>
              </tr>
              <tr>
                <td>2014-1-1 16:39:09</td>
                <td>2014-1-1 16:39:13</td>
                <td><p class="text-success">有效</p></td>
                <td>couple1</td>
                <td>李雷</td>
                <td>韩梅梅</td>
                <td><a class="btn btn-sm btn-user">编辑</a></td>
              </tr>
              <tr>
                <td>2014-1-1 16:39:09</td>
                <td>2014-1-1 16:39:13</td>
                <td><p class="text-danger">已过期</p></td>
                <td>couple0</td>
                <td>李雷</td>
                <td>韩梅梅</td>
                <td></td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
    
    <div class="modal fade" id="elove_count" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">感谢您使用Elove</h4>
          </div>
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt><h4><strong>未付款数量</strong></h4></dt>
              <dd><h4>0 <strong> 套</strong></h4></dd>
              <dt><h4><strong>未付款金额</strong></h4></dt>
              <dd><h4>0 <strong> 元</strong></h4></dd>
              <dt><h4><strong>累计消费金额</strong></h4></dt>
              <dd><h4>0 <strong> 元</strong></h4></dd>
            </dl>
          </div>
          <div class="modal-footer">
            <p class="pull-left">如有问题，请致电：18221069563</p>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="footer">
      <div class="container text-center">
        <p class="text-muted credit">Copyright ? 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
      </div>
    </div><!-- footer -->
    <!-- include jQuery -->
		<script type="text/javascript" src="./js/store/jquery-1.10.2.min.js"></script>
    <script src="./js/store/bootstrap.min.js"></script>
  </body>
</html>