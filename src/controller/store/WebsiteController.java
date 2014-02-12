package controller.store;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import article.Article;
import article.ArticleClass;
import article.dao.ArticleDAO;
import register.dao.AppInfoDAO;
import security.User;
import website.Website;
import website.dao.WebsiteDAO;

/**
 * @Title: WebsiteController
 * @Description: 微官网非Wizard管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月24日
 */
public class WebsiteController {
	/**
	 * @description: 访问微官网配置主页
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/website/home", method = RequestMethod.GET)
	public String getWebsite(@CookieValue(value = "appid", required = false) String appid,
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
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
				Website website = websiteDao.getBasicWebsiteInfo(appid);
				model.addAttribute("website", website);
				return "WebsiteViews/home";
			}
		}
	}
	
	/**
	 * @description: 删除微官网信息
	 * @param websiteid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/website/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteWebsite(@RequestParam(value = "websiteid", required = true) int websiteid,
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = 0;
		if (websiteid > 0) {
			result = websiteDao.deleteWebsite(websiteid);
		}
		
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("微官网删除成功！");
		}else {
			message.setStatus(false);
			message.setMessage("微官网删除失败！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 获取账号下可关联的文章列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/website/articlelist", method = RequestMethod.GET)
	public String getArticleList(@CookieValue(value = "appid", required = false) String appid,
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
				List<Article> articles = articleDao.getArticleinfos(appid);
				model.addAttribute("articles", articles);
				return "WebsiteViews/articlelist";
			}
		}
	}
	
	/**
	 * @description: 获取账号下可关联的文章类列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/website/articleclasslist", method = RequestMethod.GET)
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
				List<ArticleClass> articleclasses = articleDao.getDetailedClassinfos(appid);
				model.addAttribute("articleclasses", articleclasses);
				return "WebsiteViews/articleclasslist";
			}
		}
	}
	
}
