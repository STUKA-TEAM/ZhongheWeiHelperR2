package controller.internal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import elove.EloveNotpay;
import elove.dao.EloveInfoDAO;
import register.AppInfo;
import register.AuthInfo;
import register.UserInfo;
import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
import register.dao.UserInfoDAO;
import tools.CommonValidationTools;

/**
 * @Title: UserController
 * @Description: 商家CUSTOMER管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月20日
 */
@Controller
public class UserController {
	/**
	 * @description: 获取商家CUSTOMER的信息列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/customer/detail", method = RequestMethod.GET)
	public String getCustomerList(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<UserInfo> infoList = userInfoDao.getCustomerInfoList();
		model.addAttribute("infoList", infoList);
		
		return "customerList";
	}
	
	/**
	 * @description: 获取某一商家的详细模块信息
	 * @param model
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public String editCustomerInfo(Model model, @RequestParam(value = "sid", required = true) int sid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		//website
		Timestamp websiteExpired = authInfoDao.getExpiredTime(sid, "website");
		BigDecimal websitePrice = authInfoDao.getPrice(sid, "website");
		model.addAttribute("websiteExpired", websiteExpired);
		model.addAttribute("websitePrice", websitePrice);

		//elove
		Timestamp eloveExpired = authInfoDao.getExpiredTime(sid, "elove");
		BigDecimal elovePrice = authInfoDao.getPrice(sid, "elove");
		
		List<String> appidList = appInfoDao.getAppidList(sid);
		int consumedSum = 0;
		int notPaySum = 0;
		for (int i = 0; i < appidList.size(); i++) {
			String appid = appidList.get(i);
			
			int consumed = eloveInfoDao.getEloveNum(appid);
			consumedSum += consumed;
			
			Integer notPay = eloveInfoDao.getConsumeRecord(appid);
			if (notPay != null) {
				notPaySum += notPay;
			}
		}
		
		BigDecimal consumedMoney = null;
		BigDecimal notPayMoney = null;
		if (elovePrice != null) {
			consumedMoney = elovePrice.multiply(new BigDecimal(consumedSum));
			notPayMoney = elovePrice.multiply(new BigDecimal(notPaySum));
		}
		
		model.addAttribute("eloveExpired", eloveExpired);
		model.addAttribute("elovePrice", elovePrice);
		model.addAttribute("consumedSum", consumedSum);
		model.addAttribute("consumedMoney", consumedMoney);
		model.addAttribute("notPaySum", notPaySum);
		model.addAttribute("notPayMoney", notPayMoney);
		
		model.addAttribute("sid", sid);
		return "customerInfo";
	}
	
	/**
	 * @description: 更新基本信息： 过期时间和价格 (通用)
	 * @param model
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/customer/basic/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateBasic(Model model, @RequestBody String json){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		AuthInfo authInfo = gson.fromJson(json, AuthInfo.class);
		ResponseMessage message = new ResponseMessage();
		
		if (!CommonValidationTools.checkAuthInfo(authInfo)) {
			message.setStatus(false);
			message.setMessage("权限信息不完整或有误！");
		}else {
			int result = authInfoDao.updateAuthInfo(authInfo);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("更改成功！");
			}else {
				message.setStatus(false);
				message.setMessage("更改失败！");
				System.out.println("Error " + result);
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 获取elove未支付信息列表 
	 * @param model
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/customer/elove/notpaylist/query", method = RequestMethod.GET)
	public String getEloveNotPay(Model model, @RequestParam(value = "sid", required = true) int sid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<EloveNotpay> notpayList = new ArrayList<EloveNotpay>();
		List<AppInfo> appInfoList = appInfoDao.getBasicAppInfo(sid);
		for (int i = 0; i < appInfoList.size(); i++) {
			AppInfo appInfo = appInfoList.get(i);
			Integer notPay = eloveInfoDao.getConsumeRecord(appInfo.getAppid());
			
			EloveNotpay eloveNotpay = new EloveNotpay();
			eloveNotpay.setAppid(appInfo.getAppid());
			eloveNotpay.setNotPayNumber(notPay);
			eloveNotpay.setWechatName(appInfo.getWechatName());
			notpayList.add(eloveNotpay);
		}
		
		model.addAttribute("notpayList", notpayList);
		return "eloveNotpay";
	}
	
	/**
	 * @description: 更新elove未支付信息列表
	 * @param model
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/customer/elove/notpaylist/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateEloveNotpay(Model model, @RequestBody String json){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		List<EloveNotpay> notpayList = gson.fromJson(json, 
				new TypeToken<ArrayList<EloveNotpay>>(){}.getType());
		ResponseMessage message = new ResponseMessage();
		
		if (!CommonValidationTools.checkEloveNotpay(notpayList)) {
			message.setStatus(false);
			message.setMessage("未支付信息不完整或有误！");
		}else {
			int result = eloveInfoDao.updateConsumeList(notpayList);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("更改成功！");
			}else {
				message.setStatus(false);
				message.setMessage("更改失败！");
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 更改商家的联系方式
	 * @param model
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/customer/contact/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateContact(Model model, @RequestBody String json){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		UserInfo userInfo = gson.fromJson(json, UserInfo.class);
		ResponseMessage message = new ResponseMessage();
		
		if (!CommonValidationTools.checkContactInfo(userInfo)) {
			message.setStatus(false);
			message.setMessage("联系方式信息不完整或有误！");
		}else {
			int result = userInfoDao.updateContactInfo(userInfo);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("更改成功！");
			}else {
				message.setStatus(false);
				message.setMessage("更改失败！");
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
}
