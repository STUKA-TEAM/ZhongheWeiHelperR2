package controller.customer;

import java.util.List;

import order.DishBranch;
import order.DishClass;
import order.dao.DishDAO;

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
import branch.Branch;
import branch.dao.BranchDAO;

/**
 * @Title: BranchController
 * @Description: 分店管理
 * @Company: Tuka
 * @author ben
 * @date 2014年5月5日
 */
@Controller
public class BranchController {
	/**
	 * @title getBranchClass
	 * @description 查询分店类别关联的分店详细信息
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branchclass", method = RequestMethod.GET)
	public String getBranchClass(@RequestParam(value = "classid", required = true) int 
			classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<Branch> branchList = branchDao.getBranchClassForCustomer(classid);
		model.addAttribute("branchList", branchList);
		return "BranchViews/branchclass";
	}
	
	/**
	 * @title getArticleList
	 * @description 查询动态文章列表信息
	 * @param branchSid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branch/activities", method = RequestMethod.GET)
	public String getArticleList(@RequestParam(value = "branchid", required = true) int 
			branchSid, @RequestParam(value = "websiteid", required = true) int websiteid, 
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		((ConfigurableApplicationContext)context).close();
		
		Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
        model.addAttribute("website", website);
		List<Article> articleList = articleDao.getDynamicArticlesForCustomer(branchSid);
		model.addAttribute("articleList", articleList);
		return "BranchViews/articleList";
	}
	
	/**
	 * @title getArticle
	 * @description 查询单篇动态文章信息
	 * @param articleid
	 * @param websiteid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branch/activity", method = RequestMethod.GET)
	public String getArticle(@RequestParam(value = "activityid", required = true) int 
			articleid, @RequestParam(value = "websiteid", required = true) int websiteid, 
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
        model.addAttribute("website", website);
		Article article = articleDao.getDynamicArticleForCustomer(articleid);	
		if (article != null) {		
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByWebsite(websiteid));
			message.setAppLink(message.getAppLink() + "customer/branch/activity?"
					+ "activityid=" + articleid + "&websiteid=" + websiteid);
			String coverPic = article.getCoverPic();
			if (coverPic != null) {
				message.setImageLink(message.getImageLink() + coverPic + "_original.jpg");
			} else {
				message.setImageLink("");
			}
			message.setShareTitle(article.getTitle());
			message.setShareContent("");
			
			model.addAttribute("message", message);			
			model.addAttribute("article", article);	
		}
		return "BranchViews/article";
	}
	
	/**
	 * @title getDishList
	 * @description 查询某应用下分店可供应全部菜品详细信息
	 * @param branchSid
	 * @param websiteid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branch/dishes", method = RequestMethod.GET)
	public String getDishList(@RequestParam(value = "branchid", required = true) int 
			branchSid, @RequestParam(value = "websiteid", required = true) int websiteid, 
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		String appid = appInfoDao.getAppidByWebsite(websiteid);
  		if (appid != null) {
 			List<DishClass> classList = dishDao.getBasicClassinfos(appid);
 			if(classList.size() > 0){
 				classList.get(0).setSelected(true);
 			}
 			model.addAttribute("classList", classList);
 			if (classList.size() > 0) {
 				DishClass dishClass = classList.get(0);
 				int classid = dishClass.getClassid();
 				List<DishBranch> dishList = dishDao.getDishClassForCustomer(classid, branchSid);
 				model.addAttribute("dishList", dishList);
 			}
  		}
 
 		model.addAttribute("branchid", branchSid);
 		return "BranchViews/branchContainer";
	}
	
	/**
	 * @title getDishByClass
	 * @description 获取某分店某类别菜品详细信息
	 * @param branchSid
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branch/dish", method = RequestMethod.GET)
	public String getDishByClass(@RequestParam(value = "branchid", required = true) int 
			branchSid, @RequestParam(value = "classid", required = true) int classid, 
			Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<DishBranch> dishList = dishDao.getDishClassForCustomer(classid, branchSid);
		model.addAttribute("dishList", dishList);
		
		model.addAttribute("branchid", branchSid);
		return "BranchViews/dishList";
	}
	
	/**
	 * @title getBranch
	 * @description 查询分店详细信息R
	 * @param branchSid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branch", method = RequestMethod.GET)
	public String getBranch(@RequestParam(value = "branchid", required = true) int 
			branchSid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Branch branch = branchDao.getBranchForCustomer(branchSid);
		model.addAttribute("branch", branch);
		return "BranchViews/branch";
	}
}
