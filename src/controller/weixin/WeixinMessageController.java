package controller.weixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import elove.EloveWizard;
import elove.dao.EloveWizardDAO;
import register.Welcome;
import register.WelcomeContent;
import register.dao.ThemeInfoDAO;
import register.dao.WelcomeDAO;
import tools.MethodUtils;
import weixinmessage.response.NewsItemToResponse;
import weixintools.WeiXinConstant;
import weixintools.WeixinMessageUtil;

@Controller
public class WeixinMessageController {
	@RequestMapping(value = "/weixin")
	@ResponseBody
	public String responseWeixin(
			@RequestParam(value = "appid", required=false) String appid,
			@RequestParam(value = "signature", required=false) String signature,
			@RequestParam(value = "timestamp",required=false) String timestamp,
			@RequestParam(value = "nonce",required=false) String nonce,
			@RequestParam(value = "echostr",required=false) String echostr,
			HttpServletRequest request
		){
			try {
				Map<String, String> xmlMap = WeixinMessageUtil.parseXml(request);
				if(xmlMap.get("MsgType")==WeiXinConstant.MSG_TYPE_TEST_FROM_REQ){
					return echostr;
				}
				
				if(xmlMap.get("MsgType").equals(WeiXinConstant.MSG_TYPE_EVENT_FROM_REQ)){
					System.out.println(xmlMap.get("MsgType"));
					ApplicationContext context = 
							new ClassPathXmlApplicationContext("All-Modules.xml");	
					
					if(xmlMap.get("Event").equals(WeiXinConstant.EVENT_SUBSRIBE_FROM_REQ)){
						WelcomeDAO welcomeDAO = (WelcomeDAO)context.getBean("WelcomeDAO");
						Welcome welcome = welcomeDAO.getWelcome(appid);
						if (welcome.getType().equals("text")) {
							return WeixinMessageUtil.textMessageToXmlForResponse(xmlMap,
									welcome.getContents().get(0).getContent());
						} else {
							List<NewsItemToResponse> articles = new ArrayList<NewsItemToResponse>();
							for (WelcomeContent welcomeContent : welcome.getContents()) {
								NewsItemToResponse welcomeNews = new NewsItemToResponse();
								welcomeNews.setTitle(welcomeContent.getContent());
								welcomeNews.setPicUrl(MethodUtils.getImageHost() + welcomeContent.getCoverPic() + "_standard.jpg");
								welcomeNews.setUrl(welcomeContent.getLink());
								articles.add(welcomeNews);
							}
							return WeixinMessageUtil.newsMessageToXmlForResponse(xmlMap, articles);							
						}
					}
					if(xmlMap.get("Event").equals(WeiXinConstant.EVENT_CLICK_FROM_REQ)){
						if(xmlMap.get("EventKey").equals("elovedemo")){		
							EloveWizardDAO eloveWizardDAO = (EloveWizardDAO) context.getBean("EloveWizardDAO");
							ThemeInfoDAO themeInfoDAO = (ThemeInfoDAO)context.getBean("ThemeInfoDAO");
							ArrayList<Integer> themeidList = (ArrayList<Integer>) MethodUtils.getEloveDemoIdList();
							List<NewsItemToResponse> articles = new ArrayList<NewsItemToResponse>();

							for (Integer integer : themeidList) {
								EloveWizard eloveWizard = eloveWizardDAO.getElove(integer);
								NewsItemToResponse theme = new NewsItemToResponse();
								theme.setTitle(themeInfoDAO.getEloveThemeName(eloveWizard.getThemeid()));
								theme.setPicUrl(MethodUtils.getImageHost()+eloveWizard.getCoverPic()+"_standard.jpg");
								theme.setUrl(MethodUtils.getApplicationPath()+"customer/elove/elove?eloveid=" + integer );
								articles.add(theme);
							}													
						    return WeixinMessageUtil.newsMessageToXmlForResponse(xmlMap, articles);
						}
						
						if(xmlMap.get("EventKey").equals("onlineMenu")){		
								NewsItemToResponse theme = new NewsItemToResponse();
								theme.setTitle("在线菜单");
								theme.setPicUrl("");
								theme.setUrl("http://www.baidu.com");
								List<NewsItemToResponse> articles = new ArrayList<NewsItemToResponse>();
								articles.add(theme);
						    return WeixinMessageUtil.newsMessageToXmlForResponse(xmlMap, articles);
						}
						
					}
					((ConfigurableApplicationContext)context).close();
				}
				
				
				if(xmlMap.get("MsgType").equals(WeiXinConstant.MSG_TYPE_TEXT_FROM_REQ)){
					ApplicationContext context = 
							new ClassPathXmlApplicationContext("All-Modules.xml");
					EloveWizardDAO eloveWizardDAO = (EloveWizardDAO) context.getBean("EloveWizardDAO");
					ThemeInfoDAO themeInfoDAO = (ThemeInfoDAO)context.getBean("ThemeInfoDAO");
					((ConfigurableApplicationContext)context).close();
					String textContent = xmlMap.get("Content");
					Integer eloveid = eloveWizardDAO.getEloveid(appid, textContent);
					if (eloveid != null) {
						NewsItemToResponse elove = new NewsItemToResponse();
						NewsItemToResponse wish = new NewsItemToResponse();
						NewsItemToResponse join = new NewsItemToResponse();
						
						EloveWizard eloveWizard = eloveWizardDAO.getElove(eloveid);
						elove.setTitle(eloveWizard.getCoverText());
						elove.setPicUrl(MethodUtils.getImageHost()+eloveWizard.getCoverPic()+"_standard.jpg");
						elove.setUrl(MethodUtils.getApplicationPath()+"customer/elove/elove?eloveid=" + eloveid );
					
						wish.setTitle("查看亲友祝福");
						wish.setPicUrl(MethodUtils.getApplicationPath()+"img/elovewish.jpg");
						wish.setUrl(MethodUtils.getApplicationPath()+"customer/elove/wish?eloveid=" + eloveid);
						//336*163  500*242 360*175
						join.setTitle("查看已登记赴宴亲友");
						join.setPicUrl(MethodUtils.getApplicationPath()+"img/elovejoin.jpg");
						join.setUrl(MethodUtils.getApplicationPath()+"customer/elove/join?eloveid=" + eloveid);
					    
						List<NewsItemToResponse> articles = new ArrayList<NewsItemToResponse>();
						articles.add(elove);
						articles.add(wish);
						articles.add(join);
					    return WeixinMessageUtil.newsMessageToXmlForResponse(xmlMap, articles);					
					}
					if(textContent.equalsIgnoreCase("elovedemo")){
						ArrayList<Integer> themeidList = (ArrayList<Integer>) MethodUtils.getEloveDemoIdList();
						List<NewsItemToResponse> articles = new ArrayList<NewsItemToResponse>();

						for (Integer integer : themeidList) {
							EloveWizard eloveWizard = eloveWizardDAO.getElove(integer);
							NewsItemToResponse theme = new NewsItemToResponse();
							theme.setTitle(themeInfoDAO.getEloveThemeName(eloveWizard.getThemeid()));
							theme.setPicUrl(MethodUtils.getImageHost()+eloveWizard.getCoverPic()+"_standard.jpg");
							theme.setUrl(MethodUtils.getApplicationPath()+"customer/elove/elove?eloveid=" + integer );
							articles.add(theme);
						}
												
					    return WeixinMessageUtil.newsMessageToXmlForResponse(xmlMap, articles);
					}
						return WeixinMessageUtil.textMessageToXmlForResponse(xmlMap,
								"您的输入没有匹配自动回复，我们的工作人员将会尽快处理您的消息");	
				}
				return "";
			}catch(Exception e) {
				e.printStackTrace();
			    return "";
			}
	}
}
