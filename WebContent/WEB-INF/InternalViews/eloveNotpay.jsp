<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:forEach items="${notpayList}" var="item" >
  <div class="form-group inputInfo" id="${item.appid}">
    <div class="col-sm-12">关联的微信账号：${item.wechatName}</div>
    <label for="unpaid_count" class="col-sm-3 control-label">未付款数量</label>
    <div class="col-sm-9">
      <input type="text" class="form-control notPayInput" id="${item.appid}-not" placeholder="" value="${item.notPayNumber}">
    </div>
    <label for="unpaid_count" class="col-sm-3 control-label">预付款数量</label>
    <div class="col-sm-9">
      <input type="text" class="form-control prePayInput" id="${item.appid}-pre" placeholder="" value="${item.prePayNumber}">
    </div>
  </div>
</c:forEach>