<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
      <div class="col-md-2 sidebar">
        <div class="container account-change">
          <p><strong>当前正在管理的微信账号</strong></p>
          <c:forEach items="${basicAppInfoList}" var="appInfo">
          <c:if test="${appInfo.isCharged==true}">
          <p>${appInfo.wechatName}</p>
          </c:if>
          </c:forEach>
          <p><strong>切换至其他公共账号</strong></p>
          <form>
            <select id="currentAppId" class="form-control account-select">
	          <c:forEach items="${basicAppInfoList}" var="appInfo">
	          <option value="${appInfo.appid}" <c:if test="${appInfo.isCharged==true}"> selected</c:if> >${appInfo.wechatName}</option>
	          </c:forEach>
            </select>
            <a href="javascript:void(0)" class="btn btn-sm btn-user" onclick="changeAppId()">确定</a>
          </form>
        </div>
        <ul class="nav nav-pills nav-stacked">
          <li><a href="store/account"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;账号管理</a></li>
          <c:forEach items="${authPinyinList}" var="auth">
          <c:if test="${auth.key=='elove' && auth.value==true}">
             <li><a href="store/elove/detail"><span class="glyphicon glyphicon-heart-empty"></span>&nbsp;&nbsp;Elove管理</a></li>
          </c:if>
          <c:if test="${auth.key=='website' && auth.value==true}">
             <li><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><span class="glyphicon glyphicon-globe"></span>&nbsp;&nbsp;微官网管理</a>
            <div id="collapseOne" class="panel-collapse collapse">
              <ul class="nav submenu">
                <li><a href="store/article/list?classid=0"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;文章管理</a></li>
                <li><a href="store/articleclass/list"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;文章类别管理</a></li>
                <li><a href="store/album/list?classid=0"><span class="glyphicon glyphicon-picture"></span>&nbsp;&nbsp;相册管理</a></li>
                <li><a href="store/albumclass/list"><span class="glyphicon glyphicon-book"></span>&nbsp;&nbsp;相册集管理</a></li>
                <li><a href="store/vote/list"><span class="glyphicon glyphicon-align-left"></span>&nbsp;&nbsp;投票管理</a></li>
                <li><a href="store/lottery/wheel/list"><span class="glyphicon glyphicon-dashboard"></span>&nbsp;&nbsp;大转盘管理</a></li>
                <li><a href="store/website/home"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;微官网配置</a></li>
              </ul>
            </div></li>
          </c:if>
          <c:if test="${auth.key=='branch' && auth.value==true}">
             <li><a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"><span class="glyphicon glyphicon-th"></span>&nbsp;&nbsp;分店管理</a>
            <div id="collapseTwo" class="panel-collapse collapse">
              <ul class="nav submenu">
                <li><a href="store/branch/list?classid=0"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;分店维护</a></li>
                <li><a href="store/branchclass/list"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;分店区域划分</a></li>
              </ul>
            </div></li>
          </c:if>
          <c:if test="${auth.key=='dish' && auth.value==true}">
             <li><a data-toggle="collapse" data-parent="#accordion" href="#collapse3"><span class="glyphicon glyphicon-cutlery"></span>&nbsp;&nbsp;菜品管理</a>
            <div id="collapse3" class="panel-collapse collapse">
              <ul class="nav submenu">
                <li><a href="store/branch/list?classid=0"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;菜品维护</a></li>
                <li><a href="store/branchclass/list"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;菜品类别维护</a></li>
              </ul>
            </div></li>
          </c:if>
          </c:forEach>
          <!--
          <li><a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">订餐管理</a>
            <div id="collapseOne" class="panel-collapse collapse">
              <ul class="nav submenu">
                <li><a href="./order-manager.html">订单管理</a></li>
                <li><a href="./menu-manager.html">菜单管理</a></li>
                <li><a href="./dish-manager.html">菜品管理</a></li>
              </ul>
            </div>
          </li>

          
          <c:if test="${auth=='weihuodong'}">
          <li><a href="./vote.html">投票管理</a></li>
          </c:if>
          
          <c:if test="${auth=='weihuodong'}">
          <li><a href="./lottery.html">抽奖管理</a></li>
          </c:if>
          
          -->
        </ul>
      </div>