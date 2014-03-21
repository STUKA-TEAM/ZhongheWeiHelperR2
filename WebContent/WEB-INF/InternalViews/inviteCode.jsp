<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="./head.jsp"%> 
    <!-- Bootstrap core CSS -->
    <link href="./css/store/bootstrap.min.css" rel="stylesheet">
    <link href="./css/store/zhonghe-wechat.css" rel="stylesheet">
    <link href="./css/store/zhonghe-manager.css" rel="stylesheet">
    <link rel="shortcut icon" href="./img/favicon.png">
  </head>
  <body>
    <%@ include file="../InternalViews/navBar.jsp"%>
    <div class="row">
      <%@ include file="../InternalViews/leftSide.jsp"%>  
      
       <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li class="active"> <a href="internal/invite/intial">注册码管理</a></li>
        </ol>
        <div class="row website-tab">
        <form class="form-horizontal" role="form">
          <div class="form-group">
          <button type="button" class="col-md-offset-1 btn btn-info btn-lg" onclick="createCodeWindow()">生成注册密码</button>
          </div>
          <div class="form-group">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>注册码</th>
              </tr>
              <c:forEach items="${codes}" var="item">
              <tr>
                <td>${item}</td>
              </tr>
              </c:forEach>
            </table>
          </div>
          </div>
        </form>
        </div>
      </div>
    </div>   
    <!-- Modal -->
    <div class="modal fade" id="createCode" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">生成注册密码</h4>
          </div>
          <div class="modal-body">
              <div class="form-group">
                <label for="number" class="col-sm-3 control-label">生成数量:</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="number" placeholder="1至10个">
                </div>
              </div>
          </div>       
          <div class="modal-footer">
              <button type="button" class="btn btn-default" onclick="submitCreateCode()">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <%@ include file="../InternalViews/footer.jsp"%>
    <!-- 通用提示框 -->
    <%@ include file="../InternalViews/commonDialog.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../InternalViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/internal/inviteCode.js"></script>
  </body>
</html>