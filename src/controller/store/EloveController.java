package controller.store;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		UserInfoDAO info = (UserInfoDAO) context.getBean("RegisterInfoDAO");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
        return "EloveViews/test";
    }
	
	
}
