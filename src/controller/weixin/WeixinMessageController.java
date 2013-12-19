package controller.weixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tools.MethodUtils;
import weixinmessage.response.NewsItemToResponse;
import weixintools.WeiXinConstant;
import weixintools.WeixinMessageUtil;

@Controller
public class WeixinMessageController {
	@RequestMapping(value = "/weixin")
	@ResponseBody
	public String responseWeixin(
			@RequestParam(value = "storeId", required=false) String storeId,
			@RequestParam(value = "signature", required=false) String signature,
			@RequestParam(value = "timestamp",required=false) String timestamp,
			@RequestParam(value = "nonce",required=false) String nonce,
			@RequestParam(value = "echostr",required=false) String echostr,
			HttpServletRequest request
		){
			try {
				Map<String, String> xmlMap = WeixinMessageUtil.parseXml(request);
				System.out.println("Type:"+xmlMap.get("MsgType"));
				if(xmlMap.get("MsgType")==WeiXinConstant.MSG_TYPE_TEST_FROM_REQ){
					System.out.println("验证消息");
					return echostr;
				}
				if(xmlMap.get("MsgType").equals(WeiXinConstant.MSG_TYPE_EVENT_FROM_REQ)){
					System.out.println("注册消息");
					
					return WeixinMessageUtil.textMessageToXmlForResponse(xmlMap,
							"感谢您关注众合微平台公众账号！查看Elove效果请输入样例密码：elove");	
				}
				if(xmlMap.get("MsgType").equals(WeiXinConstant.MSG_TYPE_TEXT_FROM_REQ)){
					if (xmlMap.get("Content").equals("elove")) {
						NewsItemToResponse elove = new NewsItemToResponse();
						NewsItemToResponse wish = new NewsItemToResponse();
						NewsItemToResponse join = new NewsItemToResponse();
						
						elove.setTitle("Elove - 新人专属轻APP");
						elove.setDescription("分享喜悦，收获祝福！");
						elove.setPicUrl(MethodUtils.getApplicationPath()+"img/elovecoverpic.jpg");
						elove.setUrl(MethodUtils.getApplicationPath()+"customer/elove/story");
					
						wish.setTitle("查看亲友祝福");
						wish.setPicUrl(MethodUtils.getApplicationPath()+"img/elovewish.jpg");
						wish.setUrl(MethodUtils.getApplicationPath()+"customer/elove/wish");
						//336*163  500*242
						join.setTitle("查看已登记赴宴亲友");
						join.setPicUrl(MethodUtils.getApplicationPath()+"img/elovejoin.jpg");
						join.setUrl(MethodUtils.getApplicationPath()+"customer/elove/join");
					    
						List<NewsItemToResponse> articles = new ArrayList<NewsItemToResponse>();
						articles.add(elove);
						articles.add(wish);
						articles.add(join);
					    return WeixinMessageUtil.newsMessageToXmlForResponse(xmlMap, articles);
					
					} else {
						return WeixinMessageUtil.textMessageToXmlForResponse(xmlMap,
								"查看Elove效果请输入样例密码：elove");	
					}
				}
				return "";
			}catch(Exception e) {
				e.printStackTrace();
			    return "";
			}
	}
}
