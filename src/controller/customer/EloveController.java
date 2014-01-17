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

import register.UserInfo;
import register.dao.UserInfoDAO;
import elove.EloveJoinInfo;
import elove.EloveMessage;
import elove.EloveWizard;
import elove.dao.EloveInteractDAO;
import elove.dao.EloveWizardDAO;

@Controller
public class EloveController {
/**
 * @description: elove整体容器，同时引入story页面
 * @param model
 * @param eloveid
 * @return
 */
@RequestMapping(value = "/elove/elove", method = RequestMethod.GET)
public String eloveIndex(Model model, @RequestParam(value = "eloveid", required = true) int eloveid){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
	((ConfigurableApplicationContext)context).close();
	
	EloveWizard eloveWizard = eloveWizardDao.getOnlyElove(eloveid);
	model.addAttribute("elove", eloveWizard);
	
	List<String> storyImagePath = eloveWizardDao.getImageListWithType(eloveid, "story");
	model.addAttribute("storyImagePath", storyImagePath);
	
	String viewName = "EloveViews/elove-";
	if (eloveWizard != null) {
		viewName = viewName + eloveWizard.getThemeid();
	}
	
	return viewName;
}	

/**
 * @description: 相遇相知页面
 * @return
 */
@RequestMapping(value = "/elove/story", method = RequestMethod.GET)
public String story(Model model, @RequestParam(value = "eloveid", required = true) int eloveid){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
	((ConfigurableApplicationContext)context).close();
	
	EloveWizard eloveWizard = eloveWizardDao.getOnlyElove(eloveid);
	model.addAttribute("elove", eloveWizard);
	
	List<String> storyImagePath = eloveWizardDao.getImageListWithType(eloveid, "story");
	model.addAttribute("storyImagePath", storyImagePath);
	
	String viewName = "EloveViews/story-";
	if (eloveWizard != null) {
		viewName = viewName + eloveWizard.getThemeid();
	}
	
	return viewName;
}

/**
 * @description: 婚礼信息页面
 * @return
 */
@RequestMapping(value = "/elove/info", method = RequestMethod.GET)
public String wedding(Model model, @RequestParam(value = "eloveid", required = true) int eloveid){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
	((ConfigurableApplicationContext)context).close();
	
	EloveWizard eloveWizard = eloveWizardDao.getOnlyElove(eloveid);
	model.addAttribute("elove", eloveWizard);
	
	String viewName = "EloveViews/info-";
	if (eloveWizard != null) {
		viewName = viewName + eloveWizard.getThemeid();
	}
	
	return viewName;
}

/**
 * @description: 婚纱照页面
 * @return
 */
@RequestMapping(value = "/elove/dress", method = RequestMethod.GET)
public String dress(Model model, @RequestParam(value = "eloveid", required = true) int eloveid){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
	((ConfigurableApplicationContext)context).close();
	
	EloveWizard eloveWizard = eloveWizardDao.getOnlyElove(eloveid);
	model.addAttribute("elove", eloveWizard);
	
	List<String> dressImagePath = eloveWizardDao.getImageListWithType(eloveid, "dress");
	model.addAttribute("dressImages", dressImagePath);
	
	String dressVideoPath = eloveWizardDao.getVideoWithType(eloveid, "dress");
	model.addAttribute("dressVideo", dressVideoPath);
	
	String viewName = "EloveViews/dress-";
	if (eloveWizard != null) {
		viewName = viewName + eloveWizard.getThemeid();
	}
	
	return viewName;
}

/**
 * @description: 婚礼纪录页面
 * @return
 */
@RequestMapping(value = "/elove/record", method = RequestMethod.GET)
public String record(Model model, @RequestParam(value = "eloveid", required = true) int eloveid){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
	((ConfigurableApplicationContext)context).close();
	
	EloveWizard eloveWizard = eloveWizardDao.getOnlyElove(eloveid);
	model.addAttribute("elove", eloveWizard);
	
	List<String> recordImagePath = eloveWizardDao.getImageListWithType(eloveid, "record");
	model.addAttribute("recordImages", recordImagePath);
	
	String recordVideoPath = eloveWizardDao.getVideoWithType(eloveid, "record");
	model.addAttribute("recordVideo", recordVideoPath);
	
	String viewName = "EloveViews/record-";
	if (eloveWizard != null) {
		viewName = viewName + eloveWizard.getThemeid();
	}
	
	return viewName;
}

/**
 * @description: 公司介绍页面
 * @param model
 * @param eloveid
 * @return
 */
@RequestMapping(value = "/elove/intro", method = RequestMethod.GET)
public String intro(Model model, @RequestParam(value = "eloveid", required = true) int eloveid){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAO");
	((ConfigurableApplicationContext)context).close();
	
	Integer sid = userInfoDao.getSidByEloveid(eloveid);
	if (sid != null) {
		UserInfo userInfo = userInfoDao.getUserInfo(sid);
		model.addAttribute("userInfo", userInfo);
	}
	
	model.addAttribute("eloveid", eloveid);
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
        if(result>0){
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
        
        if(result>0){
                message.setStatus(true);
                message.setMessage("发送成功！");
        }else{
                message.setStatus(false);
                message.setMessage("发送失败！");
        }
                
        return gson.toJson(message);
}
}

