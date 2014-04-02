package controller.customer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lottery.LotteryWheel;
import lottery.dao.LotteryWheelDAO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LotteryWheelInterceptor implements HandlerInterceptor {

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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		Integer wheelid = null;
		try {
			wheelid = Integer.parseInt(request.getParameter("wheelid"));
		} catch (Exception e) {
			System.out.println("LotteryWheelInterceptor: " + e.getMessage());
		}
		
		if (wheelid != null) {
			ApplicationContext context = new ClassPathXmlApplicationContext("All-Modules.xml");
			LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
			((ConfigurableApplicationContext)context).close();
			
			LotteryWheel wheel = wheelDao.getWheelCookieinfo(wheelid);
			if (wheel != null) {
				Cookie[] cookies = request.getCookies();
				String expected = null;
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						if (cookies[i].getName().equalsIgnoreCase(wheel.getWheeluuid())) {
							expected = cookies[i].getValue();
							break;
						}
					}
				}
				
				if (expected == null) {
					Cookie cookie = new Cookie(wheel.getWheeluuid(), "1");
					cookie.setPath("/");
					cookie.setMaxAge(24*60*60);
					response.addCookie(cookie);
					request.setAttribute("restChance", wheel.getMaxDayCount() - 1);
					request.setAttribute("status", "active");
					return true;
				} else {
					int count = wheel.getMaxDayCount();
					try {
						count = Integer.parseInt(expected);
					} catch (Exception e) {
						System.out.println("LotteryWheelInterceptor: " + e.getMessage());
					}
					if (count >= 1 && count < wheel.getMaxDayCount()) {
						count = count + 1;
						Cookie cookie = new Cookie(wheel.getWheeluuid(), count + "");
						cookie.setPath("/");
						cookie.setMaxAge(24*60*60);
						response.addCookie(cookie);
						request.setAttribute("restChance", wheel.getMaxDayCount() - count);
						request.setAttribute("status", "active");
						return true;
					} 
				}
			}
		} 
        
		request.setAttribute("status", "inactive");
		return true;
	}

}
