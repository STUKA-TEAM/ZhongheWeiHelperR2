<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    

    <div class="container-fulid">
      <img src="img/elove/info_title.png" class="img-responsive title" alt="title background" />
      <div class="logo-container">
        <img src="img/elove/encounter_photo1.jpg" class="logo" alt="logo" />
      </div>
    </div>
    
    <div class="container-fulid">
      <div class="message">
        <img src="img/elove/message_title.png" class="message-title" />
        <img src="img/elove/message_content.png" class="message-content" />
      </div>
    </div>
    
    <div class="container-fulid">
      <ul class="info-list">
        <li>
          <div class="info-title">
            <p>婚礼<br>时间</p>
          </div>
          <div class="info-content">
            <p>2013 - 12 - 12</p>
          </div>
        </li>
        <li onclick="map_switch()">
          <div class="info-title">
            <p>婚礼<br>地点</p>
          </div>
          <div class="info-content">
            <p>上海杨浦区国和路</p>
          </div>
        </li>
        <li>
          <div class="info-title">
            <p>电话</p>
          </div>
          <div class="info-content">
            <p><a href="tel:12345678901">12345678901</a></p>
          </div>
        </li>
      </ul>
    </div>  
    <div class="container-fulid">
      <a data-toggle="modal" data-target="#attend" class="btn btn-elove btn-block">我要赴宴</a>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="attend" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <input class="modal-input" type="text" placeholder="称呼" />
            <input class="modal-input" type="text" placeholder="联系方式" />
            <input class="modal-input" type="text" placeholder="人数" />
            <p>
              <a class="modal-btn btn btn-lg btn-elove" data-dismiss="modal" onclick="">确 定</a>
              <a class="modal-btn btn btn-lg btn-elove" data-dismiss="modal" onclick="">取消</a>
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="container-fulid">
      <div id="baidumap" style="visibility:hidden;"><div id="pic"></div></div>
    </div>

    <div class="footer">
      <p>Copyright © 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
    </div><!-- footer -->