<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:forEach items="${notpayList}" var="item">
  <div class="form-group">
    <label for="unpaid_count" class="col-sm-3 control-label">${item.wechatName}</label>
    <div class="col-sm-9">
      <input type="text" class="form-control notPayInput" id="${item.appid}" placeholder="" value="${item.notPayNumber}">
    </div>
  </div>
</c:forEach>