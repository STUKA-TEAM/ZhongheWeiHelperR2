package controller.internal;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import register.UserInfo;
import register.dao.UserInfoDAO;

/**
 * @Title: UserController
 * @Description: 商家CUSTOMER管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月20日
 */
@Controller
public class UserController {
	/**
	 * @description: 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/customer/detail", method = RequestMethod.GET)
	public String getCustomerList(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<UserInfo> infoList = userInfoDao.getCustomerInfoList();
		model.addAttribute("infoList", infoList);
		
		return "customerList";
	}
	
	
}
