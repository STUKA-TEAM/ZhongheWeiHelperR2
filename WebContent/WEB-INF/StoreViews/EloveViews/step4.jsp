<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ol class="breadcrumb">
  <li><a href="./elove.html">Elove管理</a></li>
  <li class="active">婚礼信息</li>
</ol>
<div class="row">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>婚礼信息</h4>
    </div>
    <div class="panel-body row">
      <form class="form-horizontal" role="form">
        <div class="form-group">
          <label for="info_date" class="col-md-3 control-label">婚礼时间</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="info_date" placeholder="">
          </div>
        </div>
        <div class="form-group">
          <label for="info_addr" class="col-md-3 control-label">婚礼地点</label>
          <div class="col-md-7">
            <div class="input-group">
              <input type="text" class="form-control" id="info_addr" placeholder="">
              <span class="input-group-btn">
                <button class="btn btn-info" type="button">定位</button>
              </span>
            </div><!-- /input-group -->
          </div>
        </div>
        <div id="baidumap"></div>
        <div class="form-group">
          <label for="info_phone" class="col-md-3 control-label">联系电话</label>
          <div class="col-md-7">
            <input type="text" class="form-control" id="info_phone" placeholder="">
          </div>
        </div>
        <div class="form-group form-btn">
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-2 text-center" onclick="cancel()">取消</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="backStep('backstep3')">上一步</button>
            <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="nextStep('step5')">下一步</button>
        </div>
      </form>
    </div>
  </div>
</div>