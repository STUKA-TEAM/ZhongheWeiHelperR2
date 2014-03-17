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
import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
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
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			}
			else {				
				//get elove information
				List<EloveInfo> infoList = eloveInfoDao.getEloveInfoList(appid);
				for (int i = 0; i < infoList.size(); i++) {
					EloveInfo temp = infoList.get(i);
					if (temp.getExpiredTime().before(new Timestamp(System.currentTimeMillis()))) {
						temp.setIsVaild(false);
					}else {
						temp.setIsVaild(true);
					}
				}			
				model.addAttribute("infoList", infoList);
				
				//get consume information
				Integer notPayNumber = eloveInfoDao.getConsumeRecord(appid);
				if (notPayNumber != null) {
					if (notPayNumber >= 0) {
						model.addAttribute("notPayNumber", notPayNumber);
						model.addAttribute("prePayNumber", 0);
					}else {
						model.addAttribute("notPayNumber", 0);
						model.addAttribute("prePayNumber", -notPayNumber);
					}
				}else {
					model.addAttribute("notPayNumber", -1);
					model.addAttribute("prePayNumber", -1);
				}

				BigDecimal price = authInfoDao.getPrice(user.getSid(), "elove");
				BigDecimal debt = null;
				if (notPayNumber != null && price != null) {
					if (notPayNumber > 0) {
						debt = price.multiply(new BigDecimal(notPayNumber));
					}else {
						debt = new BigDecimal(0);
					}
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
