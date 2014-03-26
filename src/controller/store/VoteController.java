package controller.store;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;
import vote.Vote;
import vote.dao.VoteDAO;

/**
 * @Title: VoteController
 * @Description: 投票管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月25日
 */
@Controller
public class VoteController {
	/**
	 * @title getVoteList
	 * @description 获取投票列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/vote/list", method = RequestMethod.GET)
	public String getVoteList(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			}
			else {
				List<Vote> voteList = voteDao.getVoteinfoList(appid);
				model.addAttribute("voteList", voteList);
				
				InputStream inputStream = VoteController.class.getResourceAsStream("/environment.properties");
				Properties properties = new Properties();
				String appPath = null;
				try {
					properties.load(inputStream);
					appPath = (String)properties.get("applicationPath");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				model.addAttribute("appPath", appPath);
				return "VoteViews/voteList";
			}
		}
	}
	
	/**
	 * @title addVote
	 * @description 创建新的投票(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/vote/add", method = RequestMethod.GET)
	public String addVote(Model model){
		return "VoteViews/addVoteDialog";
	}
	
	/**
	 * @title editVote
	 * @description 编辑已有的投票(第一步)
	 * @param appid
	 * @param voteid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/vote/edit", method = RequestMethod.GET)
	public String editVote(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "voteid", required = true) int voteid, Model model,
			HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			} 
			else {
				Vote vote = voteDao.getVoteinfo(voteid);
				model.addAttribute("vote", vote);
				return "VoteViews/editVoteDialog";
			}
		}
	}
	
	/**
	 * @title createVote
	 * @description 创建新的投票(第二步)
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/vote/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createVote(@CookieValue(value = "appid", required = false) String appid, 
		@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
	    
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
			
		if (appid == null) {                      //异常
			message.setStatus(false);
			message.setMessage("请重新登录！");
		}
		else {
			if (appid == "" || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				message.setStatus(false);
				message.setMessage("当前管理的公众账号无效，请先选择或关联微信公众账号!");
			}
			else {
				Vote vote = gson.fromJson(json, Vote.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());
				vote.setCreateTime(current);
				vote.setAppid(appid);
				
				if (!CommonValidationTools.checkVote(vote)) {
					message.setStatus(false);
					message.setMessage("投票信息不完整或有误！");
				} else {
					int result = voteDao.insertVote(vote);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("投票创建成功！");
					} else {
						message.setStatus(false);
						message.setMessage("投票创建失败！");
						System.out.println("Error: " + result);
					}
				}
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateVote
	 * @description 编辑已有的投票(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/vote/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateVote(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		Vote vote = gson.fromJson(json, Vote.class);
		
		if (!CommonValidationTools.checkVote(vote)) {
			message.setStatus(false);
			message.setMessage("投票信息不完整或有误！");
		} else {
			int result = voteDao.updateVote(vote);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("投票修改成功！");
			} else {
				message.setStatus(false);
				message.setMessage("投票修改失败！");
				System.out.println("Error: " + result);
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteVote
	 * @description 删除投票
	 * @param voteid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/vote/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteVote(@RequestParam(value = "voteid", required = true) 
	    int voteid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = voteDao.deleteVote(voteid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("删除成功！");
		}else {
			message.setStatus(false);
			message.setMessage("删除失败！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
}
