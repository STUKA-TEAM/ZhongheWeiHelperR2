package controller.store;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import elove.EloveInfo;
import elove.dao.EloveInfoDAO;
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
	
	/**
	 * @description: 获取与appid相关的elove信息列表
	 * @param model
	 * @param appid
	 * @return
	 */
	@RequestMapping(value = "/elove/detail", method = RequestMethod.GET)
    public String getEloveList(Model model, @CookieValue(value = "appid", required = false) String appid) {
		if (appid == null) {
			return "redirect:/store/account";     //异常
		}
		else {
			if (appid == "") {
				return "forward:/store/account";   //需先创建app
			}
			else {
				ApplicationContext context = 
						new ClassPathXmlApplicationContext("All-Modules.xml");
				EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
				((ConfigurableApplicationContext)context).close();
				
				List<EloveInfo> infoList = eloveInfoDao.getEloveInfoList(appid);
				for (int i = 0; i < infoList.size(); i++) {
					EloveInfo temp = infoList.get(i);
					if (temp.getExpiredTime().before(new Timestamp(System.currentTimeMillis()))) {
						temp.setVaild(false);
					}else {
						temp.setVaild(true);
					}
				}
				model.addAttribute("infoList", infoList);
				
				return "EloveViews/eloveList";
			}
		}
    }
	

	
	
}
