package controller.customer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import message.ResponseMessage;
import order.DishBranch;
import order.DishClass;
import order.dao.DishDAO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import register.dao.AppInfoDAO;
import website.ShareMessage;

import com.google.gson.Gson;

import branch.Branch;
import branch.dao.BranchDAO;

/**
 * @Title: DishController
 * @Description: 菜品管理
 * @Company: Tuka
 * @author ben
 * @date 2014年5月6日
 */
@Controller
@RequestMapping("/dish")
public class DishController {
	/**
	 * @title getBranchList
	 * @description 获取菜品分店信息列表
	 * @param openid
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branchlist", method = RequestMethod.GET)
	public String getBranchList(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "appid", required = true) String appid, 
			Model model ) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<Branch> branchList = branchDao.getBranchListForCustomer(appid);
		model.addAttribute("branchList", branchList);
		
		model.addAttribute("openid", openid);
		model.addAttribute("appid", appid);
		return "DishViews/branchList";
	}
	
	/**
	 * @title getStoreImage
	 * @description 根据albumid获取相册信息
	 * @param model
	 * @param albumid
	 * @param websiteid
	 * @return
	 */
	@RequestMapping(value = "/showStore", method = RequestMethod.GET)
	public String getStoreImage(Model model, @RequestParam(value = "branchid", required = true) int brachSid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branchDAO = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		Branch branch = branchDAO.getBranchForCustomer(brachSid);
        model.addAttribute("branch", branch);

		return "DishViews/showStore";
	}
	
