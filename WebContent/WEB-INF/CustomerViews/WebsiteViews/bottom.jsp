<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <div id="nav-item" class="nav-item-home">
      <a href='customer/website/home?websiteid=${website.websiteid}'><img src="img/website-nav/nav-home.png" class="img-responsive"></a>
    </div>
    <div id="nav-item" class="nav-item-phone">
      <a href='tel:${website.phone}'><img src="img/website-nav/nav-phone.png" class="img-responsive"></a>
    </div>
    <div id="nav-item" class="nav-item-gps">
      <a href='http://api.map.baidu.com/marker?location=${website.lat},${website.lng}&amp;title=${website.address}&amp;name=${website.address}&amp;content=${website.address}&amp;output=html'><img src="img/website-nav/nav-gps.png" class="img-responsive"></a>
    </div>
    <div id="nav-item" class="nav-item-share">
      <a onclick="switch_guide('#guide_bg','#guide_img')"><img src="img/website-nav/nav-share.png" class="img-responsive"></a>
    </div>
    <div class="nav-bar-circle">
      <img src="img/website-nav/nav-bar.png" class="img-responsive">
    </div>
    <div id="guide_bg" class="guide hidden" onclick="close_guide('#guide_bg','#guide_img')">
        <img id="guide_img" class="guide-pic img-responsive hidden" src="img/common/guide.png"/>
    </div> 