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
            <a href="./paper-manager-create.html" class="btn btn-info btn-block">新建文章</a>
          </div>
          <div class="col-md-2">
            <form>
              <select class="form-control account-select">
                <option>所有类别</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
              </select>
            </form>
          </div>
        </div>
        <div class="row website-tab">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>创建时间</th>
                <th>文章标题</th>
                <th>文章消息图片</th>
                <th></th>
                <th></th>
              </tr>
              <tr>
                <td>2014-1-23 22:59:33</td>
                <td>金牌司仪小李</td>
                <td><img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/></td>
                <td><a class="btn btn-sm btn-info">编辑</a></td>
                <td><a class="btn btn-sm btn-danger">删除</a></td>
              </tr>
              <tr>
                <td>2014-1-23 22:59:33</td>
                <td>全新进口摄影机</td>
                <td>未添加</td>
                <td><a class="btn btn-sm btn-info">编辑</a></td>
                <td><a class="btn btn-sm btn-danger">删除</a></td>
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
              <dd><h4>${notPayNumber} <strong> 套</strong></h4></dd>
              <dt><h4><strong>未付款金额</strong></h4></dt>
              <dd><h4>${debt} <strong> 元</strong></h4></dd>
              <dt><h4><strong>累计消费金额</strong></h4></dt>
              <dd><h4>${sumConsume} <strong> 元</strong></h4></dd>
            </dl>
          </div>
          <div class="modal-footer">
            <p class="pull-left">如有问题，请致电：${phoneNum}</p>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/upload.js"></script>
  </body>
</html>