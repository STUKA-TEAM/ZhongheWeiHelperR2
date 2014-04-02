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
      <%@ include file="../CommonViews/leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li class="active"><a href="store/lottery/wheel/list">大转盘管理</a></li>
          <li class="active">编辑大转盘抽奖</li>
        </ol>
        <div class="row website-tab">
          <div class="row website-tab">
            <div class="panel panel-info col-md-10 col-md-offset-1">
              <div class="panel-heading">
                <h4>编辑大转盘抽奖</h4>
              </div>
              <div class="panel-body">
                <div  class="form-horizontal">
                  <div class="form-group">
                    <label for="wheelName" class="col-md-3 control-label">活动标题</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="wheelName" placeholder="" value="${wheel.wheelName}">
                    </div>
                  </div> 
                  <div class="form-group">
                    <label for="maxDayCount" class="col-md-3 control-label">每天可抽次数</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="maxDayCount" placeholder="" value="${wheel.maxDayCount}">
                    </div>
                  </div>                  
                  <div class="form-group">
                    <label for="wheelDesc" class="col-md-3 control-label">活动说明</label>
                    <div class="col-md-7">
                      <textarea type="text" class="form-control" id="wheelDesc" placeholder="" rows="10">${wheel.wheelDesc}</textarea>
                    </div>
                  </div>  
                </div>
                <input id="wheelid" type="hidden" value="${wheel.wheelid}"/>
                <div class="row">
                  <a class="btn btn-info col-md-2 col-md-offset-3 text-center" onclick="submitWheel('update')">编辑活动</a>
                  <a class="btn btn-info col-md-1 col-md-offset-1 text-center" href="store/lottery/wheel/list">取消</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <%@ include file="../CommonViews/footer.jsp"%>
    
    <!-- 通用提示框 -->
    <%@ include file="../CommonViews/commonDialog.jsp"%>

    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/wheelLottery.js"></script>
  </body>
</html>