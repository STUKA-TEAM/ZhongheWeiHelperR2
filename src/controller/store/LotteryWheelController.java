package controller.store;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import lottery.LotteryWheel;
import lottery.dao.LotteryWheelDAO;
import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;
import tools.RandomUtil;

/**
 * @Title: LotteryWheelController
 * @Description: 大转盘抽奖管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月29日
 */
@Controller
public class LotteryWheelController {
	/**
	 * @title getLotteryWheelList
	 * @description 获取转盘抽奖信息列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/list", method = RequestMethod.GET)
	public String getLotteryWheelList(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			}
			else {
				List<LotteryWheel> wheelList = wheelDao.getWheelinfoList(appid);
				model.addAttribute("wheelList", wheelList);
				
				InputStream inputStream = LotteryWheelController.class.getResourceAsStream("/environment.properties");
				Properties properties = new Properties();
				String appPath = null;
				try {
					properties.load(inputStream);
					appPath = (String)properties.get("applicationPath");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				model.addAttribute("appPath", appPath);
				return "LotteryViews/wheelList";
			}
		}
	}
	
	/**
	 * @title addLotteryWheel
	 * @description 创建新的转盘抽奖 （第一步）
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/add", method = RequestMethod.GET)
	public String addLotteryWheel(Model model){
		return "LotteryViews/insertWheel";
	}
	
	/**
	 * @title editLotteryWheel
	 * @description 编辑已有的转盘抽奖 （第一步）
	 * @param appid
	 * @param wheelid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/edit", method = RequestMethod.GET)
	public String editLotteryWheel(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "wheelid", required = true) int wheelid, Model model, 
			HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			} 
			else {
				LotteryWheel wheel = wheelDao.getWheelinfo(wheelid);
				model.addAttribute("wheel", wheel);
				return "LotteryViews/updateWheel";
			}
		}
	}
	
	/**
	 * @title createLotteryWheel
	 * @description 创建新的转盘抽奖 （第二步）
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createLotteryWheel(@CookieValue(value = "appid", required = false) String appid, 
		@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
	    
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
			
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
				LotteryWheel wheel = gson.fromJson(json, LotteryWheel.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());
				wheel.setCreateTime(current);
				wheel.setAppid(appid);
				wheel.setWheeluuid(RandomUtil.generateUUID());
				
				if (!CommonValidationTools.checkLotteryWheel(wheel)) {
					message.setStatus(false);
					message.setMessage("抽奖活动信息不完整或有误！");
				} else {
					int result = wheelDao.insertWheel(wheel);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("抽奖活动创建成功！");
					} else {
						message.setStatus(false);
						message.setMessage("抽奖活动创建失败！");
						System.out.println("Error: " + result);
					}
				}
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateLotteryWheel
	 * @description 编辑已有的转盘抽奖 （第二步）
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateLotteryWheel(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		LotteryWheel wheel = gson.fromJson(json, LotteryWheel.class);
		
		if (!CommonValidationTools.checkLotteryWheel(wheel)) {
			message.setStatus(false);
			message.setMessage("抽奖活动信息不完整或有误！");
		} else {
			int result = wheelDao.updateWheel(wheel);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("抽奖活动修改成功！");
			} else {
				message.setStatus(false);
				message.setMessage("抽奖活动修改失败！");
				System.out.println("Error: " + result);
			}
		}
		
		String response = gson.toJson(message);
		return response;		
	}
	
	/**
	 * @title deleteLotteryWheel
	 * @description 删除抽奖活动
	 * @param wheelid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteLotteryWheel(@RequestParam(value = "wheelid", required = true) 
	    int wheelid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = wheelDao.deleteWheel(wheelid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("删除成功！");
		}else {
			message.setStatus(false);
			message.setMessage("删除失败！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
}
