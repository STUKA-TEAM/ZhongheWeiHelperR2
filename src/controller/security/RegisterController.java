package controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import register.UserInfo;

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
	
	public void setRegisterValidation(
			RegisterValidation registerValidation){
		this.registerValidation = registerValidation;
	}
	
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
		}else {
			System.out.println(userInfo.getPhone());
			return "registersuccess";
		}
	}
}
