<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
  <div class="info-bg">
    <div class="container-fulid">
      <img src="img/elove/info_title_2.jpg" class="img-responsive title" alt="title background" />
      <div class="logo-container">
        <img src="img/elove/encounter_group.jpg" class="logo" alt="logo" />
      </div>
    </div>
    
    <div class="container-fulid">
      <div class="message">
        <img src="img/elove/message_content_2.png" class="message-content" />
      </div>
    </div>
    
    <div class="container-fulid">
      <ul class="info-list">
        <li>
          <div class="info-title">
            <p>婚礼<br>时间</p>
          </div>
          <div class="info-content">
            <p>${elove.weddingDate}</p>
          </div>
        </li>
        <li onclick="map_switch()">
          <div class="info-title">
            <p>婚礼<br>地点</p>
          </div>
          <div class="info-content">
            <p>${elove.weddingAddress}</p>
            <input id="lng" type="hidden" value="${elove.lng}"/>
            <input id="lat" type="hidden" value="${elove.lat}"/>
          </div>
        </li>
        <li>
          <div class="info-title">
            <p>电话</p>
          </div>
          <div class="info-content">
            <p><a href="tel:${elove.phone}">${elove.phone}</a></p>
          </div>
        </li>
      </ul>
    </div>  
    <div class="container-fulid">
      <a data-toggle="modal" data-target="#attend" class="btn btn-elove btn-block">我要赴宴</a>
    </div>
    <!-- Modal -->
    <%@ include file="info-modal.jsp"%>

    <div class="footer">
      <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
    </div><!-- footer -->
  </div>