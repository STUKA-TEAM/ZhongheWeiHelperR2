package controller.security;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import register.dao.AuthPriceDAO;
import register.UserInfo;
import register.dao.UserInfoDAO;

/**
 * @Title: RegisterController
 * @Description: 控制注册流程
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月08日
 */
@Controller
public class RegisterController {
	@Autowired
	private RegisterValidation registerValidation;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @Description: 返回注册视图
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistration(Model model){
		UserInfo userInfo = new UserInfo();
		model.addAttribute("userInfo", userInfo);
		return "register";
	}
	
	/**
	 * @Description: 处理注册流程
	 * @param userInfo
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegister(UserInfo userInfo, BindingResult result, Model model){
		registerValidation.validate(userInfo, result);
		model.addAttribute("userInfo", userInfo);
		
		if(result.hasErrors()){		
			return "register";
		}
		else {
			ApplicationContext context = 
					new ClassPathXmlApplicationContext("All-Modules.xml");
			UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
			AuthPriceDAO authPriceDao = (AuthPriceDAO) context.getBean("AuthPriceDAO");
			((ConfigurableApplicationContext)context).close();
			
			userInfo.setRoleid(1);   //default 'ROLE_USER'
			String encoded = passwordEncoder.encode(userInfo.getPassword());
			userInfo.setPassword(encoded);
			Timestamp current = new Timestamp(System.currentTimeMillis());
			userInfo.setCreateDate(current);
			if(userInfo.getCorpMoreInfoLink() != null){   //validate website format
				String webSite = userInfo.getCorpMoreInfoLink();
				if (!webSite.startsWith("http")) {
					webSite = "http://".concat(webSite);
					userInfo.setCorpMoreInfoLink(webSite);
				}
			}
			
			InputStream inputStream = RegisterController.class.getResourceAsStream("/defaultValue.properties");
			Properties properties = new Properties();
			BigDecimal elovePrice = null;
			try {
				properties.load(inputStream);
				elovePrice = new BigDecimal(Double.parseDouble((String)properties.get("defaultElovePrice")));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(elovePrice != null){
				int sid = userInfoDao.insertUserInfo(userInfo);
				authPriceDao.insertPrice(sid, "elove", elovePrice);
				
				return "registerSuccess";
			}
			else {
				System.out.println("elovePrice配置信息丢失");
				return "register";
			}
		}
	}
}
