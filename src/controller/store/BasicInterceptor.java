package controller.store;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import register.AppInfo;
import register.dao.AppInfoDAO;
import security.User;

/**
 * @Title: BasicInterceptor
 * @Description: 每个界面都要获取的基本信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年1月9日
 */
public class BasicInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		request.setAttribute("username", user.getUsername());				
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		String appid = null;
		Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equalsIgnoreCase("appid")) {
				appid = cookies[i].getValue();
				break;
			}
		}
		
		List<AppInfo> appInfoList = appInfoDao.getBasicAppInfo(user.getSid());
		List<String> authPinyinList = null;
		if (appid != null && !appid.equals("") && appInfoDao.checkAppExistsByUser(
				user.getSid(), appid) == 1) {                                               //1.不为空 2.确实是appid 
			for (int i = 0; i < appInfoList.size(); i++) {
				AppInfo appInfo = appInfoList.get(i);
				if (appInfo.getAppid().equals(appid)) {
					appInfo.setIsCharged(true);
				}else {
					appInfo.setIsCharged(false);
				}
			}
			authPinyinList = appInfoDao.getAuthPinyinList(appid);
		}else {
			if (appInfoList.size() > 0) {
				for (int i = 0; i < appInfoList.size(); i++) {
					AppInfo appInfo = appInfoList.get(i);
					if (i == 0) {
						appInfo.setIsCharged(true);
						authPinyinList = appInfoDao.getAuthPinyinList(appInfo.getAppid());
					}else {
						appInfo.setIsCharged(false);
					}
				}
			}
		}
		request.setAttribute("basicAppInfoList", appInfoList);
		request.setAttribute("authPinyinList", authPinyinList);
		
        return true;   
	}

}
