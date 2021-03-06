package controller.store;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import lottery.LotteryWheel;
import lottery.dao.LotteryWheelDAO;
import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import branch.BranchClass;
import branch.dao.BranchDAO;

import com.google.gson.Gson;

import album.Album;
import album.AlbumClass;
import album.dao.AlbumDAO;
import article.Article;
import article.ArticleClass;
import article.dao.ArticleDAO;
import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
import security.User;
import vote.Vote;
import vote.dao.VoteDAO;
import website.Website;
import website.dao.WebsiteDAO;

/**
 * @Title: WebsiteController
 * @Description: 微官网非Wizard管理
 * @Company: Tuka
 * @author ben
 * @date 2014年1月24日
 */
@Controller
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
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
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
				if (website != null) {
					website.setExpiredTime(authInfoDao.getExpiredTime(user.getSid(),"website"));
				}
				model.addAttribute("website", website);
				
				InputStream inputStream = WebsiteController.class.getResourceAsStream("/environment.properties");
				Properties properties = new Properties();
				String appPath = null;
				try {
					properties.load(inputStream);
					appPath = (String)properties.get("applicationPath");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				model.addAttribute("appPath", appPath);
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
	
	/**
	 * @title getAlbumList
	 * @description 获取账号下可关联的相册列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/website/albumlist", method = RequestMethod.GET)
	public String getAlbumList(@CookieValue(value = "appid", required = false) String appid,
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
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
				List<Album> albumList = albumDao.getBasicAlbuminfos(appid);
				model.addAttribute("albumList", albumList);
				return "WebsiteViews/albumlist";
			}
		}
	}
	
	/**
	 * @title getAlbumClassList
	 * @description 获取账号下可关联的相册集列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/website/albumclasslist", method = RequestMethod.GET)
	public String getAlbumClassList(@CookieValue(value = "appid", required = false) String appid,
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
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
				List<AlbumClass> classList = albumDao.getBasicClassinfos(appid);
				model.addAttribute("classList", classList);
				return "WebsiteViews/albumclasslist";
			}
		}
	}
	
	/**
	 * @title getVoteList
	 * @description 获取账号下可关联的投票列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/website/votelist", method = RequestMethod.GET)
	public String getVoteList(@CookieValue(value = "appid", required = false) String appid,
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
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
				List<Vote> voteList = voteDao.getVoteinfoList(appid);
				model.addAttribute("voteList", voteList);
				return "WebsiteViews/votelist";
			}
		}
	}
	
	/**
	 * @title getLotteryWheelList
	 * @description 获取账号下可关联的大转盘抽奖列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/website/lottery/wheellist", method = RequestMethod.GET)
	public String getLotteryWheelList(@CookieValue(value = "appid", required = false) String appid,
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
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
				List<LotteryWheel> wheelList = wheelDao.getWheelinfoList(appid);
				model.addAttribute("wheelList", wheelList);
				return "WebsiteViews/lotterywheellist";
			}
		}
	}
	
	/**
	 * @title getBranchClassList
	 * @description 获取账号下可关联的分店类别列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/website/branchclasslist", method = RequestMethod.GET)
	public String getBranchClassList(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<BranchClass> classList = branchDao.getBasicClassinfos(user.getSid());
		model.addAttribute("classList", classList);
		return "WebsiteViews/branchclasslist";
	}
}
