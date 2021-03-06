<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="./head.jsp"%> 
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
        <ol class="breadcrumb">
          <li class="active">用户管理</li>
        </ol>
        <div class="row website-tab">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <tr>
                <th>创建时间</th>
                <th>商户名称</th>
                <th>付款联系电话</th>
                <th></th>
              </tr>
              <c:forEach items="${infoList}" var="item">
              <tr>
                <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.storeName}</td>
                <td id="${item.sid}_contact">${item.contact}</td>
                <td>
                <a class="btn btn-sm btn-info" href="internal/customer/edit?sid=${item.sid}">管理</a>
                <a class="btn btn-sm btn-info" onclick="editContact('${item.sid}')">编辑联系电话</a>
                <a class="btn btn-sm btn-info" onclick="sendMessageWindow('${item.sid}')">发送短信</a>
                </td>
              </tr>
              </c:forEach>
            </table>
          </div>
        </div>
      </div>

    </div>
    
    <!-- Edit Contact Modal -->
    <div class="modal fade" id="edit_contact" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">编辑联系电话</h4>
          </div>
          <div class="modal-body">
              <div class="form-group">
                <label for="store_contact" class="col-sm-3 control-label">联系电话:</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="store_contact" placeholder="">
                  <input type="hidden" class="form-control" id="store_id" placeholder="">
                </div>
              </div>
          </div>       
          <div class="modal-footer">
              <button type="button" class="btn btn-default" onclick="submitEditContact()">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- Send Message Modal -->
    <div class="modal fade" id="sendMessage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">发送短信</h4>
          </div>
          <div class="modal-body">
          <div class="form-horizontal" role="form">
              <div class="form-group">
                <label for="store_contact" class="col-sm-3 control-label">发送内容:</label>
                <div class="col-sm-9">
                  <textarea rows="4" cols="15"  class="form-control" id="messageContent" ></textarea>
                  <input type="hidden" class="form-control" id="message_sid" placeholder="">
                </div>
              </div>
          </div>
          </div>       
          <div class="modal-footer">
              <button type="button" class="btn btn-default" onclick="sendMessage()">确定</button>
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