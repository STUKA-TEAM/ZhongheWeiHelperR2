package controller.store;

import java.sql.Timestamp;
import java.util.List;

import message.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import register.RoleInfo;
import register.dao.RoleInfoDAO;
import security.User;
import tools.CommonValidationTools;
import article.dao.ArticleDAO;
import branch.Branch;
import branch.BranchClass;
import branch.dao.BranchDAO;

/**
 * @Title: BranchController
 * @Description: 分店管理
 * @Company: Tuka
 * @author ben
 * @date 2014年4月24日
 */
@Controller
@RequestMapping("/branch")
public class BranchController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @title getBranchList
	 * @description 显示分店详细信息列表
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getBranchList(@RequestParam(value = "classid", required = true) int 
			classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<Branch> branchList = null;
		if (classid == 0) {
			branchList = branchDao.getDetailedBranchinfos(user.getSid());
		}else {
			if (classid > 0) {
				branchList = branchDao.getDetailedBranchinfosByClassid(classid);
			}
		}
		model.addAttribute("branchList", branchList);
		
		List<BranchClass> classList = branchDao.getBasicClassinfos(user.getSid());
		BranchClass allType = new BranchClass();
		allType.setClassid(0);
		allType.setClassName("所有类别");
		classList.add(0, allType);
		for (int i = 0, j = classList.size(); i < j; i++) {
			BranchClass branchClass = classList.get(i);
			if (classid == branchClass.getClassid()) {
				branchClass.setSelected(true);
				break;
			}
		}
		model.addAttribute("classList", classList);
		return "BranchViews/branchList";
	}
	
	/**
	 * @title addBranch
	 * @description 创建新的分店(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBranch(Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		RoleInfoDAO roleInfoDao = (RoleInfoDAO) context.getBean("RoleInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<BranchClass> classList = branchDao.getBasicClassinfos(user.getSid());
		model.addAttribute("classList", classList);
		List<RoleInfo> roleList = roleInfoDao.getBranchRoleList();
		model.addAttribute("roleList", roleList);
		return "BranchViews/insertBranch";
	}
	
	/**
	 * @title editBranch
	 * @description 编辑已有的分店(第一步)
	 * @param branchSid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editBranch(@RequestParam(value = "branchid", required = true) int 
			branchSid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		RoleInfoDAO roleInfoDao = (RoleInfoDAO) context.getBean("RoleInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		List<Integer> selectedList = null;
		if (branchSid > 0) {
			Branch branch = branchDao.getBranchContent(branchSid);
			if (branch != null) {
				selectedList = branch.getClassidList();
				model.addAttribute("branch", branch);
			}
		}
		List<BranchClass> classList = branchDao.getBasicClassinfos(user.getSid());
		if (selectedList != null) {
			for (int i = 0, j = classList.size(); i < j; i++) {
				BranchClass branchClass = classList.get(i);
				if (selectedList.contains(branchClass.getClassid())) {
					branchClass.setSelected(true);
				}
			}
		}
		model.addAttribute("classList", classList);
		List<RoleInfo> roleList = roleInfoDao.getBranchRoleList();
		model.addAttribute("roleList", roleList);
		return "BranchViews/updateBranch";
	}
	
	/**
	 * @title createBranch
	 * @description 创建新的分店(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createBranch(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		Branch branch = gson.fromJson(json, Branch.class);
		Timestamp current = new Timestamp(System.currentTimeMillis());			
		branch.setCreateDate(current);
		branch.setStoreSid(user.getSid());
		branch.setPassword(passwordEncoder.encode(branch.getPassword()));
		
		String checkMessage = CommonValidationTools.checkBranch(branch);
		if (!checkMessage.equals("pass")) {
			message.setStatus(false);
			message.setMessage(checkMessage);
		} else {
			int result = branchDao.insertBranch(branch);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("分店创建成功！");
			} else {
				message.setStatus(false);
				message.setMessage("分店创建失败！");
				System.out.println("Error: " + result);
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateBranch
	 * @description 编辑已有的分店(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateBranch(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		Branch branch = gson.fromJson(json, Branch.class);
		
		String checkMessage = CommonValidationTools.checkBranch(branch);
		if (!checkMessage.equals("pass")) {
			message.setStatus(false);
			message.setMessage(checkMessage);
		} else {
			int result = branchDao.updateBranch(branch);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("分店修改成功！");
			} else {
				message.setStatus(false);
				message.setMessage("分店修改失败！");
				System.out.println("Error: " + result);
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteBranch
	 * @description 删除分店
	 * @param branchSid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteBranch(@RequestParam(value = "branchid", required = true) 
	    int branchSid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = branchDao.deleteBranch(branchSid);
		if (result > 0) {
			ArticleDAO articleDao = (ArticleDAO) context.getBean("ArticleDAO");
			articleDao.deleteDynamicArticleList(user.getSid());
			message.setStatus(true);
			message.setMessage("删除成功！");
		}else {
			message.setStatus(false);
			message.setMessage("删除失败！");
		}
		String response = gson.toJson(message);
		((ConfigurableApplicationContext)context).close();
		return response;
	}
	
	/**
	 * @title updateBranchPasswd
	 * @description 更新分店账号密码
	 * @param branchSid
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public String updateBranchPasswd(@RequestParam(value = "branchid", required = true) 
	    int branchSid, @RequestParam(value = "passwd", required = true) String 
	    password, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		password = passwordEncoder.encode(password);
		
		if (!CommonValidationTools.checkBranchSid(branchSid, user.getSid(), branchDao)) {
			message.setStatus(false);
			message.setMessage("非法操作！");
		} else {
			int result = branchDao.updateBranchPasswd(branchSid, password);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("操作成功！");
			}else {
				message.setStatus(false);
				message.setMessage("操作失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
}
