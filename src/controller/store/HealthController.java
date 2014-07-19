package controller.store;

import health.StationInfo;
import health.dao.HealthDAO;
import message.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.google.gson.Gson;

/**
 * @Title: HealthController
 * @Description: 医疗服务管理
 * @Company: Tuka
 * @author ben
 * @date 2014年7月18日
 */
@Controller
@RequestMapping("/health")
public class HealthController {
	@Autowired
	private Gson gson;
	
	/**
	 * @title getHost
	 * @description 获取体检站信息
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/gethost", method = RequestMethod.GET)
	public String getHost(@RequestParam(value = "appid", required = true) String 
			appid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		HealthDAO healthDao = (HealthDAO) context.getBean("HealthDAO");
		((ConfigurableApplicationContext)context).close();
		
		StationInfo stationInfo = healthDao.getStationInfo(appid);
		model.addAttribute("stationInfo", stationInfo);
		return "HealthViews/stationInfo";
	}
	
	/**
	 * @title editHost
	 * @description 编辑体检站信息
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edithost", method = RequestMethod.POST)
	@ResponseBody
	public String editHost(@RequestBody String json, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		HealthDAO healthDao = (HealthDAO) context.getBean("HealthDAO");
		((ConfigurableApplicationContext)context).close();
		
		StationInfo stationInfo = gson.fromJson(json, StationInfo.class);
		ResponseMessage message = new ResponseMessage();
		int result = healthDao.editStationInfo(stationInfo);
		if (result > 0) {
			message.setMessage("操作成功！");
			message.setStatus(true);
		} else {
			message.setMessage("操作失败！");
			message.setStatus(false);
		}
		String response = gson.toJson(message);
		return response;
	}
}
