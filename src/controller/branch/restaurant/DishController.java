package controller.branch.restaurant;

import java.sql.Timestamp;
import java.util.List;

import message.ResponseMessage;
import order.Dish;
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

import branch.dao.BranchDAO;

import com.google.gson.Gson;

import register.AppInfo;
import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;

/**
 * @Title: DishController
 * @Description: 菜品管理
 * @Company: tuka
 * @author ben
 * @date 2014年5月2日
 */
@Controller
@RequestMapping("/restaurant/dish")
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
		
		match = false;
		List<DishClass> classList = dishDao.getBasicClassinfos(appid, branchSid, 
				storeSid);
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
			dishList = dishDao.getBranchDishinfos(appid, branchSid, storeSid);
		}else {
			if (classid > 0) {
				dishList = dishDao.getBranchDishinfos(classid, branchSid, storeSid);
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
	@RequestMapping(value = "/branch/update", method = RequestMethod.POST)
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
	
	/**
	 * @title addDish
	 * @description 创建新的菜品(第一步)
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addDish(@RequestParam(value = "appid", required = true) String 
			appid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		int branchSid = user.getSid();
		int storeSid = branchDao.getStoreSid(branchSid);
		
		List<DishClass> classList = dishDao.getBasicClassinfos(appid, branchSid, 
				storeSid);
		model.addAttribute("classList", classList);
		model.addAttribute("appid", appid);
		return "Restaurant/insertDish";
	}
	
	/**
	 * @title editDish
	 * @description 编辑已有的菜品(第一步)
	 * @param appid
	 * @param dishid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editDish(@RequestParam(value = "appid", required = true) String 
			appid, @RequestParam(value = "dishid", required = true) int dishid, 
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		int branchSid = user.getSid();
		int storeSid = branchDao.getStoreSid(branchSid);

		List<Integer> selectedList = null;
		if (dishid > 0) {
			Dish dish = dishDao.getDishContent(dishid);
			if (dish != null) {
				selectedList = dish.getClassidList();
				model.addAttribute("dish", dish);
			}
		}
		List<DishClass> classList = dishDao.getBasicClassinfos(appid, branchSid, 
				storeSid);
		if (selectedList != null) {
			for (int i = 0, j = classList.size(); i < j; i++) {
				DishClass dishClass = classList.get(i);
				if (selectedList.contains(dishClass.getClassid())) {
					dishClass.setSelected(true);
				}
			}
		}
		model.addAttribute("classList", classList);
		return "Restaurant/updateDish";
	}
	
	/**
	 * @title createDish
	 * @description 创建新的菜品(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createDish(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		Dish dish = gson.fromJson(json, Dish.class);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		dish.setCreatorSid(user.getSid());
		dish.setCreateTime(current);

		if (!CommonValidationTools.checkDish(dish)) {
			message.setStatus(false);
			message.setMessage("菜品信息不完整或有误！");
		} else {
			int result = dishDao.insertBranchDish(dish);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("菜品创建成功！");
			} else {
				message.setStatus(false);
				message.setMessage("菜品创建失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
    /**
     * @title updateDish
     * @description 编辑已有的菜品(第二步)
     * @param json
     * @param model
     * @return
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateDish(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		Dish dish = gson.fromJson(json, Dish.class);

		if (!CommonValidationTools.checkDish(dish)) {
			message.setStatus(false);
			message.setMessage("菜品信息不完整或有误！");
		} else {
			int result = dishDao.updateDish(dish);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("菜品修改保存成功！");
			} else {
				message.setStatus(false);
				message.setMessage("菜品修改保存失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
    /**
     * @title deleteDish
     * @description 删除菜品
     * @param dishid
     * @param model
     * @return
     */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDish(@RequestParam(value="dishid", required = true) int 
			dishid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();

		int result = dishDao.deleteDish(dishid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("菜品删除成功！");
		} else {
			message.setStatus(false);
			message.setMessage("菜品删除失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title supplyAllDish
	 * @description 供应所有菜品
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/supplyAll", method = RequestMethod.POST)
	@ResponseBody
	public String supplyAllDish(@RequestParam(value = "appid", required = true) String 
			appid, @RequestParam(value = "classid", required = true) int classid, 
			Model model){
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

		int result = 0;
		if (classid == 0) {
			result = dishDao.updateDishStatus(appid, branchSid, storeSid);
		} else {
			if (classid > 0) {
				result = dishDao.updateDishStatus(classid, branchSid, storeSid);
			}
		}
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
