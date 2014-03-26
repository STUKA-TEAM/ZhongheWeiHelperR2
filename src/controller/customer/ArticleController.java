package controller.customer;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import register.dao.AppInfoDAO;
import website.ShareMessage;
import website.Website;
import website.dao.WebsiteDAO;
import article.Article;
import article.dao.ArticleDAO;

/**
 * @Title: ArticleController
 * @Description: 文章资源管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月26日
 */
@Controller
public class ArticleController {
	/**
	 * @title getArticle
	 * @description 根据articleid获取文章内容信息
	 * @param model
	 * @param articleid
	 * @return
	 */
	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public String getArticle(Model model, @RequestParam(value = "articleid", required = true) int articleid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Article article = articleDao.getArticleForCustomer(articleid);
		String viewName = "WebsiteViews/";
		
		if (article != null) {
			Integer websiteid = articleDao.getWebsiteidByArticleid(articleid);
			if (websiteid != null) {
				Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
		        model.addAttribute("website", website);
			}
			
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByArticle(articleid));
			message.setAppLink(message.getAppLink() + "customer/article?articleid=" + articleid);
			if (article.getCoverPic() != null && !article.getCoverPic().equals("")) {
				message.setImageLink(message.getImageLink() + article.getCoverPic() + "_original.jpg");
			} else {
				message.setImageLink("");
			}
			message.setShareTitle(article.getTitle());
			message.setShareContent("");
			model.addAttribute("message", message);
			
			model.addAttribute("article", article);
			viewName = viewName + "article";		
		} else {
			viewName = viewName + "exception";
		}
		return viewName;
	}
	
	/**
	 * @title getArticleClass
	 * @description 根据classid获取文章类别下文章列表信息
	 * @param model
	 * @param classid
	 * @param websiteid
	 * @return
	 */
	@RequestMapping(value = "/articleclass", method = RequestMethod.GET)
	public String getArticleClass(Model model, @RequestParam(value = "classid", required = true) int classid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<Article> articleList = articleDao.getArticleClassForCustomer(classid);
		model.addAttribute("articleList", articleList);
		
		Integer websiteid = articleDao.getWebsiteidByClassid(classid);
		if (websiteid != null) {
			Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);	
	        model.addAttribute("website", website);
		}
		return "WebsiteViews/articleclass";
	}
}
