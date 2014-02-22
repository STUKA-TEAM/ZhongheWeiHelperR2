package controller.security;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import register.Authority;
import register.UserInfo;
import register.dao.AuthInfoDAO;
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
	@Autowired
    private AuthenticationManager authenticationManager;
	
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
	public String processRegister(UserInfo userInfo, BindingResult result, HttpServletRequest request, Model model){
		registerValidation.validate(userInfo, result);
		model.addAttribute("userInfo", userInfo);
		
		if(result.hasErrors()){		
			return "register";
		}
		else {
			ApplicationContext context = 
					new ClassPathXmlApplicationContext("All-Modules.xml");
			UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
			AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
			((ConfigurableApplicationContext)context).close();
			
			String original = userInfo.getPassword();
			userInfo.setPassword(passwordEncoder.encode(original));
			Timestamp current = new Timestamp(System.currentTimeMillis());
			userInfo.setCreateDate(current);
			if(userInfo.getCorpMoreInfoLink() != null){   //validate website format
				String webSite = userInfo.getCorpMoreInfoLink();
				if (!webSite.startsWith("http")) {
					webSite = "http://".concat(webSite);
					userInfo.setCorpMoreInfoLink(webSite);
				}
			}						
			userInfo.setRoleid(1);   //default 'CUSTOMER'
			int sid = userInfoDao.insertUserInfo(userInfo);
			
			InputStream inputStream = RegisterController.class.getResourceAsStream("/defaultValue.properties");
			Properties properties = new Properties();
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			long lifeCycle = 365;
			try {
				lifeCycle = Long.parseLong((String)properties.get("authorityLifeCycleByDay"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			Timestamp expiredTime = new Timestamp(System.currentTimeMillis() + lifeCycle * 
					24 * 60 * 60 * 1000);
			
			BigDecimal price = null;			
			List<Authority> authorities = authInfoDao.getAllAuthorities();
			for (int i = 0; i < authorities.size(); i++) {
				Authority authority = authorities.get(i);
				authInfoDao.insertCAR(sid, authority.getAuthid(), expiredTime);
				try {
					price = new BigDecimal(Double.parseDouble((String)properties.get(
							authority.getAuthPinyin() + "Price")));
				} catch (Exception e) {
					price = new BigDecimal(Double.parseDouble((String)properties.get("defaultPrice")));   
					System.out.println(e.getMessage());
				}					
				authInfoDao.insertPrice(sid, authority.getAuthid(), price);
			}
			
			int appUpperLimit = 0;
			try {
				appUpperLimit = Integer.parseInt((String)properties.get("appUpperLimit"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			userInfoDao.insertAppUpperLimit(sid, appUpperLimit);
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
	                userInfo.getUsername(), original);
			try{
				token.setDetails(new WebAuthenticationDetails(request));
		        Authentication authenticatedUser = authenticationManager.authenticate(token);
		        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
		        		SecurityContextHolder.getContext());
			 }
	        catch( AuthenticationException e ){
	            System.out.println("Authentication failed: " + e.getMessage());
	            return "register";
	        }
			
			return "redirect:../store/account";
		}
	}
}
