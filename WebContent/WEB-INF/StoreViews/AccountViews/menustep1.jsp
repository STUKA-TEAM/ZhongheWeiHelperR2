<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ol class="breadcrumb">
  <li><a href="store/menu/wizard/initial">自定义菜单配置</a></li>
  <li class="active">获取接口使用凭证</li>
</ol>
<div class="row">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>获取接口使用凭证（第一步，共两步）</h4>
    </div>
    <div class="panel-body row">
      <div class="form-horizontal">
        <div class="form-group col-md-2"></div>
          <div class="form-group col-md-9 alert alert-warning">从微信公众平台后台“开发模式”栏目获取以下信息：
        </div>
        <div class="form-group">
          <label for="appid" class="col-md-3 control-label">AppId</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="appid" placeholder="" value="${appid}">
          </div>
        </div>
        <div class="form-group">
          <label for="appsecret" class="col-md-3 control-label">AppSecret</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="appsecret" placeholder="" value="${appSecret}">
          </div>
        </div>
        <input type="hidden" id="accesstoken" value="${accessToken}">
        
        <div class="form-group form-btn">
      <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-2" onclick="cancel()">取消</button>
      <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-1" onclick="nextStep('step2')">下一步</button>
        </div>
      </div>
    </div>
  </div>
</div>