package controller.store;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import message.ResponseMessage;

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

import com.google.gson.Gson;

import register.AppInfo;
import register.AuthPrice;
import register.dao.AppInfoDAO;
import register.dao.AuthPriceDAO;
import elove.dao.EloveWizardDAO;
import register.UserInfo;
import register.dao.UserInfoDAO;
import security.User;
import tools.CommonValidationTools;

/**
 * @Title: BasicController
 * @Description: 控制账户相关的信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
@Controller
public class BasicController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @description: 每个界面都要获取的app信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/basic", method = RequestMethod.GET)
    public String getAppInfo(Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<AppInfo> appInfoList = appInfoDao.getAppInfoBySid(user.getSid());
		model.addAttribute("appInfoList", appInfoList);

        return "AccountViews/showApp";
    }
	
	/**
	 * @description: 获取个人账户信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
    public String getUserInfo(Model model, HttpServletResponse response, 
    		@CookieValue(value = "appid", required = false) String appid) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AuthPriceDAO authPriceDao = (AuthPriceDAO) context.getBean("AuthPriceDAO");
		((ConfigurableApplicationContext)context).close(); 
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		UserInfo userInfo = userInfoDao.getUserInfo(user.getSid());
	    model.addAttribute("userInfo", userInfo);
        
	    List<AppInfo> appInfoList = appInfoDao.getAppInfoBySid(user.getSid());
	    if (appInfoList != null) {
	    	InputStream inputStream = BasicController.class.getResourceAsStream("/environment.properties");
			Properties properties = new Properties();
			String applicationPath = null;
			try {
				properties.load(inputStream);
				applicationPath = (String)properties.get("applicationPath");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if (appid != null && !appid.equals("") && appInfoDao.getAppNumByAppid(appid) == 1) {  //1.不为空 2.确实是appid 
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
		}	    
	    model.addAttribute("appInfoList", appInfoList);
	    
	    List<AuthPrice> priceList = authPriceDao.getPriceBySid(user.getSid());
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
		
		InputStream inputStream = BasicController.class.getResourceAsStream("/defaultValue.properties");
		Properties properties = new Properties();
		int appUpperLimit = 0;
		try {
			properties.load(inputStream);
			appUpperLimit = Integer.parseInt((String)properties.get("appUpperLimit"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		int actualAppNum = appInfoDao.getAppNumBySid(user.getSid());
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		if (actualAppNum >= appUpperLimit) {
			message.setStatus(false);
			message.setMessage("app创建数目已达到上限！");
		}else {
			AppInfo appInfo = gson.fromJson(json, AppInfo.class);
			appInfo.setAppid(generateRandomAppid());
			appInfo.setSid(user.getSid());
			List<String> authNameList = new ArrayList<String>();
			authNameList.add("elove");
			appInfo.setAuthNameList(authNameList);
			
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
    public String login(Model model, @RequestParam(value = "appid", required = true) String appid) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
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
				appInfoDao.deleteAppAuthRelation(appid);
				appInfoDao.deleteUserAppRelation(appid);
				appInfoDao.deleteAppInfo(appid);
				eloveWizardDao.deleteConsumeRecord(appid);
				
				List<Integer> eloveidList = eloveWizardDao.getEloveidList(appid);
				if (eloveidList != null) {
					for (int i = 0; i < eloveidList.size(); i++) {
						int eloveid = eloveidList.get(i);
						eloveWizardDao.deleteImage(eloveid);
						eloveWizardDao.deleteVideo(eloveid);
						eloveWizardDao.deleteJoin(eloveid);
						eloveWizardDao.deleteMessage(eloveid);
						eloveWizardDao.deleteElove(eloveid);
					}
			    }
				
				message.setStatus(true);
				message.setMessage("删除成功！");
			}
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
}
