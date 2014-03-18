package controller.store;

import java.util.List;

import menu.Menu;
import menu.MenuNode;
import menu.Step1Info;
import menu.Step2Info;
import menu.dao.MenuDAO;
import message.GetNodeMessage;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;

import com.google.gson.Gson;
/**
 * @Title: MenuWizardController
 * @Description: 控制menu创建向导
 * @Company: Tuka
 * @author byc
 * @date 2014年3月17日
 */
@Controller
@RequestMapping("/menu/wizard")
@SessionAttributes("menuWizard")
public class MenuWizardController {
	@RequestMapping(value = "/initial", method = RequestMethod.GET)
	public String initialCreate(final Model model){
		return "AccountViews/menuWizardContainer";
	}
	
	@RequestMapping(value = "/backstep1", method = RequestMethod.GET)
	public String backstep1(final @ModelAttribute("menuWizard") Menu menu, Model model){	
		model.addAttribute("appid", menu.getAppid());
		model.addAttribute("appSecret", menu.getAppSecret());
		model.addAttribute("accessToken", menu.getAccessToken());
		return "AccountViews/menustep1";
	}
	
	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	public String step2(@RequestBody String json, final @ModelAttribute("menuWizard") Menu menu, Model model){
		Gson gson = new Gson();
		Step1Info step1Info = gson.fromJson(json, Step1Info.class);
		menu.setAppid(step1Info.getAppid());
		menu.setAppSecret(step1Info.getAppSecret());
		menu.setAccessToken(step1Info.getAccessToken());
		model.addAttribute("menuWizard", menu);
		return "AccountViews/menustep2";
	}
	
	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	@ResponseBody
	public String finish(@RequestBody String json, final @ModelAttribute("menuWizard") Menu menu,
			@CookieValue(value = "appid", required = false) String appid, Model model, 
			final SessionStatus status){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		MenuDAO menuDao= (MenuDAO) context.getBean("MenuDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		Step2Info step2Info = gson.fromJson(json, Step2Info.class);
		menu.setNodeList(step2Info.getNodeList());
		boolean success = step2Info.getSuccess();
		
		if (appid == null) {                      //异常
			message.setStatus(false);
			message.setMessage("请重新登录！");
		}
		else {
			if (appid == "" || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				message.setStatus(false);
				message.setMessage("当前管理的公众账号无效，请先选择或关联微信公众账号!");
			}
			else {
				menu.setRealAppid(appid);
				boolean checkInfo = CommonValidationTools.checkMenu(menu);
				if (!checkInfo) {
					message.setStatus(false);
					message.setMessage("自定义菜单信息填写不完整或有误！");
				}else{
					int result = menuDao.updateMenu(menu);
					
					if (result > 0) {
						if (success) {
							message.setStatus(true);
							message.setMessage("自定义菜单创建成功！");
							status.setComplete();
						}else {
							message.setStatus(false);
							message.setMessage("自定义菜单配置已经保存，但和微信服务器交互出现问题，请稍后再试！");
						}			
					}else {
						message.setStatus(false);
						message.setMessage("自定义菜单创建失败！");
						System.out.println("Error " + result);
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
	
	@RequestMapping(value = "/getnodes", method = RequestMethod.GET)
	@ResponseBody
	public String getNodes(final @ModelAttribute("menuWizard") Menu menu, 
			@CookieValue(value = "appid", required = false) String appid,
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		MenuDAO menuDao= (MenuDAO) context.getBean("MenuDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		Gson gson = new Gson();
		GetNodeMessage message = new GetNodeMessage();
		
		if (appid == null) {                      //异常
			message.setStatus(false);
			message.setMessage("请重新登录！");
		}
		else {
			if (appid == "" || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				message.setStatus(false);
				message.setMessage("当前管理的公众账号无效，请先选择或关联微信公众账号!");
			}
			else {
				List<MenuNode> nodeList = menuDao.getMenuNodeList(appid);
				message.setStatus(true);
				message.setNodeList(nodeList);
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
}
