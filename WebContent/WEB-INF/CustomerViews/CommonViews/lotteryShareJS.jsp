<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {	 
	// 发送给好友
	WeixinJSBridge.on('menu:share:appmessage', function (argv) {
		WeixinJSBridge.invoke('sendAppMessage', { 
			"img_url": window.shareInfo.imgUrl,
			"img_width": "300",
			"img_height": "300",
			"link": window.shareInfo.link,
			"desc": window.shareInfo.desc,
			"title": window.shareInfo.title
		}, function (res) {
			cutCount(1);
			window.location.reload();
		});
	});

	// 分享到朋友圈
	WeixinJSBridge.on('menu:share:timeline', function (argv) {
		WeixinJSBridge.invoke('shareTimeline', {
			"img_url": window.shareInfo.imgUrl,
			"img_width": "300",
			"img_height": "300",
			"link": window.shareInfo.link,
			"desc": window.shareInfo.desc,
			"title": window.shareInfo.title
		}, function (res) {
			cutCount(3);
			window.location.reload();
		});
	});

	// 分享到微博
	WeixinJSBridge.on('menu:share:weibo', function (argv) {
		WeixinJSBridge.invoke('shareWeibo', {
			"content": window.shareInfo.desc,
			"url": window.shareInfo.link
		}, function (res) {
		});
	});
}, false);

function cutCount(x){
	  var count = $.cookie($("#wheeluuid").val());
	  $.cookie($("#wheeluuid").val(), count-x, {path: '/', expires: 1}); 
}
</script>