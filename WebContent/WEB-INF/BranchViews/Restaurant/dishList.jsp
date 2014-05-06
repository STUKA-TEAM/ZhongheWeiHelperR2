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
          <li class="active">菜品管理</li>
        </ol>
        <div class="row website-tab">
          <div class="col-md-3 album-btn-group clearfix col-md-offset-1">
          所属的微信公众账号
            <select id="appInfo" class="form-control dishes-type-select" onchange="filterByApp(this.options[this.options.selectedIndex].value)">
            <c:forEach items="${appInfoList}" var="item">
              <option value="${item.appid}" >${item.wechatName}</option>
            </c:forEach>
            </select>
          </div>
          <div class="col-md-3 album-btn-group clearfix">
          菜品种类
            <select class="form-control dishes-type-select" onchange="filterByDishclass(this.options[this.options.selectedIndex].value)">
            <c:forEach items="${classList}" var="item">
              <option value="${item.classid}">${item.className}</option>
            </c:forEach>
            </select>
          </div>
          <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>创建时间</th>
                  <th>菜品名称</th>
                  <th>菜品图片</th>
                  <th>价格/例</th>
                  <th>是否供应</th>
                  <th>保存更改</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>2014-1-23 22:59:33</td>
                  <td>松茸菌炖辽蔘</td>
                  <td><img src="/resources/images/bc941ecbb6804de9a70f2f5811b8c21d_original.jpg" class="pic-preview img-thumbnail img-responsive"></td>
                  <td><input type="text" class="form-control" value="88"></td>
                  <td>
                    <div class="col-md-5">
                    <div class="radio">
                      <label>
                        <input type="radio" name="1" value=""> 供应
                      </label>
                    </div>
                    </div>
                    <div class="col-md-5">
                    <div class="radio">
                      <label>
                      <input type="radio" name="1" value=""> 不供应
                      </label>
                    </div> 
                    </div>                 
                  </td>
                  <td>
                  <a href="./store/elove/wizard/initial/edit?eloveid=1" class="btn btn-sm btn-user">保存</a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    

    <%@ include file="../CommonViews/footer.jsp"%>
    <!-- 通用提示框 -->
    <%@ include file="../CommonViews/commonDialog.jsp"%>
    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="./js/branch/restaurant.js"></script>
  </body>
</html>