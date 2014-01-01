<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <link href="css/store/bootstrap.min.css" rel="stylesheet">
    <link href="css/store/zhonghe-wechat.css" rel="stylesheet">
    <link rel="shortcut icon" href="img/favicon.png">
    <!-- include jQuery + carouFredSel plugin -->
        <script type="text/javascript" src="js/store/jquery-1.10.2.min.js"></script>
  </head>
  <body>    
    <div class="container">
      <div class="row">
        <div class="well col-sm-5 col-sm-offset-3 row">
          <form class="col-sm-12 form-horizontal" method="POST" name="login" action="j_spring_security_check">
            <div class="form-group">
              <label for="username" class="col-sm-3 control-label">用户名</label>
              <div class="col-sm-9">
                <input type="text" class="form-control" name='j_username' placeholder="用户名"/>
              </div>
            </div>
            <div class="form-group">
              <label for="password" class="col-sm-3 control-label">密码</label>
              <div class="col-sm-9">
                <input type="password" class="form-control" name='j_password' placeholder="******"/>
              </div>
            </div>
            <div class="form-group">
                <button type="submit" class="col-sm-offset-2 col-sm-9 btn btn-lg btn-success">登录</button>
            </div>
            <a class="login-link" href="#">忘记密码?</a>
          </form>
        </div>
      </div><!-- /.row -->
    </div> <!-- /.container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/store/bootstrap.min.js"></script>
  </body>
</html>