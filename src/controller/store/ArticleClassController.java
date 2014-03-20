package controller.store;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import article.Article;
import article.ArticleClass;
import article.dao.ArticleDAO;

/**
 * @Title: ArticleClassController
 * @Description: 文章类别管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月24日
 */
@Controller
public class ArticleClassController {
	/**
	 * @description: 获取文章类别信息列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/articleclass/list", method = RequestMethod.GET)
	public String getArticleClassList(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
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
				List<ArticleClass> classList = articleDao.getDetailedClassinfos(appid);
				model.addAttribute("classList", classList);
				return "ArticleViews/articleclassList";
			}
		}
	}
	
	/**
	 * @description: 创建新的文章类别(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/articleclass/edit/insert", method = RequestMethod.GET)
	public String editInsertArticleClass(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){	
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
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
				List<Article> articleList = articleDao.getBasicArticleinfos(appid);
				model.addAttribute("articleList", articleList);
				return "ArticleViews/addArticleclassDialog";
			}
		}
	}
	
	/**
	 * @description: 编辑已有的文章类别(第一步)
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/articleclass/edit/update", method = RequestMethod.GET)
	public String editUpdateArticleClass(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "classid", required = true) Integer classid, Model model,
			HttpServletRequest request){	
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
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
				List<Integer> selectedList = null;
				
				if (classid != null && classid > 0) {
					ArticleClass articleClass = articleDao.getClassContent(classid);
					if (articleClass != null) {
						selectedList = articleClass.getArticleidList();
						model.addAttribute("articleclass", articleClass);
					}
				}
				
				List<Article> articleList = articleDao.getBasicArticleinfos(appid);
				if (selectedList != null) {
					for (int i = 0; i < articleList.size(); i++) {
						Article article = articleList.get(i);
						if (selectedList.contains(article.getArticleid())) {
							article.setSelected(true);
						}
					}
				}
				model.addAttribute("articleList", articleList);
				return "ArticleViews/editArticleclassDialog";
			}
		}
	}
	
	/**
	 * @description: 创建新的文章类别(第二步)
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/articleclass/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createArticle(@CookieValue(value = "appid", required = false) String appid, 
		@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
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
				ArticleClass articleClass = gson.fromJson(json, ArticleClass.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());			
				articleClass.setAppid(appid);
				articleClass.setCreateTime(current);
				
				if (!CommonValidationTools.checkArticleClass(articleClass)) {
					message.setStatus(false);
					message.setMessage("文章类别信息不完整或有误！");
				}else {
					int result = articleDao.insertArticleClass(articleClass);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("文章类别创建成功！");
					}else {
						message.setStatus(false);
						message.setMessage("文章类别创建失败！");
						System.out.println("Error: " + result);
					}
				}	
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 编辑已有的文章类别（第二步）
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/articleclass/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateArticle(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
	    
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
			
		ArticleClass articleClass = gson.fromJson(json, ArticleClass.class);
		
		if (!CommonValidationTools.checkArticleClass(articleClass)) {
			message.setStatus(false);
			message.setMessage("文章类别信息不完整或有误！");
		}else {
			int result = articleDao.updateArticleClass(articleClass);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("文章类别修改成功！");
			}else {
				message.setStatus(false);
				message.setMessage("文章类别修改失败！");
				System.out.println("Error: " + result);
			}
		}	
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 文章类别的删除
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/articleclass/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteArticle(@RequestParam(value="classid", required=true) 
	    int classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = articleDao.deleteArticleClass(classid);
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
