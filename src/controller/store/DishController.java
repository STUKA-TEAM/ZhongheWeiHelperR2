package controller.store;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

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
 * @Title: DishController
 * @Description: 菜品管理
 * @Company: Tuka
 * @author ben
 * @date 2014年4月29日
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
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getDishList(@CookieValue(value = "appid", required = false) String appid, 
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
				List<Dish> dishList = null;
				if (classid == 0) {
					dishList = dishDao.getDetailedDishinfos(appid);
				}else {
					if (classid > 0) {
						dishList = dishDao.getDetailedDishinfos(classid);
					}
				}
				model.addAttribute("dishList", dishList);

				List<DishClass> classList = dishDao.getBasicClassinfos(appid);
				DishClass allType = new DishClass();
				allType.setClassid(0);
				allType.setClassName("所有类别");
				classList.add(0, allType);
				for (int i = 0, j = classList.size(); i < j; i++) {
					DishClass dishClass = classList.get(i);
					if (classid == dishClass.getClassid()) {
						dishClass.setSelected(true);
						break;
					}
				}
				model.addAttribute("classList", classList);

				InputStream inputStream = DishController.class.getResourceAsStream("/environment.properties");
				Properties properties = new Properties();
				String appPath = null;
				try {
					properties.load(inputStream);
					appPath = (String)properties.get("applicationPath");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				model.addAttribute("appPath", appPath);
				return "OrderViews/dishList";
			}
		}
	}

	/**
	 * @title addDish
	 * @description 创建新的菜品(第一步)
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addDish(@CookieValue(value = "appid", required = false) String appid, 
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
				List<DishClass> classList = dishDao.getBasicClassinfos(appid);
				model.addAttribute("classList", classList);
			    return "OrderViews/insertDish";
			}
		}
	}
	
	/**
	 * @title editDish
	 * @description 编辑已有的菜品(第一步)
	 * @param appid
	 * @param dishid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editDish(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "dishid", required = true) int dishid, Model model, 
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
				if (dishid > 0) {
					Dish dish = dishDao.getDishContent(dishid);
					if (dish != null) {
						selectedList = dish.getClassidList();
						model.addAttribute("dish", dish);
					}
				}
				List<DishClass> classList = dishDao.getBasicClassinfos(appid);
				if (selectedList != null) {
					for (int i = 0, j = classList.size(); i < j; i++) {
						DishClass dishClass = classList.get(i);
						if (selectedList.contains(dishClass.getClassid())) {
							dishClass.setSelected(true);
						}
					}
				}
				model.addAttribute("classList", classList);
			    return "OrderViews/updateDish";
			}
		}
	}
	
    /**
     * @title createDish
     * @description 创建新的菜品(第二步)
     * @param appid
     * @param json
     * @param model
     * @return
     */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createDish(@CookieValue(value = "appid", required = false) String appid, 
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
				Dish dish = gson.fromJson(json, Dish.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());
				dish.setAppid(appid);
				dish.setCreateTime(current);

				if (!CommonValidationTools.checkDish(dish)) {
					message.setStatus(false);
					message.setMessage("菜品信息不完整或有误！");
				} else {
					int result = dishDao.insertDish(dish);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("菜品创建成功！");
					} else {
						message.setStatus(false);
						message.setMessage("菜品创建失败！");
						System.out.println("Error: " + result);
					}
				}
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
				System.out.println("Error: " + result);
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
	public String deleteDish(@RequestParam(value="dishid", required = true) 
	    int dishid, Model model){
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
		}else {
			message.setStatus(false);
			message.setMessage("菜品删除失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
}
