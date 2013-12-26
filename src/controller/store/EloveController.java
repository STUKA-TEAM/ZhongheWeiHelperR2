package controller.store;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import elove.dao.AppInfoDAO;
import register.UserInfo;
import register.dao.UserInfoDAO;
import security.User;

/**
 * @Title: EloveController
 * @Description: 控制elove模块
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月24日
 */
@Controller
public class EloveController {	
	@RequestMapping(value = "/elove/test", method = RequestMethod.GET)
    public String login(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		System.out.println(user.getSid());
		System.out.println(auth.getName());
        return "EloveViews/test";
    }
	
	/**
	 * @description: 获取账户信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/elove/account", method = RequestMethod.GET)
    public String getUserInfo(Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		model.addAttribute("username", user.getUsername());
		
		UserInfo userInfo = userInfoDao.getUserInfo(user.getSid());
	    model.addAttribute("userInfo", userInfo);
        
	    
		
        return "EloveViews/account";
    }
	
	
}
