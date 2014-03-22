package controller.store;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

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

import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;
import website.ViewLinkInfo;
import website.dao.WebsiteDAO;

import com.google.gson.Gson;

import article.Article;
import article.ArticleClass;
import article.dao.ArticleDAO;

/**
 * @Title: ArticleController
 * @Description: 文章管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月22日
 */
@Controller
public class ArticleController {
	/**
	 * @description: 获取文章列表
	 * @param appid
	 * @param classid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article/list", method = RequestMethod.GET)
	public String getArticleList(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "classid", required = true) int classid, Model model, 
			HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
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
				List<Article> articles = null;
				if (classid == 0) {
					articles = articleDao.getArticleinfos(appid);
				}else {
					if (classid > 0) {
						articles = articleDao.getArticleinfosWithType(classid);
					}
				}
				model.addAttribute("articleList", articles);
				
				List<ArticleClass> classes = articleDao.getBasicClassinfos(appid);
				ArticleClass allType = new ArticleClass();
				allType.setClassid(0);
				allType.setClassName("所有类别");
				classes.add(0, allType);
				for (int i = 0; i < classes.size(); i++) {
					if (classid == classes.get(i).getClassid()) {
						classes.get(i).setSelected(true);
						break;
					}
				}
				model.addAttribute("classList", classes);
				
				Integer websiteid = websiteDao.getWebsiteidByAppid(appid);
				InputStream inputStream = ArticleController.class.getResourceAsStream("/environment.properties");
				Properties properties = new Properties();
				String appPath = null;
				try {
					properties.load(inputStream);
					appPath = (String)properties.get("applicationPath");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				ViewLinkInfo viewLinkInfo = new ViewLinkInfo();
				viewLinkInfo.setWebsiteid(websiteid);
				viewLinkInfo.setAppPath(appPath);
				model.addAttribute("viewLinkInfo", viewLinkInfo);
				return "ArticleViews/articleList";
			}
		}
	}
	
	/**
	 * @description: 创建新的文章(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/edit/insert", method = RequestMethod.GET)
	public String editInsertArticle(@CookieValue(value = "appid", required = false) String appid, 
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
			    List<ArticleClass> classList = articleDao.getBasicClassinfos(appid);
			    model.addAttribute("classList", classList);
			    return "ArticleViews/insertArticle";
			}
		}
	}
	
	/**
	 * @description: 编辑已有的文章(第一步)
	 * @param articleid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/edit/update", method = RequestMethod.GET)
	public String editUpdateArticle(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "articleid", required = true) Integer articleid, Model model, 
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
				
				if (articleid != null && articleid > 0) {
					Article article = articleDao.getArticleContent(articleid);
					article.setContent(convertSymbol(article.getContent()));
					selectedList = article.getClassidList();
					model.addAttribute("article", article);
				}
				
			    List<ArticleClass> classList = articleDao.getBasicClassinfos(appid);
			    if (selectedList != null) {
			    	for (int i = 0; i < classList.size(); i++) {
						ArticleClass articleClass = classList.get(i);
						if (selectedList.contains(articleClass.getClassid())) {
							articleClass.setSelected(true);
						}
					}
				}
			    model.addAttribute("classList", classList);
			    return "ArticleViews/updateArticle";
			}
		}
	}
	
	/**
	 * @description: 创建新的文章(第二步)
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/insert", method = RequestMethod.POST)
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
				Article article = gson.fromJson(json, Article.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());			
				article.setAppid(appid);
				article.setCreateTime(current);
				
				if (!CommonValidationTools.checkArticle(article)) {
					message.setStatus(false);
					message.setMessage("文章信息不完整或有误！");
				} else {
					int result = articleDao.insertArticle(article);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("文章创建成功！");
					} else {
						message.setStatus(false);
						message.setMessage("文章创建失败！");
						System.out.println("Error: " + result);
					}
				}	
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 编辑已有的文章（第二步）
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
			int result = articleDao.updateArticle(article);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("文章修改保存成功！");
			} else {
				message.setStatus(false);
				message.setMessage("文章修改保存失败！");
				System.out.println("Error: " + result);
			}
		}	
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 文章的删除
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
		
		int result = articleDao.deleteArticle(articleid);
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
	private String convertSymbol(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
}
