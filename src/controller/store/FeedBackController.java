package controller.store;

import java.sql.Timestamp;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import security.User;

import com.google.gson.Gson;

import feedback.Feedback;
import feedback.dao.FeedbackDAO;

@Controller
public class FeedBackController {

@RequestMapping(value="feedback", method=RequestMethod.GET)
public String feedback(){
	return "FeedbackViews/feedback";
}

@ResponseBody
@RequestMapping(value="addFeedback", method=RequestMethod.POST)
public String addFeedback(@RequestBody String json){
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("All-Modules.xml");
	FeedbackDAO feedbackDAO = (FeedbackDAO)context.getBean("FeedbackDAO");
	((ConfigurableApplicationContext)context).close();
	
	Gson gson = new Gson();
	Feedback feedback = gson.fromJson(json, Feedback.class);
	Timestamp current = new Timestamp(System.currentTimeMillis());
	feedback.setCreateTime(current);
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	User user = (User)auth.getPrincipal();
	feedback.setSid(user.getSid());;
	int result = 0;
	result = feedbackDAO.insertFeedback(feedback);
	
	ResponseMessage message = new ResponseMessage();
	if(result==1){
		message.setStatus(true);
		message.setMessage("发送成功！");
	}else{
		message.setStatus(false);
		message.setMessage("发送失败！");
	}
	return gson.toJson(message);
}
}
