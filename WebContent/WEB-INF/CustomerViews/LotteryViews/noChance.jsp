<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <div class="prize-container">
  <div class="prize-title">
  </div>
  <p class="prize"><strong>今天没有机会了！</strong></p>
  <p class="prize-token"><strong>分享到朋友圈将获得3次额外机会，分享给朋友将获得1次额外机会！</strong></p>
  <p class="prize-token"><strong>快去分享吧！</strong></p>
  <p class="prize-token"><a class="luckybutton" href="javascript:void(0)" type="button" onclick="switch_guide('#guide_bg','#guide_img')">分享</a></p>
  <input type="hidden" value="noChance"/>
</div>