<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="prize-container">
  <div class="prize-title">
  <img src="./img/lottery_wheel/prize_notLucky.png" class="img-responsive lottery_logo">
  </div>
  <p class="prize"><strong></strong></p>
  <p class="prize-token">祝您下次好运！您今天还有${restChance}次抽奖机会</p>
  <c:if test="${restChance>0}">
  <p class="prize-token"><a class="luckybutton" href="javascript:void(0)" type="button" onclick="window.location.reload();">再次抽奖</a></p>
  </c:if>
  <c:if test="${restChance==0}">
  <p class="prize-token">分享到朋友圈将获得3次额外机会，分享给朋友将获得1次额外机会！<a class="luckybutton" href="javascript:void(0)" type="button" onclick="switch_guide('#guide_bg','#guide_img')">分享</a></p>
  </c:if>

  <input type="hidden" value="notLucky"/>
</div>