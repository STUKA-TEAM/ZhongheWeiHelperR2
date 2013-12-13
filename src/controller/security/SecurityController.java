package controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
