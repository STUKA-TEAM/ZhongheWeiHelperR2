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
          <li class="active">分店维护</li>
        </ol>
        <div class="row website-tab">
         <div class="row">
          <div class="col-md-2 col-md-offset-1">
            <a href="store/branch/add" class="btn btn-info btn-block">新建分店</a>
          </div>
          <div class="col-md-2">
            <form>
              <select class="form-control account-select" onchange="filterByType(this.options[this.options.selectedIndex].value)">
                <c:forEach items="${classList}" var="item">
                <option value="${item.classid}" <c:if test="${item.selected}">selected</c:if> >${item.className}</option>
                ${item.selected}
				</c:forEach>
              </select>
            </form>
          </div>
        </div>
        
          <div class="row website-tab">
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>创建时间</th>
                  <th>分店名称</th>
                  <th>账户名</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${branchList}" var="item">
                <tr>
                  <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <td>${item.storeName}</td>
                  <td>${item.username}</td>
                  <td><a class="btn btn-sm btn-info" href="store/branch/edit?branchid=${item.branchSid}">编辑</a>
                  <a class="btn btn-sm btn-danger" onclick="submitDelete('${item.branchSid}')">删除</a>
                  <a class="btn btn-sm btn-danger" onclick="changePassword('${item.branchSid}')">更改登录密码</a></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
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
            <input id="itemidhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDelete()">确认删除</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

<!-- 更改密码弹窗 -->
    <div class="modal fade" id="paper_type_edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div id="paper_type_dialog" class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="modal-title" id="myModalLabel">更改分店密码</h4>
		  </div>
		  <div class="modal-body">
		    <div  class="form-horizontal">
		      <div class="form-group">
		        <label for="story_groom" class="col-md-3 control-label">新密码</label>
		        <div class="col-md-7">
		          <input type="text" class="form-control" id="password" placeholder="">
		        </div>
		      </div>
		      <div class="form-group">
		        <label for="story_groom" class="col-md-3 control-label">确认密码</label>
		        <div class="col-md-7">
		          <input type="text" class="form-control" id="confirm" placeholder="">
		        </div>
		      </div>
		    </div>
		  </div>
		  <div class="modal-footer">
		    <input type="hidden" id="branchidhidden"/>
		    <button type="button" class="btn btn-info" onclick="submitChange()" >确定</button>
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
    <script type="text/javascript" src="js/store/branch.js"></script>
  </body>
</html>