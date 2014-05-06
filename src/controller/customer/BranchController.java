package controller.customer;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import branch.Branch;
import branch.dao.BranchDAO;

/**
 * @Title: BranchController
 * @Description: 分店管理
 * @Company: Tuka
 * @author ben
 * @date 2014年5月5日
 */
@Controller
public class BranchController {
	/**
	 * @title getBranchClass
	 * @description 查询分店类别关联的分店详细信息
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branchclass", method = RequestMethod.GET)
	public String getBranchClass(@RequestParam(value = "classid", required = true) int 
			classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<Branch> branchList = branchDao.getBranchClassForCustomer(classid);
		model.addAttribute("branchList", branchList);
		return "BranchViews/branchclass";
	}
	
	/**
	 * @title getBranch
	 * @description 查询分店详细信息
	 * @param branchSid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branch", method = RequestMethod.GET)
	public String getBranch(@RequestParam(value = "branchid", required = true) int 
			branchSid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Branch branch = branchDao.getBranchForCustomer(branchSid);
		model.addAttribute("branch", branch);
		return "BranchViews/branch";
	}
}
