package controller.customer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import register.dao.UserInfoDAO;

/**
 * @Title: MessageController
 * @Description: 短信催款管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月23日
 */
@Controller
public class MessageController {
	/**
	 * @description: 短信确认收款
	 * @param model
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/message/ensure", method = RequestMethod.GET)
	@ResponseBody
	public String ensureMessage(Model model, @RequestParam(value = "sid", required = true) int sid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		String message = "";
		String contact = userInfoDao.getContactInfo(sid);
		if (contact != null) {
			
		}
		
		String response = "";
		return response;
	}
	
	/**
	 * @description: 短信提醒
	 * @param model
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/message/alert", method = RequestMethod.GET)
	@ResponseBody
	public String alertMessage(Model model, @RequestParam(value = "sid", required = true) int sid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		String message = "";
		String contact = userInfoDao.getContactInfo(sid);
		if (contact != null) {
			
		}
		
		String response = "";
		return response;
	}
	
	@RequestMapping(value = "/elove/bill", method = RequestMethod.GET)
	public String getEloveDetail(Model model, @RequestParam(value = "sid", required = true) int sid){
		return "";
	}
}
