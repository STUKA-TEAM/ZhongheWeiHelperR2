package controller.internal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import elove.dao.EloveInfoDAO;
import register.UserInfo;
import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
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
	 * @description: 获取商家CUSTOMER的信息列表
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
	
	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public String editCustomerInfo(Model model, @RequestParam(value = "sid", required = true) int sid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		//website
		Timestamp websiteExpired = authInfoDao.getExpiredTime(sid, "website");
		BigDecimal websitePrice = authInfoDao.getPrice(sid, "website");
		model.addAttribute("websiteExpired", websiteExpired);
		model.addAttribute("websitePrice", websitePrice);
		
		//elove
		Timestamp eloveExpired = authInfoDao.getExpiredTime(sid, "elove");
		BigDecimal elovePrice = authInfoDao.getPrice(sid, "elove");
		List<String> appidList = appInfoDao.getAppidList(sid);
		int consumedSum = 0;
		int notPaySum = 0;
		for (int i = 0; i < appidList.size(); i++) {
			String appid = appidList.get(i);
			
			int consumed = eloveInfoDao.getEloveNum(appid);
			consumedSum += consumed;
			
			Integer notPay = eloveInfoDao.getConsumeRecord(appid);
			if (notPay != null) {
				notPaySum += notPay;
			}
		}	
		BigDecimal consumedMoney = elovePrice.multiply(new BigDecimal(consumedSum));
		BigDecimal notPayMoney = elovePrice.multiply(new BigDecimal(notPaySum));
		model.addAttribute("eloveExpired", eloveExpired);
		model.addAttribute("elovePrice", elovePrice);
		
		return "customerInfo";
	}
}
