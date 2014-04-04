package controller.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lottery.LotteryWheel;
import lottery.LotteryWheelItem;
import lottery.dao.LotteryWheelDAO;
import message.ResponseMessage;

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

import register.dao.AppInfoDAO;
import tools.RandomUtil;
import website.ShareMessage;
import website.Website;
import website.dao.WebsiteDAO;

/**
 * @Title: LotteryWheelController
 * @Description: 大转盘抽奖管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月30日
 */
@Controller
public class LotteryWheelController {
	/**
	 * @title getLotteryWheel
	 * @description 根据wheelid获取转盘抽奖信息
	 * @param wheelid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel", method = RequestMethod.GET)
	public String getLotteryWheel(@RequestParam(value = "wheelid", required = true) int wheelid, 
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		LotteryWheel wheel = wheelDao.getWheelForCustomer(wheelid);
		String viewName = "LotteryViews/";
		
		if (wheel != null) {
			Integer websiteid = wheelDao.getWebsiteidByWheelid(wheelid);
			if (websiteid != null) {
				Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
		        model.addAttribute("website", website);
			}
			
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByLotteryWheel(wheelid));
			message.setAppLink(message.getAppLink() + "customer/lottery/wheel?wheelid=" + wheelid);
            message.setImageLink(message.getImageLink() + "/ZhongheWeiHelperR2/img/lottery_wheel/sharelottery.png");
			message.setShareTitle(wheel.getWheelName());
			message.setShareContent(wheel.getWheelDesc());
			model.addAttribute("message", message);
			
			model.addAttribute("wheel", wheel);
			viewName = viewName + "wheel";	
		} else {
			viewName = viewName + "exception";
		}
		return viewName;
	}
	
	/**
	 * @title doLotteryWheel
	 * @description 根据wheelid进行抽奖
	 * @param wheelid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/do", method = RequestMethod.POST)
	public String doLotteryWheel(@RequestParam(value = "wheelid", required = true) int wheelid, 
			HttpServletRequest request, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		((ConfigurableApplicationContext)context).close();
		
		String viewName = "LotteryViews/";
		if (request.getAttribute("status").equals("active")) {
			int restChance = (int) request.getAttribute("restChance");
			model.addAttribute("restChance", restChance);
			
			wheelDao.updateWheelCount(wheelid);
			List<LotteryWheelItem> itemList = wheelDao.getIteminfoList(wheelid);
			int lottery = processLotteryWheel(itemList);
			if (lottery >= 0) {
				String openid = RandomUtil.generateUUID();
				boolean check = checkValid(wheelDao, itemList.get(lottery), openid);
				if (check == true) {
					model.addAttribute("luckyType", lottery + 1);
					model.addAttribute("itemid", itemList.get(lottery).getItemid());
					model.addAttribute("itemDesc", itemList.get(lottery).getItemDesc());
					model.addAttribute("token", openid);
					viewName = viewName + "lucky";
				} else {
					viewName = viewName + "notLucky";
				}
			} else {
				viewName = viewName + "notLucky";
			}
		} else {
			viewName = viewName + "noChance";
		}
        return viewName;
	}
	
	/**
	 * @title updateContact
	 * @description 更新中奖纪录联系方式信息
	 * @param itemid
	 * @param openid
	 * @param contact
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lottery/wheel/contact", method = RequestMethod.POST)
	@ResponseBody
	public String updateContact(@RequestParam(value = "itemid", required = true) int itemid, 
			@RequestParam(value = "openid", required = true) String openid, 
			@RequestParam(value = "contact", required = true) String contact, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		LotteryWheelDAO wheelDao = (LotteryWheelDAO) context.getBean("LotteryWheelDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		String checkContact = wheelDao.getContact(itemid, openid);
		if (checkContact != null && checkContact.equals("initial")) {
			int result = wheelDao.updateContact(itemid, openid, contact);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("提交保存成功！到店提供手机号即可领奖！");
			} else {
				message.setStatus(false);
				message.setMessage("提交保存失败！请重新提交");
			}
		} else {
			message.setStatus(false);
			message.setMessage("非法操作！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title processLotteryWheel
	 * @description 模拟抽奖流程
	 * @param itemList
	 * @return
	 */
	private int processLotteryWheel(List<LotteryWheelItem> itemList){
		double lottery = RandomUtil.generateDouble();
		double measure = 0;
		for (int i = 0; i < itemList.size(); i++) {
			LotteryWheelItem item = itemList.get(i);
			if (lottery >= measure && lottery < measure + item.getItemPercent().doubleValue()) {
				return i;
			} else {
				measure = measure + item.getItemPercent().doubleValue();
			}
		}
		return -1;
	}
	
	/**
	 * @title checkValid
	 * @description 判断该次中奖是否有效
	 * @param wheelDao
	 * @param item
	 * @param openid
	 * @return
	 */
	private synchronized boolean checkValid(LotteryWheelDAO wheelDao, LotteryWheelItem item, String openid){
		int count = wheelDao.getPrizeCount(item.getItemid());
		if (count < item.getItemCount()) {
			int result = wheelDao.insertResult(item.getItemid(), openid);
			if (result > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
