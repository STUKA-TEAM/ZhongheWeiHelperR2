package controller.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import register.dao.InviteDAO;
import tools.RandomUtil;

/**
 * @Title: InviteController
 * @Description: 注册码管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月21日
 */
@Controller
public class InviteController {
	/**
	 * @title getCode
	 * @description 获取可用注册码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/invite/intial", method = RequestMethod.GET)
	public String getCode(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		InviteDAO inviteDao = (InviteDAO) context.getBean("InviteDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<String> codeList = inviteDao.getCodeList();
		model.addAttribute("codes", codeList);
		return "inviteCode";
	}
	
	/**
	 * @title createCode
	 * @description 创建一定数量的注册码
	 * @param number
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/invite/create", method = RequestMethod.POST)
	@ResponseBody
	public String createCode(@RequestParam(value = "number", required = true) int number, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		InviteDAO inviteDao = (InviteDAO) context.getBean("InviteDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		if (number >= 1 && number <= 10) {
			List<String> codeCreate = new ArrayList<String>();
			for (int i = 0; i < number; i++) {
				List<String> codeExists = inviteDao.getCodeList();
				String code = RandomUtil.generateMixedString(6);
				while (checkExists(code, codeExists)) {
					code = RandomUtil.generateMixedString(6);
				}
				codeCreate.add(code);
			}
			inviteDao.insertCodeList(codeCreate);
			message.setStatus(true);
			message.setMessage("注册码已生成！");
		} else {
			message.setStatus(false);
			message.setMessage("数量不正确！");
		}
		
		String response = gson.toJson(message);		
		return response;
	}
	
	/**
	 * @title checkExists
	 * @description 检查生成的注册码是否已存在
	 * @param code
	 * @param codeList
	 * @return
	 */
	private boolean checkExists(String code, List<String> codeList){
		for (int i = 0; i < codeList.size(); i++) {
			if (code.equalsIgnoreCase(codeList.get(i))) {
				return true;
			}
		}
		return false;
	}
}
