package controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;




/**
 * @Title: MenuWizardController
 * @Description: 控制menu创建向导
 * @Company: Tuka
 * @author byc
 * @date 2014年3月17日
 */
@Controller
@RequestMapping("/menu/wizard")
@SessionAttributes("menuWizard")
public class MenuWizardController {
	@RequestMapping(value = "/initial", method = RequestMethod.GET)
	public String initialCreate(final Model model){

		return "AccountViews/menuWizardContainer";
	}
	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	public String step2(@RequestBody String json, Model model){
		
		return "AccountViews/menustep2";
	}
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	@ResponseBody
	public String cancel(final SessionStatus status){
		status.setComplete();
		return "OK";
	}
}
