<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>   
<ol class="breadcrumb">
  <li><a href="store/elove/detail">Elove管理</a></li>
  <li class="active">婚纱剪影</li>
</ol>
<div class="row">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>婚纱剪影</h4>
    </div>
    <div class="panel-body row">
      <form class="form-horizontal" role="form">
        <div class="form-group">
          <label for="elove_footer" class="col-md-3 control-label">Elove底部脚注</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="elove_footer" placeholder="">
          </div>
        </div>
        <div class="form-group">
          <label for="elove_sidebar" class="col-md-3 control-label">Elove侧边栏名称</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="elove_sidebar" placeholder="">
          </div>
        </div>
        <div class="form-group form-btn">
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-2 text-center" onclick="cancel()">取消</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="backStep('backstep5')">上一步</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="nextStep('finish')">完成</button>
        </div>
      </form>
    </div>
  </div>
</div>