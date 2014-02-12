package controller.store;

import java.sql.Timestamp;
import java.util.List;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.Gson;

import register.ThemeInfo;
import register.dao.AppInfoDAO;
import register.dao.ThemeInfoDAO;
import security.User;
import tools.CommonValidationTools;
import website.Step1Info;
import website.Step2Info;
import website.Website;
import website.dao.WebsiteDAO;

/**
 * @Title: WebsiteWizardController
 * @Description: 微官网Wizard管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月25日
 */
@Controller
@RequestMapping("/website/wizard")
@SessionAttributes("websiteWizard")
public class WebsiteWizardController {
	@RequestMapping(value = "/initial/create", method = RequestMethod.GET)
	public String initialCreate(final Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ThemeInfoDAO themeInfoDao = (ThemeInfoDAO) context.getBean("ThemeInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<ThemeInfo> themeInfoList = themeInfoDao.getWebsiteThemeInfos();
		model.addAttribute("themeInfoList", themeInfoList);
		
		Website website = new Website();
		model.addAttribute("websiteWizard", website);
		return "WebsiteViews/wizardContainer";
	}
	
	@RequestMapping(value = "/initial/edit", method = RequestMethod.GET)
	public String initialEdit(@RequestParam(value = "websiteid", required = true) int websiteid, 
			final Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		ThemeInfoDAO themeInfoDao = (ThemeInfoDAO) context.getBean("ThemeInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<ThemeInfo> themeInfoList = themeInfoDao.getWebsiteThemeInfos();
		model.addAttribute("themeInfoList", themeInfoList);
		
		if (websiteid > 0) {
			Website website = websiteDao.getDetailedWebsiteInfo(websiteid);
			model.addAttribute("websiteWizard", website);
		}
		return "WebsiteViews/wizardContainer";
	}
	
	@RequestMapping(value = "/backstep1", method = RequestMethod.GET)
	public String backstep1(final @ModelAttribute("websiteWizard") Website website, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ThemeInfoDAO themeInfoDao = (ThemeInfoDAO) context.getBean("ThemeInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<ThemeInfo> themeInfoList = themeInfoDao.getWebsiteThemeInfos();
		model.addAttribute("themeInfoList", themeInfoList);
		
		model.addAttribute("websiteWizard", website);
		return "WebsiteViews/step1";
	}
	
	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	public String step2(@RequestBody String json, final @ModelAttribute("websiteWizard") Website website, 
			Model model){
		Gson gson = new Gson();
		Step1Info step1Info = gson.fromJson(json, Step1Info.class);
		website.setTitle(step1Info.getTitle());
		website.setImageList(step1Info.getImageList());
		website.setGetCode(step1Info.getGetCode());
		website.setPhone(step1Info.getPhone());
		website.setAddress(step1Info.getAddress());
		website.setLat(step1Info.getLat());
		website.setLng(step1Info.getLng());
		website.setCoverPic(step1Info.getCoverPic());
		website.setCoverText(step1Info.getCoverText());
		website.setShareTitle(step1Info.getShareTitle());
		website.setShareContent(step1Info.getShareContent());
		website.setFooterText(step1Info.getFooterText());
		website.setThemeId(step1Info.getThemeId());
		model.addAttribute("websiteWizard", website);
		return "WebsiteViews/step2";
	}
	
	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	@ResponseBody
	public String finish(@RequestBody String json, final @ModelAttribute("websiteWizard") Website website, 
			 @CookieValue(value = "appid", required = false) String appid, Model model, final SessionStatus status){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		Gson gson = new Gson();
		Step2Info step2Info = gson.fromJson(json, Step2Info.class);
		website.setNodeList(step2Info.getNodeList());
		ResponseMessage message = new ResponseMessage();
		
		if (website.getWebsiteid() > 0) {   //update
			String checkInfo = CommonValidationTools.checkWebsite(website);
			if (!checkInfo.equals("pass")) {
				message.setStatus(false);
				message.setMessage(checkInfo);
			}else{
				int effected = websiteDao.updateWebsite(website);
				
				if (effected > 0) {
					message.setStatus(true);
					message.setMessage("微官网信息修改成功！");
					status.setComplete();
				}else {
					message.setStatus(false);
					message.setMessage("微官网信息修改失败！");
					System.out.println("Error " + effected);
				}	
			}
			
		}else {    //insert
			if (appid == null) {                      //异常
				message.setStatus(false);
				message.setMessage("请重新登录！");
			}
			else {
				if (appid == "" || appInfoDao.checkAppExistsByUser(user.getSid(), 
						appid) == 0) {                                                      //需先创建app或appid无效
					message.setStatus(false);
					message.setMessage("当前管理的公众账号无效，请先选择或关联微信公众账号!");
				}
				else {
					website.setAppid(appid);
					
					Timestamp createTime = new Timestamp(System.currentTimeMillis());
					Timestamp expiredTime = websiteDao.getExpiredTime(user.getSid(), "website");
					website.setCreateTime(createTime);
					website.setExpiredTime(expiredTime);
					
					String checkInfo = CommonValidationTools.checkWebsite(website);
					if (!checkInfo.equals("pass")) {
						message.setStatus(false);
						message.setMessage(checkInfo);
					}else{
						int result = websiteDao.insertWebsite(website);
						
						if (result > 0) {
							message.setStatus(true);
							message.setMessage("微官网创建成功！");
							status.setComplete();
						}else {
							message.setStatus(false);
							message.setMessage("微官网创建失败！");
							System.out.println("Error " + result);
						}	
					}			
				}
			}
		}			
			
		String response = gson.toJson(message);
		return response;
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	@ResponseBody
	public String cancel(final SessionStatus status){
		status.setComplete();
		return "OK";
	}

}
