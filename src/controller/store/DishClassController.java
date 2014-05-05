package controller.store;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import message.ResponseMessage;
import order.Dish;
import order.DishClass;
import order.dao.DishDAO;

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

import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;

import com.google.gson.Gson;

/**
 * @Title: DishClassController
 * @Description: 菜品类别管理
 * @Company: Tuka
 * @author ben
 * @date 2014年4月29日
 */
@Controller
@RequestMapping("/dishclass")
public class DishClassController {
	/**
	 * @title getDishClassList
	 * @description 显示菜品类别详细信息列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getDishClassList(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();

		if (appid == null) {
			return "redirect:/store/account";     
		}
		else {
			if (appid.trim().equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			}
			else {	
				List<DishClass> classList = dishDao.getDetailedClassinfos(appid);
				model.addAttribute("classList", classList);
				return "DishViews/dishclassList";
			}
		}
	}
	
	/**
	 * @title addDishClass
	 * @description 创建新的菜品类别(第一步)
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addDishClass(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();

		if (appid == null) {
			return "redirect:/store/account";     
		}
		else {
			if (appid.trim().equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			} 
			else {
				List<Dish> dishList = dishDao.getBasicDishinfos(appid);
				model.addAttribute("dishList", dishList);
				return "DishViews/addDishclassDialog";
			}
		}	
	}
	
	/**
	 * @title editDishClass
	 * @description 编辑已有的菜品类别(第一步)
	 * @param appid
	 * @param classid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editDishClass(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "classid", required = true) int classid, Model model,
			HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();

		if (appid == null) {
			return "redirect:/store/account";     
		}
		else {
			if (appid.trim().equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			} 
			else {
				List<Integer> selectedList = null;	
				if (classid > 0) {
					DishClass dishClass = dishDao.getClassContent(classid);
					if (dishClass != null) {
						selectedList = dishClass.getDishidList();
						model.addAttribute("dishclass", dishClass);
					}
				}
				List<Dish> dishList = dishDao.getBasicDishinfos(appid);
				if (selectedList != null) {
					for (int i = 0, j = dishList.size(); i < j; i++) {
						Dish dish = dishList.get(i);
						if (selectedList.contains(dish.getDishid())) {
							dish.setSelected(true);
						}
					}
				}
				model.addAttribute("dishList", dishList);
				return "DishViews/editDishclassDialog";
			}
		}
	}

	/**
	 * @title createDishClass
	 * @description 创建新的菜品类别(第二步)
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createDishClass(@CookieValue(value = "appid", required = false) String appid, 
		@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();

		if (appid == null) {
			message.setStatus(false);
			message.setMessage("请重新登录！");
		}
		else {
			if (appid.trim().equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {
				message.setStatus(false);
				message.setMessage("当前管理的公众账号无效，请先选择或关联微信公众账号!");
			}
			else {
				DishClass dishClass = gson.fromJson(json, DishClass.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());			
				dishClass.setAppid(appid);
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
						System.out.println("Error: " + result);
					}
				}
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
				System.out.println("Error: " + result);
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
	public String deleteDishClass(@RequestParam(value = "classid", required = true) 
	    int classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();

		int result = dishDao.deleteDishClass(classid);
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
