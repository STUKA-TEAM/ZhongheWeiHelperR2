<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {	 
			// 发送给好友
			WeixinJSBridge.on('menu:share:appmessage', function (argv) {
				WeixinJSBridge.invoke('sendAppMessage', { 
					"img_url": "http://223.166.98.250/resources/images/bc941ecbb6804de9a70f2f5811b8c21d_original.jpg",
					"img_width": "640",
					"img_height": "640",
					"link": "http://223.166.98.250/ZhongheWeiHelperR2/customer/website/home?websiteid=1",
					"desc": "描述",
					"title": "标题"
				}, function (res) {
				})
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