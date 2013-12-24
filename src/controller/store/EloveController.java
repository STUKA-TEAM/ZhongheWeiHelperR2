package controller.store;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
