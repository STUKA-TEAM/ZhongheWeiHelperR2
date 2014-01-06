package controller.security;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import message.UserMessage;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

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
	@ResponseBody
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
			
			int sid = userInfoDao.insertUserInfo(userInfo);
			UserMessage message = new UserMessage();
			if(sid > 0){
				InputStream inputStream = RegisterController.class.getResourceAsStream("/defaultValue.properties");
				Properties properties = new Properties();
				BigDecimal elovePrice = null;
				try {
					properties.load(inputStream);
					elovePrice = new BigDecimal(Double.parseDouble((String)properties.get("defaultElovePrice")));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				int recordid = 0;
				try {
					recordid = authPriceDao.insertPrice(sid, "elove", elovePrice);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				if (recordid > 0 ) {
					message.setStatus(true);
					message.setMessage("用户注册成功！");
					message.setUsername(userInfo.getUsername());
				}else {
					userInfoDao.deleteUserInfo(sid);
					message.setStatus(false);
					message.setMessage("用户注册失败！系统错误");
				}				
			}else {
				message.setStatus(false);
				message.setMessage("用户注册失败！请检查注册信息");
			}
			
			Gson gson = new Gson();
			String response = gson.toJson(message);		
			return response;
		}
	}
}
