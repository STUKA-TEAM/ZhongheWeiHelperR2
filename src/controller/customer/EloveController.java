package controller.customer;

import java.sql.Timestamp;
import java.util.List;

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

import com.google.gson.Gson;

import elove.EloveJoinInfo;
import elove.EloveMessage;
import elove.dao.EloveInteractDAO;

@Controller
public class EloveController {
@RequestMapping(value = "/elove/elove", method = RequestMethod.GET)
public String eloveIndex(){
	return "EloveViews/elove";
}	
@RequestMapping(value = "/elove/story", method = RequestMethod.GET)
public String story(){
	return "EloveViews/story";
}
@RequestMapping(value = "/elove/info", method = RequestMethod.GET)
public String wedding(){
	return "EloveViews/info";
}
@RequestMapping(value = "/elove/dress", method = RequestMethod.GET)
public String dress(){
	return "EloveViews/dress";
}
@RequestMapping(value = "/elove/record", method = RequestMethod.GET)
public String record(){
	return "EloveViews/record";
}
@RequestMapping(value = "/elove/intro", method = RequestMethod.GET)
public String intro(){
	return "EloveViews/intro";
}
@RequestMapping(value = "/elove/wish", method = RequestMethod.GET)
public String wishMessage(@RequestParam(value="eloveid") int eloveid, Model model){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveInteractDAO eloveInteractDAO = (EloveInteractDAO)context.getBean("EloveInteractDAO");
	((ConfigurableApplicationContext)context).close();
	
	List<EloveMessage> messageList = eloveInteractDAO.getMessageList(eloveid);
	model.addAttribute("messageList", messageList);
	return "EloveViews/wish";
}
@RequestMapping(value = "/elove/join", method = RequestMethod.GET)
public String joinMessage(@RequestParam(value="eloveid") int eloveid, Model model){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveInteractDAO eloveInteractDAO = (EloveInteractDAO)context.getBean("EloveInteractDAO");
	((ConfigurableApplicationContext)context).close();
	
	List<EloveJoinInfo> eloveJoinInfoList = eloveInteractDAO.getJoinInfoList(eloveid);
	model.addAttribute("eloveJoinInfoList", eloveJoinInfoList);	
	return "EloveViews/join";
}
@ResponseBody
@RequestMapping(value = "/elove/addWish", method = RequestMethod.POST)
public String addWishMessage(@RequestBody String json){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveInteractDAO eloveInteractDAO = (EloveInteractDAO)context.getBean("EloveInteractDAO");
	((ConfigurableApplicationContext)context).close();
	
	Gson gson = new Gson();
	EloveMessage eloveMessage = gson.fromJson(json, EloveMessage.class);
	Timestamp current = new Timestamp(System.currentTimeMillis());
	eloveMessage.setCreateTime(current);
	int result = eloveInteractDAO.insertMessage(eloveMessage);
	ResponseMessage message = new ResponseMessage();
	if(result > 0){
		message.setStatus(true);
		message.setMessage("发送成功！");
	}else{
		message.setStatus(false);
		message.setMessage("发送失败！");
	}
	return gson.toJson(message);
}

@ResponseBody
@RequestMapping(value = "/elove/addJoin", method = RequestMethod.POST)
public String addJoinMessage(@RequestBody String json){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveInteractDAO eloveInteractDAO = (EloveInteractDAO)context.getBean("EloveInteractDAO");
	((ConfigurableApplicationContext)context).close();
	
	Gson gson = new Gson();
	EloveJoinInfo eloveJoinInfo = gson.fromJson(json, EloveJoinInfo.class);
	Timestamp current = new Timestamp(System.currentTimeMillis());
	eloveJoinInfo.setCreateTime(current);
	int result = eloveInteractDAO.insertJoinInfo(eloveJoinInfo);
	ResponseMessage message = new ResponseMessage();
	
	if(result > 0){
		message.setStatus(true);
		message.setMessage("发送成功！");
	}else{
		message.setStatus(false);
		message.setMessage("发送失败！");
	}
		
	return gson.toJson(message);
}
}
