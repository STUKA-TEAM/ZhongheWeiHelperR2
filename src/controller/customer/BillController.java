package controller.customer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import register.AppInfo;
import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
import register.dao.UserInfoDAO;
import elove.EloveInfo;
import elove.dao.EloveInfoDAO;

/**
 * @Title: BillController
 * @Description: 账单详情查看管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月23日
 */
@Controller
public class BillController {
	/**
	 * @description: 查询账单详情
	 * @param model
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/elove/bill", method = RequestMethod.GET)
	public String getEloveDetail(Model model, @RequestParam(value = "key", required = true) String key){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		String username = decodeKey(key);
		Integer sid = null;
		if (username != null) {
			sid = userInfoDao.getSidByUsername(username);
		}
		
		if (sid != null) {
			Map<String, List<EloveInfo>> infoMap = new HashMap<String, List<EloveInfo>>();
			List<AppInfo> appInfoList = appInfoDao.getBasicAppInfo(sid);
			int notPaySum = 0;
			for (int i = 0; i < appInfoList.size(); i++) {
				AppInfo temp = appInfoList.get(i);
				String appid = temp.getAppid();
				String wechatName = temp.getWechatName();
	
				Integer notPay = eloveInfoDao.getConsumeRecord(appid);
				if (notPay != null && notPay > 0) {
					notPaySum += notPay;
					List<EloveInfo> infoList = eloveInfoDao.getBillEloveInfoList(appid, notPay);
					infoMap.put(wechatName, infoList);
				}
			}
			
			BigDecimal elovePrice = authInfoDao.getPrice(sid, "elove");
			BigDecimal notPayMoney = null;
			if (elovePrice != null) {
				notPayMoney = elovePrice.multiply(new BigDecimal(notPaySum));
			}
			
			InputStream inputStream = BillController.class.getResourceAsStream("/defaultValue.properties");
			Properties properties = new Properties();
			String phoneNum = null;
			try {
				properties.load(inputStream);
				phoneNum = (String) properties.get("eloveSupportPhone");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			model.addAttribute("eloveUsedMap", infoMap);
			model.addAttribute("notPaySum", notPaySum);
			model.addAttribute("elovePrice", elovePrice);
			model.addAttribute("notPayMoney", notPayMoney);
			model.addAttribute("phoneNum", phoneNum);
			return "EloveViews/bill";
			
		}else {
			return "EloveViews/exception";
		}
	}
	
	/**
	 * @title: decodeKey
	 * @description: 检验Key,并对Key解码得到username
	 * @param key
	 * @return
	 */
	private String decodeKey(String key){
		char[] source = key.toCharArray();
		if (source.length < 4 || source.length > 20) {
			return null;
		}
		for (int i = 0; i < source.length; i++) {
			char temp = source[i];
			if (temp < '0' || temp > 'z' || (temp < 'a' && temp > '9')) {
				return null;
			}
		}		
		for (int i = 0; i < source.length; i++) {
			int offset = 2 * i + 1;
			while (offset > 0) {
				offset --;
				source[i] = move(source[i]);
			}
		}
		return source.toString();
	}
	
	/**
	 * @title: move
	 * @description: 将目标字符右移一位，并判断修正
	 * @param target
	 * @return
	 */
	private char move(char target){
		char result = (char) (target + 1);
		if (result > 'z') {
			result = '0';
		}
		if (result < 'a' && result > '9') {
			result = 'a';
		}
		return result;
	}
}
