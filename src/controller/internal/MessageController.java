package controller.internal;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import message.ResponseMessage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import elove.dao.EloveInfoDAO;
import register.dao.AppInfoDAO;
import register.dao.AuthInfoDAO;
import register.dao.UserInfoDAO;

/**
 * @Title: MessageController
 * @Description: 短信催款管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月23日
 */
@Controller
public class MessageController {
	/**
	 * @description: 短信确认收款
	 * @param model
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/message/elove/ensure", method = RequestMethod.GET)
	@ResponseBody
	public String ensureMessage(Model model, @RequestParam(value = "sid", required = true) int sid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		String content = "尊敬的Elove用户：您当月的Elove款项已经付清，谢谢您的使用!";
		String contact = userInfoDao.getContactInfo(sid);
		if (contact != null) {
			int result = 0;
			try {
				result = sendMessage(content, contact);	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("确认收款短信" + result + "条已成功发送！");
			}else {
				message.setStatus(false);
				message.setMessage("短信发送失败！返回代码： " + result);
			}
		}else {
			message.setStatus(false);
			message.setMessage("未找到此商家联系方式信息！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @description: 个性化短信提醒
	 * @param model
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/message/elove/alert", method = RequestMethod.GET)
	@ResponseBody
	public String alertMessage(Model model, @RequestParam(value = "sid", required = true) int sid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		AuthInfoDAO authInfoDao = (AuthInfoDAO) context.getBean("AuthInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		List<String> appidList = appInfoDao.getAppidList(sid);
		int notPaySum = 0;
		for (int i = 0; i < appidList.size(); i++) {
			String appid = appidList.get(i);	
			Integer notPay = eloveInfoDao.getConsumeRecord(appid);
			if (notPay != null && notPay > 0) {
				notPaySum += notPay;
			}
		}
		if (notPaySum > 0) {
			BigDecimal elovePrice = authInfoDao.getPrice(sid, "elove");
			if (elovePrice != null) {
				BigDecimal notPayMoney = elovePrice.multiply(new BigDecimal(notPaySum));
				String username = userInfoDao.getUsername(sid);
				String contact = userInfoDao.getContactInfo(sid);
				if (contact != null && username != null) {
					username = encodeUsername(username);
					String content = "尊敬的Elove客户，您当月使用了" + notPaySum + "套Elove，每套" 
						+ elovePrice + "元，未付款金额为" + notPayMoney + "元，为保证账户正常运行，"
						+ "请及时缴款。http://tukacorp.com/a.htm?p=" + username + " 点击查看详情";
					
					int result = 0;
					try {
						result = sendMessage(content, contact);	
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("催缴款短信" + result + "条已成功发送！");
					}else {
						message.setStatus(false);
						message.setMessage("短信发送失败！返回代码： " + result);
					}
					
				}else {
					message.setStatus(false);
					message.setMessage("未找到此商家联系方式信息以及用户名信息！");
				}
				
			}else {
				message.setStatus(false);
				message.setMessage("未找到此商家elove单价！");
			}
			
		}else {
			message.setStatus(false);
			message.setMessage("此商家elove费用已结清！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	@RequestMapping(value = "/message/elove/myalert", method = RequestMethod.GET)
	@ResponseBody
	public String myAlertMessage(Model model, @RequestParam(value = "sid", required = true) int sid,
			@RequestParam(value = "content", required = true) String content){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		String contact = userInfoDao.getContactInfo(sid);
		if (contact != null && getLength(content) <= 300) {
			int result = 0;
			try {
				result = sendMessage(content, contact);	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("自定义短信" + result + "条已成功发送！");
			}else {
				message.setStatus(false);
				message.setMessage("短信发送失败！返回代码： " + result);
			}
		}else {
			message.setStatus(false);
			message.setMessage("未找到此商家联系方式信息或发送内容长度过长！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title: sendMessage
	 * @description: 发送消息
	 * @param content 消息内容
	 * @param contact 联系方式
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private int sendMessage(String content, String contact) throws HttpException, IOException{
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;"
				+ "charset=utf8");                                                          //在头文件中设置转码
		
		InputStream inputStream = MessageController.class.getResourceAsStream("/environment.properties");
		Properties properties = new Properties();
		String messageKey = null;
		try {
			properties.load(inputStream);
			messageKey = (String) properties.get("messageKey");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		NameValuePair[] data ={ new NameValuePair("Uid", "tukacorp"),
		                        new NameValuePair("Key", messageKey),
		                        new NameValuePair("smsMob", contact),
		                        new NameValuePair("smsText",content) };
		post.setRequestBody(data);
		client.executeMethod(post);
		
		String result = new String(post.getResponseBodyAsString().getBytes("utf8")); 
		int status = Integer.parseInt(result);
		
		post.releaseConnection();
		return status;
	}
	
	/**
	 * @title: encodeUsername
	 * @description: 对用户名username编码
	 * @param username
	 * @return
	 */
	private String encodeUsername(String username){
		char[] source = username.toCharArray();
		for (int i = 0; i < source.length; i++) {
			int offset = 2 * i + 1;
			while (offset > 0) {
				offset --;
				source[i] = move(source[i]);
			}
		}
		return String.valueOf(source);
	}
	
	/**
	 * @title: move
	 * @description: 将目标字符左移一位，并判断修正
	 * @param target
	 * @return
	 */
	private char move(char target){
		char result = (char) (target - 1);
		if (result < '0') {
			result = 'z';
		}
		if (result < 'a' && result > '9') {
			result = '9';
		}
		return result;
	}
	
	/**
	 * @Title: getLength
	 * @Description: detect the length of one sentence
	 * @param text
	 * @return int --the length
	 */
    private int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }
}
