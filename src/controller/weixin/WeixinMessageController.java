package controller.weixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weixinmessage.response.NewsItemToResponse;
import weixinmessage.response.TextMessageToResponse;
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
						elove.setPicUrl("http://d.pcs.baidu.com/thumbnail/6c576bd98cad2aa2f114f20e2bf58d9b?fid=3709458387-250528-1913369364&time=1387304473&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-sFK%2FhqAnSnHWJ1Stb0BhMTkVDnI%3D&rt=sh&expires=8h&r=372014579&sharesign=unknown&size=c710_u500&quality=100");
						elove.setUrl("http://d.pcs.baidu.com/thumbnail/6c576bd98cad2aa2f114f20e2bf58d9b?fid=3709458387-250528-1913369364&time=1387304473&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-sFK%2FhqAnSnHWJ1Stb0BhMTkVDnI%3D&rt=sh&expires=8h&r=372014579&sharesign=unknown&size=c710_u500&quality=100");
					
						wish.setTitle("查看亲友祝福");
						wish.setPicUrl("http://d.pcs.baidu.com/thumbnail/4b0570365427633846434ed4dd04d701?fid=3709458387-250528-1787112340&time=1387303258&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-9nUjv%2FMLMPdJ3Hswvgg8H87zzDw%3D&rt=sh&expires=8h&r=256651708&sharesign=unknown&size=c710_u500&quality=100");
						wish.setUrl("http://d.pcs.baidu.com/thumbnail/4b0570365427633846434ed4dd04d701?fid=3709458387-250528-1787112340&time=1387303258&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-9nUjv%2FMLMPdJ3Hswvgg8H87zzDw%3D&rt=sh&expires=8h&r=256651708&sharesign=unknown&size=c710_u500&quality=100");
						//336*163  500*242
						join.setTitle("查看已登记赴宴亲友");
						join.setPicUrl("http://d.pcs.baidu.com/thumbnail/300e367f52a27e701b4e5515117887a9?fid=3709458387-250528-3689967681&time=1387302989&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-RSCyQUA%2FQS3TkRgm3vDrzLQoqAM%3D&rt=sh&expires=8h&r=907625477&sharesign=unknown&size=c710_u500&quality=100");
						join.setUrl("http://d.pcs.baidu.com/thumbnail/300e367f52a27e701b4e5515117887a9?fid=3709458387-250528-3689967681&time=1387302989&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-RSCyQUA%2FQS3TkRgm3vDrzLQoqAM%3D&rt=sh&expires=8h&r=907625477&sharesign=unknown&size=c710_u500&quality=100");
					    
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
