package controller.internal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return "";
	}
}
