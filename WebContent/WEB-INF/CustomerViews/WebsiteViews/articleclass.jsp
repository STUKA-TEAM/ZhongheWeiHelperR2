<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>${website.title}</title>
    
    <link href="./css/customer/bootstrap.min.css" rel="stylesheet">
    <link href="./css/customer/wei-article-list.css" rel="stylesheet">
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/wei-website-modulelist.css" rel="stylesheet">
  </head>
  <body>
  
    <div class="container">
      <c:forEach items="${articleList}" var="item">
      <div class="article-model">
        <a class="noneStyleLink" href="customer/article?articleid=${item.articleid}">
        <div class="article-model-header">
          <h4>${item.title}</h4>
          <small><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></small>
        </div>
        <div class="article-model-content">
          <div  class="article-image">
            <img src="${item.coverPic}_standard.jpg" class="img-responsive" alt="article">
          </div>
        </div>       
        <div class="article-model-footer">
          <h5>查看全文</h5>
        </div>
        </a>
      </div>
      </c:forEach>
    </div>
    <%@ include file="bottom.jsp"%>
    <script type="text/javascript" src="./js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="./js/customer/bootstrap.min.js"></script>
  </body>
</html>