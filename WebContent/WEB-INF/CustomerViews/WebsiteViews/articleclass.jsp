<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
  </head>
  <body>
    <div class="container">
      <div class="article-model">
        <div class="article-model-header">
          <h4>美好的一天</h4>
          <small>2014-2-19</small>
        </div>
        <div class="article-model-content">
          <div  class="article-image">
            <img src="./img/website/wei_web_case.jpg" class="img-responsive" alt="article">
          </div>
        </div>
        <div class="article-model-footer">
          <a href="wei-article.html"><h5>查看全文</h5></a>
        </div>
      </div>
      <div class="article-model">
        <div class="article-model-header">
          <h4>美好的二天</h4>
          <small>2014-2-19</small>
        </div>
        <div class="article-model-content">
          <div class="article-text">
            <p>Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula.Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec ullamcorper nulla non metus auctor fringilla. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Donec ullamcorper nulla non metus auctor fringilla.Maecenas sed diam eget risus varius blandit sit amet non magna. Donec id elit non mi porta gravida at eget metus. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit.</p>
          </div>
        </div>
        <div class="article-model-footer">
          <a href="wei-article.html"><h5>查看全文</h5></a>
        </div>
      </div>
    </div>
    <div class="navbar-bg"></div>
    <div class="navbar-bottom">
      <ul class="list-inline">
        <li class="navbar-item navbar-item-border"><a href="wei-website-2.html" class="noneStyleLink"><img src="./img/icon_lib/home-blue.png" class="navbar-icon" alt="home"> 首页</a> </li>
        <li onclick="location.href='http://api.map.baidu.com/marker?location=31.24232,121.527883&amp;title=上海市浦东新区东方路286号（东方路乳山路）&amp;name=上海市浦东新区东方路286号（东方路乳山路）&amp;content=上海市浦东新区东方路286号（东方路乳山路）&amp;output=html'"  class="navbar-item navbar-item-border"><a class="noneStyleLink"><img src="./img/icon_lib/gps-blue.png" class="navbar-icon" alt="gps"> 导航</a> </li>
        <li class="navbar-item navbar-item-border"><a class="noneStyleLink" href="tel:58762744"><img src="./img/icon_lib/phone-blue.png" class="navbar-icon" alt="phone"> 电话 </a></li>
        <li class="navbar-item"><a onclick="switch_guide('#guide_bg','#guide_img')" class="noneStyleLink"><img src="./img/icon_lib/share-blue.png" class="navbar-icon" alt="share"> 分享</a> </li>
      </ul>
    </div>
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 
    <script type="text/javascript" src="./js/customer/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/customer/mobile-common.js"></script>
    <script src="./js/customer/bootstrap.min.js"></script>
  </body>
</html>