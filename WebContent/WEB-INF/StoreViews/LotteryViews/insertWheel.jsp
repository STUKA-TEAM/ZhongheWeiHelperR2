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
          <li class="active">新建大转盘抽奖</li>
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
                      <input type="text" class="form-control" id="wheelName" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="lottery_title" class="col-md-3 control-label">奖项设置</label>
                    <div class="col-md-7">
                      <div class="row lottery-content">
                        <div class="col-md-6">
                          <input id="itemDesc1" type="text" class="form-control" placeholder="一等奖内容">
                        </div>
                        <div class="col-md-4">
                          <input id="itemCount1" type="text" class="form-control" placeholder="一等奖中奖人数">
                        </div>
                        <div class="col-md-2">
                          <input id="itemPercent1" type="text" class="form-control" placeholder="中奖概率" value="0.0005">
                        </div>                        
                      </div>
                      <div class="row lottery-content">
                        <div class="col-md-6">
                          <input id="itemDesc2" type="text" class="form-control" placeholder="二等奖内容">
                        </div>
                        <div class="col-md-4">
                          <input id="itemCount2" type="text" class="form-control" placeholder="二等奖中奖人数">
                        </div>
                        <div class="col-md-2">
                          <input id="itemPercent2" type="text" class="form-control" placeholder="中奖概率" value="0.005">
                        </div>                        
                      </div>
                      <div class="row lottery-content">
                        <div class="col-md-6">
                          <input id="itemDesc3" type="text" class="form-control" placeholder="三等奖内容">
                        </div>
                        <div class="col-md-4">
                          <input id="itemCount3" type="text" class="form-control" placeholder="三等奖中奖人数">
                        </div>
                        <div class="col-md-2">
                          <input id="itemPercent3" type="text" class="form-control" placeholder="中奖概率" value="0.05">
                        </div>                        
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="maxDayCount" class="col-md-3 control-label">每天可抽次数</label>
                    <div class="col-md-7">
                      <input type="text" class="form-control" id="maxDayCount" placeholder="" value="3">
                    </div>
                  </div>         
                  <div class="form-group">
                    <label for="wheelDesc" class="col-md-3 control-label">活动说明</label>
                    <div class="col-md-7">
                      <textarea type="text" class="form-control" id="wheelDesc" placeholder="为保证效果，字数为90字以内" rows="10" >本活动每人每天可以抽3次，分享后可以获得额外次数。中奖后请填写手机号，到店出示手机号即可领奖。
                      </textarea>
                    </div>
                  </div>  
                </div>
                <div class="row">
                  <a class="btn btn-info col-md-2 col-md-offset-3 text-center" onclick="submitWheel('insert')">创建活动</a>
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