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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import elove.AppInfo;
import elove.AuthPrice;
import elove.dao.AppInfoDAO;
import elove.dao.AuthPriceDAO;
import elove.dao.EloveWizardDAO;
import register.UserInfo;
import register.dao.UserInfoDAO;
import security.User;

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
    public String getUserInfo(Model model, HttpServletResponse response) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AuthPriceDAO authPriceDao = (AuthPriceDAO) context.getBean("AuthPriceDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		model.addAttribute("username", user.getUsername());
		
		UserInfo userInfo = userInfoDao.getUserInfo(user.getSid());
	    model.addAttribute("userInfo", userInfo);
        
	    List<AppInfo> appInfoList = appInfoDao.getAppInfoBySid(user.getSid());
	    if (appInfoList != null) {
	    	if (appInfoList.size() > 0) {
		    	appInfoList.get(0).setCharged(true);
		    	response.addCookie(new Cookie("appid", appInfoList.get(0).getAppid()));
				for (int i = 1; i < appInfoList.size(); i++) {
					appInfoList.get(i).setCharged(false);
				}
			}else {
				response.addCookie(new Cookie("appid", null));
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
		
		String encoded = passwordEncoder.encode(userInfo.getPassword());
		userInfo.setPassword(encoded);
		
		//图片不一致时需要处理
		
		int result = userInfoDao.updateUserInfo(userInfo);
		ResponseMessage message = new ResponseMessage();
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("修改成功！");
		}else{
			message.setStatus(false);
			message.setMessage("修改保存失败！");
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
			
			int result = appInfoDao.insertAppInfo(appInfo);
			
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("新账号创建成功！");
			}else{
				message.setStatus(false);
				message.setMessage("Error " + result);
				
				if (result <= -2) {
					appInfoDao.deleteAppAuthRelation(appInfo.getAppid());
				}
				if (result < -1) {
					appInfoDao.deleteUserAppRelation(appInfo.getAppid());
				}
				if (result < 0) {
					appInfoDao.deleteAppInfo(appInfo.getAppid());
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
    public String login(Model model, @CookieValue(value = "appid", required = true) String appid) {
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		if (appid == null) {                      //异常
			message.setStatus(false);
			message.setMessage("请重新登录！");
		}
		else {
			if (appid == "") {                    //需先创建app
				message.setStatus(false);
				message.setMessage("请先创建app!");
			}
			else {
				ApplicationContext context = 
						new ClassPathXmlApplicationContext("All-Modules.xml");
				AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
				EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
				((ConfigurableApplicationContext)context).close();
				
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
							
							//删除图片、视频资源
							
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
