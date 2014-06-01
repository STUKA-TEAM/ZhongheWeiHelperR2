package controller.branch.restaurant;

import java.sql.Timestamp;
import java.util.List;

import message.ResponseMessage;

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

import security.User;
import tools.CommonValidationTools;
import article.Article;
import article.dao.ArticleDAO;

import com.google.gson.Gson;

/**
 * @Title: ArticleController
 * @Description: 动态文章管理
 * @Company: tuka
 * @author ben
 * @date 2014年6月2日
 */
@Controller
@RequestMapping("/restaurant/article")
public class ArticleController {
	/**
	 * @title getArticleList
	 * @description 获取分店关联的文章列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
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
	@RequestMapping(value = "/add", method = RequestMethod.GET)
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
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
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
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
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
	@RequestMapping(value = "/update", method = RequestMethod.POST)
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
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
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
