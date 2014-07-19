package controller.customer;

import java.util.List;

import health.BandingResult;
import health.BandingState;
import health.QueueResultBanding;
import health.QueueResultTry;
import health.Report;
import health.ReportList;
import health.ReportListItem;
import health.dao.HealthDAO;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

/**
 * @Title: HealthController
 * @Description: 医疗服务管理
 * @Company: Tuka
 * @author ben
 * @date 2014年7月16日
 */
@Controller
@RequestMapping("/health")
public class HealthController {
	@Autowired
	private Service service;
	@Autowired
	private Gson gson;
	private static final String namespace = "http://tempuri.org/";
	private static final String prefix = "http://";
	private static final String postfix = "/webservice.asmx";
	private static final String postfix_B = "/BT_QUEQUE_Info.asmx";
	
	/**
	 * @title bindWX
	 * @description 绑定用户微信和秉泰账号
	 * @param openid
	 * @param appid
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bindwx", method = RequestMethod.POST)
	public String bindWX(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "appid", required = true) String appid, 
			@RequestParam(value = "username", required = true) String username, 
			@RequestParam(value = "password", required = true) String password, 
			Model model){
		String host = "www.ukang.cc";
		String url = prefix + host + postfix;
		String method = "BindWX";
		Call call = null;
		BandingResult result = null;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + method);
			call.setOperationName(new QName(namespace, method));
			call.addParameter(new QName(namespace, "username"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "password"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "appid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "openid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			Object[] params = new Object[] {username, password, appid, openid};
			String json = (String) call.invoke(params);
			result = gson.fromJson(json, BandingResult.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (result == null || result.getBandingResult() == false) {
			return "HealthViews/bindingFail";
		} else {
			model.addAttribute("appid", appid);
			model.addAttribute("openid", openid);
			return "HealthViews/bindingSuccess";
		}
	}
	
	/**
	 * @title getReports
	 * @description 查询体检结果报表列表
	 * @param openid
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reportlist", method = RequestMethod.GET)
	public String getReports(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "appid", required = true) String appid, 
			Model model){
		String host = "www.ukang.cc";
		String url = prefix + host + postfix;
		String method = "IsBindWX";
		Call call = null;
		BandingState state = null;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + method);
			call.setOperationName(new QName(namespace, method));
			call.addParameter(new QName(namespace, "appid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "openid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			Object[] params = new Object[] {appid, openid};
			String json = (String) call.invoke(params);
			state = gson.fromJson(json, BandingState.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (state == null || state.getBandingState() == false) {
			model.addAttribute("appid", appid);
			model.addAttribute("openid", openid);
			return "HealthViews/bindingAccount";
		} else {
			method = "GetTJListByWX";
			List<ReportListItem> itemList = null;
			try {
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(url));
				call.setUseSOAPAction(true);
				call.setSOAPActionURI(namespace + method);
				call.setOperationName(new QName(namespace, method));
				call.addParameter(new QName(namespace, "appid"), XMLType.XSD_STRING,
						ParameterMode.IN);
				call.addParameter(new QName(namespace, "openid"), XMLType.XSD_STRING,
						ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
				Object[] params = new Object[] {appid, openid};
				String json = (String) call.invoke(params);
				itemList = gson.fromJson(json, ReportList.class).getDs();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			model.addAttribute("reportList", itemList);
			return "HealthViews/reportList";
		}
	}
	
	/**
	 * @title getReport
	 * @description 查询体检结果报表
	 * @param regid
	 * @param archnum
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String getReport(@RequestParam(value = "regid", required = true) String 
			regid, @RequestParam(value = "archnum", required = true) String archnum, 
			Model model){
		String host = "www.ukang.cc";
		String url = prefix + host + postfix;
		String method = "GetTJViewByWX";
		Call call = null;
		Report report = null;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + method);
			call.setOperationName(new QName(namespace, method));
			call.addParameter(new QName(namespace, "regid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "archnum"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			Object[] params = new Object[] {regid, archnum};
			String json = (String) call.invoke(params);
			report = gson.fromJson(json, Report.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		model.addAttribute("report", report);
		return "HealthViews/report";
	}
	
	/**
	 * @title getQueueState
	 * @description 查询某用户当前排检状态
	 * @param openid
	 * @param appid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queuestate", method = RequestMethod.GET)
	public String getQueueState(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "appid", required = true) String appid, 
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		HealthDAO healthDao = (HealthDAO) context.getBean("HealthDAO");
		((ConfigurableApplicationContext)context).close();
		
		String host = healthDao.getHostInfo(appid);
		String url = prefix + host + postfix_B;
		String method = "BT_QUEQUE_Info";
		Call call = null;
		QueueResultTry result = null;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + method);
			call.setOperationName(new QName(namespace, method));
			call.addParameter(new QName(namespace, "appid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "openid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			Object[] params = new Object[] {appid, openid};
			String json = (String) call.invoke(params);
			result = gson.fromJson(json, QueueResultTry.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (result == null || result.getBandingState() == false) {
			model.addAttribute("appid", appid);
			model.addAttribute("openid", openid);
			return "HealthViews/bindingArchivesNum";
		} else {
			model.addAttribute("result", result);
			return "HealthViews/queueState";
		}
	}
	
	/**
	 * @title bindTJC
	 * @description 绑定用户微信和档案号
	 * @param openid
	 * @param appid
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bindtjc", method = RequestMethod.POST)
	public String bindTJC(@RequestParam(value = "openid", required = true) String 
			openid, @RequestParam(value = "appid", required = true) String appid, 
			@RequestParam(value = "WX_Code", required = true) String code,
			Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		HealthDAO healthDao = (HealthDAO) context.getBean("HealthDAO");
		((ConfigurableApplicationContext)context).close();
		
		String host = healthDao.getHostInfo(appid);
		String url = prefix + host + postfix_B;
		String method = "BT_Info";
		Call call = null;
		QueueResultBanding result = null;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + method);
			call.setOperationName(new QName(namespace, method));
			call.addParameter(new QName(namespace, "WX_Code"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "appid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "openid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			Object[] params = new Object[] {code, appid, openid};
			String json = (String) call.invoke(params);
			result = gson.fromJson(json, QueueResultBanding.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (result == null || result.getBandingResult() == false) {
			return "HealthViews/bindingArchivesNumFail";
		} else {
			model.addAttribute("result", result);
			return "HealthViews/queueState";
		}
	}
}
