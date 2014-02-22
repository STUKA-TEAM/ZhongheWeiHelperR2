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
    <%@ include file="../InternalViews/navBar.jsp"%>
    <div class="row">
      <%@ include file="../InternalViews/leftSide.jsp"%>  
      
       <div class="col-md-10 manager-content">
       <input type="hidden" id="store_id" value="${sid}"/>
        <ol class="breadcrumb">
          <li><a href="internal/customer/detail">用户管理</a></li>
          <li class="active">小伦婚庆</li>
        </ol>
        <div class="row website-tab">
          <div class="col-md-10 col-md-offset-1">
            <div class="panel panel-info">
              <!-- Default panel contents -->
              <div class="panel-heading">商户名称：小伦婚庆</div>
              <div class="panel-body">
                <h4>微官网</h4>
                <!-- Table -->
                <table class="table">
                  <tr>
                    <th>权限过期时间</th>
                    <th>价格/年</th>
                    <th></th>
                  </tr>
                  <tr>
                    <td id="website_expired"><fmt:formatDate value="${websiteExpired}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td id="website_price">${websitePrice}</td>
                    <td><a class="btn btn-sm btn-info" href="javascript:void(0)" onclick="editAuthInfo('website')">编辑</a></td>
                  </tr>
                </table>
              </div>
              <div class="panel-body">
                <h4>Elove</h4>
                <!-- Table -->
                <table class="table">
                  <tr>
                    <th>权限过期时间</th>
                    <th>价格/套</th>
                    <th>已使用数量</th>
                    <th>已消费金额</th>
                    <th>未付款数量</th>
                    <th>未付款金额</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                  </tr>
                  <tr>
                    <td id="elove_expired"><fmt:formatDate value="${eloveExpired}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td id="elove_price">${elovePrice}</td>
                    <td>${consumedSum}</td>
                    <td>${consumedMoney}</td>
                    <td>${notPaySum}</td>
                    <td>${notPayMoney}</td>
                    <td><a class="btn btn-sm btn-info" href="javascript:void(0)" onclick="editAuthInfo('elove')">编辑</a></td>
                    <td><a class="btn btn-sm btn-info" href="javascript:void(0)" onclick="editNotPay('${sid}')">未付款编辑</a></td>
                    <td><a class="btn btn-sm btn-success" href="javascript:void(0)">催缴款</a></td>
                    <td><a class="btn btn-sm btn-danger" href="javascript:void(0)">确认收款</a></td>
                  </tr>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Modal -->
    <div class="modal fade" id="editInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="infoName"></h4>
            <input id="infoType" type="hidden"/>
          </div>
          <div class="modal-body">
            <div class="form-horizontal" role="form">
              <div class="form-group">
                <label for="expired_date" class="col-sm-2 control-label">过期时间</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="expired_date" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <label for="price" class="col-sm-2 control-label">单价</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="price" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <div class="col-sm-offset-10 col-sm-2">
                  <button type="submit" class="btn btn-default" onclick="submitEditAuthInfo()">确定</button>
                </div>
              </div>
            </div>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
    <!-- Modal -->
    <div class="modal fade" id="editNotPay" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Elove未付款编辑</h4>
          </div>
          <div class="modal-body">
            <div  class="form-horizontal" id="eloveNotPayContent">
            </div>
          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-default" onclick="submitEditNotPay()">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <%@ include file="../InternalViews/footer.jsp"%>
    <!-- 通用提示框 -->
    <%@ include file="../InternalViews/commonDialog.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../InternalViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/internal/useradmin.js"></script>
  </body>
</html>