package controller.customer;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import register.dao.AuthInfoDAO;
import register.dao.UserInfoDAO;
import album.Album;
import album.dao.AlbumDAO;
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
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		Timestamp expiredTime = null;
		String viewName = "WebsiteViews/";
		
		if (website != null) {
			Integer sid = userInfoDao.getSidByAppid(website.getAppid());
			if (sid != null) {
				website.setExpiredTime(authInfoDao.getExpiredTime(sid, "website"));
			}
			expiredTime = website.getExpiredTime();
		}
		
		if (expiredTime != null && expiredTime.after(current)) {
			model.addAttribute("website", website);
			
			List<String> imageList = websiteDao.getWebsiteImagesWithType(websiteid, "introduce");
			model.addAttribute("images", imageList);
			
			List<WebsiteNode> nodeList = websiteDao.getFirstLayerNodeList(websiteid);
			model.addAttribute("nodes", nodeList);
			
			InputStream inputStream = WebsiteController.class.getResourceAsStream("/environment.properties");
			Properties properties = new Properties();
			String appLink = null;
			String imageLink = null;
			try {
				properties.load(inputStream);
				appLink = (String)properties.get("applicationPath");
				imageLink = (String) properties.get("imageHost");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			appLink = appLink + "customer/website/home?websiteid=" + websiteid;
			imageLink = imageLink + website.getSharePic();
			model.addAttribute("appLink", appLink);
			model.addAttribute("imageLink", imageLink);
			
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
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		WebsiteNode node = websiteDao.getWebsiteNode(nodeid);
		String viewName = "WebsiteViews/";
		
		if (node != null) {
			Website website = websiteDao.getWebsiteInfoForCustomer(node.getWebsiteid());
	        model.addAttribute("website", website);
			
			String childrenType = node.getChildrenType();
			List<Integer> nodeidList = websiteDao.getNodeChildid(nodeid);
			
			switch (childrenType) {
			case "node":
				List<WebsiteNode> nodeList = new ArrayList<WebsiteNode>();
				for (int i = 0; i < nodeidList.size(); i++) {
					WebsiteNode temp = websiteDao.getWebsiteNode(nodeidList.get(i));
					if (temp != null) {
						nodeList.add(temp);
					}
				}
				Integer sid = userInfoDao.getSidByWebsiteid(node.getWebsiteid());
				List<String> imageList = null;
				if (sid != null) {
					imageList = userInfoDao.getUserImages(sid);
				}
				model.addAttribute("imageList", imageList);
				model.addAttribute("nodeList", nodeList);
				viewName = viewName + "nodeList";
				break;
			case "article":
				Article article = articleDao.getArticleForCustomer(nodeidList.get(0));
				model.addAttribute("article", article);
				viewName = viewName + "article";
				break;
			case "articleclass":
				List<Integer> articleidList = articleDao.getArticleidList(nodeidList.get(0));
				List<Article> articleList = new ArrayList<Article>();
				for (int i = 0; i < articleidList.size(); i++) {
					Article article2 = articleDao.getArticleIntroinfo(articleidList.get(i));
					if (article2 != null) {
						articleList.add(article2);
					}
				}
				model.addAttribute("articleList", articleList);
				viewName = viewName + "articleclass";
				break;
			case "album":
				Album album = albumDao.getAlbumForCustomer(nodeidList.get(0));
				model.addAttribute("album", album);
				viewName = viewName + "album";
				break;
			case "albumclass":
				List<Album> albumList = albumDao.getAlbumClassForCustomer(nodeidList.get(0));
				model.addAttribute("albumList", albumList);
				viewName = viewName + "albumclass";
				break;
			default:
				viewName = viewName + "exception";
				break;
			}
		} else {
			viewName = viewName + "exception";
		}
		return viewName;
	}
	
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
		((ConfigurableApplicationContext)context).close();
		
		Article article = articleDao.getArticleForCustomer(articleid);
		String viewName = "WebsiteViews/";
		
		if (article != null) {
			Integer websiteid = articleDao.getWebsiteidByArticleid(articleid);
			if (websiteid != null) {
				Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
		        model.addAttribute("website", website);
			}
			
			model.addAttribute("article", article);
			viewName = viewName + "article";		
		}else {
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
		
		List<Integer> articleidList = articleDao.getArticleidList(classid);
		List<Article> articleList = new ArrayList<Article>();
		for (int i = 0; i < articleidList.size(); i++) {
			Article article = articleDao.getArticleIntroinfo(articleidList.get(i));
			if (article != null) {
				articleList.add(article);
			}
		}	
		model.addAttribute("articleList", articleList);
		
		Integer websiteid = articleDao.getWebsiteidByClassid(classid);
		if (websiteid != null) {
			Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);	
	        model.addAttribute("website", website);
		}
		return "WebsiteViews/articleclass";
	}
	
	/**
	 * @title getAlbum
	 * @description 根据albumid获取相册信息
	 * @param model
	 * @param albumid
	 * @param websiteid
	 * @return
	 */
	@RequestMapping(value = "/album", method = RequestMethod.GET)
	public String getAlbum(Model model, @RequestParam(value = "albumid", required = true) int albumid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		Album album = albumDao.getAlbumForCustomer(albumid);
		String viewName = "WebsiteViews/";
		
		if (album != null) {
			Integer websiteid = albumDao.getWebsiteidByAlbumid(albumid);
			if (websiteid != null) {
				Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
		        model.addAttribute("website", website);
			}
			
			model.addAttribute("album", album);
			viewName = viewName + "album";	
		}else {
			viewName = viewName + "exception";
		}
		return viewName;
	}
	
	/**
	 * @title getAlbumClass
	 * @description 根据classid获取相册集下相册信息
	 * @param model
	 * @param classid
	 * @param websiteid
	 * @return
	 */
	@RequestMapping(value = "/albumclass", method = RequestMethod.GET)
	public String getAlbumClass(Model model, @RequestParam(value = "classid", required = true) int classid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<Album> albumList = albumDao.getAlbumClassForCustomer(classid);
		model.addAttribute("albumList", albumList);
		
		Integer websiteid = albumDao.getWebsiteidByClassid(classid);
		if (websiteid != null) {
			Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);	
	        model.addAttribute("website", website);
		}
		return "WebsiteViews/albumclass";
	}
}
