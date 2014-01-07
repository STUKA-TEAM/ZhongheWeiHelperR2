<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
      <div class="col-md-2 sidebar">
        <div class="container account-change">
          <p><strong>当前正在管理的微信账号</strong></p>
          <p>公共账号1</p>
          <p><strong>切换至其他公共账号</strong></p>
          <form>
            <select class="form-control account-select">
              <option>1</option>
              <option>2</option>
              <option>3</option>
              <option>4</option>
              <option>5</option>
            </select>
          </form>
        </div>
        <ul class="nav nav-pills nav-stacked">
          <li><a href="store/account">账号管理</a></li>
          <li><a href="store/elove/detail">Elove管理</a></li>
          <li><a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">订餐管理</a>
            <div id="collapseOne" class="panel-collapse collapse">
              <ul class="nav submenu">
                <li><a href="./order-manager.html">订单管理</a></li>
                <li><a href="./menu-manager.html">菜单管理</a></li>
                <li><a href="./dish-manager.html">菜品管理</a></li>
              </ul>
            </div>
          </li>
          <li><a href="./vote.html">投票管理</a></li>
          <li><a href="./lottery.html">抽奖管理</a></li>
        </ul>
      </div>