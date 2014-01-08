package tools;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import elove.EloveWizard;
import register.AppInfo;
import register.UserInfo;
import register.dao.UserInfoDAO;


public class CommonValidationTools {
	public static boolean checkEmail(String email){
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
		Pattern regex = Pattern.compile(check);  
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * @title: checkPhone
	 * @description: currently for cellphone
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone){
		String check = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(phone);		 
		return matcher.matches();
	}
	
	public static boolean checkPassword(String original, String again){
		return original.equals(again);
	}
	
	public static boolean checkUsername(String username){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		int count = userInfoDao.getUserCount(username);
		return count == 0;
	}
	
	public static boolean checkLocation(BigDecimal lng, BigDecimal lat){
		if (lng == null || lat == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title: checkUserInfoForUpdate
	 * @description: 检验用户信息编辑后上传的数据
	 * @param userInfo
	 * @return
	 */
	public static boolean checkUserInfoForUpdate(UserInfo userInfo){
		int sid = userInfo.getSid();
		String storeName = userInfo.getStoreName();
		String cellPhone = userInfo.getCellPhone();
		String address = userInfo.getAddress();
		BigDecimal lng = userInfo.getLng();
		BigDecimal lat = userInfo.getLat();
		
		if (sid <= 0) {
			return false;
		}
		if (storeName == null) {
			return false;
		}
		if (cellPhone == null || !checkPhone(cellPhone)) {
			return false;
		}
		if (address == null) {
			return false;
		}
		if (!checkLocation(lng, lat)) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title: checkAppInfo
	 * @description: 检验新建app信息
	 * @param appInfo
	 * @return
	 */
	public static boolean checkAppInfo(AppInfo appInfo){
		String wechatToken = appInfo.getWechatToken();
		String wechatName = appInfo.getWechatName();
		String wechatOriginalId = appInfo.getWechatOriginalId();
		String wechatNumber = appInfo.getWechatNumber();
		String address = appInfo.getAddress();
		String industry = appInfo.getIndustry();
		
		if (wechatToken == null) {
			return false;
		}
		if (wechatName == null) {
			return false;
		}
		if (wechatOriginalId == null) {
			return false;
		}
		if (wechatNumber == null) {
			return false;
		}
		if (address == null) {
			return false;
		}
		if (industry == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title: checkEloveWizard
	 * @description: 检验EloveWizard信息
	 * @param eloveWizard
	 * @return
	 */
	public static boolean checkEloveWizard(EloveWizard eloveWizard){
		return true;
	}
}
