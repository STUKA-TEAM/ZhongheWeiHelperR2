<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <div class="navbar-bg"></div>
    <div class="navbar-bottom">
      <ul class="list-inline">      
        <li class="navbar-item navbar-item-border"><a href="customer/website/home?websiteid=${website.websiteid}" class="noneStyleLink"><img src="./img/icon_lib/home-blue.png" class="navbar-icon" alt="home"> 首页</a> </li>
        <li onclick="location.href='http://api.map.baidu.com/marker?location=${website.lat},${website.lng}&amp;title=${website.address}&amp;name=${website.address}&amp;content=${website.address}&amp;output=html'"  class="navbar-item navbar-item-border"><a class="noneStyleLink"><img src="./img/icon_lib/gps-blue.png" class="navbar-icon" alt="gps"> 导航</a> </li>
        <li class="navbar-item navbar-item-border"><a class="noneStyleLink" href="tel:${website.phone}"><img src="./img/icon_lib/phone-blue.png" class="navbar-icon" alt="phone"> 电话 </a></li>
        <li class="navbar-item"><a onclick="switch_guide('#guide_bg','#guide_img')" class="noneStyleLink"><img src="./img/icon_lib/share-blue.png" class="navbar-icon" alt="share"> 分享</a> </li>
      </ul>
    </div>
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 
