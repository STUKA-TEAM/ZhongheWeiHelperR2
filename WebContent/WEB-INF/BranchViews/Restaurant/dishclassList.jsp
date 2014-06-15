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
      <%@ include file="./leftSide.jsp"%>  
      
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li class="active">菜品类别管理</li>
        </ol>
        <div class="row website-tab">
          <div class="col-md-2 album-btn-group clearfix col-md-offset-1">
            <button type="button" class="btn btn-info" onclick="addClass()">新建菜品类别</button>
          </div>  
          <div class="col-md-2 album-btn-group clearfix col-md-offset-1">
            <button type="button" class="btn btn-info" onclick="addClass()">显示所有类别</button>
          </div>      
          <div class="col-md-3 album-btn-group clearfix">
          所属的微信公众账号
            <select id="appInfo" class="form-control dishes-type-select" onchange="filterByApp(this.options[this.options.selectedIndex].value)">
            <c:forEach items="${appInfoList}" var="item">
              <option value="${item.appid}" <c:if test="${item.isCharged}">selected</c:if> >${item.wechatName}</option>
            </c:forEach>
            </select>
          </div>
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>创建者</th>
                  <th>创建时间</th>
                  <th>类别名称</th>
                  <th>类别下菜品数量</th>
                  <th>是否显示</th>
                  <th>保存更改</th>
                  <th>其他操作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${classList}" var="item">
                <tr>
                  <td><c:if test="${item.allowed}">本分店</c:if><c:if test="${item.allowed==false}">总店</c:if></td>
                  <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <td>${item.className}</td>
                  <td>${item.dishCount}</td>                 
                  <td>
                    <div class="col-md-5">
                    <div class="radio">
                      <label>
                        <input type="radio" value="1" name="${item.classid}_dishclass" <c:if test="${item.available==1}">checked</c:if> > 显示
                      </label>
                    </div>
                    </div>
                    <div class="col-md-5">
                    <div class="radio">
                      <label>
                      <input type="radio" value="0" name="${item.classid}_dishclass"<c:if test="${item.available==0}">checked</c:if> > 不显示
                      </label>
                    </div> 
                    </div>                 
                  </td>
                  <td>
                  <a class="btn btn-sm btn-user" onclick="submitChange('${item.classid}')">保存</a>
                  </td>
                  <td>
                  <c:if test="${item.allowed}">
                  <a class="btn btn-sm btn-info" onclick="editClass('${item.classid}')">编辑</a>
                  <a class="btn btn-sm btn-danger" onclick="submitDelete('${item.classid}')">删除</a>
                  </c:if>
                  </td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
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
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDelete()">确认删除</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    <!-- 文章类别编辑框 -->
    <div class="modal fade" id="paper_type_edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div id="paper_type_dialog" class="modal-dialog">

      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->  

    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- 通用提示框 -->
    <%@ include file="../CommonViews/commonDialog.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="./js/branch/restaurant/dishclass.js"></script>
  </body>
</html>