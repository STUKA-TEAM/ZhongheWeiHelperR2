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
          <li class="active">Elove管理</li>
        </ol>
        <div class="row">
          <div class="col-md-2 col-md-offset-1">
            <a href="./store/elove/wizard/initial/create" class="btn btn-info btn-block">新建Elove</a>
          </div>
          <div class="col-md-2">
            <a class="btn btn-info btn-block" data-toggle="modal" data-target="#elove_count">消费统计</a>
          </div>
        </div>
        <div class="row elove-tab">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>创建时间</th>
                <th>过期时间</th>
                <th>状态</th>
                <th>密码</th>
                <th>新郎</th>
                <th>新娘</th>
                <th></th>
              </tr>
               <c:forEach items = "${infoList}" var = "eloveInfo" >           
               <tr>
                <td><fmt:formatDate value="${eloveInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><fmt:formatDate value="${eloveInfo.expiredTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                <c:if test="${eloveInfo.isVaild==true}">
                <p class="text-success">有效</p>
                </c:if>
                <c:if test="${eloveInfo.isVaild==false}">
                <p class="text-danger">已过期</p>
                </c:if>
                </td>
                <td>${eloveInfo.password}</td>
                <td>${eloveInfo.xinLang}</td>
                <td>${eloveInfo.xinNiang}</td>
                <td>
                <a href="./store/elove/wizard/initial/edit?eloveid=${eloveInfo.eloveid}" class="btn btn-sm btn-user">编辑</a>
                <a class="btn btn-sm btn-info" target="_blank" href="customer/elove/elove?eloveid=${eloveInfo.eloveid}">预览</a>
                <a target="_blank" href="./customer/elove/lottery/prepare?eloveid=${eloveInfo.eloveid}" class="btn btn-sm btn-user">进入抽奖</a>
                </td>
               </tr>
               </c:forEach>
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
              <dt><h4><strong>预付款剩余数量</strong></h4></dt>
              <dd><h4>${prePayNumber} <strong> 套</strong></h4></dd>
              <dt><h4><strong>累计消费金额</strong></h4></dt>
              <dd><h4>${sumConsume} <strong> 元</strong></h4></dd>
            </dl>
          </div>
          <div class="modal-footer">
            <p class="pull-left">如有问题，请联系您的客户经理：${phoneNum}</p>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
  </body>
</html>