	/**
	 * @title getBranch
	 * @description 获取某分店与用户相关的全部菜品详细信息
	 * @param openid
	 * @param branchSid
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branchMenu", method = RequestMethod.GET)
	public String getBranch(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "branchid", required = true) int branchSid,
			@RequestParam(value = "appid", required = true) String appid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<DishClass> classList = dishDao.getBasicClassinfos(appid);
		for (DishClass dishClass : classList) {
			int classid = dishClass.getClassid();
			dishClass.setDishCount(dishDao.getDishCount(classid, openid, branchSid));
		}
		if(classList.size() > 0){
			classList.get(0).setSelected(true);
		}
		model.addAttribute("classList", classList);
		
		if (classList.size() > 0) {
			DishClass dishClass = classList.get(0);
			int classid = dishClass.getClassid();
			List<DishBranch> dishList = dishDao.getDishClassForCustomer(classid, branchSid, openid);
			Collections.sort(dishList, new Comparator<DishBranch>() {
				@Override
				public int compare(DishBranch dish1, DishBranch dish2) {
					int recomNum1 = dish1.getRecomNum();
					int recomNum2 = dish2.getRecomNum();
					return recomNum1 < recomNum2 ? 1 : (recomNum1 == recomNum2 ? 0 : -1);
				}
			});
			model.addAttribute("dishList", dishList);
		}
		
		model.addAttribute("openid", openid);
		model.addAttribute("appid", appid);
		model.addAttribute("branchid", branchSid);
		return "DishViews/branchContainer";
	}
	
	/**
	 * @title getBranch
	 * @description 获取某分店与用户相关的某类别菜品详细信息
	 * @param openid
	 * @param branchSid
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/branchMenuByClass", method = RequestMethod.GET)
	public String getBranch(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "branchid", required = true) int branchSid,
			@RequestParam(value = "classid", required = true) int classid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<DishBranch> dishList = dishDao.getDishClassForCustomer(classid, branchSid, openid);
		Collections.sort(dishList, new Comparator<DishBranch>() {
			@Override
			public int compare(DishBranch dish1, DishBranch dish2) {
				int recomNum1 = dish1.getRecomNum();
				int recomNum2 = dish2.getRecomNum();
				return recomNum1 < recomNum2 ? 1 : (recomNum1 == recomNum2 ? 0 : -1);
			}
		});
		model.addAttribute("dishList", dishList);
		
		model.addAttribute("branchid", branchSid);
		model.addAttribute("openid", openid);
		return "DishViews/dishList";
	}
	
	/**
	 * @title insertDishOrder
	 * @description 插入新的菜品订单记录
	 * @param openid
	 * @param branchSid
	 * @param dishid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String insertDishOrder(@RequestParam(value = "openid", required = true) String
			openid, @RequestParam(value = "branchid", required = true) int branchSid, 
			@RequestParam(value = "dishid", required = true) int dishid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = dishDao.insertDishOrder(openid, branchSid, dishid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("操作成功！");
		} else {
			message.setStatus(false);
			message.setMessage("操作失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateDishOrder
	 * @description 更新菜品订单记录
	 * @param openid
	 * @param branchSid
	 * @param dishid
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateDishOrder(@RequestParam(value = "openid", required = true) 
	        String openid, @RequestParam(value = "branchid", required = true) int 
	        branchSid, @RequestParam(value = "dishid", required = true) int dishid, 
			@RequestParam(value = "status", required = true) int status, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		int result = 0;
		
		switch (status) {
		case 0:
			result = dishDao.updateDishOrderMinus(openid, branchSid, dishid);
			break;
        case 1:
        	result = dishDao.updateDishOrderPlus(openid, branchSid, dishid);
        	break;
		default:
			break;
		}
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("操作成功！");
		} else {
			message.setStatus(false);
			message.setMessage("操作失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteDishOrder
	 * @description 删除用户在某分店的某条菜品订单记录
	 * @param openid
	 * @param branchSid
	 * @param dishid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDishOrder(@RequestParam(value = "openid", required = true) String
			openid, @RequestParam(value = "branchid", required = true) int branchSid, 
			@RequestParam(value = "dishid", required = true) int dishid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = dishDao.deleteDishOrder(openid, branchSid, dishid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("操作成功！");
		} else {
			message.setStatus(false);
			message.setMessage("操作失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteDishOrder
	 * @description 删除用户在某分店的所有菜品订单记录
	 * @param openid
	 * @param branchSid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDishOrder(@RequestParam(value = "openid", required = true) String
			openid, @RequestParam(value = "branchid", required = true) int branchSid, 
			Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = dishDao.deleteDishOrder(openid, branchSid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("操作成功！");
		} else {
			message.setStatus(false);
			message.setMessage("操作失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateDishRecom
	 * @description 更新菜品推荐人数
	 * @param dishid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/like", method = RequestMethod.POST)
	@ResponseBody
	public String updateDishRecom(@RequestParam(value = "dishid", required = true) int 
			dishid, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = dishDao.updateDishRecom(dishid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("操作成功！");
		} else {
			message.setStatus(false);
			message.setMessage("操作失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title getMyOrderList
	 * @description 获取该应用下的个人菜单
	 * @param openid
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderlist", method = RequestMethod.GET)
	public String getMyOrderList(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "appid", required = true) String appid, 
			Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		BranchDAO branDao = (BranchDAO) context.getBean("BranchDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<Branch> branchList = branDao.getBranchListForOrder(appid, openid);
		model.addAttribute("branchList", branchList);
		
		model.addAttribute("openid", openid);
		model.addAttribute("appid", appid);
		return "DishViews/orderList";
	}
	
	/**
	 * @title getMyOrder
	 * @description 获取某分店的个人菜单
	 * @param openid
	 * @param branchSid
	 * @param appid
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String getMyOrder(@RequestParam(value = "openid", required = true) String
			openid, @RequestParam(value = "branchid", required = true) int branchSid, 
			@RequestParam(value = "appid", required = true) String appid, 
			@RequestParam(value = "type", required = true) String type, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		DishDAO dishDao = (DishDAO) context.getBean("DishDAO");
		BranchDAO branchDao = (BranchDAO) context.getBean("BranchDAO");
		AppInfoDAO appInfoDAO = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Branch branch = branchDao.getBranchForCustomer(branchSid);
		model.addAttribute("branch", branch);
		
		Map<Integer, List<DishBranch>> dishMap = dishDao.getDishMapForOrder(openid, branchSid);
		Map<Integer, String> dishClassMap = dishDao.getDishClassMapForOrder(dishMap.keySet());
		model.addAttribute("dishMap", dishMap);
		model.addAttribute("dishClassMap", dishClassMap);
		
		int sumPrice = 0;
		for (Map.Entry<Integer, List<DishBranch>> entry : dishMap.entrySet()) {
			for (DishBranch dishBranch : entry.getValue()) {
				sumPrice = sumPrice + dishBranch.getPrice()*dishBranch.getCount();
			}
		}
		model.addAttribute("sumPrice", sumPrice);
		
		model.addAttribute("openid", openid);
		model.addAttribute("branchid", branchSid);
		model.addAttribute("appid", appid);
		model.addAttribute("type", type);
		
		ShareMessage message = new ShareMessage();
		message.setWechatNumber(appInfoDAO.getWechatNumberByAppid(appid));
		message.setAppLink(message.getAppLink() + "customer/dish/order?branchid="+branchSid+"&openid="+openid+"&appid="+appid+"&type=other");
		if (branch.getImageList() != null && branch.getImageList().size()>0) {
			message.setImageLink(message.getImageLink() + branch.getImageList().get(0) + "_original.jpg");
		} else {
			message.setImageLink("");
		}
		message.setShareTitle("我在"+branch.getStoreName()+"点了菜，来看看吧");
		message.setShareContent("");
		model.addAttribute("message", message);
		return "DishViews/order";
	}
}
