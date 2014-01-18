package controller.store;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import register.Welcome;
import register.dao.WelcomeDAO;
import tools.CommonValidationTools;

import com.google.gson.Gson;

/**
 * @Title: WelcomeController
 * @Description: 控制欢迎页相关的信息配置
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
@Controller
public class WelcomeController {
	/**
	 * @description: 插入欢迎页配置信息
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome/insert", method = RequestMethod.POST)
	@ResponseBody
	public String insertWelcome(@RequestBody String json, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WelcomeDAO welcomeDao = (WelcomeDAO) context.getBean("WelcomeDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		Welcome welcome = gson.fromJson(json, Welcome.class);
		
		ResponseMessage message = new ResponseMessage();
		if (!CommonValidationTools.checkWelcome(welcome)) {
			message.setStatus(false);
			message.setMessage("提交信息不完整或有误！");
		}else {
			int result = welcomeDao.insertWelcome(welcome);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("欢迎页信息修改成功！");
			}else{
				message.setStatus(false);
				message.setMessage("欢迎页信息修改失败！");
			}	
		}
		
		String response = gson.toJson(message);				
        return response;
	}
	
	/**
	 * @description: 获取指定appid对应的欢迎页配置信息
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome/edit", method = RequestMethod.GET)
	public String getWelcome(@RequestParam(value = "appid", required = true) String appid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WelcomeDAO welcomeDao = (WelcomeDAO) context.getBean("WelcomeDAO");
		((ConfigurableApplicationContext)context).close();
		
		Welcome welcome = welcomeDao.getWelcome(appid);
		model.addAttribute("welcome", welcome);
		
        return "AccountViews/welcome";
	}
	
	/**
	 * @description: 更新欢迎页配置信息
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateWelcome(@RequestBody String json, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WelcomeDAO welcomeDao = (WelcomeDAO) context.getBean("WelcomeDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		Welcome welcome = gson.fromJson(json, Welcome.class);
		
		ResponseMessage message = new ResponseMessage();
		if (!CommonValidationTools.checkWelcome(welcome)) {
			message.setStatus(false);
			message.setMessage("提交信息不完整或有误！");
		}else {
			int result = welcomeDao.updateWelcome(welcome);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("欢迎页信息修改成功！");
			}else{
				message.setStatus(false);
				message.setMessage("欢迎页信息修改失败！");
			}	
		}
		
		String response = gson.toJson(message);				
        return response;
	}
}
