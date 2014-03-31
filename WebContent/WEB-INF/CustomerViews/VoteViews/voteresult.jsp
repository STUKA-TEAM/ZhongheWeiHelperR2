<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <div class="container">
      <div class="vote-module">
        <h4><strong>${vote.voteName}</strong><br><small><fmt:formatDate value="${vote.createTime}" pattern="yyyy-MM-dd"/></small></h4>
        <img src="${vote.coverPic}_standard.jpg" class="img-responsive">
        <p class="vote-text">${vote.voteDesc}</p>
        <div class="vote-item-module">
          <c:forEach items="${vote.itemList}" var="item">
          <div class="vote-item">
            <c:if test="${item.itemPic!=null}"><img src="${item.itemPic}_standard.jpg" class="vote-img img-responsive"></c:if>
            <div class="vote-item-title">
              <p>${item.itemName}</p>
            </div>
            <div class="vote-progress clearfix">
              <div class="vote-progress-bar">
                <div class="progress">
                  <div class="progress-bar progress-bar-info" role="progressbar" 
                  aria-valuenow="<fmt:formatNumber value='${item.percent}' pattern='##.##' minFractionDigits='2' ></fmt:formatNumber>" 
                  aria-valuemin="0" aria-valuemax="100" style="width: <fmt:formatNumber value='${item.percent}' pattern='##.##' minFractionDigits='2' ></fmt:formatNumber>%">
                  </div>
                </div>
              </div>
              <div class="vote-progress-count">
                <p>${item.count} (<fmt:formatNumber value='${item.percent}' pattern='##.##' minFractionDigits='2' ></fmt:formatNumber>%)</p>
              </div>
            </div>
          </div>
          </c:forEach>
        </div>
      </div>
    </div>
    