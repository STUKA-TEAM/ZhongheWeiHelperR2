<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <div class="navbar-bg"></div>
    <div class="navbar-bottom">
      <div class="toolbar">      
        <a href='customer/website/home?websiteid=${website.websiteid}' class="navbar-item navbar-item-border noneStyleLink"><img src="./img/icon_lib/home-blue.png" class="navbar-icon" alt="home"></a>
        <a href='http://api.map.baidu.com/marker?location=${website.lat},${website.lng}&amp;title=${website.address}&amp;name=${website.address}&amp;content=${website.address}&amp;output=html' class="navbar-item navbar-item-border noneStyleLink"><img src="./img/icon_lib/gps-blue.png" class="navbar-icon" alt="gps"></a>
        <a href='tel:${website.phone}' class="navbar-item navbar-item-border noneStyleLink"><img src="./img/icon_lib/phone-blue.png" class="navbar-icon" alt="phone"></a>
        <a onclick="switch_guide('#guide_bg','#guide_img')" class="navbar-item noneStyleLink"><img src="./img/icon_lib/share-blue.png" class="navbar-icon" alt="share"></a>
      </div>
    </div>
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 
