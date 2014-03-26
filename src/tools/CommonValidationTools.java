package tools;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import menu.Menu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import album.Album;
import album.AlbumClass;
import article.Article;
import article.ArticleClass;
import elove.EloveNotpay;
import elove.EloveWizard;
import elove.dao.EloveInfoDAO;
import elove.dao.EloveWizardDAO;
import register.AppInfo;
import register.AuthInfo;
import register.UserInfo;
import register.Welcome;
import register.dao.InviteDAO;
import register.dao.UserInfoDAO;
import vote.Vote;
import vote.VoteContent;
import website.Website;
import website.dao.WebsiteDAO;


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
		String check = "^(13[1,2,3,4,5,6,7,8,9]|15[0,1,2,3,4,5,6,,7,8,9]|188|187)\\d{8}$";
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
	
	/**
	 * @title checkInviteCode
	 * @description 验证注册码是否有效
	 * @param code
	 * @return
	 */
	public static boolean checkInviteCode(String code){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		InviteDAO inviteDao = (InviteDAO) context.getBean("InviteDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<String> codeList = inviteDao.getCodeList();
		for (int i = 0; i < codeList.size(); i++) {
			if (code.equalsIgnoreCase(codeList.get(i))) {
				inviteDao.deleteCode(codeList.get(i));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @title: checkUsernameFormat
	 * @description: 检查用户名格式，必须为小写字母或数字
	 * @param username
	 * @return
	 */
	public static boolean checkUsernameFormat(String username){
		char[] source = username.toCharArray();
		if (source.length < 4 || source.length > 20) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			char temp = source[i];
			if (temp < '0' || temp > 'z' || (temp < 'a' && temp > '9')) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @title: checkLocation
	 * @description: 验证地址坐标信息是否有误
	 * @param lng
	 * @param lat
	 * @return
	 */
	public static boolean checkLocation(BigDecimal lng, BigDecimal lat){
		if (lng == null || lat == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title: checkTime
	 * @description: 验证创建时间和过期时间是否有误
	 * @param createTime
	 * @param expiredTime
	 * @return
	 */
	public static boolean checkTime(Timestamp createTime, Timestamp expiredTime){
		if (createTime == null || expiredTime == null) {
			return false;
		}
		
		InputStream inputStream = CommonValidationTools.class.getResourceAsStream("/defaultValue.properties");
		Properties properties = new Properties();
		long lifeCycle = 0;
		try {
			properties.load(inputStream);
			lifeCycle = Long.parseLong((String)properties.get("defaultEloveLifeCycleByMonth"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		lifeCycle = lifeCycle * 30 * 24 * 60 * 60 * 1000;
		
		if (createTime.getTime() + lifeCycle != expiredTime.getTime()) {
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
	public static String checkEloveWizard(EloveWizard eloveWizard){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
		((ConfigurableApplicationContext)context).close();
		
		int eloveid = eloveWizard.getEloveid();
		String appid = eloveWizard.getAppid();
		Timestamp createTime = eloveWizard.getCreateTime();
		Timestamp expiredTime = eloveWizard.getExpiredTime();
		String title = eloveWizard.getTitle();
		String password = eloveWizard.getPassword();
		String coverPic = eloveWizard.getCoverPic();
		String coverText = eloveWizard.getCoverText();
		String majorGroupPhoto = eloveWizard.getMajorGroupPhoto();
		String xinNiang = eloveWizard.getXinNiang();
		String xinLang = eloveWizard.getXinLang();
		String storyTextImagePath = eloveWizard.getStoryTextImagePath();
		String music = eloveWizard.getMusic();
		String phone = eloveWizard.getPhone();
		String weddingDate = eloveWizard.getWeddingDate();
		String weddingAddress = eloveWizard.getWeddingAddress();
		BigDecimal lng = eloveWizard.getLng();
		BigDecimal lat = eloveWizard.getLat();
		String shareTitle = eloveWizard.getShareTitle();
		String shareContent = eloveWizard.getShareContent();
		String footerText = eloveWizard.getFooterText();
		String sideCorpInfo = eloveWizard.getSideCorpInfo();
		int themeid = eloveWizard.getThemeid();
		
		if (title == null || password == null || coverPic == null || coverText == null 
				|| majorGroupPhoto == null || xinNiang == null || xinLang == null || 
				storyTextImagePath == null || music == null || phone == null || weddingDate == null 
				|| weddingAddress == null || shareTitle == null || shareContent == null || 
				footerText == null || sideCorpInfo == null) {
			return "elove提交信息不完整！";
		}
		
		if (eloveid < 0) {
			return "elove提交信息非法！";
		}else {			
			if (eloveid > 0) {
				EloveWizard original = eloveWizardDao.getBasicElove(eloveid);
				if (original == null) {
					return "elove提交信息非法！";
				}
				if (!password.equals(original.getPassword()) && eloveWizardDao.checkPassword(appid, password) != 0) {
					return "密码已被使用！";
				}
				
			}else {
				if (eloveWizardDao.checkPassword(appid, password) != 0) {
					return "密码已被使用！";
				}	
				
				if (!checkTime(createTime, expiredTime)) {   
					return  "elove时间信息非法!";
				}
			}		
		}
		
		if (!checkLocation(lng, lat)) {
			return "地址请重新定位！";
		}
		
		List<Integer> themeidList = eloveWizardDao.getThemeidList();
		if (!themeidList.contains(themeid)) {
			return "elove主题信息非法！";
		}
		
		return "pass";
	}
	
	/**
	 * @title: checkWelcome
	 * @description: 检查欢迎页配置信息
	 * @param welcome
	 * @return
	 */
	public static boolean checkWelcome(Welcome welcome){
		return true;
	}
	
	/**
	 * @title: checkArticle
	 * @description: 检查文章信息
	 * @param article
	 * @return
	 */
	public static boolean checkArticle(Article article){
		if (article.getTitle() == null || article.getContent() == null) {
			return false;
		}
		if (article.getArticleid() < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title: checkArticleClass
	 * @description: 检查文章类别信息
	 * @param articleClass
	 * @return
	 */
	public static boolean checkArticleClass(ArticleClass articleClass){
		if (articleClass.getClassName() == null) {
			return false;
		}
		if (articleClass.getClassid() < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title: checkWebsite
	 * @description: 检查微官网创建向导中的信息
	 * @param website
	 * @return
	 */
	public static String checkWebsite(Website website){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		((ConfigurableApplicationContext)context).close();
		
		if (website.getGetCode() == null || website.getTitle() == null || 
				website.getPhone() == null || website.getAddress() == null ||
				website.getCoverPic() == null || website.getCoverText() == null || 
				website.getShareTitle() == null || website.getShareContent() == null || 
				website.getFooterText() == null ) {
			return "微官网提交信息不完整！";
		}
		
		if (!checkLocation(website.getLng(), website.getLat())) {
			return "地址请重新定位！";
		}
		
		List<Integer> themeidList = websiteDao.getThemeidList();
		if (!themeidList.contains(website.getThemeId())) {
			return "微官网主题信息非法！";
		}
		
		if (website.getWebsiteid() < 0) {
			return "微官网提交信息非法！";
		}else{
			if (website.getWebsiteid() == 0) {
				if (website.getAppid() == null || website.getCreateTime() == null) {   
					return  "微官网操作信息不完整!";
				}
				
				int count = websiteDao.getWebsiteNum(website.getAppid());
				if (count != 0) {
					return "抱歉，创建失败！此公众账号已经创建了一个微官网";
				}
			}
		}
		
		return "pass";
	}
	
	/**
	 * @title: checkAuthInfo
	 * @description: 检查权限更新时的关键信息
	 * @param authInfo
	 * @return
	 */
	public static boolean checkAuthInfo(AuthInfo authInfo){
		if (authInfo.getSid() <= 0 || authInfo.getAuthPinyin() == null || 
				authInfo.getExpiredTime() == null || authInfo.getPrice() == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title: checkEloveNotpay
	 * @description: 检查更新时提交的elove未支付信息
	 * @param eloveNotpay
	 * @return
	 */
	public static boolean checkEloveNotpay(List<EloveNotpay> eloveNotpayList){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		EloveInfoDAO eloveInfoDao = (EloveInfoDAO) context.getBean("EloveInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		for (int i = 0; i < eloveNotpayList.size(); i++) {
			EloveNotpay eloveNotpay = eloveNotpayList.get(i);			
			if (eloveNotpay.getNotPayNumber() < 0 || eloveNotpay.getAppid() == null) {
				return false;
			}
			
			Integer totalNum = eloveInfoDao.getEloveNum(eloveNotpay.getAppid());
			if (totalNum == null || eloveNotpay.getNotPayNumber() > totalNum) {
				return false;
			}
		}	
		return true;
	}
	
	/**
	 * @title: checkContactInfo
	 * @description: 检查更新时提交的商家联系方式
	 * @param userInfo
	 * @return
	 */
	public static boolean checkContactInfo(UserInfo userInfo){
		if (userInfo.getSid() <= 0 || userInfo.getContact() == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * @title checkMenu
	 * @description 检查菜单定义信息
	 * @param menu
	 * @return
	 */
	public static boolean checkMenu(Menu menu){
		return true;
	}
	
	/**
	 * @title checkAlbumClass
	 * @description 检查相册集信息
	 * @param albumClass
	 * @return
	 */
	public static boolean checkAlbumClass(AlbumClass albumClass){
		return true;
	}
	
	/**
	 * @title checkAlbum
	 * @description 检查相册信息
	 * @param album
	 * @return
	 */
	public static boolean checkAlbum(Album album){
		return true;
	}
	
	/**
	 * @title checkVote
	 * @description 检查投票信息
	 * @param vote
	 * @return
	 */
	public static boolean checkVote(Vote vote){
		return true;
	}
	
	/**
	 * @title checkVoteContent
	 * @description 检查单个用户投票信息
	 * @param message
	 * @return
	 */
	public static boolean checkVoteContent(VoteContent content){
		return true;
	}
}
