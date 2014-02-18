package controller.customer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import article.Article;
import article.dao.ArticleDAO;
import website.Website;
import website.WebsiteNode;
import website.dao.WebsiteDAO;

/**
 * @Title: WebsiteController
 * @Description: 手机端微官网管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月18日
 */
@Controller
public class WebsiteController {
	/**
	 * @description: 微官网手机端首页
	 * @param model
	 * @param websiteid
	 * @return
	 */
	@RequestMapping(value = "/website/home", method = RequestMethod.GET)
	public String websiteHome(Model model, @RequestParam(value = "websiteid", required = true) int websiteid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		((ConfigurableApplicationContext)context).close();
		
		Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		String viewName = "WebsiteViews/";
		
		if (website != null && website.getExpiredTime().after(current)) {
			model.addAttribute("website", website);
			
			List<String> imageList = websiteDao.getWebsiteImagesWithType(websiteid, "introduction");
			model.addAttribute("images", imageList);
			
			List<WebsiteNode> nodeList = websiteDao.getFirstLayerNodeList(websiteid);
			model.addAttribute("nodes", nodeList);
			
			viewName = viewName + "website-" + website.getThemeId();
		}else {
			viewName = viewName + "expired";
		}
		
		return viewName;
	}
	
	/**
	 * @description: 根据nodeid获取手机端节点请求的资源信息
	 * @param model
	 * @param nodeid
	 * @return
	 */
	@RequestMapping(value = "/website/resources", method = RequestMethod.GET)
	public String getResources(Model model, @RequestParam(value = "nodeid", required = true) int nodeid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
		
		WebsiteNode node = websiteDao.getWebsiteNode(nodeid);
		String viewName = "WebsiteViews/";
		
		if (node != null) {
			String childrenType = node.getChildrenType();
			List<Integer> nodeidList = websiteDao.getNodeChildid(nodeid);
			
			if (childrenType.equals("node")) {
				List<WebsiteNode> nodeList = new ArrayList<WebsiteNode>();
				for (int i = 0; i < nodeidList.size(); i++) {
					WebsiteNode temp = websiteDao.getWebsiteNode(nodeidList.get(i));
					if (temp != null) {
						nodeList.add(temp);
					}
				}
				
				model.addAttribute("nodeList", nodeList);
				viewName = viewName + "nodeList";
				
			}else {
				if (childrenType.equals("article") && nodeidList.size() == 1) {
					Article article = articleDao.getArticleForCustomer(nodeidList.get(0));
					article.setContent(convertSymbol(article.getContent()));

					model.addAttribute("article", article);
					viewName = viewName + "article";
					
				}else {
					if (childrenType.equals("articleclass") && nodeidList.size() == 1) {
						List<Integer> articleidList = articleDao.getArticleidList(nodeidList.get(0));
						List<Article> articleList = new ArrayList<Article>();
						for (int i = 0; i < articleidList.size(); i++) {
							Article article = articleDao.getArticleIntroinfo(articleidList.get(i));
							if (article != null) {
								articleList.add(article);
							}
						}
						
						model.addAttribute("articleList", articleList);
						viewName = viewName + "articleclass";
						
					}else {
						viewName = viewName + "exception";
					}
				}
			}
		}else {
			viewName = viewName + "exception";
		}
		
		return viewName;
	}
	
	/**
	 * @description: 根据articleid获取单篇文章内容信息
	 * @param model
	 * @param articleid
	 * @return
	 */
	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public String getArticle(Model model, @RequestParam(value = "articleid", required = true) int articleid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
		
		Article article = articleDao.getArticleForCustomer(articleid);
		String viewName = "WebsiteViews/";
		
		if (article != null) {
			article.setContent(convertSymbol(article.getContent()));
			
			model.addAttribute("article", article);
			viewName = viewName + "article";
			
		}else {
			viewName = viewName + "exception";
		}
		
		return viewName;
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
