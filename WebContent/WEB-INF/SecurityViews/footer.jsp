<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
    <div id="footer">
      <div class="container text-center">
        <p class="text-muted credit">Copyright &copy; ${year} tukacorp.com All Rights Reserved. 上海图卡网络科技有限公司 版权所有  沪ICP备14006765号</p>
      </div>
    </div><!-- footer -->