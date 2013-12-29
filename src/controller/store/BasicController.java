package controller.store;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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

import elove.AppInfo;
import elove.AuthPrice;
import elove.dao.AppInfoDAO;
import elove.dao.AuthPriceDAO;
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

        return "EloveViews/showApp";
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
	    if (appInfoList.size() > 0) {
	    	appInfoList.get(0).setCharged(true);
	    	response.addCookie(new Cookie("appid", appInfoList.get(0).getAppid()));
			for (int i = 1; i < appInfoList.size(); i++) {
				appInfoList.get(i).setCharged(false);
			}
		}else {
			response.addCookie(new Cookie("appid", null));
		}
	    model.addAttribute("appInfoList", appInfoList);
	    
	    List<AuthPrice> priceList = authPriceDao.getPriceBySid(user.getSid());
	    model.addAttribute("priceList", priceList);
		
        return "EloveViews/account";
    }
	

	
	@RequestMapping(value = "/delete/app", method = RequestMethod.POST)
    public String login(Model model, @CookieValue(value = "appid", required = true) String appid) {
		
        return "EloveViews/test";
    }
}
