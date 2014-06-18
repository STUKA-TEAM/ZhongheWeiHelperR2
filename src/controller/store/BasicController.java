package controller.store;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import lottery.dao.LotteryWheelDAO;
import menu.dao.MenuDAO;
import message.ResponseMessage;
import order.dao.DishDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import album.dao.AlbumDAO;
import article.dao.ArticleDAO;

import com.google.gson.Gson;

import register.AppInfo;
import register.AuthPrice;
import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
import register.dao.WelcomeDAO;
import elove.dao.EloveWizardDAO;
import register.UserInfo;
import register.dao.UserInfoDAO;
import security.User;
import tools.CommonValidationTools;
import vote.dao.VoteDAO;
import website.dao.WebsiteDAO;

/**
 * @Title: BasicController
 * @Description: 控制账户相关的信息
 * @Company: tuka
 * @author ben
 * @date 2014年6月12日
 */
@Controller
public class BasicController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @title: jumpHandler
	 * @description: 中间过渡界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	public String jumpHandler(Model model){
		return "CommonViews/transfer";
	}
	
	/**
	 * @description: 获取个人账户信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account")
    public String getUserInfo(Model model, HttpServletResponse response, 
    		@CookieValue(value = "appid", required = false) String appid) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		((ConfigurableApplicationContext)context).close(); 
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		UserInfo userInfo = userInfoDao.getUserInfo(user.getSid());
	    model.addAttribute("userInfo", userInfo);
	    
	    InputStream inputStream = BasicController.class.getResourceAsStream("/environment.properties");
		Properties properties = new Properties();
		String applicationPath = null;
		try {
			properties.load(inputStream);
			applicationPath = (String)properties.get("applicationPath");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		List<AppInfo> appInfoList = appInfoDao.getAppInfoBySid(user.getSid());
		if (appid != null && !appid.equals("") && appInfoDao.checkAppExistsByUser(
				user.getSid(), appid) == 1) {                                           //1.不为空 2.确实是appid 
			for (int i = 0; i < appInfoList.size(); i++) {
				AppInfo appInfo = appInfoList.get(i);
				appInfo.setUrl(applicationPath + "zhongheapi/weixin?appid=" + appInfo.getAppid());
				if (appInfo.getAppid().equals(appid)) {
					appInfo.setIsCharged(true);
				}else {
					appInfo.setIsCharged(false);
				}
			}
		}else {
			if (appInfoList.size() > 0) {
	    		AppInfo appInfo = appInfoList.get(0);
	    		appInfo.setIsCharged(true);
	    		appInfo.setUrl(applicationPath + "zhongheapi/weixin?appid=" + appInfo.getAppid());
	    		
	    		Cookie cookie = new Cookie("appid", appInfoList.get(0).getAppid());
	    		cookie.setPath("/");
		    	response.addCookie(cookie);
				for (int i = 1; i < appInfoList.size(); i++) {
					AppInfo temp = appInfoList.get(i);
					temp.setIsCharged(false);
					temp.setUrl(applicationPath + "zhongheapi/weixin?appid=" + temp.getAppid());
				}
			}else {
				Cookie cookie = new Cookie("appid", null);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}	    
	    model.addAttribute("appInfoList", appInfoList);
	    
	    List<AuthPrice> priceList = authInfoDao.getPriceBySid(user.getSid());
	    model.addAttribute("priceList", priceList);
		
        return "AccountViews/account";
    }
	
	/**
	 * @description: 更新用户信息
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userinfo/update", method = RequestMethod.POST)
	@ResponseBody
    public String updateUserInfo(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		UserInfo userInfo = gson.fromJson(json, UserInfo.class);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		userInfo.setSid(user.getSid());	
		
		ResponseMessage message = new ResponseMessage();
		if (!CommonValidationTools.checkUserInfoForUpdate(userInfo)) {
			message.setStatus(false);
			message.setMessage("关键信息未填写或填写有误！");
		}else{
			if(userInfo.getCorpMoreInfoLink() != null){   //validate website format
				String webSite = userInfo.getCorpMoreInfoLink();
				if (!webSite.startsWith("http")) {
					webSite = "http://".concat(webSite);
					userInfo.setCorpMoreInfoLink(webSite);
				}
			}
			
			int result = userInfoDao.updateUserInfo(userInfo);						
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("用户信息修改成功！");
			}else{
				message.setStatus(false);
				message.setMessage("用户信息修改失败！");
				System.out.println("Error " + result);
			}		
		}	
		
		String response = gson.toJson(message);				
        return response;
    }
	
	/**
	 * @description: 关联新的公众账号
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/insert", method = RequestMethod.POST)
	@ResponseBody
    public String insertApp(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		int appUpperLimit = 0;
		try {
			appUpperLimit = appInfoDao.getAppUpperLimit(user.getSid());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		int actualAppNum = appInfoDao.getAppNumBySid(user.getSid());
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		if (actualAppNum >= appUpperLimit) {
			message.setStatus(false);
			message.setMessage("app创建数目已达到上限！");
		}else {
			AppInfo appInfo = gson.fromJson(json, AppInfo.class);
			appInfo.setAppid(generateRandomAppid());
			appInfo.setWechatToken(generateWechatToken());
			appInfo.setSid(user.getSid());
			List<Integer> authidList = appInfoDao.getAuthidList(user.getSid());
			appInfo.setAuthidList(authidList);
			
			if (!CommonValidationTools.checkAppInfo(appInfo)) {
				message.setStatus(false);
				message.setMessage("app信息未填写完整！");
			}else {
				int result = appInfoDao.insertAppInfo(appInfo);
				
				if (result > 0) {
					message.setStatus(true);
					message.setMessage("新账号创建成功！");
				}else{
					message.setStatus(false);
					message.setMessage("Error " + result);
				}	
			}			
		}
		
		String response = gson.toJson(message);
        return response;
    }
	
	/**
	 * @description: 删除已创建的app 
	 * @param model
	 * @param appid
	 * @return
	 */
	@RequestMapping(value = "/app/delete", method = RequestMethod.POST)
	@ResponseBody
    public String deleteApp(@RequestParam(value = "appid", required = true) String 
    		appid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
		WelcomeDAO welcomeDao = (WelcomeDAO) context.getBean("WelcomeDAO");
		MenuDAO menuDao = (MenuDAO) context.getBean("MenuDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		if (appInfoDao.getAppNumByAppid(appid) == 0) {
			message.setStatus(false);
			message.setMessage("此应用不存在！");
		}else {
			Integer notPayNum = eloveWizardDao.getConsumeRecord(appid);
			if (notPayNum == null || notPayNum.intValue() > 0) {
				message.setStatus(false);
				message.setMessage("当前应用账单异常或未结清！");
			}else{
				//application basic and relationship
				appInfoDao.deleteAppAuthRelation(appid);
				appInfoDao.deleteUserAppRelation(appid);
				appInfoDao.deleteAppInfo(appid);
				
				//elove
				eloveWizardDao.deleteConsumeRecord(appid);		
				List<Integer> eloveidList = eloveWizardDao.getEloveidList(appid);
				for (int i = 0; i < eloveidList.size(); i++) {
					int eloveid = eloveidList.get(i);
					eloveWizardDao.deleteImage(eloveid);
					eloveWizardDao.deleteVideo(eloveid);
					eloveWizardDao.deleteJoin(eloveid);
					eloveWizardDao.deleteMessage(eloveid);
					eloveWizardDao.deleteElove(eloveid);
				}
				
				//website
				Integer websiteid = websiteDao.getWebsiteidByAppid(appid);
				if (websiteid != null) {
					websiteDao.deleteWebsite(websiteid);
				}
				
				//article
				articleDao.deleteArticle(appid);
				
				//articleclass
				articleDao.deleteArticleClass(appid);
				
				//welcome
				welcomeDao.deleteWelcomeContent(appid);

				//menu
				menuDao.deleteMenu(appid);

				//album
				albumDao.deleteAlbum(appid);

				//albumclass
				albumDao.deleteAlbumClass(appid);
				
				//vote
				voteDao.deleteVote(appid);
				
				//lotterywheel
				wheelDao.deleteWheel(appid);
				
				//dish
				dishDao.deleteDish(appid);
				
				//dishclass
				dishDao.deleteDishClass(appid);
				
				message.setStatus(true);
				message.setMessage("删除成功！");
			}
		}
		String response = gson.toJson(message);
        return response;
    }
	
	/**
	 * @title editFollowLink
	 * @description 编辑关注链接（第一步）
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/followlink/edit", method = RequestMethod.GET)
	public String editFollowLink(@RequestParam(value = "appid", required = true) String 
			appid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		String followLink = appInfoDao.getFollowLink(appid);
		model.addAttribute("followLink", followLink);
		model.addAttribute("appid", appid);
		return "AccountViews/updateFollowLink";
	}
	
	/**
	 * @title updateFollowLink
	 * @description 编辑关注链接（第二步）
	 * @param appid
	 * @param followLink
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/followlink/update", method = RequestMethod.POST)
	public String updateFollowLink(@RequestParam(value = "appid", required = true) String 
			appid, @RequestParam(value = "follow", required = true) String followLink, 
			Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = appInfoDao.updateFollowLink(appid, followLink);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("操作成功！");			
		} else {
			message.setStatus(false);
			message.setMessage("操作失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
    /**
     * @title: generateRandomAppid
     * @description: 生成appid
     * @return
     */
    private String generateRandomAppid() {    	
        String randomAppid = UUID.randomUUID().toString().replace("-", "");
        return randomAppid;
    }
    
    /**
     * @title generateWechatToken
     * @description 随机生成验证token
     * @return
     */
    private String generateWechatToken() {
    	Random random = new Random();
    	int i = random.nextInt(1000000);
    	return new DecimalFormat("000000").format(i);
    }
}
