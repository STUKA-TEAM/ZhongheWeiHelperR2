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
          <li class="active">大转盘中奖情况</li>
        </ol>
        <div class="row website-tab">
            <div class="col-md-10 col-md-offset-1">
            <c:forEach items="${prizeList}" var="item1">
            <div class="well well-sm"><c:choose><c:when test="${item1.prizeIndex == 1}">一</c:when><c:when test="${item1.prizeIndex == 2}">二</c:when><c:when test="${item1.prizeIndex == 3}">三</c:when></c:choose>等奖：${item1.prizeDesc}&nbsp;&nbsp;总数量：${item1.prizeNum}&nbsp;&nbsp;已中奖人数：${item1.luckyNum}</div>
            <table class="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>中奖者联系电话</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${item1.luckyList}" var="item">
                <tr>
                  <td>${item.contact}</td>
                  <td><c:choose><c:when test="${item.status==1}">未领奖</c:when><c:when test="${item.status==0}">已领奖</c:when></c:choose></td>
                  <td><c:choose><c:when test="${item.status==1}"><a class="btn btn-sm btn-danger" onclick="getLucky('${item.resultid}')">领奖</a></c:when><c:when test="${item.status==0}"><button type="button" class="btn btn-default btn-sm" disabled="disabled">已领奖</button></c:when></c:choose></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
            </c:forEach>          
          </div>
        </div>
      </div>
    </div>
    

    <!-- 确认删除弹框 -->
    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 id="confirmModalTitle" class="modal-title text-danger"></h4>
          </div>
          <div class="modal-body">
            <h4 id="confirmModalMes" class="modal-title"></h4>
            <input id="idhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmGetLucky()">确认领奖</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <%@ include file="../CommonViews/footer.jsp"%>
    
    <!-- 通用提示框 -->
    <%@ include file="../CommonViews/commonDialog.jsp"%>
    <!-- 查看链接通用框 -->
    <%@ include file="../CommonViews/viewLink.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/wheelLottery.js"></script>
  </body>
</html>