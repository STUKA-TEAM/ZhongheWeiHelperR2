<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-white navbar-static-top">
        <div class="navbar-header">
          <a class="navbar-brand" href="#"><img src="./img/logo.png" alt="Zhonghe Software" /></a>
        </div>
        
        <div class="navbar-collapse collapse">
          <div class="btn-group navbar-right nav-user">
            <button type="button" class="btn btn-user dropdown-toggle" data-toggle="dropdown">
            <span class="glyphicon glyphicon-user"></span>  ${username}  <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
              <li><a href="store/account">设置</a></li>
              <li class="divider"></li>
              <li><a href="security/logout">退出</a></li>
            </ul>
          </div>
        </div><!--/.nav-collapse -->
    </div><!-- navbar -->