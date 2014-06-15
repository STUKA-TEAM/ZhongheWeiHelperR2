package controller.branch.restaurant;

import java.sql.Timestamp;
import java.util.List;

import message.ResponseMessage;
import order.DishClass;
import order.DishClassBranch;
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
import branch.dao.BranchDAO;

/**
 * @Title: DishClassController
 * @Description: 菜品类别管理
 * @Company: tuka
 * @author ben
 * @date 2014年6月15日
 */
@Controller
@RequestMapping("/restaurant/dishclass")
public class DishClassController {
	/**
	 * @title getDishClassList
	 * @description 显示菜品类别详细信息列表
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getDishClassList(@RequestParam(value = "appid", required = true) String 
			appid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		int branchSid = user.getSid();
		int storeSid = branchDao.getStoreSid(branchSid);
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
		
		List<DishClassBranch> classList = dishDao.getBranchDishClassinfos(appid, branchSid, storeSid);
		model.addAttribute("classList", classList);
		return "Restaurant/dishclassList";
	}
	
	/**
	 * @title updateDishClassBranch
	 * @description 更新分店菜品类别信息
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branch/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateDishClassBranch(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		DishClassBranch classBranch = gson.fromJson(json, DishClassBranch.class);
		
		if (!CommonValidationTools.checkDishClassBranch(classBranch)) {
			message.setStatus(false);
			message.setMessage("信息填写不完整或有误！");
		} else {
			int result = dishDao.updateDishClassBranch(classBranch, user.getSid());
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
	
	/**
	 * @title addDishClass
	 * @description 创建新的菜品类别(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addDishClass(Model model){
		return "Restaurant/addDishclassDialog";
	}
	
	/**
	 * @title editDishClass
	 * @description 编辑已有的菜品类别(第一步)
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editDishClass(@RequestParam(value = "classid", required = true) int 
			classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		DishClass dishClass = dishDao.getClassContent(classid);
		model.addAttribute("dishclass", dishClass);
		return "Restaurant/editDishclassDialog";
	}
	
	/**
	 * @title createDishClass
	 * @description 创建新的菜品类别(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createDishClass(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
        DishClass dishClass = gson.fromJson(json, DishClass.class);
	    Timestamp current = new Timestamp(System.currentTimeMillis());			
		dishClass.setCreatorSid(user.getSid());
	    dishClass.setCreateTime(current);

		if (!CommonValidationTools.checkDishClass(dishClass)) {
			message.setStatus(false);
			message.setMessage("菜品类别信息不完整或有误！");
		} else {
			int result = dishDao.insertDishClass(dishClass);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("菜品类别创建成功！");
			} else {
				message.setStatus(false);
				message.setMessage("菜品类别创建失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
    /**
     * @title updateDishClass
     * @description 编辑已有的菜品类别(第二步)
     * @param json
     * @param model
     * @return
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateDishClass(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		DishClass dishClass = gson.fromJson(json, DishClass.class);

		if (!CommonValidationTools.checkDishClass(dishClass)) {
			message.setStatus(false);
			message.setMessage("菜品类别信息不完整或有误！");
		} else {
			int result = dishDao.updateDishClass(dishClass);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("菜品类别修改成功！");
			} else {
				message.setStatus(false);
				message.setMessage("菜品类别修改失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteDishClass
	 * @description 删除菜品类别
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDishClass(@RequestParam(value = "classid", required = true) int 
			classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();

		int result = dishDao.deleteBranchDishClass(classid);
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
	
	/**
	 * @title supplyAllDishClass
	 * @description 提供所有菜品类别
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/supplyAll", method = RequestMethod.POST)
	@ResponseBody
	public String supplyAllDishClass(@RequestParam(value = "appid", required = true) String 
			appid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int branchSid = ((User)auth.getPrincipal()).getSid();
		int storeSid = branchDao.getStoreSid(branchSid);
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();

		int result = dishDao.updateDishClassStatus(appid, branchSid, storeSid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("操作成功！");
		} else {
			message.setStatus(false);
			message.setMessage("操作失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
}
