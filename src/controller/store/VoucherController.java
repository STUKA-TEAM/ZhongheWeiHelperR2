package controller.store;

import java.sql.Timestamp;
import java.util.List;

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
import voucher.Voucher;
import voucher.dao.VoucherDAO;

/**
 * @Title: VoucherController
 * @Description: 优惠券管理
 * @Company: tuka
 * @author ben
 * @date 2014年6月17日
 */
@Controller
@RequestMapping("/voucher")
public class VoucherController {
	/**
	 * @title getVoucherList
	 * @description 显示优惠券详细信息列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getVoucherList(@CookieValue(value = "appid", required = false) String 
			appid, Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		VoucherDAO voucherDao = (VoucherDAO) context.getBean("VoucherDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();

		if (appid == null) {
			return "redirect:/store/account";
		}
		else {
			if (appid.trim().equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			}
			else {
				List<Voucher> voucherList = voucherDao.getDetailedVoucherinfos(appid);
				model.addAttribute("voucherList", voucherList);
				return "VoucherViews/voucherList";
			}
		}
	}
	
	/**
	 * @title addVoucher
	 * @description 创建新的优惠券(第一步)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addVoucher(Model model){
		return "VoucherViews/insertVoucher";
	}
	
	/**
	 * @title editVoucher
	 * @description 编辑已有的优惠券(第一步)
	 * @param voucherid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editVoucher(@RequestParam(value = "voucherid", required = true) int 
			voucherid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoucherDAO voucherDao = (VoucherDAO) context.getBean("VoucherDAO");
		((ConfigurableApplicationContext)context).close();

        Voucher voucher = voucherDao.getVoucherContent(voucherid);
        model.addAttribute("voucher", voucher);
		return "VoucherViews/updateVoucher";
	}
	
	/**
	 * @title createVoucher
	 * @description 创建新的优惠券(第二步)
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createVoucher(@CookieValue(value = "appid", required = false) String 
			appid, @RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoucherDAO voucherDao = (VoucherDAO) context.getBean("VoucherDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int uid = ((User)auth.getPrincipal()).getSid();
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();

		if (appid == null) {
			message.setStatus(false);
			message.setMessage("请重新登录！");
		}
		else {
			if (appid.trim().equals("") || appInfoDao.checkAppExistsByUser(uid, 
					appid) == 0) {
				message.setStatus(false);
				message.setMessage("当前管理的公众账号无效，请先选择或关联微信公众账号!");
			}
			else {
				Voucher voucher = gson.fromJson(json, Voucher.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());
				voucher.setAppid(appid);
				voucher.setCreateTime(current);

				if (!CommonValidationTools.checkVoucher(voucher)) {
					message.setStatus(false);
					message.setMessage("优惠券信息不完整或有误！");
				} else {
					int result = voucherDao.insertVoucher(voucher);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("优惠券创建成功！");
					} else {
						message.setStatus(false);
						message.setMessage("优惠券创建失败！");
					}
				}
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateVoucher
	 * @description 编辑已有的优惠券(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateVoucher(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoucherDAO voucherDao = (VoucherDAO) context.getBean("VoucherDAO");
		((ConfigurableApplicationContext)context).close();

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		Voucher voucher = gson.fromJson(json, Voucher.class);

		if (!CommonValidationTools.checkVoucher(voucher)) {
			message.setStatus(false);
			message.setMessage("优惠券信息不完整或有误！");
		} else {
			int result = voucherDao.updateVoucher(voucher);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("优惠券修改保存成功！");
			} else {
				message.setStatus(false);
				message.setMessage("优惠券修改保存失败！");
			}
		}
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteVoucher
	 * @description 删除优惠券信息
	 * @param voucherid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteVoucher(@RequestParam(value="voucherid", required = true) int 
			voucherid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		VoucherDAO voucherDao = (VoucherDAO) context.getBean("VoucherDAO");
		((ConfigurableApplicationContext)context).close();

		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();

		int result = voucherDao.deleteVoucher(voucherid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("优惠券删除成功！");
		} else {
			message.setStatus(false);
			message.setMessage("优惠券删除失败！");
		}
		String response = gson.toJson(message);
		return response;
	}
}
