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

import article.Article;
import article.dao.ArticleDAO;

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
		((ConfigurableApplicationContext)context).close();

		List<DishClass> classList = dishDao.getBasicClassinfos(appid);
		model.addAttribute("classList", classList);
		return "Restaurant/insertDish";
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

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		Dish dish = gson.fromJson(json, Dish.class);
		Timestamp current = new Timestamp(System.currentTimeMillis());
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
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title getArticleList
	 * @description 获取分店关联的文章列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/list", method = RequestMethod.GET)
	public String getArticleList(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<Article> articles = articleDao.getDynamicArticleinfos(user.getSid());
		model.addAttribute("articleList", articles);
		return "Restaurant/articleList";
	}
	
	/**
	 * @title addArticle
	 * @description 创建新的动态文章(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/add", method = RequestMethod.GET)
	public String addArticle(Model model){			
		return "Restaurant/insertArticle";
	}
	
	/**
	 * @title editArticle
	 * @description 编辑已有的动态文章(第一步)
	 * @param articleid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/edit", method = RequestMethod.GET)
	public String editArticle(@RequestParam(value = "articleid", required = true) int 
			articleid, Model model){	
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();

		if (articleid > 0) {
			Article article = articleDao.getDynamicArticleContent(articleid);
			article.setContent(convertSymbol(article.getContent()));
			model.addAttribute("article", article);
		}
		return "Restaurant/updateArticle";
	}
	
	/**
	 * @title createArticle
	 * @description 创建新的动态文章(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createArticle(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
			
		Article article = gson.fromJson(json, Article.class);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		article.setBranchSid(user.getSid());
		article.setCreateTime(current);

		if (!CommonValidationTools.checkArticle(article)) {
			message.setStatus(false);
			message.setMessage("文章信息不完整或有误！");
		} else {
			int result = articleDao.insertDynamicArticle(article);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("文章创建成功！");
			} else {
				message.setStatus(false);
				message.setMessage("文章创建失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateArticle
	 * @description 编辑已有的动态文章(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateArticle(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
	    
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		Article article = gson.fromJson(json, Article.class);
		
		if (!CommonValidationTools.checkArticle(article)) {
			message.setStatus(false);
			message.setMessage("文章信息不完整或有误！");
		} else {
			int result = articleDao.updateDynamicArticle(article);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("文章修改保存成功！");
			} else {
				message.setStatus(false);
				message.setMessage("文章修改保存失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteArticle
	 * @description 动态文章的删除
	 * @param articleid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteArticle(@RequestParam(value="articleid", required = true) 
	    int articleid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = articleDao.deleteDynamicArticle(articleid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("文章删除成功！");
		}else {
			message.setStatus(false);
			message.setMessage("文章删除失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title: convertSymbol
	 * @description: 转换符号，使之在编辑器中正确显示
	 * @param str
	 * @return
	 */
	private static String convertSymbol(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
}
