package controller.branch.restaurant;

import java.util.List;

import message.ResponseMessage;
import order.DishBranch;
import order.DishClass;
import order.dao.DishDAO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import register.AppInfo;
import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;

/**
 * @Title: DishController
 * @Description: 菜品管理
 * @Company: Tuka
 * @author ben
 * @date 2014年5月2日
 */
@Controller
@RequestMapping("/dish")
public class DishController {
	/**
	 * @title getDishList
	 * @description 显示菜品详细信息列表
	 * @param appid
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getDishList(@RequestParam(value = "appid", required = true) String 
			appid, @RequestParam(value = "classid", required = true) int classid,
			Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		int branchSid = user.getSid();
		boolean match = false;
		
		List<AppInfo> appInfoList = appInfoDao.getAppInfoForBranch(branchSid);
		if (appid.equals("initial")) {
			AppInfo appInfo = appInfoList.get(0);
			appid = appInfo.getAppid();
			appInfo.setIsCharged(true);
		} else {
			for (AppInfo appInfo : appInfoList) {
				if (appInfo.getAppid().equals(appid)) {
					appInfo.setIsCharged(true);
					match = true;
					break;
				}
			}
			if (!match) {
				AppInfo appInfo = appInfoList.get(0);
				appid = appInfo.getAppid();
				appInfo.setIsCharged(true);
			}		
		}
		model.addAttribute("appInfoList", appInfoList);
		
		match = false;
		List<DishClass> classList = dishDao.getBasicClassinfos(appid);
		DishClass allType = new DishClass();
		allType.setClassid(0);
		allType.setClassName("所有类别");
		classList.add(0, allType);
		for (DishClass dishClass : classList) {
			if (dishClass.getClassid() == classid) {
				dishClass.setSelected(true);
				match = true;
				break;
			}
		}
		if (!match) {
			DishClass dishClass = classList.get(0);
			classid = dishClass.getClassid();
			dishClass.setSelected(true);
		}
		model.addAttribute("classList", classList);
		
		List<DishBranch> dishList = null;
		if (classid == 0) {
			dishList = dishDao.getBranchDishinfos(appid, branchSid);
		}else {
			if (classid > 0) {
				dishList = dishDao.getBranchDishinfos(classid, branchSid);
			}
		}
		model.addAttribute("dishList", dishList);
		return "Restaurant/dishList";
	}
	
	/**
	 * @title updateDishBranch
	 * @description 更新分店菜品信息
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateDishBranch(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		DishBranch dishBranch = gson.fromJson(json, DishBranch.class);
		
		if (!CommonValidationTools.checkDishBranch(dishBranch)) {
			message.setStatus(false);
			message.setMessage("信息填写不完整或有误！");
		} else {
			int result = dishDao.updateDishBranch(dishBranch, user.getSid());
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("修改保存成功！");
			} else {
				message.setStatus(false);
				message.setMessage("修改保存失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
}
