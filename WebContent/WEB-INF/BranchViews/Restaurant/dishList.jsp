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
              <option value="${item.appid}" <c:if test="${item.isCharged}">selected</c:if> >${item.wechatName}</option>
            </c:forEach>
            </select>
          </div>
          <div class="col-md-3 album-btn-group clearfix">
          菜品种类
            <select id="dishclass" class="form-control dishes-type-select" onchange="filterByDishclass(this.options[this.options.selectedIndex].value)">
            <c:forEach items="${classList}" var="item">
              <option value="${item.classid}" <c:if test="${item.selected}">selected</c:if> >${item.className}</option>
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
                  <th>价格</th>
                  <th>是否供应</th>
                  <th>保存更改</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${dishList}" var="item">
                <tr>
                  <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <td>${item.dishName}</td>
                  <td><c:if test="${not empty item.dishPic}"><img src="${item.dishPic}_original.jpg" class="dish-pic-preview"/></c:if><c:if test="${empty item.dishPic}">未添加图片</c:if></td>
                  <td><div class="col-md-9"><input id="${item.dishid}_price" type="text" class="form-control" value="${item.price}"></div><div class="col-md-3">${item.dishUnit}</div></td>
                  <td>
                    <div class="col-md-5">
                    <div class="radio">
                      <label>
                        <input type="radio" value="1" name="${item.dishid}_dish" <c:if test="${item.available==1}">checked</c:if> > 供应
                      </label>
                    </div>
                    </div>
                    <div class="col-md-5">
                    <div class="radio">
                      <label>
                      <input type="radio" value="0" name="${item.dishid}_dish"<c:if test="${item.available==0}">checked</c:if> > 不供应
                      </label>
                    </div> 
                    </div>                 
                  </td>
                  <td>
                  <a class="btn btn-sm btn-user" onclick="submitChange('${item.dishid}')">保存</a>
                  </td>
                </tr>
                </c:forEach>
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