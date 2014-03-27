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

import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
import register.dao.UserInfoDAO;
import album.Album;
import album.dao.AlbumDAO;
import article.Article;
import article.dao.ArticleDAO;
import vote.Vote;
import vote.dao.VoteDAO;
import website.ShareMessage;
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
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
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
			
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByWebsite(websiteid));
			message.setAppLink(message.getAppLink() + "customer/website/home?websiteid=" + websiteid);
			if (website.getSharePic() != null && !website.getSharePic().equals("")) {
				message.setImageLink(message.getImageLink() + website.getSharePic() + "_original.jpg");
			} else {
				message.setImageLink("");
			}
			message.setShareTitle(website.getShareTitle());
			message.setShareContent(website.getShareContent());
			model.addAttribute("message", message);
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
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		UserInfoDAO userInfoDao = null;
		ArticleDAO articleDao = null;
		AlbumDAO albumDao = null;
		VoteDAO voteDao = null;
		
		WebsiteNode node = websiteDao.getWebsiteNode(nodeid);
		String viewName = "WebsiteViews/";
		
		if (node != null) {
			Website website = websiteDao.getWebsiteInfoForCustomer(node.getWebsiteid());
	        model.addAttribute("website", website);
			
			String childrenType = node.getChildrenType();
			List<Integer> nodeidList = websiteDao.getNodeChildid(nodeid);
			
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByWebsite(website.getWebsiteid()));
			
			switch (childrenType) {
			case "node":
				userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
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
				viewName = "ArticleViews/";
				articleDao = (ArticleDAO) context.getBean("ArticleDAO");
				Article article = articleDao.getArticleForCustomer(nodeidList.get(0));
				model.addAttribute("article", article);
				
				message.setAppLink(message.getAppLink() + "customer/article?articleid=" + article.getArticleid());
				if (article.getCoverPic() != null && !article.getCoverPic().equals("")) {
					message.setImageLink(message.getImageLink() + article.getCoverPic() + "_original.jpg");
				} else {
					message.setImageLink("");
				}
				message.setShareTitle(article.getTitle());
				message.setShareContent("");
				model.addAttribute("message", message);
				viewName = viewName + "article";
				break;
			case "articleclass":
				viewName = "ArticleViews/";
				articleDao = (ArticleDAO) context.getBean("ArticleDAO");
				List<Article> articleList = articleDao.getArticleClassForCustomer(nodeidList.get(0));
				model.addAttribute("articleList", articleList);
				viewName = viewName + "articleclass";
				break;
			case "album":
				viewName = "AlbumViews/";
				albumDao = (AlbumDAO) context.getBean("AlbumDAO");
				Album album = albumDao.getAlbumForCustomer(nodeidList.get(0));
				model.addAttribute("album", album);
				
				message.setAppLink(message.getAppLink() + "customer/album?albumid=" + album.getAlbumid());
				if (album.getCoverPic() != null && !album.getCoverPic().equals("")) {
					message.setImageLink(message.getImageLink() + album.getCoverPic() + "_original.jpg");
				} else {
					message.setImageLink("");
				}
				message.setShareTitle(album.getAlbumName());
				message.setShareContent("");
				model.addAttribute("message", message);
				viewName = viewName + "album";
				break;
			case "albumclass":
				viewName = "AlbumViews/";
				albumDao = (AlbumDAO) context.getBean("AlbumDAO");
				List<Album> albumList = albumDao.getAlbumClassForCustomer(nodeidList.get(0));
				model.addAttribute("albumList", albumList);
				viewName = viewName + "albumclass";
				break;
			case "vote":
				viewName = "VoteViews/";
				voteDao = (VoteDAO) context.getBean("VoteDAO");
				Vote vote = voteDao.getVoteForCustomer(nodeidList.get(0));
				model.addAttribute("vote", vote);
				
				message.setAppLink(message.getAppLink() + "customer/vote?voteid=" + vote.getVoteid());
				if (vote.getCoverPic() != null && !vote.getCoverPic().equals("")) {
					message.setImageLink(message.getImageLink() + vote.getCoverPic() + "_original.jpg");
				} else {
					message.setImageLink("");
				}
				message.setShareTitle(vote.getVoteName());
				message.setShareContent(vote.getVoteDesc());
				model.addAttribute("message", message);
				viewName = viewName + "vote";
				break;
			default:
				viewName = viewName + "exception";
				break;
			}
		} else {
			viewName = viewName + "exception";
		}
		
		((ConfigurableApplicationContext)context).close();
		return viewName;
	}
}
