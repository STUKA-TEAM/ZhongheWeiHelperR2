package controller.security;

import java.util.Collection;

import message.ResponseMessage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import security.User;

/**
 * @Title: SecurityController
 * @Description: 控制用户登录、退出，访问权限
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月08日
 */
@Controller
public class SecurityController {
	/**
	 * @Description: 用户登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

	/**
	 * @Description: 用户退出登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        return "logout";
    }
	
	/**
	 * @Description: 用户登录被拒绝
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public String loginerror(Model model) {
        model.addAttribute("error", "true");
        return "denied";
    }
	
	/**
	 * @description: 用户成功登陆
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/success/login", method = RequestMethod.GET)
	@ResponseBody
	public String loginSuccess(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
        Gson gson = new Gson();
        ResponseMessage message = new ResponseMessage();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
        	switch (grantedAuthority.getAuthority()) {
			case "CUSTOMER":
				message.setStatus(true);
	        	message.setMessage("store/account");
				break;
			case "ADMINISTRATOR":
				message.setStatus(true);
	        	message.setMessage("internal/customer/detail");
	        	break;
			case "BRANCHREST":
				message.setStatus(true);
				message.setMessage("branch/restaurant/dish/list?appid='initial'&classid=0");
				break;
			default:
				message.setStatus(false);
	        	message.setMessage("用户角色异常！");
				break;
			}
        }
        String response = gson.toJson(message);
        return response;
	}
}
