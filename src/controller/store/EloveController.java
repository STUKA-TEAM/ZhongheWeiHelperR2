package controller.store;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

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
import register.dao.AuthPriceDAO;
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
	/**
	 * @description: 获取与appid相关的elove信息列表
	 * @param model
	 * @param appid
	 * @return
	 */
	@RequestMapping(value = "/elove/detail", method = RequestMethod.GET)
    public String getEloveList(Model model, HttpServletRequest request, 
    		@CookieValue(value = "appid", required = false) String appid) {
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("")) {       //需先创建app
				request.setAttribute("message", "请先创建应用！");
				request.setAttribute("jumpLink", "/store/account");
				return "forward:/store/transfer";   
			}
			else {
				ApplicationContext context = 
						new ClassPathXmlApplicationContext("All-Modules.xml");
				AuthPriceDAO authPriceDao = (AuthPriceDAO) context.getBean("AuthPriceDAO");
				EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
				((ConfigurableApplicationContext)context).close();
				
				//get elove information
				List<EloveInfo> infoList = eloveInfoDao.getEloveInfoList(appid);
				if (infoList != null) {
					for (int i = 0; i < infoList.size(); i++) {
						EloveInfo temp = infoList.get(i);
						if (temp.getExpiredTime().before(new Timestamp(System.currentTimeMillis()))) {
							temp.setIsVaild(false);
						}else {
							temp.setIsVaild(true);
						}
					}
				}				
				model.addAttribute("infoList", infoList);
				
				//get consume information
				Integer notPayNumber = eloveInfoDao.getConsumeRecord(appid);
				model.addAttribute("notPayNumber", notPayNumber);
				
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				User user = (User)auth.getPrincipal();
				BigDecimal price = authPriceDao.getPrice(user.getSid(), "elove");
				BigDecimal debt = null;
				if (notPayNumber != null && price != null) {
					debt = price.multiply(new BigDecimal(notPayNumber));
				}
				model.addAttribute("debt", debt);
				
				int sum = eloveInfoDao.getEloveNum(appid);
				BigDecimal sumConsume = null;
				if (price != null) {
					sumConsume = price.multiply(new BigDecimal(sum));
				}
				model.addAttribute("sumConsume", sumConsume);
				
				InputStream inputStream = EloveController.class.getResourceAsStream("/defaultValue.properties");
				Properties properties = new Properties();
				String phoneNum = null;
				try {
					properties.load(inputStream);
					phoneNum = (String) properties.get("eloveSupportPhone");
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				model.addAttribute("phoneNum", phoneNum);
				
				return "EloveViews/detail";
			}
		}
    }
}
