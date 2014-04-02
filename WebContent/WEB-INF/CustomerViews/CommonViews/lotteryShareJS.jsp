<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="js/customer/jquery.cookie.js">
</script>
<script type="text/javascript">
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {	 
			// 发送给好友
			WeixinJSBridge.on('menu:share:appmessage', function (argv) {
				WeixinJSBridge.invoke('sendAppMessage', { 
					"img_url": "http://58.247.181.41/resources/images/368e19daee184fc78b8bd529b1ce3fa1_original.jpg",
					"img_width": "300",
					"img_height": "300",
					"link": window.shareInfo.link,
					"desc": "",
					"title": window.shareInfo.title
				}, function (res) {
				});
			});

			// 分享到朋友圈
			WeixinJSBridge.on('menu:share:timeline', function (argv) {
				WeixinJSBridge.invoke('shareTimeline', {
					"img_url": window.shareData.imgUrl,
					"img_width": "640",
					"img_height": "640",
					"link": window.shareData.timeLineLink,
					"desc": window.shareData.tContent,
					"title": window.shareData.tTitle
				}, function (res) {
				});
			});

			// 分享到微博
			WeixinJSBridge.on('menu:share:weibo', function (argv) {
				WeixinJSBridge.invoke('shareWeibo', {
					"content": window.shareData.wContent,
					"url": window.shareData.weiboLink
				}, function (res) {
				});
			});
		}, false);
</script>