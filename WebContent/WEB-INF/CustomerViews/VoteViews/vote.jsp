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
    <link href="./css/customer/vote.css" rel="stylesheet">
    <link href="./css/customer/vote-result.css" rel="stylesheet">
    <link href="./css/customer/mobile-common.css" rel="stylesheet">
    <link href="./css/customer/wei-website-modulelist.css" rel="stylesheet">
  </head>
  <body>
    <div id="voteBody"class="container">
      <div class="vote-module">
        <h4><strong>${vote.voteName}</strong><br><small><fmt:formatDate value="${vote.createTime}" pattern="yyyy-MM-dd"/></small></h4>
        <img src="${vote.coverPic}_standard.jpg" class="img-responsive">
        <p class="vote-text">${vote.voteDesc}</p>
        <c:if test="${vote.maxSelected==1}">
        <input id="voteType" type="hidden" value="radio"/>
        <div class="vote-item-module">
          <div class="vote-type">
            <p>投票类型(如：单选投票 只可选1项)</p>
          </div>
          <c:forEach items="${vote.itemList}" var="item">
          <div class="vote-item">
            <c:if test="${item.itemPic!=null}"><img src="${item.itemPic}_standard.jpg" class="vote-img img-responsive"></c:if>
            <div class="vote-checkbox radio">
              <label>
                <input type="radio" name="optionsRadios"  value="${item.itemid}"> ${item.itemName}
              </label>
            </div>
          </div>
          </c:forEach>
        </div>
        </c:if>
        <c:if test="${vote.maxSelected>1}">
         <input id="voteType" type="hidden" value="check"/>
         <div class="vote-item-module">
          <div class="vote-type">
            <p>投票类型(如：多选投票 可选${vote.maxSelected}项)</p>
          </div>
          <c:forEach items="${vote.itemList}" var="item">
          <div class="vote-item">
            <c:if test="${item.itemPic!=null}"><img src="${item.itemPic}_standard.jpg" class="vote-img img-responsive"></c:if>            
            <div class="vote-checkbox checkbox">
              <label>
                <input type="checkbox" name="optionsCheckbox" value="${item.itemid}"> ${item.itemName}
              </label>
            </div>
          </div>
          </c:forEach>
        </div>
        </c:if>
        <div class="vote-input">
          <input id="contact" class="form-control" type="text" placeholder="您的联系方式">
          <input id="voteid" type="hidden" value="${vote.voteid}"/>
        </div>
        <div class="vote-summit">
          <button type="button" class="btn btn-info" onclick="submitVote(${vote.maxSelected})">提交</button>
        </div>
      </div>
    </div>
    
    <%@ include file="../CommonViews/weifooter.jsp"%>   
    <%@ include file="../WebsiteViews/bottom.jsp"%> 
    <script type="text/javascript" src="./js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="./js/customer/bootstrap.min.js"></script>   
    <script type="text/javascript" src="js/customer/vote.js"></script> 
    <%@ include file="../CommonViews/shareJS.jsp"%>
    <script type="text/javascript">
    window.shareInfo = new Object();
    shareInfo.imgUrl = '${message.imageLink}';
    shareInfo.link = '${message.appLink}';
    shareInfo.desc = '${message.shareContent}';
    shareInfo.title = '${message.shareTitle}';
    </script>
  </body>
</html>