package controller.store;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import security.User;
import tools.CommonValidationTools;
import branch.Branch;
import branch.BranchClass;
import branch.dao.BranchDAO;

/**
 * @Title: BranchClassController
 * @Description: 分店类别管理
 * @Company: Tuka
 * @author ben
 * @date 2014年4月24日
 */
@Controller
@RequestMapping("/branchclass")
public class BranchClassController {
	/**
	 * @title getBranchClassList
	 * @description 显示分店类别详细信息列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getBranchClassList(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<BranchClass> classList = branchDao.getDetailedClassinfos(user.getSid());
		model.addAttribute("classList", classList);
		
		InputStream inputStream = BranchClassController.class.getResourceAsStream("/environment.properties");
		Properties properties = new Properties();
		String appPath = null;
		try {
			properties.load(inputStream);
			appPath = (String)properties.get("applicationPath");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		model.addAttribute("appPath", appPath);
		return "BranchViews/branchclassList";
	}
	
	/**
	 * @title addBranchClass
	 * @description 创建新的分店类别(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBranchClass(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<Branch> branchList = branchDao.getBasicBranchinfos(user.getSid());
		model.addAttribute("branchList", branchList);
		return "BranchViews/addBranchclassDialog";
	}
	
	/**
	 * @title editBranchClass
	 * @description 编辑已有的分店类别(第一步)
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editBranchClass(@RequestParam(value = "classid", required = true) int 
			classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<Integer> selectedList = null;	
		if (classid > 0) {
			BranchClass branchClass = branchDao.getClassContent(classid);
			if (branchClass != null) {
				selectedList = branchClass.getBranchSidList();
				model.addAttribute("branchclass", branchClass);
			}
		}
		List<Branch> branchList = branchDao.getBasicBranchinfos(user.getSid());
		if (selectedList != null) {
			for (int i = 0, j = branchList.size(); i < j; i++) {
				Branch branch = branchList.get(i);
				if (selectedList.contains(branch.getBranchSid())) {
					branch.setSelected(true);
				}
			}
		}
		model.addAttribute("branchList", branchList);
		return "BranchViews/editBranchclassDialog";
	}
	
	/**
	 * @title createBranchClass
	 * @description 创建新的分店类别(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createBranchClass(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		BranchClass branchClass = gson.fromJson(json, BranchClass.class);
		Timestamp current = new Timestamp(System.currentTimeMillis());			
		branchClass.setCreateTime(current);
		branchClass.setStoreSid(user.getSid());
		
		if (!CommonValidationTools.checkBranchClass(branchClass)) {
			message.setStatus(false);
			message.setMessage("分店类别信息不完整或有误！");
		} else {
			int result = branchDao.insertBranchClass(branchClass);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("分店类别创建成功！");
			} else {
				message.setStatus(false);
				message.setMessage("分店类别创建失败！");
				System.out.println("Error: " + result);
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateBranchClass
	 * @description 编辑已有的分店类别(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateBranchClass(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		BranchClass branchClass = gson.fromJson(json, BranchClass.class);
		
		if (!CommonValidationTools.checkBranchClass(branchClass)) {
			message.setStatus(false);
			message.setMessage("分店类别信息不完整或有误！");
		} else {
			int result = branchDao.updateBranchClass(branchClass);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("分店类别修改成功！");
			} else {
				message.setStatus(false);
				message.setMessage("分店类别修改失败！");
				System.out.println("Error: " + result);
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteBranchClass
	 * @description 删除分店类别
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteBranchClass(@RequestParam(value = "classid", required = true) 
	    int classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = branchDao.deleteBranchClass(classid);
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
